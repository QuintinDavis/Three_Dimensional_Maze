package qdavis1.mine.minefront;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import qdavis1.mine.minefront.graphics.Screen;
import qdavis1.mine.minefront.input.Controller;
import qdavis1.mine.minefront.input.InputHandler;
/**
Display sets the settings for the game and adds the listeners for all user input.
Also, display implements runnable so that the canvas refreshes constantly on it's own thread.
 */
public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Tomb Raiders of the Lost Art";
	private Thread thread;
	private boolean running;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	public static Game game;
	private InputHandler input;
	private int newX=WIDTH/2;
	private int oldX=WIDTH/2;
	private int fps;
	public static int mouseSpeed;
	public static double timeAtFirstMove;
	public static double timeAtWin;
	public static boolean nuclearFallout;
	public static boolean win;
	public static boolean mainMenu;
	public static boolean gameOver;
	private int tombRaiderTime=80;//time allowed to complete tomb raider level, in seconds
	/**
	A Display is the visual used to display the maze game. Sets static variables to starting values.
	@param level signifies which level to generate(mini, moderate, colossal, tomb raider, or custom).
	@param width only useful when level is 5, to set custom map's width, passed on to Game().
	@param height only useful when level is 5, to set custom map's height, passed on to Game().
	@param lives only useful when level is 5, to set custom map's lives, passed on to Game().
	@param bombsPerLife only useful when level is 5, to set custom map's bombs, passed on to Game().
	@param wallDensity only useful when level is 5, to set custom map's density, passed on to Game().
	 */
	public Display(int level, boolean nuclearFallout, int width, int height, int lives, int bombsPerLife, int wallDensity) {
		Display.gameOver=false;
		Display.win=false;
		running =false;
		Display.mainMenu=false;
		Display.timeAtWin=0;
		Display.timeAtFirstMove=0;
		Controller.z=25;
		Controller.x=25;
		Dimension size= new Dimension (WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		Display.nuclearFallout=nuclearFallout;
		screen = new Screen(WIDTH, HEIGHT);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		game = new Game(level,width, height,lives,bombsPerLife,wallDensity);
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

	}
	/**
	Starts the thread, invokes run 
	 */
	public void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	/**
	Continuously updates the canvas by calling render and keeps track of the FPS.
	Public to give visibility to Runnable to invoke it.
	 */
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			requestFocus();
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
					render();	
				frames++;
			}
			render();
			frames++;
			if(mainMenu){
				RunGame.frame.dispose();
				Launcher lan = new Launcher();
				lan.start();
				stop();
			}
			newX = InputHandler.MouseX;
			if(newX > oldX)
			{
				Controller.turnRight=true;
			}
			if(newX < oldX)
			{
				Controller.turnLeft=true;
			}
			if(newX == oldX)
			{
				Controller.turnRight=false;
				Controller.turnLeft=false;
			}
			if(newX<=100||newX>=700){
				try {
					Robot robot = new Robot();

					robot.mouseMove(RunGame.frame.getLocation().x+WIDTH/2, RunGame.frame.getLocation().y+HEIGHT/2);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
			if(timeAtFirstMove==0){
				mouseSpeed=0;
			}else{
			mouseSpeed = Math.abs(newX-oldX);
			}
			oldX = newX;

		}

	}

	private void tick() {
		game.tick(input.key);
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render(game);
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.setFont(new Font("Veranda", 0, 20));
		g.setColor(Color.MAGENTA.darker().darker());
		g.drawString("fps: "+Integer.toString(fps), 10, 25);
		if(gameOver){
		if(win){
			g.setFont(new Font("Veranda", 1, 30));
			g.setColor(Color.WHITE);
			int threeSigFigs = (int) (Display.timeAtWin-Display.timeAtFirstMove)/100;
			g.drawString("Time Elapsed:     "+Double.toString(threeSigFigs/10.0)+ " X  -10  =  "+(-threeSigFigs) , WIDTH/2 - 300, HEIGHT/2-25);
			g.drawString("Lives Remaining:   "+Game.health+ "   X  400  =  "+Game.health*400 , WIDTH/2 - 300, HEIGHT/2);
			g.drawString("Bombs Remaining: "+Game.bombs+ "   X  50    =  "+Game.bombs*50 , WIDTH/2 - 300, HEIGHT/2+25);
			g.drawString("Final Score:                             = "+(-threeSigFigs+(Game.health*400)+Game.bombs*50) , WIDTH/2 - 300, HEIGHT/2+75);
		}else{
			g.setFont(new Font("Veranda", 1, 60));
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER", WIDTH/2 - 200, HEIGHT/2);
		}
		
		}
		
		if(Display.timeAtFirstMove!=0&&Controller.showHUD&&!Display.gameOver){
			if(Launcher.level!=4){
			g.setColor(Color.decode("0x9A00FA"));
			int threeSigFigs = (int) ((System.currentTimeMillis()-Display.timeAtFirstMove)/100);
			g.drawString("Time Elapsed: "+Double.toString(threeSigFigs/10.0), WIDTH/2 - 100, HEIGHT-10);
			}else{
				g.setColor(Color.decode("0x9A00FA"));
				int threeSigFigs = (int) (tombRaiderTime*10-(System.currentTimeMillis()-Display.timeAtFirstMove)/100);
				if(threeSigFigs<0){
					Display.gameOver=true;
				}
				g.drawString("Time Remaining: "+Double.toString(threeSigFigs/10.0), WIDTH/2 - 100, HEIGHT-10);
			}
		}
		g.dispose();
		bs.show();
	}

}
