package engine.components.graphics;

public final class Color {
	private Color() {
		super();
	}
	
	public static int color(int r, int g, int b) {
		return -16777216 + (r << 16) + (g << 8) + b;
	}
	
	public static int getr(int color) {
		return ((color + 16777216) >> 16) & 0xFF;
	}
	
	public static int getg(int color) {
		return ((color + 16777216) >> 8) & 0xFF;
	}
	
	public static int getb(int color) {
		return (color + 16777216) & 0xFF;
	}
}
