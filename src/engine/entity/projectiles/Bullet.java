package engine.entity.projectiles;

import engine.components.graphics.ColorDrawable;
import engine.components.movement.BulletMovable;
import engine.components.movement.PathMovable;
import engine.coordinates.Bounds;
import engine.entity.Entity;
import engine.entity.special.DeathZone;
import engine.world.World;

public class Bullet extends Entity {
	private static final long serialVersionUID = -5468408350751705913L;
	
	protected ColorDrawable drawable;
	protected PathMovable movable;
	protected DeathZone deathZone;
	
	public Bullet(World world, Bounds bounds) {
		super(world, bounds);
		
		drawable = new ColorDrawable(this, 255, 255, 255);
		components.add(drawable);
		
		BulletMovable movable = new BulletMovable(this);
		components.add(movable);
		
		deathZone = new DeathZone(world, this);
		World.getInstance().add(deathZone);
	}
}
