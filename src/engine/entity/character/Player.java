package engine.entity.character;

import engine.components.graphics.ColorDrawable;
import engine.components.input.Input;
import engine.components.input.PlayerInput;
import engine.components.movement.PlayerMovable;
import engine.coordinates.Bounds;
import engine.entity.special.DeathZone;
import engine.entity.special.SpawnPoint;
import engine.events.EventManager;
import engine.events.events.CharacterSpawnEvent;
import engine.world.World;

public class Player extends Character {
	private static final long serialVersionUID = -7984270478574881289L;
	
	PlayerInput input;
	PlayerMovable movable;
	ColorDrawable drawable;
	
	private void initializeComponents(Input input) {
		this.input = new PlayerInput(this, input);
		components.add(this.input);
		
		movable = new PlayerMovable(this, this.input);
		components.add(movable);
		
		drawable = new ColorDrawable(this);
		components.add(drawable);
	}
	
	public Player(World world, Input input) {
		super(world);
		
		initializeComponents(input);
	}
	
	public Player(World world, Input input, Bounds bounds) {
		super(world, bounds);
		
		initializeComponents(input);
	}
	
	public Player(World world, Input input, float x, float y, float width, float height) {
		super(world, x, y, width, height);
		
		initializeComponents(input);
	}
	
	public PlayerInput getInput() {
		return input;
	}

	@Override
	public void die(DeathZone deathZone) {
		//not sure what to do here yet
		//world.respawn(this, -1); //spawn to random spawnpoint
		
		EventManager.raise(new CharacterSpawnEvent(null, this));
	}
	
	public void die(SpawnPoint spawnPoint) {
		//die(); //maybe?
		
		//world.respawn(this, spawn);
	}
}
