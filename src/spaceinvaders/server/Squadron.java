package spaceinvaders.server;

import java.io.Serializable;

import engine.components.movement.PathMovable;
import engine.components.movement.paths.PatternMovementPath;
import engine.coordinates.Bounds;
import engine.coordinates.Point;
import engine.entity.character.Starship;
import engine.world.World;

public class Squadron implements Serializable {
		private static final long serialVersionUID = -3370977661319578916L;
		
		private static final int WIDTH = 11;
		private static final int HEIGHT = 5;
		
		private int left = 0;
		private int right = 10;
		
		Starship[][] squadron;
		
		private int getLeft() {
			for(int x = 0; x < WIDTH; x++)
				for(int y = 0; y < HEIGHT; y++)
					if(World.getInstance().getEntity(squadron[x][y].getGUID()).isEnabled())
						return x;
			
			return -1;
		}
		
		private int getRight() {
			for(int x = WIDTH - 1; x >= 0; x--)
				for(int y = 0; y < HEIGHT; y++)
					if(World.getInstance().getEntity(squadron[x][y].getGUID()).isEnabled())
						return x;
			
			return -1;
		}
		
		public Squadron() {
			squadron = new Starship[WIDTH][HEIGHT];
			
			for(int x = 0; x < squadron.length; x++)
				for(int y = 0; y < squadron[x].length; y++) {
					squadron[x][y] = new Starship(World.getInstance(), this, new Bounds(50 + x * 111, 50 + y * 75, 125 + x * 111, 100 + y * 75));
					
					World.getInstance().add(squadron[x][y]);
				}
		}
		
		public void update() {
			if(getLeft() != left) {
				int diff = getLeft() - left;
				
				left = getLeft();
				
				for(int x = 0; x < WIDTH; x++)
					for(int y = 0; y < HEIGHT; y++)
						if(squadron[x][y].isEnabled()) {
							Starship s = (Starship)World.getInstance().getEntity(squadron[x][y].getGUID());
							
							synchronized(s) {
								((PatternMovementPath)(s.getComponent(PathMovable.class).getPath())).updateMovement(2, new Point(diff * -111, 0));
								((PatternMovementPath)(s.getComponent(PathMovable.class).getPath())).updateMovement(0, new Point(diff * 111, 0));
							}
						}
			}

			if(getRight() != right) {
				int diff = getRight() - right;
				
				right = getRight();
				
				for(int x = 0; x < WIDTH; x++)
					for(int y = 0; y < HEIGHT; y++)
						if(squadron[x][y].isEnabled()) {
							Starship s = (Starship)World.getInstance().getEntity(squadron[x][y].getGUID());
							
							((PatternMovementPath)(s.getComponent(PathMovable.class).getPath())).updateMovement(2, new Point(diff * -111, 0));
							((PatternMovementPath)(s.getComponent(PathMovable.class).getPath())).updateMovement(0, new Point(diff * 111, 0));
						}
			}
		}
	}