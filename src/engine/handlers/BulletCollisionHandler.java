package engine.handlers;

import engine.components.movement.BulletMovable;
import engine.entity.projectiles.Bullet;
import engine.events.events.Event;
import engine.events.events.physics.BulletCollisionEvent;
import engine.world.World;

public class BulletCollisionHandler extends EventHandler {
	private static final long serialVersionUID = 3787859764792561338L;
	
	private static final BulletCollisionHandler INSTANCE = new BulletCollisionHandler();

	private BulletCollisionHandler() {
	}
	
	@Override
	public void handle(Event e) {
		BulletCollisionEvent event = (BulletCollisionEvent)e;
		
		Bullet b = (Bullet)World.getInstance().getEntity(event.getBullet().getGUID());
		
		//b.removeComponent(BulletMovable.class);
		b.getComponent(BulletMovable.class).stop();
		b.getPosition().setPosition(-50 - (int)(Math.random() * 500), 0);
		
		b.disable();
	}
	
	public static BulletCollisionHandler getInstance() {
		return INSTANCE;
	}
}