package engine.handlers;

import engine.entity.character.Character;
import engine.entity.special.DeathZone;
import engine.events.events.CharacterDeathEvent;
import engine.events.events.Event;

public class CharacterDeathHandler extends EventHandler {
	private static final long serialVersionUID = -5780912139660200205L;
	
	private static final CharacterDeathHandler INSTANCE = new CharacterDeathHandler();
	
	private CharacterDeathHandler() {
	}
	
	@Override
	public void handle(Event e) {
		CharacterDeathEvent event = (CharacterDeathEvent)e;
		Character c = event.getCharacter();
		DeathZone deathPoint = event.getDeathZone();
		
		if(c != null)
			c.die(deathPoint);
	}
	
	public static CharacterDeathHandler getInstance() {
		return INSTANCE;
	}
}
