/**
 * 
 */
package bernhard.scharrer.netapi.client;

/**
 * @author Bernhard Scharrer
 *
 */
public interface UDPModul {
	
	public void receive(int[] data);
	public void receive(float[] data);
	
}
