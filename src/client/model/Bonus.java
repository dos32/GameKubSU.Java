package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Bonus extends Circle implements JSONSerializable {
	
	public enum Type {
		FLAG,
		MED_KIT,
		NITRO_FUEL,
		KAMIKADZE,
		BULLETS;
	}
	
	private Type type;
	
	/**
	 * 
	 * @return	Type of bonus
	 */
	public Type getType() {
		return type;
	}
	
	protected Type stringToType(String string) {
		if(string.equals("MED_KIT"))
			return Type.MED_KIT;
		else if(string.equals("FLAG"))
			return Type.FLAG;
		else if(string.equals("NITRO_FUEL"))
			return Type.NITRO_FUEL;
		else if(string.equals("KAMIKADZE"))
			return Type.KAMIKADZE;
		else if(string.equals("BULLETS"))
			return Type.BULLETS;
		else {
			System.err.println("Bonus.stringToType()::Cant recognize BonusType name");
			return Type.FLAG;
		}
	}
	
	@Override
	public String getClassName() {
		return "Bonus";
	}

	@Override
	public JSONObject toJSON() {
		return null;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
		type = stringToType(json.getString("type"));
	}
}
