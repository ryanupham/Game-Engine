package engine.handlers;

import engine.events.EventManager;
import engine.events.events.Event;
import engine.events.events.replay.RecordPlayEvent;
import engine.events.events.replay.RecordPlaySpeedEvent;
import engine.events.events.replay.RecordStartEvent;
import engine.events.events.replay.RecordStopEvent;
import engine.time.GlobalVirtualTime;

public class PlaybackHandler extends EventHandler {
	private static final long serialVersionUID = -5953038647851551217L;
	
	private static final PlaybackHandler INSTANCE = new PlaybackHandler();
	
	private PlaybackHandler() {
	}
	
	private void handlePlaybackStart() {
		if(!EventManager.isPlayback() && EventManager.isRecorded()) {
			EventManager.startPlayback();
		}
	}
	
	private void handleChangePlaybackSpeed(RecordPlaySpeedEvent e) {
		if(EventManager.isPlayback()) {
			GlobalVirtualTime.getInstance().setScale(e.getSpeed().speed());
		}
	}
	
	private void handleRecordStart() {
		if(!EventManager.isRecording() && !EventManager.isPlayback()) {
			EventManager.startRecording();
		}
	}
	
	private void handleRecordStop() {
		if(EventManager.isRecording()) {
			EventManager.stopRecording();
			
			GlobalVirtualTime.getInstance().setScale(1);
		}
	}
	
	@Override
	public void handle(Event e) {
		if(e instanceof RecordPlayEvent) {
			handlePlaybackStart();
		} else if(e instanceof RecordPlaySpeedEvent) {
			handleChangePlaybackSpeed((RecordPlaySpeedEvent)e);
		} else if(e instanceof RecordStartEvent) {
			handleRecordStart();
		} else if(e instanceof RecordStopEvent) {
			handleRecordStop();
		}
	}
	
	public static PlaybackHandler getInstance() {
		return INSTANCE;
	}
}
