package com.mohamed.game;

import com.mohamed.engine.AbstractGame;
import com.mohamed.engine.GameContainer;
import com.mohamed.engine.Renderer;
import com.mohamed.engine.gfx.Image;
import com.mohamed.engine.gfx.ImageTile;

public class GameManager extends AbstractGame {
	private Image image;
	private Image image2;
	
	public GameManager() {
		image = new Image("/test.png");
		image.setAlpha(true);
		image2 = new Image("/test2.png");
		image2.setAlpha(true);
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				renderer.setLightMapPixel(x, y, image.getPixels()[x + y * image.getWidth()]);
			}
		}
		
		renderer.setzDepth(1);
		renderer.drawImage(image2, gc.getInput().getMouseX(), gc.getInput().getMouseY());
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(2f);
		gc.start();
	}
}
