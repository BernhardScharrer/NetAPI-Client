package bernhard.scharrer.netapi.client;

public class NetAPI {
	
	private static Client client;
	
	public static Client start(String ip, int port, TCPModul tcp, ConsoleType type) {
		client = new Client(ip, port, tcp, getConsole(type));
		return client;
	}
	
	public static Client start(String ip, int port, TCPModul tcp, Console console) {
		client = new Client(ip, port, tcp, console);
		return client;
	}
	
	public static Client start(String ip, int port, TCPModul tcp, UDPModul udp, int uport, int buffer_length, Console console) {
		client = new Client(ip, port, tcp, console);
		client.initUDP(udp, uport, buffer_length);
		return client;
	}
	
	private static Console getConsole(ConsoleType type) {
		switch (type) {
		case LINUX: new LinuxConsole();
		case RAW: new RawConsole();
		case WINDOWS: return new WindowsConsole();
		}
		return null;
	}
	
	public static void stop() {
		if (client != null) {
			client.cleanUp();
		}
	}
	
}
