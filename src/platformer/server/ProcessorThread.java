package platformer.server;

import engine.events.EventManager;
import engine.events.Priority;
import engine.time.GlobalVirtualTime;
import engine.world.World;

class ProcessorThread extends Thread {
	
	private final int RATE = 60; // calculations/second
	
	public void run() {
		synchronized(this) {
			while(true) {
				World.getInstance().move();
				
				EventManager.handleAll(new Priority());
				
				GlobalVirtualTime.getInstance().step();
				
				try {
					this.wait((int)(1000.0 / RATE));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
