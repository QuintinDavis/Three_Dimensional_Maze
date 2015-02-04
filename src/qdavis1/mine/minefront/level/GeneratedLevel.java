package qdavis1.mine.minefront.level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import qdavis1.mine.minefront.Game;
/**
Generates a random maze level.
 */
public class GeneratedLevel extends Level{

	/**
	Generates walls randomly, dependent on the static variables, density, from the Game class.
	Leaves an open area for the start area.
	@param width The number of blocks wide that the level/map is to be made.
	@param height The number of blocks long that the level/map is to be made.
	 */
	public GeneratedLevel(int width, int height){
		Level.width=width;
		Level.height= height;
		Level.blocks = new Block[width*height];

		Random random =  new Random();

		for(int y = 0 ; y< height; y++){
			ArrayList<Point> ranges = new ArrayList<Point>();
			for(int k=2;k<width;k+=5){
				if(Game.wallDensity<random.nextInt(11)){
					Point range = new Point(k-2,k+3);//not actually points, just handy aggregation of 2 numbers
					ranges.add(range);
				}
			}
			for(int x = 0 ; x< width; x++){
				Block block = null;
				if(y<2||x<2||y==height-1||x==width-1){
					block = new SolidBlock();
					blocks[x+y*width]=block;
					continue;
				}
				loop:{
					if(y%5==0){
						for(int k=0;k<ranges.size();k++){
							if(ranges.get(k).x<x&&ranges.get(k).y>x){
								block = new Block();
								blocks[x+y*width]=block;
								break loop;
							}
						}
						block = new SolidBlock();
						blocks[x+y*width]=block;
						continue;
					}

					else if(x%5==0){

						block = new SolidBlock();
						blocks[x+y*width]=block;
						continue;
					}
					else{
						block = new Block();
					}
				}
				blocks[x+y*width]=block;
			}
		}

		for(int x = 1 ; x< width-1; x++){
			ArrayList<Point> ranges = new ArrayList<Point>();
			for(int k=2;k<height;k+=5){
				if(Game.wallDensity<random.nextInt(11)){
					Point range = new Point(k-2,k+3);//not actually points, just handy aggregation of 2 numbers
					ranges.add(range);
				}
			}
			for(int y = 1 ; y< height-1; y++){
				if(y<10&&y>0&&x<10&x>0){//open starting area
					blocks[x+y*width].solid=false;
				}
				else{
					if(x%5==0){
						for(int k=0;k<ranges.size();k++){
							if(ranges.get(k).x<y&&ranges.get(k).y>y){
								blocks[x+y*width].solid=false;
							}
						}
					}

				}
			}
		}
		for(int x = 1 ; x< width-1; x++){
			for(int y = 1 ; y< height-1; y++){
				if(blocks[x+y*width].solid&&//no single, stranded blocks
						!(blocks[x+y*width-1].solid||
								blocks[x+y*width+1].solid||
								blocks[x+(y+1)*width].solid||
								blocks[x+(y-1)*width].solid)){
					blocks[x+y*width].solid=false;
				}

			}
		}
		for(int x = 1 ; x< width-1; x++){
			for(int y = 1 ; y< height-1; y++){
				if(y<height&&y>height-5&&x<width&x>width-5){
					blocks[x+y*width].victory=true;
					blocks[x+y*width].solid=false;
				}
			}
		}
	}
}
