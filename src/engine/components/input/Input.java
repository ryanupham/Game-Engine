package engine.components.input;

import java.io.Serializable;
import java.util.ArrayList;

public class Input implements Serializable {
	private static final long serialVersionUID = 8094881532273943990L;
	
	protected ArrayList<Character> keys;
	
	public Input() {
		keys = new ArrayList<Character>();
	}
	
	public void keyDown(char key) {
		key = Character.toLowerCase(key);
		
		if(!keys.contains(key))
			keys.add(key);
	}
	
	public void keyUp(char key) {
		key = Character.toLowerCase(key);
		
		keys.remove(new Character(key));
	}
	
	public boolean contains(char key) {
		key = Character.toLowerCase(key);
		
		return keys.contains(key);
	}
}
