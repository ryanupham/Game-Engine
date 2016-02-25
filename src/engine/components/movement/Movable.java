package engine.components.movement;

import engine.components.Component;
import engine.entity.Entity;

public abstract class Movable extends Component {
	private static final long serialVersionUID = 1012567388333348690L;

	public Movable(Entity parent) {
		super(parent);
	}
	
	public abstract void move();
	
	public abstract void handleMove(Movement movement);
	
	public abstract void handleCollision(Entity entity);
}
