package jp.dip.tako4652.custom_analogclock_view;

import jp.dip.tako4652.clockstudy.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class ClockView extends View {

	private Clock clk;
	private String HMS = "00:00:00";

	private int clockOffsetX;
	private int clockOffsetY;

	private String message = "";
	private int messageSize;
	private int messageColor;
	private int messageOffsetX;
	private int messageOffsetY;
	private boolean messageVisible;

	private int centerX;
	private int centerY;
	private int radius;

	public ClockView(Context context) {
		super(context);
		ClockHands h = new ClockHands(ClockHands.HAND_HOUR);
		ClockHands m = new ClockHands(ClockHands.HAND_MINUTE);
		ClockHands s = new ClockHands(ClockHands.HAND_SECOND);
		clk = new Clock(h, m, s);

		Resources r = context.getResources();
		clk.setBgImage(BitmapFactory.decodeResource(r, R.drawable.none2));
	}

	/**
	 * 画面サイズ変更時の通知
	 *
	 * @param w
	 *            , h, oldw, oldh
	 */
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		centerX = w / 2;
		centerY = h / 2;
		radius = (int) (Math.min(centerX, centerY) * 1.0);
		clk.setCenter(centerX + clockOffsetX, centerY + clockOffsetY, radius);
	}

	/* 描画関数 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		clk.setTime(HMS);
		clk.draw(canvas);
		if (messageVisible) {
			Paint tPaint = new Paint();
			tPaint.setAntiAlias(true);
			tPaint.setDither(true);
			tPaint.setColor(messageColor);
			tPaint.setTextSize(messageSize);
			int tCenterX = (int) (tPaint.measureText(message) / 2);

			canvas.drawText(message, centerX - tCenterX + clockOffsetX + messageOffsetX, centerY + clockOffsetY + messageOffsetY, tPaint);
		}
	}

	public void setClockOffset(int offsetX, int offsetY) {
		this.clockOffsetX = offsetX;
		this.clockOffsetY = offsetY;
	}

	public void setTime(String hms) { HMS = hms; }

	public void setDefaultMessageFormat () {
		this.messageSize = 48;
		this.messageColor = 0xff000000;
	}
	public void setMessage(String message) {
		this.message = message;
		if (this.messageSize == 0) setDefaultMessageFormat();
	}
	public void setMessageFormat(int size, int color) {
		this.messageSize = size;
		this.messageColor = color;
	}
	public void setMseeageOffset(int offsetX, int offsetY) {
		this.messageOffsetX = offsetX;
		this.messageOffsetY = offsetY;
	}
	public void setMessageVisible(boolean visible) { this.messageVisible = visible; }

	public void setDigitalClockFormat(int size, int color) { clk.setDigitalClockFormat(size, color); }
	public void setDigitalDisp12h(boolean disp12h) { clk.setDigitalDisp12h(disp12h); }
	public void setDigitalClockStringFormat(String format) { clk.setDigitalClockStringFormat(format); }
	public void setDigitalClockOffset(int offsetX, int offsetY) { clk.setDigitalClockOffset(offsetX, offsetY); }
	public void setDigitalClockVisible(boolean visible) { clk.setDigitalClockVisible(visible); }

	public void setBgImage(Bitmap image) { clk.setBgImage(image); }
	public void setBgImageOffset(int offsetX, int offsetY) { clk.setBgImageOffset(offsetX, offsetY); }
	public void setBgImageVisible(boolean visible) { clk.setBgImageVisible(visible); }

	public void setHandsVisible(boolean hHandDisp, boolean mHandDisp, boolean sHandDisp) { clk.setHandsVisible(hHandDisp, mHandDisp, sHandDisp); }

	public void setJust(boolean just) { clk.setJust(just); }
	public void setHalf(boolean half) { clk.setHalf(half); }
	public void setCenterAdjustMode(boolean adjustMode) { clk.setCenterAdjustMode(adjustMode); }
}
