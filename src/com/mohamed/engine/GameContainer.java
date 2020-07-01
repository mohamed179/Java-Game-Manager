package com.mohamed.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameContainer implements Runnable {	
	private final double UPDATE_CAP = 1.0/60.0;
	
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	
	private boolean isRunning = false;
	
	private int width = 320, height = 240;
	private float scale = 2f;
	private String title = "Game Engine v1.0";

	public GameContainer() {
		
	}
	
	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		
	}
	
	public void run() {
		isRunning = true;
		
		boolean render = false;
		
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unProcessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while (isRunning) {
			render = false;
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unProcessedTime += passedTime;
			frameTime += passedTime;
			
			while(unProcessedTime >= UPDATE_CAP) {
				unProcessedTime -= UPDATE_CAP;
				render = true;
				
				// TODO: Update game
				System.out.println("x: " + input.getMouseX() + " | y: " + input.getMouseY());
				input.update();
				
				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					System.out.println("FPS: " + fps);
				}
			}
			
			if (render) {
				// TODO: Render game
				renderer.clear();
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		despose();
	}
	
	public void despose() {
		
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer();
		gc.start();
	}
	
	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}
}
