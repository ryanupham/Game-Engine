package engine.events;

import java.io.Serializable;
import java.util.LinkedList;

import engine.events.events.Event;

public class EventList implements Serializable {
	private static final long serialVersionUID = -2256977301278739807L;
	
	LinkedList<Event> events;
	
	public EventList() {
		events = new LinkedList<Event>();
	}
	
	public Event getNext() {
		Priority minPriority = null;
		Event minEvent = null;
		
		synchronized(events) {
			for(Event e : events) {
				if(minPriority == null) {
					minPriority = e.getPriority();
					minEvent = e;
					
					continue;
				}
				
				if(e.getPriority().compareTo(minPriority) < 0) {
					minPriority = e.getPriority();
					minEvent = e;
				}
			}
		}
		
		return minEvent;
	}
	
	public void enqueue(Event e) {
		synchronized(events) {
			events.add(e);
		}
	}
	
	public void dequeue(Event e) {
		synchronized(events) {
			events.remove(e);
		}
	}
	
	public Event dequeue() {
		Event e = getNext();
		
		synchronized(events) {
			events.remove(e);
		}
		
		return e;
	}
	
	public Priority getLowestPriority() {
		Event next = getNext();
		
		if(next != null)
			return next.getPriority();
		
		return null;
	}
	
	public boolean hasPriority(Priority p) {
		Priority lowest = getLowestPriority();
		
		if(lowest != null)
			return getLowestPriority().compareTo(p) <= 0;
		
		return false;
	}
}
