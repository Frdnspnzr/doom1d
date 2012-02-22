/**
 * 
 */
package engine;

import org.newdawn.slick.Input;

/**
 * @author Pascal
 *
 */
public class BasicInputAbstractor implements InputAbstractor {
		
	int timeSinceLastRunLeft = SpeedVars.offsetRun;
	int timeSinceLastRunRight = SpeedVars.offsetRun;
	int timeSinceLastShoot = SpeedVars.offsetShoot;
	int timeSinceLastUse = SpeedVars.offsetUse;

	/*
	 * @see engine.InputAbstractor#doMagic(org.newdawn.slick.Input, engine.InputReceiver, int)
	 */
	@Override
	public void doMagic(Input input, InputReceiver receiver, int delta) {
		
		//Alle Timer hochzählen
		if (this.timeSinceLastShoot < SpeedVars.offsetShoot) this.timeSinceLastShoot += delta;
		if (this.timeSinceLastRunLeft < SpeedVars.offsetRun) this.timeSinceLastRunLeft += delta;
		if (this.timeSinceLastRunRight < SpeedVars.offsetRun) this.timeSinceLastRunRight += delta;
		if (this.timeSinceLastUse < SpeedVars.offsetUse) this.timeSinceLastUse += delta;
		
		if (input.isKeyDown(Input.KEY_SPACE) && this.timeSinceLastShoot >= SpeedVars.offsetShoot) {
			receiver.PlayerShoot();
			this.timeSinceLastShoot = 0;
		}
		if (input.isKeyDown(Input.KEY_LEFT) && this.timeSinceLastRunLeft >= SpeedVars.offsetRun) {
			receiver.PlayerRunLeft();
			this.timeSinceLastRunLeft = 0;
		}
		if (input.isKeyDown(Input.KEY_RIGHT) && this.timeSinceLastRunRight >= SpeedVars.offsetRun) {
			receiver.PlayerRunRight();
			this.timeSinceLastRunRight = 0;
		}
		if (input.isKeyDown(Input.KEY_LSHIFT) && this.timeSinceLastUse >= SpeedVars.offsetUse) {
			receiver.PlayerUse();
			this.timeSinceLastUse = 0;
		}
	}

}
