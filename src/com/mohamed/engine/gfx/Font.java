package com.mohamed.engine.gfx;

public class Font {
	public static final Font STANDARD_FONT = new Font("/fonts/comic.png");
	
	private Image fontImage;
	private int[] offsets;
	private int[] widths;
	
	public Font(String path) {
		fontImage = new Image(path);
		
		offsets = new int[256];
		widths = new int[256];
		
		int unicode = 0;
		
		for (int x = 0; x < fontImage.getWidth(); x++) {
			if (fontImage.getPixels()[x] == 0xff0000ff) {
				offsets[unicode] = x;
			} else if (fontImage.getPixels()[x] == 0xffffff00) {
				widths[unicode] = x - offsets[unicode] + 1;
				unicode++;
			}
		}
	}

	public Image getFontImage() {
		return fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public int[] getWidths() {
		return widths;
	}
}
