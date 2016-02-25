package engine.events.events.physics;

import engine.entity.Entity;
import engine.events.enums.EventType;
import engine.events.events.Event;
import engine.world.World;

public class CollisionEvent extends Event {
	private static final long serialVersionUID = 1497335781868960754L;

	public static final EventType EVENT_TYPE = EventType.COLLISION;
	
	protected Entity entity1, entity2;

	public CollisionEvent(Entity entity1, Entity entity2) {
		super();
		
		priority.setEventType(EVENT_TYPE);
		
		this.entity1 = entity1;
		this.entity2 = entity2;
	}
	
	public Entity getEntity1() {
		return World.getInstance().getEntity(entity1.getGUID());
	}
	
	public Entity getEntity2() {
		return World.getInstance().getEntity(entity2.getGUID());
	}
}
