package engine.events;

import java.util.HashMap;
import java.util.LinkedList;

import engine.events.events.Event;
import engine.events.events.UserInputEvent;
import engine.events.events.replay.RecordPlaySpeedEvent;
import engine.handlers.EventHandler;
import engine.time.GlobalVirtualTime;
import engine.world.World;

public final class EventManager {
	private static EventList events = new EventList();
	private static HashMap<Class<? extends Event>, EventHandler> handlers = new HashMap<Class<? extends Event>, EventHandler>();

	private static boolean recording = false;
	private static boolean recorded = false;
	private static boolean playback = false;
	private static LinkedList<Event> recordedEvents;
	private static long GVTStart;
	private static long GVTRestore;
	//private static long GRTStart;
	
	private EventManager() {
	}
	
	public static void raise(Event event) {
		if(playback) {
			if((event instanceof UserInputEvent) || (event instanceof RecordPlaySpeedEvent)) { //must make this less ugly if I have time
				EventHandler handler = handlers.get(event.getClass());
				
				handler.handle(event);
			}
		} else
			events.enqueue(event);
	}
	
	public static <T extends Event> void register(EventHandler handler, Class<T> type) {
		handlers.put(type, handler);
	}
	
	public static void handleNext(Priority priority) {
		if(playback) {
			if(recordedEvents.size() > 0 && 
					recordedEvents.element().getPriority().compareTo(priority) <= 0) {
				Event e = recordedEvents.remove();
				EventHandler handler = handlers.get(e.getClass());
				
				handler.handle(e);
			}
			
			if(recordedEvents.size() == 0)
				stopPlayback();
		} else {
			if(!events.hasPriority(priority))
				return;
			
			Event e = events.getNext();
			EventHandler handler = handlers.get(e.getClass());
			
			handler.handle(e);
			
			if(recording)
				if(!(e instanceof RecordPlaySpeedEvent))
					recordedEvents.add(e);
			
			events.dequeue(e);
		}
	}
	
	public static void handleAll(Priority priority) {
		if(playback)
			while(recordedEvents.size() > 0 && 
					recordedEvents.element().getPriority().compareTo(priority) <= 0)
				handleNext(priority);
		else
			while(!playback && events.hasPriority(priority))
				handleNext(priority);
	}
	
	public static void startRecording() {
		if(recording)
			return;
		
		World.startRecording();
		
		GVTStart = GlobalVirtualTime.getInstance().getElapsedTime();
		//GRTStart = GlobalTimeline.getInstance().getElapsedTime();
		
		recordedEvents = new LinkedList<Event>();
		recording = true;
		recorded = false;
		
		World.startRecording();
	}
	
	public static void stopRecording() {
		if(!recording)
			return;
		
		recording = false;
		recorded = true;
		
		World.stopRecording();
	}
	
	public static void startPlayback() {
		if(recording || !recorded || playback)
			return;
		
		playback = true;
		recorded = false;
		
		GVTRestore = GlobalVirtualTime.getInstance().getElapsedTime();
		GlobalVirtualTime.getInstance().set(GVTStart);
		
		World.startPlayback();
	}
	
	public static void stopPlayback() {
		if(!playback)
			return;
		
		GlobalVirtualTime.getInstance().set(GVTRestore);
		
		playback = false;
		
		World.stopPlayback();
	}
	
	public static boolean isRecording() {
		return recording;
	}
	
	public static boolean isRecorded() {
		return recorded;
	}
	
	public static boolean isPlayback() {
		return playback;
	}
}
