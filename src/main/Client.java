package main;

import networking.Connection;
import networking.DefaultConsole;

public class Client {
	
	private static Connection con;
	
	public static void connect() {
		
		con = new Connection("84.200.106.98", 7777, new DefaultConsole());
		
	}
	
	public static void disconnect() {
		
		con.close();
		
	}
	
	public static void send(String msg) {
		con.send(msg);
	}
	
}
