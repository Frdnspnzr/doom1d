/**
 * 
 */
package base;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import engine.PlayingState;

/**
 * @author Pascal
 *
 */
public class StateGame extends StateBasedGame implements GameStateBackfire {

	public StateGame() {
		super("doom1d");
	}

	/*
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		this.addState(new VorspannState(this));
		this.addState(new PlayingState());
		this.enterState(PublicGameStates.vorspannID);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new StateGame());
			app.setDisplayMode(370, 240, false);
			app.start();
		} catch (SlickException e) {
			//Something went horribly wrong!
			e.printStackTrace();
		}
	}

	/*
	 * @see base.GameStateBackfire#ended(int)
	 */
	@Override
	public void ended(int gameStateID) {
		if (gameStateID == PublicGameStates.vorspannID)
			this.enterState(PublicGameStates.singleplayerID);
	}

}
