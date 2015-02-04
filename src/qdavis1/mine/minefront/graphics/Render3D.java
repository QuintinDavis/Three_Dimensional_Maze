package qdavis1.mine.minefront.graphics;

import java.util.Random;

import qdavis1.mine.minefront.Display;
import qdavis1.mine.minefront.Game;
import qdavis1.mine.minefront.input.Controller;
import qdavis1.mine.minefront.level.Block;
import qdavis1.mine.minefront.level.Level;
/**
Used to render the walls, ceiling, and HUD components.
 */
public class Render3D extends Render {

	public double[] zBuffer;
	public double[] zBufferWall;
	private double renderDistance = 7500;
	Random random = new Random();
	private double forward, right, cosine, sine, up, walking;
	private int wallColor = 0x9A00FA;
	private int textureShift = 0;
	
	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width*height];
		zBufferWall = new double[width];
		if(Display.nuclearFallout){
			textureShift = 16;
		}
	}
	/**
	Used to render the walls and  ceiling.
	@param game A Game object. It's level variable is used when drawing the sides of the blocks. 
	 */
	public void floor(Game game) {
		for(int x = 0 ; x< width; x++){
			zBufferWall[x] = 0;
		}
		double floorPosition = 8.0;
		double ceilingPosition = 10.0;

		double bobHeight, bobSpeed;
		if(Controller.crouching){
			bobHeight = .3;
			bobSpeed = 6;
		}
		else if(Controller.running){
			bobHeight=.8;
			bobSpeed = 3;
		}
		else{
			bobHeight=.5;
			bobSpeed = 6;
		}

		right = Controller.x;
		forward = Controller.z;
		up = game.controls.y;
		walking = Math.sin(Game.time/bobSpeed)*bobHeight;
		double rotation =	Controller.rotation;
		if(Display.gameOver){
			Controller.rotation+=.02;
			Controller.bobbing=false;
		}
		if(Display.timeAtFirstMove==0){
			long temp=System.currentTimeMillis();
			int delay = 4000;
			if(temp%delay<delay/4){
				Controller.rotation= 6.25;
			}
			else if(temp%delay<delay/2){
				Controller.rotation= 7;
			}
			else if(temp%delay<delay/4*3){
				Controller.rotation= 8;
			}
			else{
				Controller.rotation= 7;
			}
			Controller.bobbing=false;
		}
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;
			double z = (floorPosition + up) / ceiling;
			if(Controller.bobbing){
				z = (floorPosition + up + walking) / ceiling;
			}
			if (ceiling < 0) {
				z = (ceilingPosition - up) / -ceiling;
			}
			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;
				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);
				zBuffer[x+y*width]=z;
				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7)+textureShift + (yPix & 7) * 32];
				if(z>250){
					pixels[x + y * width]=0;
				}
			}
		}
		Level level = game.level;
		int width =Game.width;
		int length =Game.length;
		for(double k =0;k<1;k+=0.5){
			for(int xBlock = -width; xBlock<=width;xBlock++)
			{
				for(int zBlock = -length; zBlock<=length;zBlock++)
				{
					Block block = level.create(xBlock, zBlock);
					Block east = level.create(xBlock+1, zBlock);
					Block south = level.create(xBlock, zBlock+1);
					if(block.solid){
						if(!east.solid){
							renderWall(xBlock+1, xBlock+1, zBlock, zBlock+1,k,true);
						}
						if(!south.solid){
							renderWall(xBlock+1, xBlock, zBlock+1, zBlock+1,k,true);
						}
					}else{
						if(east.solid){
							renderWall(xBlock+1, xBlock+1, zBlock+1, zBlock,k,true);
						}
						if(south.solid){
							renderWall(xBlock, xBlock+1, zBlock+1, zBlock+1,k,true);
						}	
					}
					if(block.victory){
						if(!east.victory){
							renderWall(xBlock+1, xBlock+1, zBlock, zBlock+1,k,false);
						}
						if(!south.victory){
							renderWall(xBlock+1, xBlock, zBlock+1, zBlock+1,k,false);
						}
					}else{
						if(east.victory){
							renderWall(xBlock+1, xBlock+1, zBlock+1, zBlock,k,false);
						}
						if(south.victory){
							renderWall(xBlock, xBlock+1, zBlock+1, zBlock+1,k,false);
						}	
					}
				}

			}
		}
	}
	/**
	Used to render the walls and  ceiling.
	@param xLeft The left x coordinate.
	@param xRight The right x coordinate.
	@param zDistanceLeft The left z coordinate.
	@param zDistanceRight The right z coordinate.
	@param yHeight The height/y coordinate.
	@param wall A boolean for whether the wall is present. 
	 */
	public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight, boolean wall){
		double upCorrect = 16;
		double rightCorrect = 1.0/16.0;
		double forwardCorrect = 1.0/16.0;
		double bobCorrect = walking/16.0;
		if(!Controller.bobbing){
			bobCorrect=0;
		}

		double xcLeft = ((xLeft/2) - (right*rightCorrect)) * 2.0;
		double zcLeft = ((zDistanceLeft/2) - (forward*forwardCorrect)) * 2.0;
		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) - (-up /upCorrect)+bobCorrect) * 2.0;
		double yCornerBL = ((+0.5 - yHeight) - (-up/upCorrect)+bobCorrect) * 2.0;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

		double xcRight = ((xRight/2)-(right*rightCorrect))*2.0;
		double zcRight = ((zDistanceRight/2)-(forward*forwardCorrect))*2.0;
		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight)-(-up /upCorrect)+bobCorrect)*2; 
		double yCornerBR = ((+0.5-yHeight)-(-up /upCorrect)+bobCorrect)*2.0; 
		double rotRightSideZ = zcRight * cosine + xcRight * sine;

		double tex30 = 0;
		double tex40 = 8.0;
		double clip = 0.5;

		if(rotLeftSideZ<clip && rotRightSideZ<clip){
			return;
		}
		if(rotLeftSideZ<clip){
			double clipper=(clip-rotLeftSideZ)/(rotRightSideZ-rotLeftSideZ);//Cohen-Sutherland algorithm
			rotLeftSideZ = rotLeftSideZ +(rotRightSideZ-rotLeftSideZ)*clipper;
			rotLeftSideX = rotLeftSideX +(rotRightSideX-rotLeftSideX)*clipper;
			tex30 = tex30 + (tex40 - tex30)*clipper;
		}
		if(rotRightSideZ<clip){
			double clipper=(clip-rotLeftSideZ)/(rotRightSideZ-rotLeftSideZ);//Cohen-Sutherland algorithm
			rotRightSideZ = rotLeftSideZ +(rotRightSideZ-rotLeftSideZ)*clipper;
			rotRightSideX = rotLeftSideX +(rotRightSideX-rotLeftSideX)*clipper;
			tex40 = tex30 + (tex40 - tex30)*clipper;
		}

		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width /2.0);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width /2.0);

		if (xPixelLeft >= xPixelRight){
			return;	
		}

		int xPixelLeftInt = (int) (xPixelLeft);
		int xPixelRightInt = (int) (xPixelRight);

		if(xPixelLeftInt < 0){
			xPixelLeftInt = 0;
		}
		if(xPixelRightInt > width){
			xPixelRightInt = width;
		}

		double yPixelLeftTop = (yCornerTL/rotLeftSideZ*height+height/2.0);
		double yPixelLeftBottom = (yCornerBL/rotLeftSideZ*height+height/2.0);
		double yPixelRightTop = (yCornerTR/rotRightSideZ*height+height/2.0);
		double yPixelRightBottom = (yCornerBR/rotRightSideZ*height+height/2.0);
		double tex1 = 1.0/rotLeftSideZ;
		double tex2 = 1.0/rotRightSideZ;
		double tex3 = tex30;//0/rotLeftSideZ;
		double tex4 = tex40 /rotRightSideZ-tex3;

		for(int x = xPixelLeftInt; x < xPixelRightInt; x++){
			double pixelRotation = (x-xPixelLeft)/(xPixelRight - xPixelLeft);

			double zWall = (tex1 + (tex2-tex1)*pixelRotation);
			if(zBufferWall[x]>zWall){
				continue;
			}
			zBufferWall[x]=zWall;
			int xTexture = (int) ((tex3+tex4*pixelRotation)/zWall);
			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop)*pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom)*pixelRotation;

			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);

			if(yPixelTopInt < 0){
				yPixelTopInt = 0;
			}
			if(yPixelBottomInt > height){
				yPixelBottomInt = height;
			}
			for(int y = yPixelTopInt; y < yPixelBottomInt; y++){
				double pixelRotationY = (y-yPixelTop)/(yPixelBottom-yPixelTop);
				int yTexture = (int)(8*pixelRotationY);
				if(wall){
					pixels[x + y * width] = Texture.floor.pixels[(xTexture & 7)+8 + textureShift+ (yTexture & 7) * 32];
				}
				else{
					pixels[x+y*width] = xTexture*100+yTexture*100*256;
				}
				zBuffer[x+y*width] = 1/(tex1 + (tex2-tex1)*pixelRotation)*8;
			}
		}
	}
	/**
	Limits the distance that pixels are drawn. As the depth increases, the brightness decreases.
	 */
	public void renderDistanceLimiter(){
		for(int k =0;k<width*height;k++){
			int colour = pixels[k];
			int brightness= (int) (renderDistance/ (zBuffer[k]/Game.brightness));

			if(brightness<0){
				brightness=0;
			}
			if(brightness>255){
				brightness=255;
			}
			int r= (colour >>16) & 0xff;
			int	g= (colour >>8) & 0xff;
			int b= (colour) & 0xff;
			r= r*brightness/255;
			g= g*brightness/255;
			b= b*brightness/255;
			pixels[k]= r<<16|g<<8|b;
		}
	}
	/**
	Renders the map, when the Controller.map boolean is true, a pop-up map is displayed.
	 */
	public void renderMap(){
		
		int playerColor = 0xE8170C;
		int finishColor = 0x05FA94;
		int shiftX = width-(Level.width+10);
		int shiftY = 10;
		if(!Controller.map){
		for(int y = 0; y < Level.height; y++){
			for(int x = 0; x < Level.width; x++){
				if(Level.blocks[x+y*Level.width].solid){
					pixels[x+shiftX+ (Level.height-y+shiftY) * width] = wallColor;
				}
				else{
					if(Level.blocks[x+y*Level.width].victory){
						pixels[x +shiftX + (Level.height-y+shiftY) * width] = finishColor;
					}
					else{
					pixels[x +shiftX + (Level.height-y+shiftY) * width] = 0;
					}
				}
			}

		}
		pixels[((int)(Controller.x/8) +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) * width)] = playerColor;//actual player location
		pixels[((int)(Controller.x/8) +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) * width)+1] = playerColor;//increase player visibility
		pixels[((int)(Controller.x/8) +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) * width)-1] = playerColor;//increase player visibility
		pixels[((int)(Controller.x/8) +shiftX + (Level.height-(int)(Controller.z/8)+shiftY+1) * width)] = playerColor;//increase player visibility
		pixels[((int)(Controller.x/8) +shiftX + (Level.height-(int)(Controller.z/8)+shiftY-1) * width)] = playerColor;//increase player visibility
		}
		
		if(Controller.map){
			int magnification=8;
			while((magnification*Level.height>height-20||magnification*Level.width>width-20)){
				magnification --;
			}
			shiftX = width/2-(Level.width*magnification/2);
			shiftY = 10;
			for(int y = 0; y < Level.height; y++){
				for(int x = 0; x < Level.width; x++){
					if(Level.blocks[x+y*Level.width].solid||Level.blocks[x+y*Level.width].victory){//4x4 pixel wall on map, poorly written
						int tempColor=finishColor;
						if(Level.blocks[x+y*Level.width].solid){
							tempColor=wallColor;
						}
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+1] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+2] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+3] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+width] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+width+1] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+width+2] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+width+3] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+2*width] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+2*width+1] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+2*width+2] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+2*width+3] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+3*width] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+3*width+1] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+3*width+2] = tempColor;
						pixels[x*magnification+shiftX+ (Level.height-y+shiftY) *magnification * width+3*width+3] = tempColor;
					}
					else{
					}
				}
			}
			//4x4 pixel player on map, also poorly written
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+1] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+2] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+3] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY)* magnification * width)+width] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+width+1] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+width+2] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+width+3] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+2*width] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+2*width+1] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+2*width+2] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+2*width+3] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+3*width] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+3*width+1] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+3*width+2] = playerColor;
			pixels[((int)(Controller.x/8)*magnification +shiftX + (Level.height-(int)(Controller.z/8)+shiftY) *magnification* width)+3*width+3] = playerColor;
		}
	}

	/**
	Renders the health left.
	 */
	public void renderHealth(){
		for(int h=0;h<Game.health;h++){

			int shiftX = 10+h*40;
			int shiftY = height-70;
			for(int y = 0; y < 60; y++){
				for(int x = 0; x < 30; x++){
					if(Texture.life.pixels[x+y*30]==0){
						continue;
					}
					pixels[x+shiftX+ (y+shiftY) * width] = wallColor;
				}
			}
		}
	}
	/**
	Renders the bombs left.
	 */
	public void renderBomb(){
		for(int h=0;h<Game.bombs;h++){

			int shiftX = width-(h+1)*40;
			int shiftY = height-60;
			for(int y = 0; y < 41; y++){
				for(int x = 0; x < 30; x++){
					if(Texture.bomb.pixels[x+y*30]==0){
						continue;
					}
					pixels[x+shiftX+ (y+shiftY) * width] = wallColor;
				}
			}
		}
	}

}
