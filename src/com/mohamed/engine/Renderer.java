package com.mohamed.engine;

import java.awt.image.DataBufferInt;

import com.mohamed.engine.gfx.Font;
import com.mohamed.engine.gfx.Image;
import com.mohamed.engine.gfx.ImageTile;

public class Renderer {
	private int pixelWidth;
	private int pixelHeight;
	private int[] pixels;
	
	private final Font font = Font.STANDARD_FONT;
	
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
	
	public void drawText(String text, int offX, int offY, int color) {
		text = text.toUpperCase();
		
		int offset = 0;
		
		for (int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			
			for (int y = 0; y < font.getFontImage().getHeight(); y++) {
				for (int x = 0; x < font.getWidths()[unicode]; x++) {
					if (font.getFontImage().getPixels()[(x + font.getOffsets()[unicode]) + (y * font.getFontImage().getWidth())] == 0xffffffff) {
						setPixel(x + offX + offset, y + offY, color);
					}
				}
			}
			
			offset += font.getWidths()[unicode];
		}
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
	
	public void drawImageTile(ImageTile imageTile, int offX, int offY, int tileX, int tileY) {
		// Don't render if all image is out of bounds
		if (offX >= pixelWidth) return;
		if (offY >= pixelHeight) return;
		if (offX + imageTile.getTileWidth() < 0) return;
		if (offY + imageTile.getTileHeight() < 0) return; 
		
		int xStart = 0;
		int yStart = 0;
		int renderWidth = imageTile.getTileWidth();
		int renderHeight = imageTile.getTileHeight();
		
		// Remove image starting pixels that will not be rendered
		if (offX < 0) {xStart += -offX;}
		if (offY < 0) {yStart += -offY;}
		
		// Remove image ending pixels that will not be rendered
		if (offX + renderWidth >= pixelWidth) {renderWidth -= offX + imageTile.getTileWidth() - pixelWidth;}
		if (offY + renderHeight >= pixelHeight) {renderHeight -= offY + imageTile.getTileHeight() - pixelHeight;}
		
		// Render
		for (int y = yStart; y < renderHeight; y++) {
			for (int x = xStart; x < renderWidth; x++) {
				int value = imageTile.getPixels()[(x + tileX * imageTile.getTileWidth())
				                                  + (y + tileY * imageTile.getTileHeight())
				                                  * imageTile.getWidth()];
				setPixel(x + offX, y + offY, value);
			}
		}
	}
}
