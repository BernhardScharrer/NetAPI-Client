package networking.channel;

import networking.Connection;
import networking.Packet;

public abstract class PacketChannel extends ObjChannel {

	public PacketChannel(String name, Connection con) {
		super(name, con);
	}
	
	public void send(Packet packet) {
		super.send(packet);
	}
	
	@Override
	void recieve(Object obj) {
		if (obj instanceof Packet) incoming((Packet) obj);
	}
	
	protected abstract void incoming(Packet packet);
	
	@Override
	public ChannelType getType() {
		return ChannelType.PACKET;
	}
	
}
