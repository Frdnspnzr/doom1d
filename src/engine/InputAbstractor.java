/**
 * 
 */
package engine;

import org.newdawn.slick.Input;

/**
 * @author Pascal
 * Abstrahiert Benutzereingaben in Methodenaufrufe an einem zu �bergebenden Interface
 */
public interface InputAbstractor {
	
	/*
	 * Wandelt Benutzereingaben in Methodenaufrufe um
	 * 
	 * @author Pascal
	 * @param input Input-Handler von Slick
	 * @param receiver Objekt, das die Methoden empf�ngt
	 * @param delta Zeit seit letztem Aufruf in ms
	 */
	public void doMagic(Input input, InputReceiver receiver, int delta);

}
