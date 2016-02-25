package engine.components.movement.paths;

import java.io.Serializable;

import engine.coordinates.Point;

public abstract class MovementPath implements Serializable {
	private static final long serialVersionUID = -8432218092177222933L;
	
	protected float velocity;
	protected Point position;
	
	public MovementPath(float velocity, Point position) {
		this.velocity = velocity;
		this.position = position;
	}
	
	public abstract Point getNextPosition(long elapsedTime);
}
