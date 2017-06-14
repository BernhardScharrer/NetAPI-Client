package networking.channels;

/**
 * represents a type of channel
 * which online accepts string transfer.
 */
public abstract class StringChannel extends ObjectChannel {

	public StringChannel(String name) {
		super(name);
	}

	@Override
	protected void recieve(Object obj) {
		if (obj instanceof String) incoming((String) obj);
		else console.error("Wrong type! (" + obj.getClass().toString() + ")");
	}

	protected abstract void incoming(String msg);

	@Override
	public ChannelType getType() {
		return ChannelType.STRING;
	}
	
}
