package engine.events.events.replay;

import engine.events.enums.EventType;
import engine.events.events.Event;

public class RecordStartEvent extends Event {
	private static final long serialVersionUID = 1363898711012697251L;
	
	public static final EventType EVENT_TYPE = EventType.REPLAY;
	
	public RecordStartEvent() {
		super();
		
		priority.setEventType(EVENT_TYPE);
	}
}
