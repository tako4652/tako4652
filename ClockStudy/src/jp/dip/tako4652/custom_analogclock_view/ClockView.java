package jp.dip.tako4652.custom_analogclock_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class ClockView extends View {

	private static final int CLOCK_OFFSET_Y = -100;
	private static final int MESSSAGE_OFFSET_Y = 250;
	private Clock clk;
	private String HMS="00:00:00";

	private String message = "";

	private int centerX;
	private int centerY;
	private int radius;

	public ClockView(Context context) {
		super(context);
		clk = new Clock(context);
	}

	/**
	 * 画面サイズ変更時の通知
	 *
	 * @param w, h, oldw, oldh
	 */
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		centerX = w / 2;
		centerY = h / 2;
		radius = (int) (Math.min(centerX, centerY) * 1.0);
		clk.setCenter(centerX, centerY + CLOCK_OFFSET_Y, radius);
	}

	/* 描画関数 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		try {
			clk.setTime(HMS);
		} catch (NumberFormatException e) {

		} finally {
			if (!message.equals("")){
				Paint tPaint = new Paint();
				tPaint.setAntiAlias(true);
				tPaint.setDither(true);
				tPaint.setColor(0xff000000);
				tPaint.setTextSize(48);
				tPaint.setStyle(Paint.Style.STROKE);
				tPaint.setStrokeWidth(1);
				int tCenterX = (int) (tPaint.measureText(message) / 2);

				canvas.drawText(message, centerX - tCenterX, centerY + MESSSAGE_OFFSET_Y, tPaint);

			}
		}
		clk.draw(canvas);
	}

	public void setTime(String hms){
		HMS = hms;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDispDigital(boolean dispDigital) {
		clk.setDispDigital(dispDigital);
	}

	public void setHandsVisible(boolean hHandDisp,boolean mHandDisp,boolean sHandDisp) {
		clk.setVisible(hHandDisp, mHandDisp, sHandDisp);
	}

	public void setJust(boolean just) {
		clk.setJust(just);
	}

	public void setHalf(boolean half) {
		clk.setHalf(half);
	}

	public void setBmp(boolean bmp) {
		clk.setBmp(bmp);
	}
}
