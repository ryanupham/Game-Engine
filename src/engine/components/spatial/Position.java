package engine.components.spatial;

import engine.components.Component;
import engine.coordinates.Bounds;
import engine.coordinates.Point;
import engine.entity.Entity;

public class Position extends Component {
	private static final long serialVersionUID = 5631246203356864260L;
	
	private Bounds bounds;
	
	public Position(Entity parent) {
		super(parent);
		
		bounds = new Bounds();
	}
	
	public Position(Entity parent, Bounds bounds) {
		super(parent);
		
		this.bounds = bounds;
	}
	
	public Position(Entity parent, Point position) {
		super(parent);
		
		bounds = new Bounds(position.x, position.y, position.x, position.y);
	}
	
	public Position(Entity parent, Point position, float width, float height) {
		super(parent);
		
		bounds = new Bounds(position.x, position.y,
					position.x + width, position.y + height);
	}
	
	public Position(Entity parent, float x1, float y1, float x2, float y2) {
		super(parent);
		
		bounds = new Bounds(x1, y1, x2, y2);
	}
	
	public void setX(float x) {
		bounds.x2 += x - bounds.x1;
		bounds.x1 = x;
	}
	
	public float getX() {
		return bounds.x1;
	}
	
	public void setY(float y) {
		bounds.y2 += y - bounds.y1;
		bounds.y1 = y;
	}
	
	public float getY() {
		return bounds.y1;
	}
	
	public void setPosition(Point position) {
		setPosition(position.x, position.y);
	}
	
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public Point getPosition() {
		return new Point(bounds.x1, bounds.y1);
	}
	
	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}
	
	public void setBounds(float x1, float y1, float x2, float y2) {
		setBounds(new Bounds(x1, y1, x2, y2));
	}
	
	public Bounds getBounds() {
		return bounds;
	}
	
	public void setWidth(float width) {
		if(width >= 0)
			bounds.x2 = bounds.x1 + width;
	}
	
	public float getWidth() {
		return bounds.getWidth();
	}
	
	public void setHeight(float height) {
		if(height >= 0)
			bounds.y2 = bounds.y1 + height;
	}
	
	public float getHeight() {
		return bounds.getHeight();
	}
	
	public void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}
	
	public Position copy() {
		return new Position(getParent(), getBounds());
	}
	
}
