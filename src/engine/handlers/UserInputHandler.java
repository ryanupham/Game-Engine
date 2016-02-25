package engine.handlers;

import engine.components.input.Input;
import engine.entity.character.Player;
import engine.events.EventManager;
import engine.events.enums.PlaybackSpeed;
import engine.events.events.Event;
import engine.events.events.UserInputEvent;
import engine.events.events.replay.RecordPlayEvent;
import engine.events.events.replay.RecordPlaySpeedEvent;
import engine.events.events.replay.RecordStartEvent;
import engine.events.events.replay.RecordStopEvent;

public class UserInputHandler extends EventHandler {
	private static final long serialVersionUID = -7715779200196316675L;
	
	private static final UserInputHandler INSTANCE = new UserInputHandler();
	
	private UserInputHandler() {
	}
	
	private void handlePlayback(Input in) {
		if(in.contains('1'))
			EventManager.raise(new RecordPlaySpeedEvent(PlaybackSpeed.HALF));
		else if(in.contains('2'))
			EventManager.raise(new RecordPlaySpeedEvent(PlaybackSpeed.NORMAL));
		else if(in.contains('3'))
			EventManager.raise(new RecordPlaySpeedEvent(PlaybackSpeed.DOUBLE));
	}
	
	private void handleRecording(Input in) {
		if(in.contains('r'))
			EventManager.raise(new RecordStartEvent());
		else if(in.contains('t'))
			EventManager.raise(new RecordStopEvent());
		else if(in.contains('y'))
			EventManager.raise(new RecordPlayEvent());
	}
	
	@Override
	public void handle(Event e) {
		UserInputEvent event = (UserInputEvent)e;
		Player c = event.getPlayer();
		Input in = event.getInput();
		
		if(EventManager.isPlayback())
			handlePlayback(in);
		else {
			c.getInput().setInput(in);
			
			handleRecording(in);
		}
	}
	
	public static UserInputHandler getInstance() {
		return INSTANCE;
	}
}
