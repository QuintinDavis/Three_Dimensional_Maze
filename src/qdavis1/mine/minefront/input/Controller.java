package qdavis1.mine.minefront.input;

import qdavis1.mine.minefront.Display;
import qdavis1.mine.minefront.Game;
import qdavis1.mine.minefront.level.Level;
/**
Tracks movement using the static variables x and z. 
 */
public class Controller {

	public static double x= 25;
	public static double z=25;
	public static double rotation= 7;
	public double y, xa, za, rotationa;
	public static boolean turnRight, turnLeft = false;
	public static boolean bobbing = false;
	public static boolean crouching = false;
	public static boolean jumping = false;
	public static boolean running = false;
	public static boolean map = false;
	public static boolean bomb = false;
	public static int arrowUp=0;
	public static int arrowLeft=0;
	public static boolean showHUD=true;
	/**
	Alters the y, rotation, x, and z values depending on if the moves are valid, dependent on collision detection.
	@param forward True if the user wants to attempt to move forward.
	@param back True if the user wants to attempt to move back.
	@param left True if the user wants to attempt to move left.
	@param right True if the user wants to attempt to move right.
	@param jump True if the user wants to jump.
	@param crouch True if the user wants to crouch.
	@param run True if the user want to run.
	@param map True if the user wants to show the pop-up map.
	@param bomb True if the user wants to use a bomb.
	@param hideHUD True if the user wants toggle the HUD.
	 */
	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump,
		boolean crouch, boolean run, boolean map, boolean bomb, boolean hideHUD) {
		double rotationSpeed = 0.002 * Display.mouseSpeed;
		double walkSpeed = .5;
		double jumpHeight = .75;
		double crouchHeight = .85;
		double xMove = 0;
		double zMove = 0;
		bobbing = false;
		crouching = false; 
		jumping = false;
		running = false;
		Controller.map = map;
		if(hideHUD&&(System.currentTimeMillis()-Game.lastHUDChange>200)){
			Game.lastHUDChange=System.currentTimeMillis();
			Controller.showHUD = !Controller.showHUD;
		}
		

		if(bomb&&(System.currentTimeMillis())-Game.lastBomb>200&&Game.bombs>0){//half second of reaction time to get out of fire
			Game.bombs--;
			Game.lastBomb=System.currentTimeMillis();
			for(int y2 = (int) (Controller.z/8-4); y2 < (int) (Controller.z/8+4); y2++){
				for(int x2 = (int) (Controller.x/8-4); x2 < (int) (Controller.x/8+4); x2++){
					try{
						if(!((x2<1)||(y2<1)||(x2>Level.width-2)||(y2>Level.height-2))){//ensure not to destroy outer boundary
							Level.blocks[x2+y2*Level.width].solid=false;
						}
					}catch(ArrayIndexOutOfBoundsException e){
						
						continue;
					}
				}
			}
		}
		
		if(forward){
			outerLoop:{ //collision checking
			for(int y1 = 0; y1 < Level.height-0; y1++){
				for(int x1 = 0; x1 < Level.width-0; x1++){
					if(Level.blocks[x1+y1*Level.width].solid==true){
						if((x1-.6)<(Controller.x/8)&&(x1+1.4)>(Controller.x/8)&&(y1-.6)<(Controller.z/8)&&(y1+1.4)>(Controller.z/8)){
							zMove-=10;
							if((System.currentTimeMillis())-Game.lastHit>500){//half second of reaction time to get out of fire
							Game.health--;
							if(Game.health<0){
								Display.gameOver=true;
//								Controller.showHUD=false;
							}
							Game.bombs=Game.bombsPerLife;
							Game.lastHit=System.currentTimeMillis();
							}
							if(Controller.x<0||Controller.z<0){
								Game.health=0;
								Game.bombs=0;
							}
							break outerLoop;
						}
					}
				}
			}
			if(Level.blocks[(int)(Controller.x/8)+(int)(Controller.z/8)*Level.width].victory==true){
				Display.win=true;
				Display.gameOver=true;
				Display.timeAtWin=System.currentTimeMillis();
				Controller.showHUD=false;
			}
			zMove++;
			bobbing =true;
		}
		
		}
		if(back){
			outerLoop:{ //collision checking
			for(int y1 = 0; y1 < Level.height-0; y1++){
				for(int x1 = 0; x1 < Level.width-0; x1++){
					if(Level.blocks[x1+y1*Level.width].solid==true){
						if((x1-.6)<(Controller.x/8)&&(x1+1.4)>(Controller.x/8)&&(y1-.6)<(Controller.z/8)&&(y1+1.4)>(Controller.z/8)){
							zMove+=10;
							if((System.currentTimeMillis())-Game.lastHit>500){//half second of reaction time to get out of fire
							Game.health--;
							if(Game.health<0){
								Display.gameOver=true;
//								Controller.showHUD=false;
							}
							Game.bombs=Game.bombsPerLife;
							Game.lastHit=System.currentTimeMillis();
							}
							if(Controller.x<0||Controller.z<0){
								Game.health=0;
								Game.bombs=0;
							}
							break outerLoop;
						}
					}
				}
			}
			if(Level.blocks[(int)(Controller.x/8)+(int)(Controller.z/8)*Level.width].victory==true){
				Display.win=true;
				Display.gameOver=true;
				Display.timeAtWin=System.currentTimeMillis();
				Controller.showHUD=false;
			}
		}
			zMove--;
			bobbing =true;
		}
		if(left){
			outerLoop:{ //collision checking
			for(int y1 = 0; y1 < Level.height-0; y1++){
				for(int x1 = 0; x1 < Level.width-0; x1++){
					if(Level.blocks[x1+y1*Level.width].solid==true){
						if((x1-.6)<(Controller.x/8)&&(x1+1.4)>(Controller.x/8)&&(y1-.6)<(Controller.z/8)&&(y1+1.4)>(Controller.z/8)){
							xMove+=10;
							if((System.currentTimeMillis())-Game.lastHit>500){//half second of reaction time to get out of fire
							Game.health--;
							if(Game.health<0){
								Display.gameOver=true;
//								Controller.showHUD=false;
							}
							Game.bombs=Game.bombsPerLife;
							Game.lastHit=System.currentTimeMillis();
							}
							if(Controller.x<0||Controller.z<0){
								Game.health=0;
								Game.bombs=0;
							}
							break outerLoop;
						}
					}
				}
			}
			if(Level.blocks[(int)(Controller.x/8)+(int)(Controller.z/8)*Level.width].victory==true){
				Display.win=true;
				Display.gameOver=true;
				Display.timeAtWin=System.currentTimeMillis();
				Controller.showHUD=false;
			}
		}
			xMove--;
			bobbing =true;

		}
		if(right){
			outerLoop:{ //collision checking
			for(int y1 = 0; y1 < Level.height-0; y1++){
				for(int x1 = 0; x1 < Level.width-0; x1++){
					if(Level.blocks[x1+y1*Level.width].solid==true){
						if((x1-.6)<(Controller.x/8)&&(x1+1.4)>(Controller.x/8)&&(y1-.6)<(Controller.z/8)&&(y1+1.4)>(Controller.z/8)){
							xMove-=10;
							if((System.currentTimeMillis())-Game.lastHit>500){//half second of reaction time to get out of fire
							Game.health--;
							if(Game.health<0){
								Display.gameOver=true;
//								Controller.showHUD=false;
							}
							Game.bombs=Game.bombsPerLife;
							Game.lastHit=System.currentTimeMillis();
							}
							if(Controller.x<0||Controller.z<0){
								Game.health=0;
								Game.bombs=0;
							}
							break outerLoop;
						}
					}
				}
			}
			if(Level.blocks[(int)(Controller.x/8)+(int)(Controller.z/8)*Level.width].victory==true){
				Display.win=true;
				Display.gameOver=true;
				Display.timeAtWin=System.currentTimeMillis();
				Controller.showHUD=false;
			}
			}
			xMove++;
			bobbing =true;
		}
		if(turnRight){
			rotationa += rotationSpeed;
			//			bobbing =true;
		}
		if(turnLeft){
			rotationa -= rotationSpeed;
			//			bobbing =true;
		}
		if(jump){
			y += jumpHeight;
			run = false;
			jumping = true;
			bobbing = false;
		}
		if(crouch){
			y -= crouchHeight;
			run = false;
			crouching = true;
			walkSpeed=0.2;
		}
		if(run)
		{
			walkSpeed=1;
			bobbing =true;
			running = true;
		}

		if(!forward && !back && !right && !left){
			bobbing = false;
		}

		if(bobbing == true&&Display.timeAtFirstMove==0){
			Display.timeAtFirstMove=System.currentTimeMillis();
		}
		xa+=(xMove *Math.cos(rotation)+zMove *Math.sin(rotation))*walkSpeed;
		za+=(zMove *Math.cos(rotation)-xMove *Math.sin(rotation))*walkSpeed;
		x+= xa;
		z+= za;
		y *=0.9;
		xa*=0.1;
		za*=0.1;
		rotation += rotationa;
		rotationa *= 0.2;
	}
}
