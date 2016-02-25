package engine.components.graphics;

import processing.core.PApplet;
import engine.components.spatial.Position;
import engine.entity.Entity;

public class ColorDrawable extends Drawable {
	private static final long serialVersionUID = 1101179343919476211L;
	
	protected int color;
	protected Position position;
	
	public ColorDrawable(Entity parent) {
		super(parent);
		
		position = parent.getComponent(Position.class);
	}
	
	public ColorDrawable(Entity parent, int color) {
		this(parent);
		
		setColor(color);
	}
	
	public ColorDrawable(Entity parent, int r, int g, int b) {
		this(parent);
		
		setColor(r, g, b);
	}

	@Override
	public void draw(PApplet screen) {
		screen.fill(color, 255);
		screen.rect(position.getX(), position.getY(), position.getWidth(), position.getHeight());
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setColor(int r, int g, int b) {
		color = color(r, g, b);
	}
	
	public int getColor() {
		return color;
	}
	
	private int color(int r, int g, int b) {
		return -16777216 + (r << 16) + (g << 8) + b;
	}

}
