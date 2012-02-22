/**
 * 
 */
package base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Pascal
 *
 */
public class VorspannState extends BasicGameState {
	
	//Speichert, wie lange der Startbildschirm schon angezeigt wird
	private int aroundFor = 0;
	private GameStateBackfire backfire;
	
	private Image cover;
	
	private static final int fadeOut = 1000;
	private static final int fadeIn = 1000;
	private static final int stayFor = 3000;
	
	VorspannState(GameStateBackfire b) {
		this.backfire = b;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		cover = new Image("art/title.png");

	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		float percent = 100;
		
		if (aroundFor <= fadeIn) {
			percent = aroundFor / fadeIn * 100;
		} else if (aroundFor > fadeIn && aroundFor <= (fadeIn + stayFor)) {
			percent = 100;
		} else {
			percent = 100 - ((aroundFor - fadeIn - stayFor) / fadeOut * 100);
			
		}
		
		cover.setAlpha(percent);
		cover.drawCentered(container.getWidth()/2,container.getHeight()/2);
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		this.aroundFor += delta;
		
		//aroundFor gibt in Millisekunden an, wie lange der Bildschirm schon angezeigt wird
		//Wenn er schon so lange angezeigt wird, wie die Animation insgesamt braucht, versuchen,
		//wieder auszublenden
		if (this.aroundFor > (fadeOut + fadeIn + stayFor)) {
			this.backfire.ended(this.getID());
		}

	}

	/*
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		return PublicGameStates.vorspannID;
	}

}
