package engine.entity.obstacle;

import engine.components.graphics.ColorDrawable;
import engine.components.graphics.Drawable;
import engine.coordinates.Bounds;
import engine.world.World;

public class Floor extends Obstacle {
	private static final long serialVersionUID = 2917338842927967134L;
	
	ColorDrawable drawable;
	
	private void initializeComponents() {
		drawable = new ColorDrawable(this);
		components.add(drawable);
	}
	
	public Floor(World world) {
		super(world);
		
		initializeComponents();
	}
	
	public Floor(World world, Bounds bounds) {
		super(world, bounds);
		
		initializeComponents();
	}
	
	public Floor(World world, float x, float y, float width, float height) {
		super(world, x, y, width, height);
		
		initializeComponents();
	}
	
	public void setDrawable(Drawable drawable) {
		for(int i = 0; i < components.size(); i++)
			if(components.get(i) instanceof Drawable) {
				components.remove(i);
				i--;
			}
		
		components.add(drawable);
	}
}
