package engine.entity;

import java.io.Serializable;
import java.util.ArrayList;

import engine.GUID;
import engine.components.Component;
import engine.components.spatial.Position;
import engine.coordinates.Bounds;
import engine.world.World;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = -5770236298801819269L;

	protected GUID id;
	
	protected World world;

	protected Position position;
	
	protected ArrayList<Component> components;
	
	protected boolean enabled = true;
	
	public Entity(World world) {
		id = GUID.create();
		
		this.world = world;
		
		position = new Position(this);
		
		components = new ArrayList<Component>();
		
		components.add(position);
	}
	
	public Entity(World world, Bounds bounds) {
		this(world);
		
		position.setBounds(bounds);
	}
	
	public Entity(World world, float x, float y, float width, float height) {
		this(world);
		
		position.setPosition(x, y);
		position.setSize(width, height);
	}
	
	public GUID getGUID() {
		return id;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public <T extends Component> T getComponent(Class<T> type) {
		for(int i = 0; i < components.size(); i++)
			if(type.isInstance(components.get(i)))
				return type.cast(components.get(i));
		
		return null;
	}
	
	public boolean addComponent(Component component) {
		if(getComponent(component.getClass()) != null)
			return false;
		
		components.add(component);
		
		return true;
	}
	
	public <T extends Component> boolean removeComponent(Class<T> type) {
		for(int i = 0; i < components.size(); i++)
			if(type.isInstance(components.get(i))) {
				components.remove(components.get(i));
				
				return true;
			}
		
		return false;
	}
	
	public World getWorld() {
		return world;
	}
	
	public boolean equals(Entity e) {
		return id.equals(e.getGUID());
	}
	
	public boolean equals(GUID guid) {
		return id.equals(guid);
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
}
