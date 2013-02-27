package client.json;

public interface JSONSerializable {
	public abstract String getClassName();
	public abstract JSONObject toJSON();
	public abstract void fromJSON(JSONObject json) throws JSONClassCheckException;
}
