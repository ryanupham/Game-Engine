package engine.coordinates;

import java.io.Serializable;

public class Point implements Serializable {
	private static final long serialVersionUID = -4978315088770863467L;
	
	public float x, y;
	
	public Point() {
		x = y = 0;
	}
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point copy() {
		return new Point(x, y);
	}
}
