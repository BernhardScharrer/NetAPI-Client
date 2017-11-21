package bernhard.scharrer.netapi.client;

public class NetAPI {
	
	private static Client client;
	
	public static Client start(String ip, int port, TrafficListener listener) {
		printHeadline();
		client = new Client(ip, port, listener, new LinuxConsole(false));
		return client;
	}
	
	public static Client start(boolean headline, boolean debug, String ip, int port, TrafficListener listener) {
		if (headline) printHeadline();
		client = new Client(ip, port, listener, new LinuxConsole(debug));
		return client;
	}
	
	public static Client start(boolean headline, Console console, String ip, int port, TrafficListener listener) {
		if (headline) printHeadline();
		client = new Client(ip, port, listener, console);
		return client;
	}
	
	private static void printHeadline() {
		System.out.println("\n ____  _____        _        _       _______  _____  ");
		System.out.println("|_   \\|_   _|      / |_     / \\     |_   __ \\|_   _| ");
		System.out.println("  |   \\ | |  .---.`| |-'   / _ \\      | |__) | | |   ");
		System.out.println("  | |\\ \\| | / /__\\\\| |    / ___ \\     |  ___/  | |   ");
		System.out.println(" _| |_\\   |_| \\__.,| |, _/ /   \\ \\_  _| |_    _| |_  ");
		System.out.println("|_____|\\____|'.__.'\\__/|____| |____||_____|  |_____| ");
		System.out.println("");
		System.out.println("Written by Bernhard Scharrer\n");
	}
	
	public static void stop() {
		if (client != null) {
			client.cleanUp();
		}
	}
	
}
