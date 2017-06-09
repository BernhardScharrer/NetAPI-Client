package networking.channel;

import networking.Connection;

public class ByteChannel extends Channel {

	ByteChannel(String name, Connection con, int timeout) {
		super(name, con, timeout);
	}

	@Override
	protected void setup() {
		// TODO
	}

	@Override
	protected ChannelType getType() {
		return ChannelType.BYTE;
	}

	@Override
	public void close() {
		
	}

}
