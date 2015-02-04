package qdavis1.mine.minefront.input;
import qdavis1.mine.minefront.Launcher;
import qdavis1.mine.minefront.graphics.MainMenu;
/**
Mutates the MainMenu static variables shiftX and shiftY. 
 */
public class MenuController {

	private int diamondSpeed=6;
	/**
	Mutates the MainMenu static variables shiftX and shiftY dependent on if the moves are valid, dependent on collision detection.
	@param arrowUp True if the user wants to attempt to move up.
	@param arrowDown True if the user wants to attempt to move down.
	@param arrowRight True if the user wants to attempt to move right.
	@param arrowLeft True if the user wants to attempt to move left.
	@param run True if the user wants to move run.
	 */
	public void tick(boolean arrowUp, boolean arrowDown, boolean arrowRight, boolean arrowLeft, boolean run) {
		int nextMoveUp=0;
		int nextMoveLeft=0;
		diamondSpeed=6;
		if(run){
			diamondSpeed=10;
		}
		if(arrowUp){
			nextMoveUp-=diamondSpeed-2;
		}
		if(arrowDown){
			nextMoveUp+=diamondSpeed-2;
		}
		if(arrowLeft){
			nextMoveLeft-=diamondSpeed;
		}
		if(arrowRight){
			nextMoveLeft+=diamondSpeed;
		}
		
		for(int y = 0; y < 35; y++){//Collision Detection, split solidPixels statements to allow sliding
			for(int x = 0; x < 35; x++){
				if(MainMenu.diamond.pixels[x+y*35]==0){
					continue;
				}
				if(MainMenu.solidPixels[x+MainMenu.shiftX+
				                        (y+MainMenu.shiftY+nextMoveUp) * MainMenu.width]==true){
					arrowUp=false;
					arrowDown=false;
				}
				if(MainMenu.solidPixels[x+MainMenu.shiftX +nextMoveLeft+
				                        (y+MainMenu.shiftY) * MainMenu.width]==true){

					arrowRight=false;
					arrowLeft=false;
				}
				if(MainMenu.levelPixels[x+MainMenu.shiftX +nextMoveLeft+
				                        (y+MainMenu.shiftY+nextMoveUp) * MainMenu.width]!=0){
					Launcher.level=MainMenu.levelPixels[x+MainMenu.shiftX +nextMoveLeft+
								                        (y+MainMenu.shiftY+nextMoveUp) * MainMenu.width];
				}
			}
		}
		if(arrowUp){
			MainMenu.shiftY-=diamondSpeed-2;
		}
		if(arrowDown){
			MainMenu.shiftY+=diamondSpeed-2;
		}
		if(arrowLeft){
			MainMenu.shiftX-=diamondSpeed;
		}
		if(arrowRight){
			MainMenu.shiftX+=diamondSpeed;
		}
		
		for(int y = 0; y < 35; y++){
			for(int x = 0; x < 35; x++){
				if(MainMenu.diamond.pixels[x+y*35]==0){
					continue;
				}
				if((y+MainMenu.shiftY)>488){
					if((x+MainMenu.shiftX)>750&&(x+MainMenu.shiftX)<850){
						Launcher.nuclearFallout=true;
						MainMenu.rePixel=true;
					}
					else if((x+MainMenu.shiftX)>480&&(x+MainMenu.shiftX)<520){
						Launcher.controls=true;
					}
					else if((x+MainMenu.shiftX)>225&&(x+MainMenu.shiftX)<275){
						Launcher.nuclearFallout=false;
						MainMenu.rePixel=true;
					}
				}
				
			}
		}
	}
}
