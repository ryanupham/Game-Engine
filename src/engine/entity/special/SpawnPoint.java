package engine.entity.special;

import engine.coordinates.Point;
import engine.entity.Entity;
import engine.world.World;

public class SpawnPoint extends Entity {
	private static final long serialVersionUID = 6283732418298848387L;

	public SpawnPoint(World world) {
		super(world);

		position.setHeight(0);
		position.setWidth(0);
	}

	public SpawnPoint(World world, Point position) {
		this(world);
		
		this.position.setX(position.x);
		this.position.setY(position.y);
	}
	
	public SpawnPoint(World world, float x, float y) {
		this(world, new Point(x, y));
	}
}
