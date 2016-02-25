package engine.entity.obstacle;

import engine.components.graphics.ColorDrawable;
import engine.coordinates.Bounds;
import engine.world.World;

public class Platform extends Obstacle {
	private static final long serialVersionUID = 3838891161146681569L;
	
	ColorDrawable drawable;

	private void initializeComponents() {
		drawable = new ColorDrawable(this);
		components.add(drawable);
	}
	
	public Platform(World world) {
		super(world);
		
		initializeComponents();
	}
	
	public Platform(World world, Bounds bounds) {
		super(world, bounds);
		
		initializeComponents();
	}
	
	public Platform(World world, float x, float y, float width, float height) {
		super(world, x, y, width, height);
		
		initializeComponents();
	}
}
