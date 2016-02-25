package engine.coordinates;

import java.io.Serializable;

public class Bounds implements Serializable {
	private static final long serialVersionUID = 4883098621379735996L;
	
	public float x1, y1, x2, y2;
	
	public Bounds() {
		x1 = y1 = x2 = y2 = 0;
	}
	
	public Bounds(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public boolean intersects(Bounds box) {
		return (x1 < box.x2 && x2 > box.x1 && y1 < box.y2 && y2 > box.y1);
	}
	
	public float getWidth() {
		return Math.abs(x2 - x1);
	}
	
	public float getHeight() {
		return Math.abs(y2 - y1);
	}
}
