package engine.time;

import java.io.Serializable;

public abstract class Timeline implements Serializable {
	private static final long serialVersionUID = 8763299219552474029L;
	
	protected long start;
	
	public abstract long getElapsedTime();
}
