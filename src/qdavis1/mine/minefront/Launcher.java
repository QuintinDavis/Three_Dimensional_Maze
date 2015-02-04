package qdavis1.mine.minefront;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import qdavis1.mine.minefront.graphics.MainMenu;
import qdavis1.mine.minefront.input.InputHandler;
/**
Launcher sets the settings for the launcher JFrame and adds the listeners for all user input.
Also, launcher is implements runnable so that the frame refreshes constantly on it's own thread.
 */
public class Launcher extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Launcher";
	private Thread thread;
	private boolean running = false;
	private BufferedImage img;
	private int[] pixels;
	public static Game game;
	private InputHandler input;
	private int fps;
	public double timeAtFirstMove = System.currentTimeMillis();
	public static Display display;
	private MainMenu menu;
	public static int level;
	public static boolean nuclearFallout;
	public static boolean controls;
	
	/**
	Launcher resets the static variable, level. Creates a Game object and add listeners to the JFrame.
	 */
	public Launcher(){		
		Launcher.level=0;
		MainMenu.rePixel=true;
		Launcher.controls=false;
		Launcher.nuclearFallout=false;
		menu = new MainMenu(WIDTH,HEIGHT);
		setTitle("Launcher");
		setSize(new Dimension(WIDTH,HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		game = new Game(0, 0,0,0,0,0);
		Display.mainMenu=true;
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		setVisible(true);
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

	private void renderMenu() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		menu.render();
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = MainMenu.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.setFont(new Font("TIMES NEW ROMAN", 0, 50));
		g.setColor(Color.BLUE.darker().darker());
		g.drawString("RAIDERS OF THE LOST ART", WIDTH/2-340, 70);
		
		g.setFont(new Font("Veranda", 0, 20));
		g.setColor(Color.MAGENTA.darker().darker());
		g.drawString("fps: "+Integer.toString(fps), 10, 50);
		g.setColor(Color.BLUE.darker().darker());
		if(System.currentTimeMillis()-4000<timeAtFirstMove)
		g.drawString("Use Arrow Keys to Move the Diamond", WIDTH/2-200, HEIGHT-15);
		g.setColor(Color.BLUE.darker().darker());
		g.setFont(new Font("Veranda", 0, 15));
		int yShift = 195;
		int xShift = 198;
		g.drawString("Mini", xShift+15, yShift-15);
		g.drawString("Moderate", xShift+136, yShift-15);
		g.drawString("Colossal", xShift+136*2, yShift-15);
		g.drawString("Fire & Ice", 190+15, 540);
		g.drawString("Controls/Information", 218+205, 540);
		g.drawString("Nuclear Fallout", 210+250*2, 540);
		g.drawString("Expert Tomb", xShift+136*3-18, yShift-15);
		g.drawString("Raiders Only", xShift+136*3-16, yShift);
		g.drawString("Custom", xShift+136*4, yShift);
		g.setFont(new Font("Veranda", 0, 10));
		g.drawString("40 by 60", xShift+10, yShift);
		g.drawString("80 by 120", xShift+136+10, yShift);
		g.drawString("200 by 400", xShift+136*2+2, yShift);
		g.dispose();
		bs.show();
	}
	/**
	Continuously updates the visual by calling renderMenu() and keeps track of the FPS.
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
				renderMenu();
			}
			frames++;
			if(Launcher.controls){
				dispose();
				new ControlsInfo();
				stop();
			}
			if(Launcher.level!=0){
				if(Launcher.level==5){
					dispose();
					new CustomMenu();
					stop();
				}else{
				dispose();
				new RunGame(level, nuclearFallout, 0, 0, 0, 0, 0);
				stop();
				}
			}
		}
			renderMenu();
			frames++;
	}

	private void tick() {
		game.tick(input.key);
	}

}
