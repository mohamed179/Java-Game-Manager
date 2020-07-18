package com.mohamed.game;

import com.mohamed.engine.AbstractGame;
import com.mohamed.engine.GameContainer;
import com.mohamed.engine.Renderer;
import com.mohamed.engine.gfx.Image;
import com.mohamed.engine.gfx.ImageTile;
import com.mohamed.engine.gfx.Light;

public class GameManager extends AbstractGame {
	public GameManager() {
		
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(2f);
		gc.start();
	}
}
