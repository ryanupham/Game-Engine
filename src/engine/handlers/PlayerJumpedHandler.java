package engine.handlers;

import engine.components.graphics.ColorDrawable;
import engine.entity.character.Player;
import engine.events.events.Event;
import engine.events.events.physics.PlayerJumpedEvent;
import engine.scripting.ScriptManager;

public class PlayerJumpedHandler extends EventHandler {
	private static final long serialVersionUID = -5649252774741553447L;

	private static final PlayerJumpedHandler INSTANCE = new PlayerJumpedHandler();
	
	private PlayerJumpedHandler() {
	}
	
	@Override
	public void handle(Event e) {
		PlayerJumpedEvent event = (PlayerJumpedEvent)e;
		Player p = event.getPlayer();
		
		ColorDrawable c = p.getComponent(ColorDrawable.class);
		
		if(c != null) {
			ScriptManager.loadScript("scripts/change_color.js");
			ScriptManager.bindArgument("color_component", c);
			
			ScriptManager.executeScript();
		}
	}
	
	public static PlayerJumpedHandler getInstance() {
		return INSTANCE;
	}
}
