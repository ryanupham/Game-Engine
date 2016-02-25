package engine.events.enums;

public enum EventType {
	REPLAY		(0),
	INPUT		(1),
	COLLISION	(2),
	DEATH		(3),
	SPAWN		(4),
	MOVEMENT	(5),
	BULLET		(6),
	LAST		(Integer.MAX_VALUE);
	
	private int priority;
	
	private EventType(int priority) {
		this.priority = priority;
	}
	
	public int priority() {
		return priority;
	}
}
