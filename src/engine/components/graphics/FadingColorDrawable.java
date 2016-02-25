package engine.components.graphics;

import java.util.ArrayList;

import processing.core.PApplet;
import engine.components.spatial.Position;
import engine.entity.Entity;

public class FadingColorDrawable extends Drawable {
	private static final long serialVersionUID = -7444537515134870854L;

	protected ArrayList<Integer> colors;
	
	protected float curInd;
	protected long fadeTime;
	protected long lastDrawTime;

	protected Position position;
	
	public FadingColorDrawable(Entity parent) {
		super(parent);
		
		position = parent.getComponent(Position.class);
		
		colors = null;
		curInd = 0;
		fadeTime = 0;
		lastDrawTime = System.currentTimeMillis(); //might need to change to nanotime
	}

	public FadingColorDrawable(Entity parent, ArrayList<Integer> colors,
			long timeBetweenColors) {
		this(parent);
		
		setColors(colors);
		fadeTime = timeBetweenColors;
	}
	
	public void setColors(ArrayList<Integer> colors) {
		this.colors = colors;
		
		curInd = 0;
	}
	
	public void setFadeTime(long timeBetweenColors) {
		fadeTime = timeBetweenColors;
	}
	
	private int getFadeColor() {
		if(colors == null)
			return 0;
		
		if(colors.size() == 0)
			return 0;
		
		if(colors.size() == 1)
			return colors.get(0);
		
		int ind1 = (int)Math.floor(curInd) % colors.size();
		int ind2 = (int)Math.ceil(curInd) % colors.size();
		
		int col1 = colors.get(ind1);
		int col2 = colors.get(ind2);

		float p1 = curInd - (float)Math.floor(curInd);
		float p2 = 1 - p1;

		float r = (getr(col1) * p2) + (getr(col2) * p1);
		float g = (getg(col1) * p2) + (getg(col2) * p1);
		float b = (getb(col1) * p2) + (getb(col2) * p1);
		
		return color(Math.round(r), Math.round(g), Math.round(b));
	}
	
	private int color(int r, int g, int b) {
		return -16777216 + (r << 16) + (g << 8) + b;
	}
	
	private int getr(int color) {
		return ((color + 16777216) >> 16) & 0xFF;
	}
	
	private int getg(int color) {
		return ((color + 16777216) >> 8) & 0xFF;
	}
	
	private int getb(int color) {
		return (color + 16777216) & 0xFF;
	}
	
	public int getCurrentColor() {
		long elapsedTime = System.currentTimeMillis() - lastDrawTime;
		lastDrawTime = System.currentTimeMillis();
		
		curInd += elapsedTime * 1f / fadeTime;
		
		return getFadeColor();
	}
	
	@Override
	public void draw(PApplet screen) {
		screen.fill(getCurrentColor(), 255);
		screen.rect(position.getX(), position.getY(), position.getWidth(), position.getHeight());
	}

}
