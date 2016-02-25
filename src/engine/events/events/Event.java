package engine.events.events;

import java.io.Serializable;

import engine.events.Priority;

public abstract class Event implements Serializable {
	private static final long serialVersionUID = -8661324118666782146L;
	
	protected Priority priority;
	
	public Event() {
		priority = new Priority();
	}
	
	public Priority getPriority() {
		return priority;
	}
}
