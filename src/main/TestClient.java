package main;

import networking.Connection;
import networking.DefaultConsole;
import networking.Packet;
import networking.channel.PacketChannel;

public class TestClient {
	
	public static void main(String[] args) {
		
		Connection con = new Connection("localhost", 7777, new DefaultConsole());
		
		System.out.println("Runs in extra thread...");
		
		con.addChannel(new PacketChannel("PACKET", con) {
			protected void incoming(Packet packet) {
				
				
				
			}
		});
		
	}
	
}
