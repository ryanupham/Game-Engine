package engine.events.events.physics;

import engine.entity.Entity;
import engine.entity.projectiles.Bullet;
import engine.events.enums.EventType;
import engine.events.events.Event;
import engine.world.World;

public class BulletCollisionEvent extends Event {
	private static final long serialVersionUID = 1497335781868960754L;

	public static final EventType EVENT_TYPE = EventType.BULLET;
	
	protected Bullet bullet;
	protected Entity entity;

	public BulletCollisionEvent(Bullet bullet, Entity entity) {
		super();
		
		priority.setEventType(EVENT_TYPE);
		
		this.bullet = bullet;
		this.entity = entity;
	}
	
	public Bullet getBullet() {
		return bullet;
	}
	
	public Entity getEntity() {
		return World.getInstance().getEntity(entity.getGUID());
	}
}
