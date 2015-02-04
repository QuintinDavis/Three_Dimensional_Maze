package qdavis1.mine.minefront.level;

import qdavis1.mine.minefront.graphics.Texture;
/**
Makes a game level based on pixels from an image.
 */
public class CustomLevel extends Level{
	/**
	Takes the different colored pixels from an image and converts them to a block type.
	@param width The number of blocks wide that the level/map is to be made.
	@param height The number of blocks long that the level/map is to be made.
	 */
	public CustomLevel(int width, int height){
		Level.width=width;
		Level.height=height;
		Level.blocks = new Block[width*height];
		
		for(int y = 0 ; y< height; y++){
			for(int x = 0 ; x< width; x++){
				Block block = null;
				if(Texture.customMaze.pixels[x+(height-y-1)*width]!=0){
					if(Texture.customMaze.pixels[x+(height-y-1)*width]<-1163264){
						block = new VictoryBlock();
					}else{
					block = new SolidBlock();
					}
				}else{
					block = new Block();
				}
				
				blocks[x+y*width]=block;
			}
		}
		for(int x = 1 ; x< width-1; x++){
			for(int y = 1 ; y< height-1; y++){
				if(y<10&&y>0&&x<10&x>0){
					blocks[x+y*width].solid=false;
				}
			}
		}	
	}
}
