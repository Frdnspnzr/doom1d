/**
 * 
 */
package engine;

/**
 * @author Pascal
 * Enthält öffentlich alles, was Einfluss auf die Spielgeschwindigkeit haben könnte
 */
public interface SpeedVars {

	public int offsetShoot = 400;
	public int offsetRun = 30;
	public int offsetUse = 300;
	
	public int bulletFlightSpeed = 15;
	
	public int baseKIRadius = 400;
	public int modKIRadius = 300;
	
}
