package platformer.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import engine.GUID;
import engine.components.graphics.Color;
import engine.components.graphics.ColorDrawable;
import engine.components.graphics.FadingColorDrawable;
import engine.components.input.Input;
import engine.components.movement.PathMovable;
import engine.components.movement.paths.LinearMovementPath;
import engine.coordinates.Point;
import engine.entity.character.Player;
import engine.entity.obstacle.Floor;
import engine.entity.obstacle.Platform;
import engine.entity.special.DeathZone;
import engine.entity.special.SpawnPoint;
import engine.events.EventManager;
import engine.events.events.CharacterDeathEvent;
import engine.events.events.CharacterSpawnEvent;
import engine.events.events.UserInputEvent;
import engine.events.events.physics.CollisionEvent;
import engine.events.events.physics.EntityMoveEvent;
import engine.events.events.physics.PlayerJumpedEvent;
import engine.events.events.replay.RecordPlayEvent;
import engine.events.events.replay.RecordPlaySpeedEvent;
import engine.events.events.replay.RecordStartEvent;
import engine.events.events.replay.RecordStopEvent;
import engine.handlers.CharacterDeathHandler;
import engine.handlers.CharacterSpawnHandler;
import engine.handlers.CollisionHandler;
import engine.handlers.EntityMoveHandler;
import engine.handlers.PlaybackHandler;
import engine.handlers.PlayerJumpedHandler;
import engine.handlers.UserInputHandler;
import engine.world.World;

public class Server {
	public static final float GRAVITY = 3;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public static final int NUMBER_OF_CLIENTS = 1;
	public static final int PORT = 25674;

	private static ServerSocket server;
	
	private static void registerHandlers() {
		EventManager.register(CollisionHandler.getInstance(), CollisionEvent.class);
		EventManager.register(CharacterDeathHandler.getInstance(), CharacterDeathEvent.class);
		EventManager.register(CharacterSpawnHandler.getInstance(), CharacterSpawnEvent.class);
		EventManager.register(EntityMoveHandler.getInstance(), EntityMoveEvent.class);
		EventManager.register(PlayerJumpedHandler.getInstance(), PlayerJumpedEvent.class);
		EventManager.register(UserInputHandler.getInstance(), UserInputEvent.class);
		EventManager.register(PlaybackHandler.getInstance(), RecordPlayEvent.class);
		EventManager.register(PlaybackHandler.getInstance(), RecordPlaySpeedEvent.class);
		EventManager.register(PlaybackHandler.getInstance(), RecordStartEvent.class);
		EventManager.register(PlaybackHandler.getInstance(), RecordStopEvent.class);
	}
	
	private static void setupTestWorld(World world) {
		Platform platform = new Platform(world, 300, world.getHeight() - 100, 200, 30);
		platform.getComponent(ColorDrawable.class).setColor(0, 255, 0);
		world.add(platform);
		
		Floor floor = new Floor(world, 700, world.getHeight() - 250, 30, 250);
		floor.getComponent(ColorDrawable.class).setColor(0, 0, 255);
		world.add(floor);
		
		floor = new Floor(world, 25, world.getHeight() - 150, 200, 30);
		floor.getComponent(ColorDrawable.class).setColor(0, 0, 255);
		world.add(floor);
		
		floor = new Floor(world, 25, world.getHeight() - 450, 200, 30);
		floor.getComponent(ColorDrawable.class).setColor(0, 0, 255);
		world.add(floor);
		
		Platform movingPlatform = new Platform(world, 775, world.getHeight() - 150, 175, 30);
		movingPlatform.getComponent(ColorDrawable.class).setColor(0, 255, 255);
		Point startPoint = movingPlatform.getPosition().getPosition().copy();
		LinearMovementPath linearPattern = new LinearMovementPath(startPoint,
				new Point(startPoint.x + 200, startPoint.y + 80), startPoint.copy(), 1/2f);
		PathMovable platformMover = new PathMovable(movingPlatform, linearPattern);
		movingPlatform.addComponent(platformMover);
		world.add(movingPlatform);
		
		floor = new Floor(world, world.getWidth() - 100, world.getHeight() - 50
				, world.getWidth(), world.getHeight());
		ArrayList<Integer> colors = new ArrayList<Integer>();
		colors.add(Color.color(255, 0, 0));
		colors.add(Color.color(128, 0, 0));
		floor.setDrawable(new FadingColorDrawable(floor, colors, 1000));
		world.add(floor);
		
		DeathZone death = new DeathZone(world, floor);
		world.add(death);

		world.add(new SpawnPoint(world, 100, 100));
		world.add(new SpawnPoint(world, 700, 300));
		world.add(new SpawnPoint(world, 1000, 500));
	}
	
	public static void main(String[] args) {
		registerHandlers();
		
		try {
			GUID.initialize(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
			GUID.initialize("error");
		}
		
		World world = World.getInstance();//new World(WIDTH, HEIGHT);
		world.setDimensions(WIDTH, HEIGHT);
		
		ProcessorThread processor = new ProcessorThread();
		
		processor.start();
		
		setupTestWorld(world);
		
		try {
			server = new ServerSocket(PORT);
			
			ArrayList<Socket> sockets = new ArrayList<Socket>();
			
			while(true) {
				Socket s = server.accept();
				
				sockets.add(s);
				
				ObjectOutputStream objectOutput = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream objectInput = new ObjectInputStream(s.getInputStream());
				
				objectOutput.writeObject("init");
				objectOutput.flush();
				
				String str = (String)objectInput.readObject();
				
				while(str == null || !str.equals("ready"))
					str = (String)objectInput.readObject();
				
				Player player = new Player(world, new Input(), 0, 0, 50, 50);
				player.getComponent(ColorDrawable.class).setColor(255, 0, 0);
				
				world.spawn(player, -1);
				
				objectOutput.writeObject(world);
				objectOutput.flush();
				
				(new NetworkThread(objectInput, objectOutput, player, world)).start();
				
				System.out.println("Client connected");
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
