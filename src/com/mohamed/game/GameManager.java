package com.mohamed.game;

import com.mohamed.engine.AbstractGame;
import com.mohamed.engine.GameContainer;
import com.mohamed.engine.Renderer;
import com.mohamed.engine.gfx.Image;
import com.mohamed.engine.gfx.ImageTile;

public class GameManager extends AbstractGame {
	private Image image;
	private Image image2;
	private ImageTile imageTile;
	
	public GameManager() {
		image = new Image("/test3.png");
		image2 = new Image("/test4.png");
		image2.setAlpha(true);
		imageTile = new ImageTile("/test4.png", 16, 16);
		imageTile.setAlpha(true);
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.setzDepth(1);
		renderer.drawImageTile(imageTile, gc.getInput().getMouseX(), gc.getInput().getMouseY(), 0, 0);
		renderer.setzDepth(0);
		renderer.drawImage(image2, 10, 10);
		renderer.drawImage(image, 10, 10);
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(2f);
		gc.start();
	}
}
