package com.mohamed.engine;

import java.awt.image.DataBufferInt;

import com.mohamed.engine.gfx.Image;

public class Renderer {
	private int pixelWidth;
	private int pixelHeight;
	private int[] pixels;
	
	public Renderer(GameContainer gc) {
		pixelWidth = gc.getWidth();
		pixelHeight = gc.getHeight();
		pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void setPixel(int x, int y, int value) {
		if ((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) || value == 0xffff00ff) {
			return;
		}
		
		pixels[x + (y * pixelWidth)] = value;
	}
	
	public void drawImage(Image image, int offX, int offY) {
		// Don't render if all image is out of bounds
		if (offX >= pixelWidth) return;
		if (offY >= pixelHeight) return;
		if (offX + image.getWidth() < 0) return;
		if (offY + image.getHeight() < 0) return; 
		
		int xStart = 0;
		int yStart = 0;
		int renderWidth = image.getWidth();
		int renderHeight = image.getHeight();
		
		// Remove image starting pixels that will not be rendered
		if (offX < 0) {xStart += -offX;}
		if (offY < 0) {yStart += -offY;}
		
		// Remove image ending pixels that will not be rendered
		if (offX + renderWidth >= pixelWidth) {renderWidth -= offX + image.getWidth() - pixelWidth;}
		if (offY + renderHeight >= pixelHeight) {renderHeight -= offY + image.getHeight() - pixelHeight;}
		
		// Render
		for (int y = yStart; y < renderHeight; y++) {
			for (int x = xStart; x < renderWidth; x++) {
				int value = image.getPixels()[x + y * image.getWidth()];
				setPixel(x + offX, y + offY, value);
			}
		}
	}
}
