package engine.events.events.physics;

import engine.entity.character.Player;
import engine.events.enums.EventType;
import engine.events.events.Event;
import engine.world.World;

public class PlayerJumpedEvent extends Event {
	private static final long serialVersionUID = -3820710387085401043L;
	
	public static final EventType EVENT_TYPE = EventType.MOVEMENT;

	protected Player player;

	public PlayerJumpedEvent(Player player) {
		super();
		
		priority.setEventType(EVENT_TYPE);
		
		this.player = player;
	}
	
	public Player getPlayer() {
		return (Player)World.getInstance().getEntity(player.getGUID());
	}
}