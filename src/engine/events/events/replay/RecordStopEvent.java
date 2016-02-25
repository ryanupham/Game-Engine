package engine.events.events.replay;

import engine.events.enums.EventType;
import engine.events.events.Event;

public class RecordStopEvent extends Event {
	private static final long serialVersionUID = 1969644720197505572L;
	
	public static final EventType EVENT_TYPE = EventType.REPLAY;
	
	public RecordStopEvent() {
		super();
		
		priority.setEventType(EVENT_TYPE);
	}
}
