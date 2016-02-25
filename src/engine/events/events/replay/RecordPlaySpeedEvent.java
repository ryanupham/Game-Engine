package engine.events.events.replay;

import engine.events.enums.EventType;
import engine.events.enums.PlaybackSpeed;
import engine.events.events.Event;

public class RecordPlaySpeedEvent extends Event {
	private static final long serialVersionUID = 3939510394739411637L;
	
	public static final EventType EVENT_TYPE = EventType.REPLAY;
	
	protected PlaybackSpeed speed;
	
	public RecordPlaySpeedEvent(PlaybackSpeed speed) {
		super();
		
		priority.setEventType(EVENT_TYPE);
		
		this.speed = speed;
	}
	
	public PlaybackSpeed getSpeed() {
		return speed;
	}
}
