package engine.handlers;

import engine.components.movement.Movable;
import engine.components.movement.Movement;
import engine.entity.Entity;
import engine.events.events.Event;
import engine.events.events.physics.EntityMoveEvent;

public class EntityMoveHandler extends EventHandler {
	private static final long serialVersionUID = 1988721488844364238L;
	
	private static final EntityMoveHandler INSTANCE = new EntityMoveHandler();
	
	private EntityMoveHandler() {
	}
	
	@Override
	public void handle(Event e) {
		EntityMoveEvent event = (EntityMoveEvent)e;
		Entity entity = event.getEntity();
		Movement m = event.getMovement();
		
		if(entity != null) {
			Movable movable = entity.getComponent(Movable.class);
			
			if(movable != null)
				movable.handleMove(m);
		}
	}
	
	public static EntityMoveHandler getInstance() {
		return INSTANCE;
	}
}
