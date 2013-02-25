package client.model;

import game.engine.Settings;
import client.json.JSONObject;
import client.json.JSONSerializable;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Bonus extends Circle implements JSONSerializable {
	protected BonusType type;
	
	public BonusType getType() {
		return type;
	}
	
	@Override
	public String getClassName() {
		return "Bonus";
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		String strType = "";
		switch (type) {
		case MED_KIT:
			strType = "MED_KIT";
			break;
		case FLAG:
			strType = "FLAG";
			break;
		case NITRO_FUEL:
			strType = "NITRO_FUEL";
			break;
		default:
			break;
		}
		json.put("type", strType);
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) {
		// TODO Auto-generated method stub
	}
}
