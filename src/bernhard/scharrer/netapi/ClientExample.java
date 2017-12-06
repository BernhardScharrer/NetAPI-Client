package bernhard.scharrer.netapi;

import bernhard.scharrer.netapi.client.Client;
import bernhard.scharrer.netapi.client.Console;
import bernhard.scharrer.netapi.client.ConsoleType;
import bernhard.scharrer.netapi.client.NetAPI;
import bernhard.scharrer.netapi.client.TCPModul;
import bernhard.scharrer.netapi.packet.Packet;

public class ClientExample {
	
	private static Console console;
	
	public static void main(String[] args) {
		Client client = NetAPI.start("localhost", 8888, new TCPModul() {
			@Override
			public void receive(Packet packet) {
				console.debug("Incoming: "+packet.toString());
			}
			
			@Override
			public void receive(String message) {
				console.debug("Incoming: "+message);
			}

			@Override
			public void disconnect() {
				console.info("Connection closed.");
			}
		}, ConsoleType.WINDOWS);
		
		console = client.getConsole();
		
		client.setDebugging(true);
		client.setHeadlineVisible(true);
		client.start();
		
		client.send("test");
		
	}
	
}
