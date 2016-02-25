package engine.components.spatial;

import engine.components.Component;
import engine.entity.Entity;

public class Velocity extends Component {
	private static final long serialVersionUID = 6900407680867418479L;
	
	public float x, y;
	
	public Velocity(Entity parent) {
		super(parent);
		
		x = y = 0;
	}
	
	public Velocity(Entity parent, float x, float y) {
		super(parent);
		
		this.x = x;
		this.y = y;
	}
	
	public boolean ascending() {
		return y > 0;
	}

}
