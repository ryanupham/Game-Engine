package engine.components;

import java.io.Serializable;

import engine.entity.Entity;

public abstract class Component implements Serializable {
	private static final long serialVersionUID = 2902023734748273702L;
	
	private Entity parent;
	
	public Component(Entity parent) {
		this.parent = parent;
	}
	
	public Entity getParent() {
		return parent;
	}
	
	protected void setParent(Entity parent) {
		this.parent = parent;
	}
}
