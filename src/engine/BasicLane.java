/**
 * 
 */
package engine;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Pascal
 *
 */
public class BasicLane implements Lane, InputReceiver {
	
	//Hier definieren wir uns mal ein paar Konstanten, was die Zahlen auf der Lane bedeuten
	
	private static final int pEmpty = 0;
	private static final int pPlayer = 1;
	private static final int pEnemy = 2;
	private static final int pBulletL = 3;
	private static final int pBulletR = 4;
	private static final int pDoor = 5;
	private static final int pExit = 6;
	
	private static final int flightSpeed = SpeedVars.bulletFlightSpeed;
	
	private int KIRadius = 200;
	
	private int lastFlight = 0;
	private int KISpeed = SpeedVars.baseKIRadius + (SpeedVars.modKIRadius / 2);
	private int lastKI = 0;
	
	InputAbstractor ia;
	AbstractList<Integer> level;
	
	BasicLane() {
		this.ia = new BasicInputAbstractor();
		this.level = new ArrayList<Integer>();
	}

	/*
	 * @see engine.Lane#render(int, int)
	 */
	@Override
	public void render(int fromTop, int height, int screenWidth) throws SlickException {
		
		int startWith = 0;
		int count = 0;
		Image content = null;
		
		//Wir starten immer drei Felder links vom Spieler
		startWith = this.playerPosition() - 3;
		
		//Wenn der Spieler aber so weit links steht, dass das nicht geht starten wir ganz am Anfang
		if (startWith < 0) startWith = 0;
		
		//Wir render soweit, wie das Spielfeld eben in den Bildschirm reinpasst
		count = (int) Math.ceil(screenWidth / height);
		
		//Wenn wir nicht mehr genug Spielfeld haben soweit rendern wie geht
		if (startWith + count >= this.level.size())
			count = this.level.size() - startWith;
		
		this.KIRadius = count;
		
		for (int i = 0; i < count; i++) {
			int here = this.level.get(i+startWith);
			
			if (here == pEmpty) {
				content = new Image("art/texture_empty.png");
			} else if (here == pPlayer) {
				content = new Image("art/texture_player.png");
			} else if (here == pEnemy) {
				content = new Image("art/texture_enemy.png");
			} else if (here == pDoor) {
				content = new Image("art/texture_door.png");
			} else if (here == pBulletL || here == pBulletR) {
				content = new Image("art/texture_bullet.png");
			}
			
			content.draw(i*height,fromTop,height,height);
			
		}		
		
	}

	/*
	 * @see engine.Lane#generate(int, int)
	 */
	@Override
	public void generate(int length, int skill) {
		
		Random random = new Random();
		int ready = 0;
		float skillMod = 1;
		
		//TODO Skillmod setzen
		
		level.clear();
		
		//Die ersten zehn Elemente immer leer
		for (int i = 0; i < 10; i++) {
			level.add(i,pEmpty);
			ready++;
		}
		
		//Spieler startet an Position 3
		level.set(2,pPlayer);
		
		while (ready < length) {
			
			float chance = random.nextFloat() * 100;
			
			if (chance < 3 * skillMod) {
				level.add(ready,pEnemy);
			} else if (chance < 6) {
				level.add(ready,pDoor);
			} else {
				level.add(ready,pEmpty);
			}
			
			ready++;
		}
		

	}

	/*
	 * @see engine.InputReceiver#PlayerRunRight()
	 */
	@Override
	public void PlayerRunRight() {
		
		int playerPos = this.playerPosition();
		
		if (playerPos < this.level.size()) {
			if (this.level.get(playerPos+1) == pEmpty) {
				this.level.set(playerPos+1,pPlayer);
				this.level.set(playerPos, pEmpty);
			}	
		}
	}

	/*
	 * @see engine.InputReceiver#PlayerRunLeft()
	 */
	@Override
	public void PlayerRunLeft() {
		
		int playerPos = this.playerPosition();
		
		if (playerPos > 0) {
			if (this.level.get(playerPos-1) == pEmpty) {
				this.level.set(playerPos-1,pPlayer);
				this.level.set(playerPos, pEmpty);
			}		
		}
	}

	/*
	 * @see engine.InputReceiver#PlayerShoot()
	 */
	@Override
	public void PlayerShoot() {
		
		int playerPos = this.playerPosition();
		
		if (PlayerInfo.ammo > 0) {
		
			if (playerPos + 1 < this.level.size()) {
				
				//Wenn Platz ist feuern
				if (this.level.get(playerPos+1) == pEmpty) {
					this.level.set(playerPos + 1,pBulletR);
					PlayerInfo.ammo--;
					
			    //Wenn dort ein Gegner steht Instant Kill
				} else if (this.level.get(playerPos+1) == pEnemy) {
					this.level.set(playerPos+1,pEmpty);
					PlayerInfo.ammo--;
				}
			}
		}
	
	}

	/*
	 * @see engine.InputReceiver#PlayerUse()
	 */
	@Override
	public void PlayerUse() {
		
		if (this.level.get(this.playerPosition()+1) == pDoor) {
			this.level.set(this.playerPosition()+1,pEmpty);
		}
		
	}

	/*
	 * @see engine.Lane#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		ia.doMagic(gc.getInput(), this, delta);
		
		this.lastFlight += delta;
		
		//Kugeln fliegen lassen
		if (this.lastFlight >= flightSpeed) {
			this.lastFlight = 0;
			for (int i = 0; i < this.level.size(); i++) {
				
				//Bei nach rechts fliegenden Kugeln
				if (this.level.get(i) == pBulletR) {
					
					//Mit nach Links fliegenden Kugeln tauschen
					if (this.level.get(i+1) == pBulletL) {
						this.level.set(i, pBulletL);
						this.level.set(i+1, pBulletR);
						i++;
						
					//Gegner tšten und Kugel vernichten
					} else if (this.level.get(i+1) == pEnemy) {
						this.level.set(i, pEmpty);
						this.level.set(i+1, pEmpty);
						
					//An TŸren einfach aufhšren
					} else if (this.level.get(i+1) == pDoor || this.level.get(i+1) == pExit) {
						this.level.set(i,pEmpty);
						
					//Wenn daneben leer ist weiterfliegen
					} else {
						this.level.set(i,pEmpty);
						this.level.set(i+1, pBulletR);
						i++;
					}
				}
				
				//Nach Links fliegend Kugeln
				if (this.level.get(i) == pBulletL) {
					
					//Kugel trifft Spieler
					if (this.level.get(i-1) == pPlayer) {
						this.level.set(i,pEmpty);
						PlayerInfo.health--;
						
					//Friendly fire isn't
					} else if (this.level.get(i-1) == pEnemy) {
						this.level.set(i-1, pEmpty);
						this.level.set(i, pEmpty);	
						
					//Von TŸren aufhalten lassen
					} else if (this.level.get(i-1) == pDoor) {
						this.level.set(i,pEmpty);
					}
					
					//Einfach weiterfliegen
					else if (this.level.get(i-1) == pEmpty) {
						this.level.set(i,pEmpty);
						this.level.set(i-1,pBulletL);
					}
					
				}
			}
		}
		
		//"KI"
		this.lastKI += delta;
		if (this.lastKI >= this.KISpeed) {
			this.KISpeed = (int) (SpeedVars.baseKIRadius + (new Random().nextFloat() * SpeedVars.modKIRadius));
			this.lastKI = 0;
			
			for (int i = 0; i < this.level.size(); i++) {
				
				if (this.level.get(i) == pEnemy) {
				
					int useRadius = KIRadius;
					if (i-useRadius < 0) useRadius = i;
						
					for (int k = 0; k < useRadius; k++) {
						if (this.level.get(i-k) == pPlayer) {
							
							if (this.level.get(i-1) == pEmpty) {
								this.level.set(i-1, pBulletL);
								k = KIRadius;
							}
						}
					}
				}
			}
		}
	}
	
	private int playerPosition() {
		for (int i = 0; i < this.level.size(); i++) {
			if (this.level.get(i) == pPlayer)
				return i;
		}
		return -1;
	}

}
