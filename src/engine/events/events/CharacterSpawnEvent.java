package engine.events.events;

import engine.entity.character.Character;
import engine.entity.special.SpawnPoint;
import engine.events.enums.EventType;
import engine.world.World;

public class CharacterSpawnEvent extends Event {
	private static final long serialVersionUID = -8365129034895562669L;

	public static final EventType EVENT_TYPE = EventType.SPAWN;
	
	protected SpawnPoint spawnPoint;
	protected Character character;
	
	public CharacterSpawnEvent(SpawnPoint spawnPoint, Character character) {
		super();
		
		priority.setEventType(EVENT_TYPE);
		
		this.spawnPoint = spawnPoint;
		this.character = character;
	}
	
	public SpawnPoint getSpawnPoint() {
		if(spawnPoint != null)
			return (SpawnPoint)World.getInstance().getEntity(spawnPoint.getGUID());
		
		return null;
	}
	
	public Character getCharacter() {
		return (Character)World.getInstance().getEntity(character.getGUID());
	}
}
