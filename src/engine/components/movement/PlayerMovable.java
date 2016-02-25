package engine.components.movement;

import java.util.ArrayList;

import engine.components.input.PlayerInput;
import engine.components.spatial.Position;
import engine.components.spatial.Velocity;
import engine.coordinates.Bounds;
import engine.entity.Entity;
import engine.entity.character.Character;
import engine.entity.character.Player;
import engine.entity.obstacle.Floor;
import engine.entity.obstacle.Obstacle;
import engine.entity.obstacle.Platform;
import engine.entity.special.DeathZone;
import engine.events.EventManager;
import engine.events.events.CharacterDeathEvent;
import engine.events.events.physics.CollisionEvent;
import engine.events.events.physics.EntityMoveEvent;
import engine.events.events.physics.PlayerJumpedEvent;
import engine.world.World;

public class PlayerMovable extends Movable {
	private static final long serialVersionUID = 4965132720649539791L;
	
	protected PlayerInput input;
	protected Position position;
	protected Velocity velocity;
	
	public PlayerMovable(Entity parent, PlayerInput input) {
		super(parent);
		
		this.input = input;
		this.position = parent.getComponent(Position.class);
		this.velocity = parent.getComponent(Velocity.class);
	}
	
	protected void jump() {
		if(velocity.y == 0) {
			velocity.y = -40;
			
			EventManager.raise(new PlayerJumpedEvent((Player)getParent()));
		}
	}
	
	@Override
	public void move() {
		if(input.getJump())
			jump();
		
		if(input.getRight())
			velocity.x = 10;
		
		if(input.getLeft())
			velocity.x = -10;
		
		if(!(input.getRight() || input.getLeft()))
			velocity.x = 0;
		
		velocity.y += World.GRAVITY;
		
		for(int i = 0; i < 10; i++) {
			Movement m = new Movement(velocity.x / 10f, velocity.y / 10f);
			
			EventManager.raise(new EntityMoveEvent(getParent(), m));
		}
	}
	
	@Override
	public void handleMove(Movement m) {
		position.setX(position.getX() + m.getDeltaX());
		position.setY(position.getY() + m.getDeltaY());
		
		checkCollisions();
	}
	
	protected void checkCollisions() {
		if(position.getX() < 0) { //raise collision event, handle elsewhere
			position.setX(0);
			velocity.x = 0;
		}
		
		if(position.getY() < 0) {
			position.setY(0);
			velocity.y = 0.01f;
		}
		
		if(position.getX() + position.getWidth() > World.getInstance().getWidth()) {
			position.setX(World.getInstance().getWidth() - position.getWidth());
			velocity.x = 0;
		}
		
		if(position.getY() + position.getHeight() > World.getInstance().getHeight()) {
			position.setY(World.getInstance().getHeight() - position.getHeight());
			velocity.y = 0;
		}
		
		Bounds bounds = position.getBounds();
		
		ArrayList<DeathZone> deathZones = World.getInstance().getDeathZones();
		
		for(DeathZone deathZone : deathZones)
			if(bounds.intersects(deathZone.getPosition().getBounds())) {
				EventManager.raise(new CharacterDeathEvent(deathZone, (Character)(this.getParent())));
				
				return;
			}
		
		ArrayList<Obstacle> obstacleBounds = World.getInstance().getEntities(Obstacle.class);
		
		for(Entity e : obstacleBounds)
			if(bounds.intersects(e.getPosition().getBounds()))
				EventManager.raise(new CollisionEvent(this.getParent(), e));
	}
	
	public void handleCollision(Entity e) {
		Bounds bounds = position.getBounds();
		
		if(e instanceof Platform) {
			if(velocity.y >= 0) {
				Bounds box = e.getPosition().getBounds();
				
				if(bounds.intersects(box)) {
					float left = Math.abs(box.x1 - bounds.x2);
					float right = Math.abs(bounds.x1 - box.x2);
					float top = Math.abs(box.y1 - bounds.y2);
					float bottom = Math.abs(bounds.y1 - box.y2);
					
					//uncomment to prevent walking through side of platform
					if(left < position.getWidth() && (left < right && left < top && left < bottom)) {
						//x = box.x1 - width;
						//xSpeed = 0;
					} else if(right < position.getWidth() && (right < top && right < bottom)) {
						//x = box.x2;
						//xSpeed = 0;
					} else if(top < 10 && (top < bottom)) {
						position.setY(box.y1 - position.getHeight());
						velocity.y = 0;
					}
				}
			}
		} else if(e instanceof Floor) {
			Bounds box = e.getPosition().getBounds();
			
			if(bounds.intersects(box)) {
				float left = Math.abs(box.x1 - bounds.x2);
				float right = Math.abs(bounds.x1 - box.x2);
				float top = Math.abs(box.y1 - bounds.y2);
				float bottom = Math.abs(bounds.y1 - box.y2);
				
				if(left < position.getWidth() && (left < right && left < top && left < bottom)) {
					position.setX(box.x1 - position.getWidth());
					velocity.x = 0;
				} else if(right < position.getWidth() && (right < top && right < bottom)) {
					position.setX(box.x2);
					velocity.x = 0;
				} else if(top < position.getHeight() && (top < bottom)) {
					position.setY(box.y1 - position.getHeight());
					velocity.y = 0;
				} else {
					position.setY(box.y2);
					velocity.y = 0.01f;
				}
			}
		}
	}

}
