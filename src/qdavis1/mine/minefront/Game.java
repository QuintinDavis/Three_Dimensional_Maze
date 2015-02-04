package qdavis1.mine.minefront;

import java.awt.event.KeyEvent;

import qdavis1.mine.minefront.graphics.Texture;
import qdavis1.mine.minefront.input.Controller;
import qdavis1.mine.minefront.input.MenuController;
import qdavis1.mine.minefront.level.CustomLevel;
import qdavis1.mine.minefront.level.GeneratedLevel;
import qdavis1.mine.minefront.level.Level;
/**
Handles setting up the width, length, health, etc... of a maze.
Also, passes along user inputs to the appropriate controller classes.
 */
public class Game {
	public static int time;
	public Controller controls;
	public MenuController menuControls;
	public Level level;
	public static int width;
	public static int length;
	public static int health;
	public static int bombs;
	public static long lastHit = System.currentTimeMillis();
	public static long lastBomb = System.currentTimeMillis();
	public static long lastHUDChange = System.currentTimeMillis();
	public static double brightness = 1;
	public static int bombsPerLife;
	public static int wallDensity;
	
	/**
	Sets Game's static variables to appropriate values and creates a level depending on the params passed. 
	@param levelType signifies which level to generate(mini, moderate, colossal, tomb raider, or custom)
	@param width only used when level is 5, to set custom map's width
	@param height only used when level is 5, to set custom map's height
	@param lives only used when level is 5, to set custom map's lives
	@param bombsPerLife only used when level is 5, to set custom map's bombs
	@param wallDensity only used when level is 5, to set custom map's density
	 */
	public Game(int levelType, int width, int height, int lives, int bombsPerLife, int wallDensity){
		Game.health=5;
		Game.bombs=5;
		Game.bombsPerLife=5;
		Game.wallDensity=5;
		Game.length= Texture.customMaze.height;
		Game.width= Texture.customMaze.width;
		if(levelType!=0){
			if(levelType==1){
				Game.width = 40;
				Game.length = 60;
				level = new GeneratedLevel(Game.width,Game.length);	
			}
			else if(levelType==2){
				Game.width = 80;
				Game.length = 120;
				level = new GeneratedLevel(Game.width,Game.length);	
			}
			else if(levelType==3){
				Game.width = 200;
				Game.length = 400;
				level = new GeneratedLevel(Game.width,Game.length);	
			}
			else if(levelType==4){//tomb raider expert level
				level = new CustomLevel(Game.width,Game.length);
				Game.health=1;
			}
			else if(levelType==5){
				Game.bombsPerLife=bombsPerLife;
				Game.bombs=bombsPerLife;
				Game.health=lives;
				Game.width =width;
				Game.length =height;
				Game.wallDensity=wallDensity;
				level = new GeneratedLevel(Game.width,Game.length);
			}
			
		}
		controls= new Controller();
		menuControls= new MenuController();	

	}
	/**
	Determines the movements that the user has entered via the keyboard and invokes the movement "controllers".
	@param key a array of boolean of the keys pressed. 
	 */
	public void tick(boolean key[]){
		time++;
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean right = key[KeyEvent.VK_D];
		boolean left = key[KeyEvent.VK_A];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crouch = key[KeyEvent.VK_C];
		boolean run = key[KeyEvent.VK_SHIFT];
		boolean map = key[KeyEvent.VK_M];
		boolean bomb = key[KeyEvent.VK_B];
		boolean arrowUp = key[KeyEvent.VK_UP];
		boolean arrowDown = key[KeyEvent.VK_DOWN];
		boolean arrowRight = key[KeyEvent.VK_RIGHT];
		boolean arrowLeft = key[KeyEvent.VK_LEFT];
		boolean hideHUD = key[KeyEvent.VK_H];
		if(key[KeyEvent.VK_ESCAPE]){
			Display.mainMenu=true;
		}
		if(!Display.gameOver&&Launcher.level!=0){
		controls.tick(forward, back, left, right, jump, crouch, run, map, bomb, hideHUD);
		}
		menuControls.tick(arrowUp, arrowDown, arrowRight, arrowLeft, run);
	}

}
