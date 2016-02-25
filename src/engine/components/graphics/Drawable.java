package engine.components.graphics;

import processing.core.PApplet;
import engine.components.Component;
import engine.entity.Entity;

public abstract class Drawable extends Component {
	private static final long serialVersionUID = 3834458020272711736L;

	public Drawable(Entity parent) {
		super(parent);
	}
	
	public abstract void draw(PApplet screen);
}
