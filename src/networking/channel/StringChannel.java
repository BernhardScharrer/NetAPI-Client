package networking.channel;

import networking.Connection;

public abstract class StringChannel extends ObjChannel {

	public StringChannel(String name, Connection con) {
		super(name, con);
	}

	public void send(String string) {
		super.send(string);
	}
	
	@Override
	void recieve(Object obj) {
		if (obj instanceof String) incoming((String) obj);
	}
	
	@Override
	public ChannelType getType() {
		return ChannelType.STRING;
	}
	
	protected abstract void incoming(String string);
	
}
