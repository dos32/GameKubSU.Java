package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Bonus extends Circle implements JSONSerializable {
	protected BonusType type;
	
	protected BonusType stringToType(String string) {
		if(string.equals("MED_KIT"))
			return BonusType.MED_KIT;
		else if(string.equals("FLAG"))
			return BonusType.FLAG;
		else if(string.equals("NITRO_FUEL"))
			return BonusType.NITRO_FUEL;
		else {
			System.err.println("Bonus.stringToType()::Cant recognize BonusType name");
			return BonusType.FLAG;
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
