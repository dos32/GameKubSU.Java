package game.physics.objects;

import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Obstacle extends Circle implements Serializable, JSONSerializable {
	private static final long serialVersionUID = 4749768202608073202L;
	
	public Obstacle(double radius) {
		super(radius);
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
		// TODO Auto-generated method stub
	}
	
	@Override
	public String getClassName() {
		return "Obstacle";
	}
	
	@Override
	public JSONObject toJSON() {
		return super.toJSON();
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
	}
	
}
