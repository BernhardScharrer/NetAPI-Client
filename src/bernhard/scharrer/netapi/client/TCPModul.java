package bernhard.scharrer.netapi.client;

import bernhard.scharrer.netapi.packet.Packet;

public interface TCPModul {
	
	public void receive(String message);
	public void receive(Packet packet);
	
	public void disconnect();
	
}
