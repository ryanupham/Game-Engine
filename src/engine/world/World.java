package engine.world;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import processing.core.PApplet;
import engine.GUID;
import engine.components.graphics.Drawable;
import engine.components.movement.Movable;
import engine.coordinates.Bounds;
import engine.entity.Entity;
import engine.entity.character.Character;
import engine.entity.character.Player;
import engine.entity.obstacle.Floor;
import engine.entity.obstacle.Obstacle;
import engine.entity.obstacle.Platform;
import engine.entity.special.DeathZone;
import engine.entity.special.SpawnPoint;
import engine.events.EventManager;
import engine.events.events.physics.CollisionEvent;

public class World implements Serializable {
	private static final long serialVersionUID = 8815086010618783485L;
	
	public static final float GRAVITY = 3;
	
	private static final World INSTANCE = new World();
	
	private static boolean recording = false;
	private static boolean recorded = false;
	private static boolean playback = false;
	private static World recordingCopy;
	
	private int width, height;
	
	private LinkedList<Entity> entities;
	
	private World() {
		entities = new LinkedList<Entity>();
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void add(Entity entity) {
		entities.add(entity);
	}
	
	public void remove(GUID guid) {
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i).getGUID().equals(guid)) {
				entities.remove(i);
				break;
			}
	}
	
	public void remove(Entity entity) {
		remove(entity.getGUID());
	}
	
	public Entity getEntity(GUID guid) {
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i).equals(guid))
				return entities.get(i);
		
		return null;
	}
	
	public boolean hasEntity(GUID guid) {
		return getEntity(guid) != null;
	}
	
	public ArrayList<Bounds> getPlatformBounds() {
		ArrayList<Bounds> bounds = new ArrayList<Bounds>();
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof Platform)
				bounds.add(entities.get(i).getPosition().getBounds());
		
		return bounds;
	}
	
	public ArrayList<Bounds> getFloorBounds() {
		ArrayList<Bounds> bounds = new ArrayList<Bounds>();
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof Floor)
				bounds.add(entities.get(i).getPosition().getBounds());
		
		return bounds;
	}
	
	public ArrayList<Bounds> getObstacleBounds() {
		ArrayList<Bounds> bounds = new ArrayList<Bounds>();
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof Obstacle)
				bounds.add(entities.get(i).getPosition().getBounds());
		
		return bounds;
	}
	
	public ArrayList<Bounds> getCharacterBounds() {
		ArrayList<Bounds> bounds = new ArrayList<Bounds>();
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof Character)
				bounds.add(entities.get(i).getPosition().getBounds());
		
		return bounds;
	}
	
	public ArrayList<Bounds> getPlayerBounds() {
		ArrayList<Bounds> bounds = new ArrayList<Bounds>();
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof Player)
				bounds.add(entities.get(i).getPosition().getBounds());
		
		return bounds;
	}
	
	public ArrayList<Bounds> getPlayerBounds(GUID skip) {
		ArrayList<Bounds> bounds = new ArrayList<Bounds>();
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof Player)
				if(!entities.get(i).equals(skip))
					bounds.add(entities.get(i).getPosition().getBounds());
		
		return bounds;
	}

	public ArrayList<DeathZone> getDeathZones() {
		ArrayList<DeathZone> deathZones = new ArrayList<DeathZone>();
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof DeathZone)
				deathZones.add((DeathZone)entities.get(i));
		
		return deathZones;
	}
	
	public void move() {
		if(World.playback)
			return;
		
		for(int i = 0; i < entities.size(); i++)
			if(!(entities.get(i) instanceof Player)) {
				Movable c = entities.get(i).getComponent(Movable.class);
				
				if(c != null)
					c.move();
			}
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof Player) {
				Movable c = entities.get(i).getComponent(Movable.class);
				
				if(c != null)
					c.move();
			}
	}
	
	public void checkCollisions() {
		for(int i = 0; i < entities.size(); i++)
			for(int j = 0; j < entities.size(); j++)
				if(i != j && i < entities.size() && j < entities.size()) {
					Entity e1 = entities.get(i);
					Entity e2 = entities.get(j);
					
					if(e1 == null || e2 == null)
						continue;
					
					if(e1.isEnabled() && e2.isEnabled())
						if(e1.getPosition().getBounds().intersects(e2.getPosition().getBounds()))
							EventManager.raise(new CollisionEvent(e1, e2));
				}
	}
	
	public void draw(PApplet screen) {
		for(int i = 0; i < entities.size(); i++) {
			Drawable c = entities.get(i).getComponent(Drawable.class);

			if(c != null)
				c.draw(screen);
		}
	}

	public void spawn(Character character, SpawnPoint spawn) {
		if(hasEntity(character.getGUID()))
			respawn(character, spawn);
		else if(spawn == null)
			spawn(character, -1);
		else {
			character.getPosition().setX(spawn.getPosition().getX());
			character.getPosition().setY(spawn.getPosition().getY());
			
			add(character);
		}
	}
	
	public void spawn(Character character, int index) {
		ArrayList<SpawnPoint> points = new ArrayList<SpawnPoint>();
		
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i) instanceof SpawnPoint)
				points.add((SpawnPoint)entities.get(i));
		
		if(index >= 0)
			spawn(character, points.get(index));
		else
			spawn(character, points.get(0));
	}
	
	public void respawn(Character character, SpawnPoint spawn) {
		entities.remove(character);
		
		if(spawn != null)
			spawn(character, spawn);
		else
			spawn(character, -1);
	}
	
	public void respawn(Character character, int index) {
		entities.remove(character);
		
		spawn(character, index);
	}

	public <T extends Entity> ArrayList<T> getEntities(Class<T> type) {
		ArrayList<T> ents = new ArrayList<T>();
		
		for(int i = 0; i < entities.size(); i++)
			if(type.isInstance(entities.get(i)))
				ents.add(type.cast(entities.get(i)));
		
		return ents;
	}

	public static World getInstance() {
		if(playback)
			return recordingCopy;
		
		return INSTANCE;
	}

	public static void startRecording() {
		if(recording)
			return;
		
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream worldOut = new ObjectOutputStream(bout);
			worldOut.writeObject(INSTANCE);
			
			ObjectInputStream worldIn = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
			recordingCopy = (World)worldIn.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		recording = true;
	}
	
	public static void stopRecording() {
		if(!recording)
			return;
		
		recorded = true;
		recording = false;
	}
	
	public static void startPlayback() {
		if(recording || !recorded || playback)
			return;
		
		playback = true;
	}
	
	public static void stopPlayback() {
		playback = false;
		recorded = false;
	}
}
