package test;

import networking.Connection;
import networking.DefaultConsole;

public class ClientTest {
	
	public static void main(String[] args) {
		
		new Connection("localhost", 7777, new DefaultConsole());
		
	}
	
}
