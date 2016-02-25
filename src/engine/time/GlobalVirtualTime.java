package engine.time;

public final class GlobalVirtualTime extends Timeline {
	private static final long serialVersionUID = -2536909264358670711L;
	
	private static final GlobalVirtualTime TIMELINE = new GlobalVirtualTime();
	
	public static final long BASE_STEP_SIZE = 1<<5 * 3;
	public static final double TIME_PER_STEP = 1000.0/60/BASE_STEP_SIZE;
	
	private double coeff = 1;
	
	private long cur;
	
	private GlobalVirtualTime() {
		start = 0;
		cur = start;
	}
	
	public void step() {
		cur += (long)(BASE_STEP_SIZE * coeff);
	}
	
	public void set(long tic) {
		cur = tic;
	}
	
	public static GlobalVirtualTime getInstance() {
		return TIMELINE;
	}

	@Override
	public long getElapsedTime() {
		return cur - start;
	}

	public void setScale(double coeff) {
		this.coeff = coeff;
	}
}
