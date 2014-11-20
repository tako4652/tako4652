package jp.dip.tako4652.custom_analogclock_view;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ClockHands {
	public static final int HAND_HOUR = 1;
	public static final int HAND_MINUTE = 2;
	public static final int HAND_SECOND = 3;

	private int hand;

	private int centerX;
	private int centerY;

	private int posX;
	private int posY;

	private int length;
	private int width;
	private int color;

	private int time;

	private boolean visible = true;

	ClockHands() { this.hand = 0; }
	ClockHands(int hand) { this.hand = hand; }

	void setAngle(double angle) {
		posX = ((int) (centerX + Math.sin(Math.toRadians(angle)) * length));
		posY = ((int) (centerY - Math.cos(Math.toRadians(angle)) * length));
	}

	void setHands(int centerX, int centerY, int length) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.length = length;
		setHandsDefaultAttributes(length);
	}

	void setHands(int centerX, int centerY, int length, int width) {
		setHands(centerX, centerY, length);
		this.width = width;
	}

	void setHands(int centerX, int centerY, int length, int width, int color) {
		setHands(centerX, centerY, length, width);
		this.color = color;
	}

	void setHandsAttributes(int length, int width, int color) {
		if (!(hand == 0)) throw new IllegalArgumentException();
		this.length = length;
		this.width = width;
		this.color = color;
	}

	void setHandsDefaultAttributes(int length){
		if (hand == 0) throw new IllegalArgumentException();
		switch(hand) {
		case HAND_HOUR:
			this.length = (int) (length * 0.55);
			width = 12;
			color = 0xffff0000;
			break;
		case HAND_MINUTE:
			this.length = (int) (length * 0.85);
			width = 9;
			color = 0xff0000ff;
			break;
		case HAND_SECOND:
			this.length = (int) (length * 0.90);
			width = 6;
			color = 0xff000000;
			break;
		}
	}

	void setVisible(boolean visible) { this.visible = visible; }
	boolean getVisible() { return this.visible; }

	void setTime(int time) { this.time = time; }
	int getTime() { return time; }

	void draw(Canvas canvas) {
		if (visible) {
			Paint hPaint = new Paint();
			hPaint.setAntiAlias(true);
			hPaint.setDither(true);
			hPaint.setColor(color);
			hPaint.setStrokeWidth(width);
			hPaint.setStyle(Paint.Style.STROKE);
			hPaint.setStrokeJoin(Paint.Join.ROUND);
			hPaint.setStrokeCap(Paint.Cap.ROUND);
			canvas.drawLine(centerX, centerY, posX, posY, hPaint);
			canvas.drawCircle(centerX, centerY, (float) (length * 0.025), hPaint);
		}

	}
}
