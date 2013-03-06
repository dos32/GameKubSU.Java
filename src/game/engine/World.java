package game.engine;

import game.Runner;
import game.json.JSONArray;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;
import game.physics.objects.Bonus;
import game.physics.objects.Obstacle;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;
import game.server.ClientListener;

import java.io.Serializable;
import java.util.ArrayList;

public class World implements Serializable, JSONSerializable, UnitContainer {
	private static final long serialVersionUID = 5624391324336770137L;

	public int tick = 0;
	public final double width, height;
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();

	public World() {
		this.width = Settings.World.width;
		this.height = Settings.World.height;
	}

	@Override
	public void addUnit(Unit unit) {
		Runner.inst().renderer.updated = true;
		if (unit instanceof Vehicle)
			vehicles.add((Vehicle) unit);
		else if (unit instanceof Obstacle)
			obstacles.add((Obstacle) unit);
		else if (unit instanceof Bonus)
			bonuses.add((Bonus) unit);
	}

	@Override
	public void removeUnit(Unit unit) {
		vehicles.remove(unit);
		obstacles.remove(unit);
		bonuses.remove(unit);
	}

	@Override
	public void clearUnits() {
		vehicles.clear();
		obstacles.clear();
		bonuses.clear();
	}
	
	public void loadMap() {
		// TODO
	}

	@Override
	public String getClassName() {
		return "World";
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("class", this.getClassName());
		
		json.put("tick", this.tick);
		json.put("width", this.width);
		json.put("height", this.height);
		
		json.put("players", new JSONArray());
		for(ClientListener client : Runner.inst().server.clients)
			json.append("players", client.player.toJSON());
		
		json.put("vehicles", new JSONArray());
		for(Vehicle vehicle : this.vehicles)
			json.append("vehicles", vehicle.toJSON());
		
		json.put("obstacles", new JSONArray());
		for(Obstacle obstacle : this.obstacles)
			json.append("obstacles", obstacle.toJSON());
		
		json.put("bonuses", new JSONArray());
		for(Bonus bonus : this.bonuses)
			json.append("bonuses", bonus.toJSON());
		
		return json;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		throw new JSONClassCheckException("not realized fromJSON");
	}

}
