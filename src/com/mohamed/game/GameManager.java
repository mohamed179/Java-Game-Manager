package com.mohamed.game;

import java.awt.event.KeyEvent;

import com.mohamed.engine.AbstractGame;
import com.mohamed.engine.GameContainer;
import com.mohamed.engine.Renderer;
import com.mohamed.engine.audio.SoundClip;
import com.mohamed.engine.gfx.Image;
import com.mohamed.engine.gfx.ImageTile;

public class GameManager extends AbstractGame {
	private Image image;
	private ImageTile imageTile;
	private SoundClip soundClip;
	private float temp = 0;
	
	public GameManager() {
		image = new Image("/test.png");
		imageTile = new ImageTile("/test2.png", 16, 16);
		soundClip = new SoundClip("/audios/airplanes.wav");
		soundClip.setVolume(0);
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		if (gc.getInput().isKey(KeyEvent.VK_A)) {
			soundClip.play();
		}
		
		temp += deltaTime * 10;
		if (temp > 4) {
			temp = 0;
		}
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImageTile(imageTile, gc.getInput().getMouseX() - 16, gc.getInput().getMouseY() - 16, (int)temp, 0);
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}
