package engine.entity.special;

import engine.components.spatial.Position;
import engine.coordinates.Bounds;
import engine.entity.Entity;
import engine.world.World;

public class DeathZone extends Entity {
	private static final long serialVersionUID = 8539668097957018871L;
	
	protected Entity entity;
	protected SpawnPoint spawnPoint;
	
	public DeathZone(World world) {
		super(world);
		
		entity = null;
		spawnPoint = null;
	}
	
	public DeathZone(World world, Entity attachedEntity) {
		this(world);
		
		entity = attachedEntity;
	}
	
	public DeathZone(World world, Bounds bounds) {
		super(world, bounds);
	}

	public DeathZone(World world, float x, float y, float width, float height) {
		super(world, x, y, width, height);
	}
	
	public void setSpawnPoint(SpawnPoint spawnPoint) {
		this.spawnPoint = spawnPoint;
	}
	
	public SpawnPoint getSpawnPoint() {
		return spawnPoint;
	}
	
	@Override
	public Position getPosition() {
		if(entity == null)
			return position;
		
		return entity.getPosition();
	}

}
