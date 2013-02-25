package game.physics.objects;

import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

public class Circle extends Unit implements Serializable, JSONSerializable {
	private static final long serialVersionUID = -2503510928736687406L;
	public double radius;
	
	public Circle(double radius) {
		this.radius = radius;
	}

	@Override
	public void draw(Graphics2D graphics) {
		AffineTransform oldTransform = graphics.getTransform();
		graphics.translate(position.x-radius, position.y-radius);
		graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
		graphics.setTransform(oldTransform);
	}
	
	@Override
	public String getClassName() {
		return "Circle";
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		json.put("radius", Double.toString(radius));
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
		this.radius = json.getDouble("radius");
	}
}
