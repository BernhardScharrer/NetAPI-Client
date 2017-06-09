package networking;

public abstract class PacketChannel extends ObjChannel {

	public PacketChannel(String name, Connection con, int timeout) {
		super(name, con, timeout);
	}
	
	public void send(Packet packet) {
		super.send(packet);
	}
	
}
