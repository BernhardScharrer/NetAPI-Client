package networking.utils;

public interface Listener<TYPE> {

	public void incoming(TYPE type);
	
}
