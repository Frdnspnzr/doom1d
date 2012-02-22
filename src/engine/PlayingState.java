/**
 * 
 */
package engine;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import base.PublicGameStates;

/**
 * @author Pascal
 *
 */
public class PlayingState extends BasicGameState {
	
	private Lane lane;

	/*
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		this.lane = new BasicLane();
		this.lane.generate(1000, 1);
		
	}

	/*
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		int renderHeight = 4;
		Font text = new AngelCodeFont("font/consolas.fnt", "font/consolas_0.png");
		
		this.lane.render((container.getHeight()/2)-(renderHeight/2), renderHeight, container.getWidth());
		
		//Zusatzinformationen Ÿber den Spieler rendern
		text.drawString((container.getHeight()/2)+(renderHeight/2), 0, "HP: " + PlayerInfo.health + " AMMO: " + PlayerInfo.ammo);
		
	}

	/*
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		this.lane.update(container, game, delta);
		
	}

	/*
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		return PublicGameStates.singleplayerID;
	}

}
