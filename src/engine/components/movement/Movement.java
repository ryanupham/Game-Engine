package engine.components.movement;

import java.io.Serializable;

public class Movement implements Serializable {
	private static final long serialVersionUID = 8930053677985046612L;
	
	private float deltaX, deltaY;
	
	public Movement(float deltaX, float deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
	
	public float getDeltaX() {
		return deltaX;
	}
	
	public float getDeltaY() {
		return deltaY;
	}
}
