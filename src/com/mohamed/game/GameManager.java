package com.mohamed.game;

import com.mohamed.engine.AbstractGame;
import com.mohamed.engine.GameContainer;
import com.mohamed.engine.Renderer;
import com.mohamed.engine.gfx.Image;

public class GameManager extends AbstractGame {
	private Image image;
	
	public GameManager() {
		image = new Image("/test2.png");
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImage(image, gc.getInput().getMouseX(), gc.getInput().getMouseY());
		renderer.drawFillRect(100, 100, 32, 32, 0xff123456);
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(2f);
		gc.start();
	}
}
