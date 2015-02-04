package qdavis1.mine.minefront.graphics;
import qdavis1.mine.minefront.Game;
import qdavis1.mine.minefront.input.Controller;
/**
Invokes the various methods from Render3D.
 */
public class Screen extends Render {
	private Render3D render;

	public Screen(int width, int height) {
		super(width, height);
		render=new Render3D(width,height);
	}
	/**
	Invokes the various methods from Render3D.
	@param game A game to be passed to the floor method in Render3D.
	 */
	public void render(Game game) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}

		render.floor(game);

		render.renderDistanceLimiter();
		if(Controller.showHUD){
			render.renderMap();
			render.renderHealth();
			render.renderBomb();
		}
		draw(render, 0, 0);
	}
}
