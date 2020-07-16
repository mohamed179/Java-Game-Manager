package com.mohamed.game;

import com.mohamed.engine.AbstractGame;
import com.mohamed.engine.GameContainer;
import com.mohamed.engine.Renderer;
import com.mohamed.engine.gfx.Image;
import com.mohamed.engine.gfx.ImageTile;
import com.mohamed.engine.gfx.Light;

public class GameManager extends AbstractGame {
	private Image image;
	private Image image2;
	private Light light;
	
	public GameManager() {
		image = new Image("/test.png");
		image.setAlpha(true);
		image.setLightBlock(Light.FULL);
		image2 = new Image("/test2.png");
		image2.setAlpha(true);
		light = new Light(100, 0xff00ffff);
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.setzDepth(0);
		renderer.drawImage(image2, 0, 0);
		renderer.drawImage(image, 100, 100);
		renderer.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(2f);
		gc.start();
	}
}
