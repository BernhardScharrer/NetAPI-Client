package main;

import java.util.Scanner;

import networking.Connection;
import networking.DefaultConsole;
import networking.Packet;
import networking.channel.PacketChannel;

public class TestClient {
	
	public static void main(String[] args) {
		
		Connection con = new Connection("localhost", 7777, new DefaultConsole());
		
//		con.addChannel(new PacketChannel("PACKET", con) {
//			protected void incoming(Packet packet) {
//				
//				
//				
//			}
//		});
		
		Scanner scanner = new Scanner(System.in);
		
		String line = "";
		
		while ((line = scanner.nextLine())!=null) {
			con.send(line);
		}
		
	}
	
}
