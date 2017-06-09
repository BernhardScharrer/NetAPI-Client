package networking.channel;

import networking.Connection;

public abstract class StringChannel extends ObjChannel {

	public StringChannel(String name, Connection con, int timeout) {
		super(name, con, timeout);
	}

	public void send(String string) {
		super.send(string);
	}
	
	@Override
	void recieve(Object obj) {
		if (obj instanceof String) incoming((String) obj);
	}
	
	@Override
	protected ChannelType getType() {
		return ChannelType.STRING;
	}
	
	protected abstract void incoming(String string);
	
}
