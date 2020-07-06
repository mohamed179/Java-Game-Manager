package com.mohamed.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.mohamed.engine.gfx.Font;
import com.mohamed.engine.gfx.Image;
import com.mohamed.engine.gfx.ImageRequest;
import com.mohamed.engine.gfx.ImageTile;

public class Renderer {
	private int pixelWidth;
	private int pixelHeight;
	private int[] pixels;
	private int[] zBuffer;
	private int zDepth = 0;
	
	private ArrayList<ImageRequest> imageRequests = new ArrayList<ImageRequest>();
	private boolean processing = false;
	
	private final Font font = Font.STANDARD_FONT;
	
	public Renderer(GameContainer gc) {
		pixelWidth = gc.getWidth();
		pixelHeight = gc.getHeight();
		pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zBuffer = new int[pixels.length];
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
			zBuffer[i] = 0;
		}
	}
	
	public void process() {
		processing = true;
		
		// sort image requests according to z depth
		Collections.sort(imageRequests, new Comparator<ImageRequest>() {
			@Override
			public int compare(ImageRequest ir1, ImageRequest ir2) {
				if (ir1.getzDepth() < ir2.getzDepth())
					return -1;
				if (ir1.getzDepth() > ir2.getzDepth())
					return 1;
				return 0;
			}
		});
		
		// process image requests
		for (int i = 0; i < imageRequests.size(); i++) {
			ImageRequest ir = imageRequests.get(i);
			setzDepth(ir.getzDepth());
			drawImage(ir.getImage(), ir.getOffX(), ir.getOffY());
		}
		
		imageRequests.clear();
		processing = false;
	}

	public void setPixel(int x, int y, int value) {
		int alpha = (value >> 24) & 0xff;
		
		// out of bounds
		if ((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) || alpha == 0) {
			return;
		}
		
		int pixelIdx = x + y * pixelWidth;
		
		// in lower layer
		if (zBuffer[pixelIdx] > zDepth) {
			return;
		}
		
		zBuffer[pixelIdx] = zDepth;
		
		if (alpha == 255) {
			// no transparency
			pixels[pixelIdx] = value;
		} else {
			// alpha blending
			int pixelColor = pixels[x + (y * pixelWidth)];
			
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
			int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f));
			
			pixels[pixelIdx] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
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
		if (image.isAlpha() && !processing) {
			imageRequests.add(new ImageRequest(image, zDepth, offX, offY));
			return;
		}
		
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
		if (imageTile.isAlpha() && !processing) {
			imageRequests.add(new ImageRequest(imageTile.getTileImage(tileX, tileY), zDepth, offX, offY));
			return;
		}
		
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
	
	public void drawRect(int offX, int offY, int width, int height, int color) {
		for (int y = 0; y < height; y++) {
			setPixel(offX, y + offY, color);
			setPixel(offX + width -1, y + offY, color);
		}
		
		for (int x = 0; x < width; x++) {
			setPixel(x + offX, offY, color);
			setPixel(x + offX, offY + height - 1, color);
		}
	}
	
	public void drawFillRect(int offX, int offY, int width, int height, int color) {
		// Don't render if all Rectangle is out of bounds
		if (offX >= pixelWidth) return;
		if (offY >= pixelHeight) return;
		if (offX + width < 0) return;
		if (offY + height < 0) return;
		
		int xStart = 0;
		int yStart = 0;
		int renderWidth = width;
		int renderHeight = height;
		
		// Remove Rectangle starting pixels that will not be rendered
		if (offX < 0) {xStart += -offX;}
		if (offY < 0) {yStart += -offY;}
		
		// Remove Rectangle ending pixels that will not be rendered
		if (offX + renderWidth >= pixelWidth) {renderWidth -= offX + width - pixelWidth;}
		if (offY + renderHeight >= pixelHeight) {renderHeight -= offY + height - pixelHeight;}
		
		// Render
		for (int y = yStart; y < renderHeight; y++) {
			for (int x = xStart; x < renderWidth; x++) {
				setPixel(x + offX, y + offY, color);
			}
		}
	}
	
	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
}
