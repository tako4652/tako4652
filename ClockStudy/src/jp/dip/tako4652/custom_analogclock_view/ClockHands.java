package jp.dip.tako4652.custom_analogclock_view;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ClockHands extends Hands {

	private int hand;
	private boolean visible = true;

	public ClockHands(int hand) {
		this.hand = hand;
	}

	@Override
	public void setAngle(double angle) {
		setPosX((int) (getCenterX() + Math.sin(Math.toRadians(angle))
				* getRadius()));
		setPosY((int) (getCenterY() - Math.cos(Math.toRadians(angle))
				* getRadius()));
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getVisible() {
		return visible;
	}

	public void draw(Canvas canvas) {
		if (visible) {
			Paint hPaint = new Paint();
			hPaint.setAntiAlias(true);
			hPaint.setDither(true);
			switch (hand) {
			case Hands.HAND_HOUR:
				hPaint.setColor(0xffff0000);
				hPaint.setStrokeWidth(12);
				break;
			case Hands.HAND_MINUTE:
				hPaint.setColor(0xff0000ff);
				hPaint.setStrokeWidth(9);
				break;
			case Hands.HAND_SECOND:
				hPaint.setColor(0xff000000);
				hPaint.setStrokeWidth(6);
				break;
			}
			hPaint.setStyle(Paint.Style.STROKE);
			hPaint.setStrokeJoin(Paint.Join.ROUND);
			hPaint.setStrokeCap(Paint.Cap.ROUND);
			canvas.drawLine(getCenterX(), getCenterY(), getPosX(), getPosY(),
					hPaint);
		}

	}
}
