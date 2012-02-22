/**
 * 
 */
package engine;

/**
 * @author Pascal
 * Wird bei der Input-Abstraktion verwendet, bei der Benutzereingaben intransparent in Methodenaufrufe dieses Interface gemappt werden.
 */
public interface InputReceiver {
	
	public void PlayerRunRight();
	public void PlayerRunLeft();
	public void PlayerShoot();
	public void PlayerUse();	

}
