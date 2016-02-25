package engine.handlers;

import engine.components.movement.Movable;
import engine.entity.Entity;
import engine.events.events.Event;
import engine.events.events.physics.CollisionEvent;

public class CollisionHandler extends EventHandler {
	private static final long serialVersionUID = 3787859764792561338L;
	
	private static final CollisionHandler INSTANCE = new CollisionHandler();

	private CollisionHandler() {
	}
	
	@Override
	public void handle(Event e) {
		CollisionEvent event = (CollisionEvent)e;
		Entity entity1 = event.getEntity1();
		Entity entity2 = event.getEntity2();
		
		Movable m1 = null;
		Movable m2 = null;
		
		if(entity1 != null)
			m1 = entity1.getComponent(Movable.class);
		
		if(entity2 != null)
			m2 = entity2.getComponent(Movable.class);
		
		if(m1 != null)
			m1.handleCollision(entity2);
		
		if(m2 != null)
			m2.handleCollision(entity1);
	}
	
	public static CollisionHandler getInstance() {
		return INSTANCE;
	}
}
