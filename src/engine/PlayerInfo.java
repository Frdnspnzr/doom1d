/**
 * 
 */
package engine;

/**
 * @author Pascal
 *
 */
public class PlayerInfo {
	
	public static int health = 100;
	public static int ammo = 30;
	
	public static void addHealth(int add) {
		health += add;
		if (health > 100) health = 100;
	}

}
