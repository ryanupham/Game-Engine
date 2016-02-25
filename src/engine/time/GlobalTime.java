package engine.time;

public final class GlobalTime extends Timeline {
	private static final long serialVersionUID = -8610182063451226930L;
	
	private static final GlobalTime TIMELINE = new GlobalTime();
	
	private GlobalTime() {
		start = System.currentTimeMillis();
	}
	
	public static GlobalTime getInstance() {
		return TIMELINE;
	}

	@Override
	public long getElapsedTime() {
		return System.currentTimeMillis() - start;
	}
}
