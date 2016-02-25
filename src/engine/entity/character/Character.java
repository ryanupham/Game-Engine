package engine.entity.character;

import engine.components.movement.Movable;
import engine.components.spatial.Velocity;
import engine.coordinates.Bounds;
import engine.entity.Entity;
import engine.entity.special.DeathZone;
import engine.world.World;

public abstract class Character extends Entity {
	private static final long serialVersionUID = 3817852321542099450L;
	
	protected float jumpSpeed, gravity;
	protected boolean falling;
	protected Velocity velocity;
	protected Movable movable;
	
	public Character(World world) {
		super(world);
		
		velocity = new Velocity(this);
		components.add(velocity);
	}
	
	public Character(World world, Bounds bounds) {
		super(world, bounds);
		
		velocity = new Velocity(this);
		components.add(velocity);
	}

	public Character(World world, float x, float y, float width, float height) {
		super(world, x, y, width, height);
		
		velocity = new Velocity(this);
		components.add(velocity);
	}
	
	public abstract void die(DeathZone deathPoint);
}
