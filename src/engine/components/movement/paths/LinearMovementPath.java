package engine.components.movement.paths;

import engine.coordinates.Point;

public class LinearMovementPath extends MovementPath {
	private static final long serialVersionUID = 5478231362187222515L;

	protected Point startPoint, endPoint;
	protected Point curPoint;
	
	private float deltaX, deltaY;
	
	protected boolean toEnd = true;
	protected boolean stopAtEnd = false;
	protected boolean stopped = false;
	
	private float distance(float x1, float y1, float x2, float y2) {
		float x = x1 - x2;
		float y = y1 - y2;
		
		return (float) Math.sqrt(x * x + y * y);
	}

	public LinearMovementPath(Point startPoint, Point endPoint,
			Point position, float velocity) {
		super(velocity, position);
		
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		
		float distance = distance(startPoint.x, startPoint.y,
				endPoint.x, endPoint.y);
		
		deltaX = (endPoint.x - startPoint.x) / distance;
		deltaY = (endPoint.y - startPoint.y) / distance;
	}
	
	public Point getNextPosition(float dist) {
		Point dest = toEnd ? endPoint : startPoint;
		
		float distRemaining = distance(position.x, position.y,
				dest.x, dest.y);
		
		if(distRemaining < dist) {
			position.x = dest.x;
			position.y = dest.y;
			
			if(stopAtEnd) {
				stopped = true;
				
				return position.copy();
			}
			
			toEnd = !toEnd;
			
			return getNextPosition(dist - distRemaining);
		}
		
		position.x = (position.x + deltaX * (toEnd ? dist : -dist));
		position.y = (position.y + deltaY * (toEnd ? dist : -dist));
		
		return position.copy();
	}
	
	public void stopAtEnd(boolean stop) {
		stopAtEnd = stop;
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	@Override
	public Point getNextPosition(long elapsedTime) {
		if(stopped)
			return position.copy();
		
		return getNextPosition(velocity * elapsedTime);
	}
}
