package engine.components.movement;

import engine.components.movement.paths.MovementPath;
import engine.components.spatial.Position;
import engine.components.spatial.Velocity;
import engine.coordinates.Point;
import engine.entity.Entity;
import engine.entity.character.Character;
import engine.entity.character.Player;
import engine.entity.obstacle.Obstacle;
import engine.entity.special.DeathZone;
import engine.events.EventManager;
import engine.events.events.CharacterDeathEvent;
import engine.events.events.physics.EntityMoveEvent;
import engine.scripting.ScriptManager;

public class PathMovable extends Movable {
	private static final long serialVersionUID = -1310682784846017654L;
	
	protected Position position;
	protected Velocity velocity;
	
	protected MovementPath path;
	
	protected boolean moving = false;

	public PathMovable(Entity parent) {
		super(parent);
		
		this.position = parent.getComponent(Position.class);
		this.velocity = parent.getComponent(Velocity.class);
	}

	public PathMovable(Entity parent, MovementPath path) {
		this(parent);
		
		this.path = path;
	}
	
	public void setMovementPath(MovementPath path) {
		this.path = path;
	}
	
	@Override
	public void move() {
		if(moving && path != null) {
			Point pos = path.getNextPosition(2);
			
			Movement m = new Movement(pos.x - position.getX(), pos.y - position.getY());
			
			EventManager.raise(new EntityMoveEvent(getParent(), m));
		}
	}
	
	public MovementPath getPath() {
		return path;
	}
	
	public void start() {
		moving = true;
	}
	
	public void stop() {
		moving = false;
	}
	
	@Override
	public void handleCollision(Entity entity) {
		if(entity instanceof Player && getParent() instanceof Obstacle) {
			ScriptManager.loadScript("scripts/move_platform.js");
			ScriptManager.bindArgument("movable", this);
			
			ScriptManager.executeScript();
		} else if(getParent() instanceof Character && entity instanceof DeathZone)
			EventManager.raise(new CharacterDeathEvent((DeathZone)entity, (Character)getParent()));
	}

	@Override
	public void handleMove(Movement m) {
		position.setX(position.getX() + m.getDeltaX());
		position.setY(position.getY() + m.getDeltaY());
	}

}
