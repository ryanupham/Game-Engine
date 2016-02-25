package engine.events.events.replay;

import engine.events.enums.EventType;
import engine.events.events.Event;

public class RecordPlayEvent extends Event {
	private static final long serialVersionUID = -6265102639172547059L;

	public static final EventType EVENT_TYPE = EventType.REPLAY;
	
	public RecordPlayEvent() {
		super();
		
		priority.setEventType(EVENT_TYPE);
	}
}
