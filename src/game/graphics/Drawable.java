package game.graphics;

import java.awt.Graphics2D;

public interface Drawable extends Comparable<Drawable> {
	public void draw(Graphics2D graphics);
	public int getZIndex();
	public void setZIndex(int zIndex);
}
