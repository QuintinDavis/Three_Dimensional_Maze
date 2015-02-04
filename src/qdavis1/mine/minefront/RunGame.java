package qdavis1.mine.minefront;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
/**
The JFrame for the game-play. Creates a Display object and adds it to the JFrame.
 */
public class RunGame {
	public static JFrame frame;
	/**
	Sets up the JFrame for the game-play. Creates a Display object and adds it to the JFrame.
	@param level signifies which level to generate(mini, moderate, colossal, tomb raider, or custom).
	@param nuclearFallout only useful when level is 5, to set custom map's texture.
	@param width only useful when level is 5, to set custom map's width.
	@param height only useful when level is 5, to set custom map's height.
	@param lives only useful when level is 5, to set custom map's lives.
	@param bombsPerLife only useful when level is 5, to set custom map's bombs.
	@param wallDensity only useful when level is 5, to set custom map's density.
	 */
	public RunGame(int level, boolean nuclearFallout, int width, int height, int lives, int bombsPerLife, int wallDensity){
		frame = new JFrame();
		Display game = new Display(level, nuclearFallout, width, height, lives, bombsPerLife, wallDensity);
		BufferedImage cursor =  new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");
		frame.getContentPane().setCursor(blank);
		frame.add(game);
		frame.pack();
		frame.setTitle(Display.TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		game.start();
	}
}
