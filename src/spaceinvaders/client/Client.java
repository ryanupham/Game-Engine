package spaceinvaders.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import processing.core.PApplet;
import engine.GUID;
import engine.components.input.Input;
import engine.world.World;

public class Client extends PApplet {
	private static final long serialVersionUID = -2820837791415862002L;
	
	private World world;
	private Input input;

	public static final int PORT = 25674;
	
	ObjectOutputStream objectOutput;
	ObjectInputStream objectInput;
	Socket s;
	
	public void setup() {
		try {
			GUID.initialize(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
			GUID.initialize("error");
		}
		
		input = new Input();
		
		try {
			s = new Socket("localhost", PORT);
			
			objectOutput = new ObjectOutputStream(s.getOutputStream());
			objectInput = new ObjectInputStream(s.getInputStream());
			
			String str = (String)objectInput.readObject();
			
			if(str != null) {
				objectOutput.writeObject("ready");
				objectOutput.flush();
			}
			
			world = (World)objectInput.readObject();
			
			size(world.getWidth(), world.getHeight());
			
			background(0);
			world.draw(this);
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
		}
	}
	
	public void draw() {
		try {
			objectOutput.reset();
			objectOutput.writeObject(input);
			objectOutput.flush();
			
			Object obj = objectInput.readObject();
			
			if(obj instanceof World)
				world = (World)obj;
			
			background(0);
			
			world.draw(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void keyPressed() {
		input.keyDown(key);
	}
	
	public void keyReleased() {
		input.keyUp(key);
	}
}
