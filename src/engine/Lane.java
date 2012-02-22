/**
 * 
 */
package engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Pascal
 * Stellt eine Bahn dar, auf der der Spieler sich bewegt
 * Kann sich selber rendern
 */
public interface Lane {
	
	/*
	 * @author Pascal
	 * @param fromTop Abstand vom oberen Bildschirmrand, auf dem die Lane gerendert werden soll
	 * @apram height Hšhe, in der die Lane gerendert werden soll. Implizit auch die Breite eines Pixels
	 * 
	 * Stellt die Lane dar. 
	 */
	public void render(int fromTop, int height, int screenWidth) throws SlickException;
	
	/*
	 * @author Pascal
	 * @param length LŠnge der Lane
	 * @param  skill 0: Easy, 1: Normal, 2: Hard, 3: Hell, 4: Chief Petty Master Officer of Arms (SO REALISTIC!), 5: Lulz
	 * 
	 * Generiert die Lane per Zufallsgenerator
	 */
	public void generate(int length, int skill);
	
	/*
	 * @author Pascal
	 * @param gc GameContainer von Slick
	 * @param game StateBasedGame von Slick
	 * @param delta Zeit seit letztem Update in ms
	 * 
	 * 
	 */
	public void update(GameContainer gc, StateBasedGame game, int delta);

}
