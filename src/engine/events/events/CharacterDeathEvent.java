package engine.events.events;

import engine.entity.character.Character;
import engine.entity.special.DeathZone;
import engine.events.enums.EventType;
import engine.world.World;

public class CharacterDeathEvent extends Event {
	private static final long serialVersionUID = -473226592230829655L;

	public static final EventType EVENT_TYPE = EventType.DEATH;
	
	protected DeathZone deathZone;
	protected Character character;
	
	public CharacterDeathEvent(DeathZone deathZone, Character character) {
		super();
		
		priority.setEventType(EVENT_TYPE);
		
		this.deathZone = deathZone;
		this.character = character;
	}
	
	public DeathZone getDeathZone() {
		return deathZone;
	}
	
	public Character getCharacter() {
		return (Character)World.getInstance().getEntity(character.getGUID());
	}
}