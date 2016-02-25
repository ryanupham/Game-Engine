package engine.events.events;

import engine.components.input.Input;
import engine.entity.character.Player;
import engine.events.enums.EventType;
import engine.world.World;

public class UserInputEvent extends Event {
	private static final long serialVersionUID = 2322768368782315775L;

	public static final EventType EVENT_TYPE = EventType.INPUT;
	
	protected Input input;
	protected Player player;
	
	public UserInputEvent(Input input, Player player) {
		super();
		
		priority.setEventType(EVENT_TYPE);
		
		this.input = input;
		this.player = player;
	}
	
	public Input getInput() {
		return input;
	}
	
	public Player getPlayer() {
		return (Player)World.getInstance().getEntity(player.getGUID());
	}
}
