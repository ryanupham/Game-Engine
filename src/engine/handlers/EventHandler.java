package engine.handlers;

import java.io.Serializable;

import engine.events.events.Event;

public abstract class EventHandler implements Serializable {
	private static final long serialVersionUID = 4381373508251726181L;
	
	public abstract void handle(Event e);
}
