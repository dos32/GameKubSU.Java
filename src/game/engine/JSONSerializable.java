package game.engine;

public interface JSONSerializable {
	public abstract void serialize(java.io.StringWriter stream);
	public abstract void deSerialize(java.io.StringReader stream);
}
