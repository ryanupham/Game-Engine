package engine.entity.character;

import spaceinvaders.server.Squadron;
import engine.components.graphics.ColorDrawable;
import engine.components.movement.PathMovable;
import engine.components.movement.paths.PatternMovementPath;
import engine.coordinates.Bounds;
import engine.coordinates.Point;
import engine.entity.special.DeathZone;
import engine.world.World;

public class Starship extends Character {
	private static final long serialVersionUID = -5675635039998516888L;
	
	protected PathMovable movable;
	protected ColorDrawable drawable;
	protected DeathZone deathZone;
	
	protected transient Squadron squadron;
	
	private void InitializeComponents() {
		PatternMovementPath path = new PatternMovementPath(1/4f, position.getPosition(), new Point(50, 0), new Point(0, 25), new Point(-50, 0), new Point(0, 25));
		movable = new PathMovable(this, path);
		movable.start();
		components.add(movable);
		
		drawable = new ColorDrawable(this, 255, 0, 0);
		components.add(drawable);
		
		deathZone = new DeathZone(world, this);
	}
	
	public Starship(World world, Squadron squadron, Bounds bounds) {
		super(world, bounds);
		
		this.squadron = squadron;
		
		InitializeComponents();
	}

	@Override
	public void die(DeathZone deathPoint) {
		//removeComponent(PathMovable.class);
		getComponent(PathMovable.class).stop();
		position.setPosition(-150 - (int)(Math.random() * 500), 0);
		
		enabled = false;
		
		squadron.update();
	}
}
