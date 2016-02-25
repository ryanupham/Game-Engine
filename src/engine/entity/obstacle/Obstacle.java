package engine.entity.obstacle;

import engine.coordinates.Bounds;
import engine.entity.Entity;
import engine.world.World;

public abstract class Obstacle extends Entity {
	private static final long serialVersionUID = 2082471450251994697L;

	public Obstacle(World world) {
		super(world);
	}
	
	public Obstacle(World world, Bounds bounds) {
		super(world, bounds);
	}
	
	public Obstacle(World world, float x, float y, float width, float height) {
		super(world, x, y, width, height);
	}
}
