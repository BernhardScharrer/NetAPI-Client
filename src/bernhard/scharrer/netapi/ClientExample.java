package bernhard.scharrer.netapi;

import bernhard.scharrer.netapi.client.Client;
import bernhard.scharrer.netapi.client.Console;
import bernhard.scharrer.netapi.client.ConsoleType;
import bernhard.scharrer.netapi.client.NetAPI;
import bernhard.scharrer.netapi.client.TCPModul;
import bernhard.scharrer.netapi.client.UDPModul;
import bernhard.scharrer.netapi.packet.Packet;

public class ClientExample {
	
	private static Console console;
	private static Client client;
	
	public static void main(String[] args) {
		client = NetAPI.start("localhost", 7788
		/**
		 * tcp modul
		 */
		, new TCPModul() {
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
		/**
		 *  udp modul
		 */
		}, new UDPModul() {
			
			@Override
			public void receive(float[] data) {
				for (float d : data) System.out.println(d);
			}
			
			@Override
			public void receive(int[] data) {
				
			}
		}, ConsoleType.WINDOWS);
		
		console = client.getConsole();
		
		client.setDebugging(true);
		client.setHeadlineVisible(true);
		client.start();
		
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		for (int n=0;n<1000;n++) {
//			client.send(new float[] {7.1f,12349.57f,-12349.2f});
//		}
		
	}
	
}
