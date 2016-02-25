package engine.components.movement.paths;

import java.util.ArrayList;

import engine.coordinates.Point;
import engine.world.World;

public class PatternMovementPath extends MovementPath {
	private static final long serialVersionUID = -5625997103622423550L;
	
	protected Point[] movements;
	private transient ArrayList<Point> updateQueue;
	private transient ArrayList<Integer> updateQueueDir;
	
	protected int index = 0;
	
	protected LinearMovementPath curPath;

	public PatternMovementPath(float velocity, Point position, Point... movements) {
		super(velocity, position);
		
		this.movements = movements;
		updateQueue = new ArrayList<Point>();
		updateQueueDir = new ArrayList<Integer>();
		
		makeNextPath();
	}
	
	private void makeNextPath() {
		Point pnt = movements[index++ % movements.length];
		
		curPath = new LinearMovementPath(position.copy(), new Point(position.x + pnt.x, position.y + pnt.y), position, velocity);
		curPath.stopAtEnd(true);
	}

	@Override
	public Point getNextPosition(long elapsedTime) {
		Point pos = null;
		
		synchronized(World.getInstance()) {
			pos = curPath.getNextPosition(elapsedTime);
			
			if(updateQueueDir.size() > 0 && updateQueueDir.get(0) == index % movements.length) {
				//while(updateQueueDir.contains(index % movements.length)) {
					Point tmp = updateQueue.get(0);
					
					movements[updateQueueDir.get(0)].x += tmp.x;
					movements[updateQueueDir.get(0)].y += tmp.y;
					
					updateQueueDir.remove(0);
					updateQueue.remove(0);
				//}
			}
			
			if(curPath.isStopped())
				makeNextPath();
		}
		
		return pos;
	}
	
	public void updateMovement(int ind, Point movement) {
		updateQueueDir.add(ind);
		updateQueue.add(movement);
	}
}
