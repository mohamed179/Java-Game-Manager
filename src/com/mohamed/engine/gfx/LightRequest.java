package com.mohamed.engine.gfx;

public class LightRequest {
	private Light light;
	private int offX, offY;
	
	public LightRequest(Light light, int offX, int offY) {
		this.light = light;
		this.offX = offX;
		this.offY = offY;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}

	public int getOffX() {
		return offX;
	}

	public void setOffX(int offX) {
		this.offX = offX;
	}

	public int getOffY() {
		return offY;
	}

	public void setOffY(int offY) {
		this.offY = offY;
	}
}
