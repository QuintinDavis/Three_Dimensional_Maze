package qdavis1.mine.minefront.level;
/**
A game level, includes a static array of the Blocks in the map/level.
 */
public abstract class Level {
	public static Block[] blocks;
	public static int width;
	public static int height;
	/**
	Return a Block from the blocks Block array.
	@param x The x value of the Block.
	@param y The y value of the Block.
	@return Block The Block at the coordinate x, y.
	 */
	public Block create(int x, int y){
		if(x<0 || y<0 || x>=width|| y>=height){
			return Block.solidwall;
		}
		return blocks[x+y*width];
	}
	
}
