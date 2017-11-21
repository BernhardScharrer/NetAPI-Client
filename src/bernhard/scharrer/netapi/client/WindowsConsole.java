package bernhard.scharrer.netapi.client;

public class WindowsConsole extends Console {

	public WindowsConsole(boolean debug) {
		super(debug);
	}

	@Override
	public void debug(String debug) {
		System.out.println("[DEBUG] "+debug);
	}

	@Override
	public void info(String info) {
		System.out.println("[INFO] "+info);
	}

	@Override
	public void warn(String warn) {
		System.out.println("[WARN] "+warn);
	}

	@Override
	public void error(String error) {
		System.err.println("[ERROR] "+error);
	}
	
}
