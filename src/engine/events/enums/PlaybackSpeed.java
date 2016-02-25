package engine.events.enums;

public enum PlaybackSpeed {
	HALF	(0.5),
	NORMAL	(1),
	DOUBLE	(2);
	
	private double speed;
	
	private PlaybackSpeed(double speed) {
		this.speed = speed;
	}
	
	public double speed() {
		return speed;
	}
}
