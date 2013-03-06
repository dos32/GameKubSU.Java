package game.engine;

import game.physics.objects.Unit;

public interface UnitContainer {
	
	public abstract void addUnit(Unit unit);
	
	public abstract void removeUnit(Unit unit);
	
	public abstract void clearUnits();
	
}
