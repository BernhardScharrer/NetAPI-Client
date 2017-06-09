package networking;

public abstract class StringChannel extends ObjChannel {

	public StringChannel(String name, Connection con, int timeout) {
		super(name, con, timeout);
	}
	
	public void send(String string) {
		super.send(string);
	}
	
}
