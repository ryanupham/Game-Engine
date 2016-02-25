package engine.components.movement;

import engine.components.movement.paths.LinearMovementPath;
import engine.coordinates.Point;
import engine.entity.Entity;
import engine.entity.projectiles.Bullet;
import engine.events.EventManager;
import engine.events.events.physics.BulletCollisionEvent;

public class BulletMovable extends PathMovable {
	private static final long serialVersionUID = -5751372036158267502L;
	
	public BulletMovable(Bullet parent) {
		super(parent);
		
		LinearMovementPath path = new LinearMovementPath(parent.getPosition().getPosition(), new Point(parent.getPosition().getPosition().x, -100), parent.getPosition().getPosition(), 3.5f);
		path.stopAtEnd(true);
		setMovementPath(path);
		start();
	}
	
	@Override
	public void handleCollision(Entity entity) {
		super.handleCollision(entity);
		
		EventManager.raise(new BulletCollisionEvent((Bullet)getParent(), entity));
	}
}