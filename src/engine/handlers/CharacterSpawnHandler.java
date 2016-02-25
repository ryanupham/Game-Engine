package engine.handlers;

import engine.entity.character.Character;
import engine.entity.special.SpawnPoint;
import engine.events.events.CharacterSpawnEvent;
import engine.events.events.Event;
import engine.world.World;

public class CharacterSpawnHandler extends EventHandler {
	private static final long serialVersionUID = 2323614935943920603L;
	
	private static final CharacterSpawnHandler INSTANCE = new CharacterSpawnHandler();
	
	private CharacterSpawnHandler() {
	}
	
	@Override
	public void handle(Event e) {
		CharacterSpawnEvent event = (CharacterSpawnEvent)e;
		Character c = event.getCharacter();
		SpawnPoint spawnPoint = event.getSpawnPoint();
		
		World.getInstance().spawn(c, spawnPoint);
	}
	
	public static CharacterSpawnHandler getInstance() {
		return INSTANCE;
	}
}
