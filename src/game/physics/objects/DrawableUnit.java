package game.physics.objects;

import game.Runner;
import game.graphics.Drawable;

public abstract class DrawableUnit extends Unit implements Drawable {
	private static final long serialVersionUID = -2058648426872439077L;
	protected int zIndex = 0;

	@Override
	public int getZIndex() {
		return zIndex;
	}

	@Override
	public void setZIndex(int zIndex) {
		Runner.inst().renderer.removeUnit(this);
		this.zIndex = zIndex;
		Runner.inst().renderer.addUnit(this);
	}

	@Override
	public int compareTo(Drawable o) {
		int res = this.zIndex - o.getZIndex();
		if(res == 0 && (o instanceof Unit))
			res= this.id - ((Unit)o).id;
		if(res == 0 && this != o)
			System.err.println("Box.compareTo()::res==0");
		return res;
	}
}
