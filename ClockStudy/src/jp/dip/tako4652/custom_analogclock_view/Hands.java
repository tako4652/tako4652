package jp.dip.tako4652.custom_analogclock_view;

public abstract class Hands {
	public static final int HAND_HOUR = 0;
	public static final int HAND_MINUTE = 1;
	public static final int HAND_SECOND = 2;

	private int centerX;
	private int centerY;
	private int radius;

	private int posX;
	private int posY;

	private int time;

	private int color = 0;

	abstract public void setAngle(double angle);

	public void setCenter(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public void setCenter(int centerX, int centerY,int radius) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getRadius() {
		return radius;
	}

	public int getColor() {
		return color;
	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public int getTime() {
		return time;
	}
}
