package engine.components.movement;

import engine.components.input.PlayerInput;
import engine.components.spatial.Position;
import engine.components.spatial.Velocity;
import engine.coordinates.Bounds;
import engine.entity.Entity;
import engine.entity.character.Character;
import engine.entity.character.Player;
import engine.entity.projectiles.Bullet;
import engine.entity.special.DeathZone;
import engine.events.EventManager;
import engine.events.events.CharacterDeathEvent;
import engine.events.events.physics.EntityMoveEvent;
import engine.time.GlobalVirtualTime;
import engine.world.World;

public class ShipMovable extends Movable {
	private static final long serialVersionUID = -8148897693085500581L;
	
	protected PlayerInput input;
	protected Position position;
	protected Velocity velocity;
	
	protected long lastShoot = 0;
	
	public ShipMovable(Player parent, PlayerInput input) {
		super(parent);
		
		this.input = input;
		this.position = parent.getComponent(Position.class);
		this.velocity = parent.getComponent(Velocity.class);
	}
	
	@Override
	public void move() {
		if(input.getJump())
			if(lastShoot + 30 * GlobalVirtualTime.BASE_STEP_SIZE <= GlobalVirtualTime.getInstance().getElapsedTime()) {
				lastShoot = GlobalVirtualTime.getInstance().getElapsedTime();
				
				float mx = position.getX() + position.getWidth() / 2;
				
				Bullet bullet = new Bullet(World.getInstance(), new Bounds(mx - 3, position.getY() - position.getHeight() / 2, mx + 3, position.getY() - position.getHeight() / 2 - 20));
				World.getInstance().add(bullet);
			}
		
		if(input.getRight())
			velocity.x = 10;
		
		if(input.getLeft())
			velocity.x = -10;
		
		if(!(input.getRight() || input.getLeft()))
			velocity.x = 0;
		
		for(int i = 0; i < 10; i++) {
			Movement m = new Movement(velocity.x / 10f, velocity.y / 10f);
			
			EventManager.raise(new EntityMoveEvent(getParent(), m));
		}
	}
	
	@Override
	public void handleMove(Movement m) {
		position.setX(position.getX() + m.getDeltaX());
		position.setY(position.getY() + m.getDeltaY());
		
		checkBounds();
	}
	
	protected void checkBounds() {
		if(position.getX() < 0) {
			position.setX(0);
			velocity.x = 0;
		}
		
		if(position.getY() < 0)
			position.setY(0);
		
		if(position.getX() + position.getWidth() > World.getInstance().getWidth()) {
			position.setX(World.getInstance().getWidth() - position.getWidth());
			velocity.x = 0;
		}
		
		if(position.getY() + position.getHeight() > World.getInstance().getHeight())
			position.setY(World.getInstance().getHeight() - position.getHeight());
	}
	
	public void handleCollision(Entity e) {
		if(e instanceof DeathZone)
			EventManager.raise(new CharacterDeathEvent((DeathZone)e, (Character)getParent()));
	}
}
