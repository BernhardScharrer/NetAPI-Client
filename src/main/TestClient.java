package main;

import networking.Connection;
import networking.DefaultConsole;

public class TestClient {
	
	public static void main(String[] args) {
		
		Connection con = new Connection("localhost", 7777, new DefaultConsole());
		
		System.out.println("Runs in extra thread...");
		
//		con.addChannel(new MyChannel(con));
		
	}
	
}
