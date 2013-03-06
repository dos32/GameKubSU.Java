package game.json;

public interface JSONSerializable {
	public abstract String getClassName(); // Special field for checking class compatibility
	public abstract JSONObject toJSON();
	public abstract void fromJSON(JSONObject json) throws JSONClassCheckException;
}
