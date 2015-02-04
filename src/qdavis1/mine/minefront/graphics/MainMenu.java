package qdavis1.mine.minefront.graphics;

import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import qdavis1.mine.minefront.Launcher;
/**
Creates an array called Pixels. The Pixels array is altered and formed by many loops and loaded .png images.
 */
public class MainMenu {
	public static int width;
	public static int height;
	public static int[] pixels;
	public static Render diamond = loadBitmap("/textures/diamond.png");
	private Render dirt = loadBitmap("/textures/dirt.png");
	private Render cement = loadBitmap("/textures/cement.png");
	private Render caves = loadBitmap("/textures/caves.png");
	private Render controls = loadBitmap("/textures/controls.png");
	public static boolean[] solidPixels;
	public static int[] levelPixels;
	public static int[] textureDenoterFire;
	public static int[] textureDenoterNuc;
	private int cavesWidth = 450;
	private Random random = new Random();
	private int tDSize = 20;
	public static boolean rePixel = true;
	public static int shiftX;
	public static int shiftY;
	/**
	Sets the classes static variables. Creates arrays of ints.
	@param width The width of the menu.
	@param height The height of the menu.
	 */
	public MainMenu(int width, int height){
		MainMenu.width = width;
		MainMenu.height= height;
		pixels = new int[width*height];
		solidPixels = new boolean[width*height];
		levelPixels = new int[width*height];
		textureDenoterFire = new int[tDSize*tDSize];
		textureDenoterNuc = new int[tDSize*tDSize];
		MainMenu.shiftX = 1000/2-15;
		MainMenu.shiftY = 600/2+60;

	}
	/**
	Alters the class's Pixels array.
	 */
	public void render(){
		for (int i = 0; i < width * height; i++) {
			pixels[i] = dirt.pixels[i];
		}
		for(int xx = 40;xx<960;xx+=40){
			for(int yy = 80;yy<560;yy+=40){
				for(int y = 0; y < 40; y++){
					for(int x = 0; x < 40; x++){
						if(!((yy>100&&yy<500)&&(xx>60&&xx<920))){
							if(cement.pixels[x+y*40]==0){
								continue;
							}
							solidPixels[xx+x+ (yy+y) * width]=true;
							pixels[xx+x+ (yy+y) * width] = cement.pixels[x+y*40];
						}
					}
				}
			}
		}
		int caveTop=160;
		for(int xx = 140;xx<830;xx+=40){
			for(int y = 0; y < 40; y++){
				for(int x = 0; x < 40; x++){
					if(x<400){
						if(cement.pixels[x+y*40]==0){
							continue;
						}
						solidPixels[xx+x+ (caveTop+y) * width]=true;
						pixels[xx+x+ (caveTop+y) * width] = cement.pixels[x+y*40];
					}
				}
			}
		}
		for(int xx = 140;xx<880;xx+=136){
			for(int yy = caveTop+40;yy<320;yy+=40){
				for(int y = 0; y < 40; y++){
					for(int x = 0; x < 40; x++){
						if(cement.pixels[x+y*40]==0){
							continue;
						}
						solidPixels[xx+x+ (yy+y) * width]=true;
						pixels[xx+x+ (yy+y) * width] = cement.pixels[x+y*40];
					}
				}
			}
		}
		int lowerCavesTop = 440;
		for(int xx = 140;xx<821;xx+=40){
			if(!((xx>720&&xx<780)||(xx>600&&xx<640)||(xx>420&&xx<520)||(xx>300&&xx<380)||(xx>200&&xx<240)))
				for(int y = 0; y < 40; y++){
					for(int x = 0; x < 40; x++){
						if(!(
								(((xx>150&&xx<220)&&(x>30))||((xx>180&&xx<290)&&(x<10)))||
								(((xx>660&&xx<720)&&(x>30))||((xx>720&&xx<800)&&(x<10)))
								)){//widen the texture changing portals
						if(cement.pixels[x+y*40]==0){
							continue;
						}
						solidPixels[xx+x+ (lowerCavesTop+y) * width]=true;
						pixels[xx+x+ (lowerCavesTop+y) * width] = cement.pixels[x+y*40];
						}
					}
				}
		}
		int lowerCavesWalls = 480;
		for(int xx = 140;xx<821;xx+=40){
			if(!((xx>680&&xx<800)||(xx>600&&xx<660)||(xx>400&&xx<580)||(xx>300&&xx<380)||(xx>140&&xx<280)))
				for(int y = 0; y < 40; y++){
					for(int x = 0; x < 40; x++){
						if(cement.pixels[x+y*40]==0){
							continue;
						}
						solidPixels[xx+x+ (lowerCavesWalls+y) * width]=true;
						pixels[xx+x+ (lowerCavesWalls+y) * width] = cement.pixels[x+y*40];
					}
				}
		}
		for(int y = 0; y < 90; y++){//mini entrance
			for(int x = 0; x < 90; x++){
				if(caves.pixels[x+y*450]==0){
					continue;

				}else{
					int pixel = x+182+ (y+caveTop+48) * width;
					pixels[pixel] = caves.pixels[x+y*cavesWidth];
					levelPixels[pixel]=1;
				}
			}
		}
		for(int y = 0; y < 90; y++){//moderate entrance
			for(int x = 0; x < 90; x++){
				if(caves.pixels[x+90+y*cavesWidth]==0){
					continue;

				}else{
					int pixel = x+182+136+ (y+caveTop+48) * width;
					pixels[pixel] = caves.pixels[x+90+y*cavesWidth];
					levelPixels[pixel]=2;
				}
			}
		}
		for(int y = 0; y < 90; y++){//colossal entrance
			for(int x = 0; x < 90; x++){
				if(caves.pixels[x+180+y*cavesWidth]==0){
					continue;

				}else{
					int pixel = x+182+136*2+ 1+(y+caveTop+48) * width;
					pixels[pixel] = caves.pixels[x+180+y*cavesWidth];
					levelPixels[pixel]=3;
				}
			}
		}
		for(int y = 0; y < 90; y++){//tomb raider entrance
			for(int x = 0; x < 90; x++){
				if(caves.pixels[x+270+y*cavesWidth]==0){
					continue;

				}else{
					int pixel = x+182+136*3+ 1+(y+caveTop+48) * width;
					pixels[pixel] = caves.pixels[x+270+y*cavesWidth];
					levelPixels[pixel]=4;
				}
			}
		}
		for(int y = 0; y < 90; y++){//custom entrance
			for(int x = 0; x < 90; x++){
				if(caves.pixels[x+360+y*cavesWidth]==0){
					continue;

				}else{
					int pixel = x+182+136*4+ 1+(y+caveTop+48) * width;
					pixels[pixel] = caves.pixels[x+360+y*cavesWidth];
					levelPixels[pixel]=5;
				}
			}
		}

		if(rePixel){
			for(int i = 0; i<tDSize*tDSize; i++)
			{
				if(!Launcher.nuclearFallout){
					if(i%8==0){
						if(i%6<3){
							textureDenoterFire[i]=0x1B83FA;
						}else{
							textureDenoterFire[i]=0xFA921B;
						}
					}else{
						textureDenoterFire[i]=0;
					}
				}else{
					if(i%8==0){
						if(i%6<3){
							textureDenoterFire[i]=0xADADAA;
						}else{
							textureDenoterFire[i]=0x565752;
						}
					}else{
						textureDenoterFire[i]=0;
					}
				}
			}
			for(int i = 0; i<tDSize*tDSize; i++)
			{
				if(Launcher.nuclearFallout){
					if(i%8==0){
						if(i%6<3){
							textureDenoterNuc[i]=0x02EB0A;
						}else{
							textureDenoterNuc[i]=0xE1F505;
						}
					}else{
						textureDenoterNuc[i]=0;
					}
				}else{
					if(i%8==0){
						if(i%6<3){
							textureDenoterNuc[i]=0xADADAA;
						}else{
							textureDenoterNuc[i]=0x565752;
						}
					}else{
						textureDenoterNuc[i]=0;
					}
				}
			}
			rePixel = false;
		}
		double speed = 1200.0;//larger= slower and also shorter tail
		for(int i=0; i<300; i++)
		{
			int xS = (int) (Math.sin((System.currentTimeMillis()+i)%speed/speed*Math.PI*2)*40);
			int yS = (int) (Math.cos((System.currentTimeMillis()+i)%speed/speed*Math.PI*2)*6);
			for(int y = 0; y < tDSize; y++){
				for(int x = 0; x < tDSize; x++){
					if(textureDenoterFire[x+y*tDSize]==0){
						continue;
					}
					pixels[x+xS+230+ (y-yS+490) * width] = textureDenoterFire[x+y*tDSize];
				}
			}
			for(int y = 0; y < tDSize; y++){
				for(int x = 0; x < tDSize; x++){
					if(textureDenoterNuc[x+y*tDSize]==0){
						continue;
					}
					pixels[x+xS+750+ (y-yS+490) * width] = textureDenoterNuc[x+y*tDSize];
				}
			}
			for(int y = 0; y < tDSize; y++){
				for(int x = 0; x < tDSize; x++){
					if(textureDenoterFire[x+y*tDSize]==0){
						continue;
					}
					pixels[x-xS+230+ (y+yS+490) * width] = textureDenoterFire[x+y*tDSize];
				}
			}
			for(int y = 0; y < tDSize; y++){
				for(int x = 0; x < tDSize; x++){
					if(textureDenoterNuc[x+y*tDSize]==0){
						continue;
					}
					pixels[x-xS+750+ (y+yS+490) * width] = textureDenoterNuc[x+y*tDSize];
				}
			}
		}
		for(int xx = 0;xx<1000;xx++){
			for(int yy = 0;yy<600;yy++){
				if(((yy<50||yy>550)||(xx<60||xx>940))){
					solidPixels[xx+ (yy) * width]=true;
				}
			}
		}
		
		for(int y = 0; y < 30; y++){//diamond
			for(int x = 0; x < 150; x++){
				if(controls.pixels[x+y*150]==0){
					continue;
				}
				else{
					pixels[x+1000/2-75+ (y+485) * width] = controls.pixels[x+y*150];
					if(random.nextInt(40)<1){//computer board typing animation
						pixels[x+1000/2-75+ (y+485) * width] = 0x000000;
						pixels[x+1000/2-75+ (y+485) * width] = 0x000000;
					}
				}
			}
		}
		
		for(int y = 0; y < 35; y++){//diamond
			for(int x = 0; x < 35; x++){
				if(diamond.pixels[x+y*35]==0){
					if(random.nextInt(10)<1&&y<10){//diamond reflection
						pixels[x+shiftX+ (y+MainMenu.shiftY) * width] = 0xFFFFFF;
					}
					continue;
				}
				else{
					pixels[x+shiftX+ (y+MainMenu.shiftY) * width] = diamond.pixels[x+y*35];
				}
			}
		}
	}


	private static Render loadBitmap(String fileName){
		try{
			BufferedImage image = ImageIO.read(Texture.class.getResource(fileName));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width,height);
			image.getRGB(0,0,width,height, result.pixels, 0, width);
			return result;
		}catch(Exception e){
			System.out.println("crash");
			throw new RuntimeException(e);
		}

	}
}
