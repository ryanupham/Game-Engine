package engine.events;

import java.io.Serializable;

import engine.events.enums.EventType;
import engine.time.GlobalVirtualTime;
import engine.time.LocalTimeline;

public class Priority implements Serializable, Comparable<Priority> {
	private static final long serialVersionUID = 9181806419060526839L;
	
	public static final int DELAY_MILLIS = 0;
	public static final int DELAY_FRAMES = 1;
	public static final int DELAY_TICS = 2;

	protected long timestamp;
	
	protected EventType eventType = EventType.LAST;
	
	protected LocalTimeline anchor;
	
	public Priority() {
		this(LocalTimeline.getVirtualTime());
	}
	
	public Priority(LocalTimeline anchor) {
		this.anchor = anchor;
		
		timestamp = anchor.getElapsedTime();
	}
	
	public Priority(EventType eventType) {
		this();
		
		this.eventType = eventType;
	}
	
	public Priority(LocalTimeline anchor, EventType eventType) {
		this(anchor);
		
		this.eventType = eventType;
	}
	
	private long millisToSteps(long time) {
		return (long)(time * GlobalVirtualTime.BASE_STEP_SIZE * GlobalVirtualTime.TIME_PER_STEP);
	}
	
	private long framesToSteps(long time) {
		return time * GlobalVirtualTime.BASE_STEP_SIZE;
	}
	
	public Priority delay(long time, int delayType) {
		switch(delayType) {
			case DELAY_MILLIS:
				timestamp += millisToSteps(time);
				break;
			case DELAY_FRAMES:
				timestamp += framesToSteps(time);
				break;
			case DELAY_TICS:
				timestamp += time;
				break;
			default:
				return null;
		}
		
		return this;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	
	public int compareTo(Priority priority) {
		int difference = (int)(timestamp - priority.getTimestamp());
		
		if(difference == 0)
			return eventType.priority() - priority.getEventType().priority();
		
		return difference;
	}
	
	public boolean equals(Priority priority) {
		return compareTo(priority) == 0;
	}
}
