package game.server;

import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;

import java.io.Serializable;

public class BotAction implements Serializable, JSONSerializable {
	private static final long serialVersionUID = 6715590463417335976L;
	public double power, turn;
	
	public BotAction() {
	}
	
	public BotAction(double power, double turn) {
		this.power = power;
		this.turn = turn;
	}
	
	public void assign(double power, double turn) {
		this.power = power;
		this.turn = turn;
	}

	@Override
	public String getClassName() {
		return "BotAction";
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("class", getClassName());
		json.put("power", power);
		json.put("turn", turn);
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		power = json.getDouble("power");
		turn = json.getDouble("turn");
	}
}
