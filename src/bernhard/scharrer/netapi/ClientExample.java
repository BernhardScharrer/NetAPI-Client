package bernhard.scharrer.netapi;

import java.util.Random;

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
		client = NetAPI.start("4ahel.at", 7788
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
		
		Random r = new Random();
		int n = 0;
		
		while (true) {
			client.send(new float[] {r.nextFloat(),r.nextFloat(),r.nextFloat()});
			if (n++==99) {
				client.send("Now");
				n=0;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
