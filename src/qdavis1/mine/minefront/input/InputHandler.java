package qdavis1.mine.minefront.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import qdavis1.mine.minefront.graphics.MainMenu;
/**
Handles the input. 
 */
public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {
	
	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;
	public static int MouseButton;
	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void mouseMoved(MouseEvent e) {
		try{
			MouseX = e.getX();
			MouseY = e.getY();
		}catch(Exception ex){
			System.out.println(1);
		}

	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void mouseClicked(MouseEvent e) {
	try{
		outerLoop:{
		for(int x=0; x<35;x++){
			for(int y=0; y<35;y++){
				if((MouseX-17+x+(MouseY-17+y)*1000)>(1000*600)||MainMenu.solidPixels[MouseX-17+x+(MouseY+y-17)*1000]){
					break outerLoop;
				}
			}
		}

		MainMenu.shiftX=MouseX-17;
		MainMenu.shiftY=MouseY-17;
	}
	}catch(Exception ex){
		System.out.println(2);
	}
	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void mouseExited(MouseEvent e) {

	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void focusGained(FocusEvent e) {

	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void focusLost(FocusEvent e) {
		for(int k=0;k<key.length;k++){
			key[k] = false;
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;
		}

	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;

		}

	}

	@Override
	/**
	Handles the input.
	@param e A MouseEvent. 
	 */
	public void keyTyped(KeyEvent e) {

	}

}
