package qdavis1.mine.minefront.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import qdavis1.mine.minefront.Game;
/**
Loads .pngs, gets an array pixels and the height and width from the images loaded.
 */
public class Texture {
	public static int dimension = 0;
	public static Render floor = loadBitmap("/textures/textures.png");
	public static Render life = loadBitmap("/textures/life.png");
	public static Render bomb = loadBitmap("/textures/bomb.png");
	public static Render customMaze = loadBitmap("/textures/TombRaider.png");
	
	private static Render loadBitmap(String fileName){
		try{
			BufferedImage image = ImageIO.read(Texture.class.getResource(fileName));
			int width = image.getWidth();
			int height = image.getHeight();
			Game.length=height;
			Game.width=width;
			Render result = new Render(width,height);
			image.getRGB(0,0,width,height, result.pixels, 0, width);
			return result;
		}catch(Exception e){
			System.out.println("crash");
			throw new RuntimeException(e);
		}
		
	}
			
}
