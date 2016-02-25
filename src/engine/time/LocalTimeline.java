package engine.time;

public class LocalTimeline extends Timeline {
	private static final long serialVersionUID = 5197702324539303943L;

	protected Timeline anchor;
	
	protected long start;
	protected long elapsedTime = 0;
	
	protected double scale = 1;
	
	protected boolean paused = false;

	protected static final LocalTimeline realTime = new LocalTimeline(GlobalTime.getInstance());
	protected static final LocalTimeline virtualTime = new LocalTimeline(GlobalVirtualTime.getInstance());
	
	public LocalTimeline() {
		anchor = GlobalTime.getInstance();
		
		start = anchor.getElapsedTime();
	}
	
	public LocalTimeline(Timeline anchor) {
		this.anchor = anchor;
		
		start = anchor.getElapsedTime();
	}
	
	public void pause() {
		if(!paused) {
			elapsedTime += elapsedScale();
			paused = true;
		}
	}
	
	public void resume() {
		if(paused) {
			start = anchor.getElapsedTime();
			paused = false;
		}
	}
	
	public void setScale(double scale) {
		elapsedTime += elapsedScale();
		start = anchor.getElapsedTime();
		
		this.scale = scale;
	}
	
	@Override
	public long getElapsedTime() {
		long elapsed = elapsedScale();
		
		return elapsed + elapsedTime;
	}
	
	private long elapsedScale() {
		return (paused ? 0 : Math.round((anchor.getElapsedTime() - start) * scale));
	}
	
	public static LocalTimeline getRealTime() {
		return realTime;
	}
	
	public static LocalTimeline getVirtualTime() {
		return virtualTime;
	}
}
