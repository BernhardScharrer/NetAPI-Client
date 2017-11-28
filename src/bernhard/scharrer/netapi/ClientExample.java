package bernhard.scharrer.netapi;

import bernhard.scharrer.netapi.client.Client;
import bernhard.scharrer.netapi.client.Console;
import bernhard.scharrer.netapi.client.NetAPI;
import bernhard.scharrer.netapi.client.TrafficListener;
import bernhard.scharrer.netapi.client.WindowsConsole;
import bernhard.scharrer.netapi.packet.Packet;

public class ClientExample {
	
	public static void main(String[] args) {
		final Console console = new WindowsConsole(true);
		Client client = NetAPI.start(true, console, "localhost", 7777, 1, new TrafficListener() {
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

			@Override
			public void receive(int[] data) {
				System.out.println(data[0]);
			}

			@Override
			public void receive(float[] data) {
				
			}
		});
		
		client.send("test");
		
	}
	
}
