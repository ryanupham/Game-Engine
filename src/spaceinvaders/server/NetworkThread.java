package spaceinvaders.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import engine.components.input.Input;
import engine.entity.character.Player;
import engine.events.EventManager;
import engine.events.events.UserInputEvent;
import engine.world.World;

class NetworkThread extends Thread {
	private Player player;
	
	private World world;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public NetworkThread(ObjectInputStream in, ObjectOutputStream out, Player player, World world) {
		this.in = in;
		this.out = out;
		
		this.player = player;
		
		this.world = world;
	}
	
	public void run() {
		synchronized(this) {
			while(true) {
				try {
					Object obj = in.readObject();
					
					if(obj instanceof Input)
						EventManager.raise(new UserInputEvent((Input)obj, player));
					
					this.wait(10);
					
					synchronized(World.getInstance()) {
						out.reset();
						out.writeObject(World.getInstance());
						out.flush();
					}
				} catch(Exception e) {
					System.out.println("Client disconnected");
					
					world.remove(player);
					
					try {
						in.close();
						out.close();
					} catch (IOException err) {
						err.printStackTrace();
					}
					
					break;
				}
			}
		}
	}
}
