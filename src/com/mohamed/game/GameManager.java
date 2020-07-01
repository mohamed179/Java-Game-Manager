package com.mohamed.game;

import java.awt.event.KeyEvent;

import com.mohamed.engine.AbstractGame;
import com.mohamed.engine.GameContainer;
import com.mohamed.engine.Renderer;

public class GameManager extends AbstractGame {
	public GameManager() {
		
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		if (gc.getInput().isKey(KeyEvent.VK_A)) {
			System.out.println("Key A is pressed!");
		}
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}