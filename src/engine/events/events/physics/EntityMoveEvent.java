package engine.events.events.physics;

import engine.components.movement.Movement;
import engine.entity.Entity;
import engine.events.enums.EventType;
import engine.events.events.Event;
import engine.world.World;

public class EntityMoveEvent extends Event {
	private static final long serialVersionUID = 2358085607260419607L;

	public static final EventType EVENT_TYPE = EventType.MOVEMENT;
	
	protected Entity e;
	protected Movement m;
	
	public EntityMoveEvent(Entity e, Movement m) {
		super();
		
		priority.setEventType(EVENT_TYPE);
		
		this.e = e;
		this.m = m;
	}
	
	public Entity getEntity() {
		return World.getInstance().getEntity(e.getGUID());
	}
	
	public Movement getMovement() {
		return m;
	}
}
