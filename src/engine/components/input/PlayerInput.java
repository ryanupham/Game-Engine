package engine.components.input;

import engine.components.Component;
import engine.entity.Entity;

public class PlayerInput extends Component {
	private static final long serialVersionUID = -5217404128640492380L;
	
	protected Input input;
	
	public PlayerInput(Entity parent, Input input) {
		super(parent);
		
		this.input = input;
	}
	
	public void setInput(Input input) {
		this.input = input;
	}
	
	public boolean getJump() {
		return input.contains(' ');
	}
	
	public boolean getLeft() {
		return input.contains('a');
	}
	
	public boolean getRight() {
		return input.contains('d');
	}
}
