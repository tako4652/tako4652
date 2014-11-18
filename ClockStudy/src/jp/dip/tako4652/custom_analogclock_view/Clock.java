package jp.dip.tako4652.custom_analogclock_view;

import jp.dip.tako4652.clockstudy.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Clock {

	private int centerX;
	private int centerY;
	private int radius;

	private boolean dispDigital = true;

	private ClockHands chHour;
	private ClockHands chMinute;
	private ClockHands chSecond;

	private boolean just = true;
	private boolean half = true;
	private boolean bmp = false;
	private Bitmap image;

	public Clock(Context context) {
		chHour = new ClockHands(Hands.HAND_HOUR);
		chMinute = new ClockHands(Hands.HAND_MINUTE);
		chSecond = new ClockHands(Hands.HAND_SECOND);

		Resources r = context.getResources();
		image = BitmapFactory.decodeResource(r, R.drawable.none2);
	}

	public void setCenter(int centerX, int centerY, int radius) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;

		chHour.setCenter(centerX, centerY, (int) (radius * 0.55));
		chMinute.setCenter(centerX, centerY, (int) (radius * 0.85));
		chSecond.setCenter(centerX, centerY, (int) (radius * 0.9));
	}

	public void setVisible(boolean hHandDisp, boolean mHandDisp,
			boolean sHandDisp) {
		chHour.setVisible(hHandDisp);
		chMinute.setVisible(mHandDisp);
		chSecond.setVisible(sHandDisp);
	}

	public void setDispDigital(boolean dispDigital) {
		this.dispDigital = dispDigital;
	}

	public void setJust(boolean just) {
		this.just = just;
	}

	public void setHalf(boolean half) {
		this.half = half;
	}

	public void setBmp(boolean bmp) {
		this.bmp = bmp;
	}

	public void setTime(String time) throws NumberFormatException {
		String[] str = time.split(":");
		int hour = Integer.valueOf(str[0]);
		int minute = Integer.valueOf(str[1]);
		int second = Integer.valueOf(str[2]);
		if (!(0 <= hour && hour <= 11))
			throw new NumberFormatException();
		if (!(0 <= minute && minute <= 59))
			throw new NumberFormatException();
		if (!(0 <= second && second <= 59))
			throw new NumberFormatException();

		chHour.setTime(hour);
		chMinute.setTime(minute);
		chSecond.setTime(second);

		double hAngle, mAngle, sAngle;
		sAngle = 360 / 60 * second;
		mAngle = 360 / 60 * minute + sAngle / 60;
		hAngle = 360 / 12 * hour + mAngle / 12;

		chHour.setAngle(hAngle);
		chMinute.setAngle(mAngle);
		chSecond.setAngle(sAngle);
	}

	@SuppressLint("DefaultLocale")
	public void draw(Canvas canvas) {

		if (bmp)
			canvas.drawBitmap(image, 3, 40, null);

		if (dispDigital) {
			String hms = "";

			int hour = chHour.getTime();
			if (hour == 0)
				hour = 12;

			boolean check = false;
			int minute = chMinute.getTime();
			String strM = String.valueOf(minute);
			if (strM.substring(strM.length() - 1).equals("0"))
				check = true;
			if (strM.substring(strM.length() - 1).equals("1"))
				check = true;
			if (strM.substring(strM.length() - 1).equals("2"))
				check = false;
			if (strM.substring(strM.length() - 1).equals("3"))
				check = true;
			if (strM.substring(strM.length() - 1).equals("4"))
				check = true;
			if (strM.substring(strM.length() - 1).equals("5"))
				check = false;
			if (strM.substring(strM.length() - 1).equals("6"))
				check = true;
			if (strM.substring(strM.length() - 1).equals("7"))
				check = false;
			if (strM.substring(strM.length() - 1).equals("8"))
				check = true;
			if (strM.substring(strM.length() - 1).equals("9"))
				check = false;

			if (!chSecond.getVisible()) {
				if (minute == 0) {
					if (just)
						hms = String.format("%dじちょうど", hour);
					else
						hms = String.format("%dじ", hour);
				} else if (minute == 30) {
					if (half)
						hms = String.format("%dじはん", hour);
					else
						hms = String.format("%dじ30ぷん", hour);
				} else {
					hms = String.format("%dじ%d", hour, minute);
					if (check) {
						hms += "ぷん";
					} else {
						hms += "ふん";
					}
				}
			} else {
				hms = String.format("%02d:%02d:%02d", hour, chMinute.getTime(),
						chSecond.getTime());
			}
			Paint tPaint = new Paint();
			tPaint.setAntiAlias(true);
			tPaint.setDither(true);
			tPaint.setColor(0xffff0000);
			tPaint.setTextSize(48);
			tPaint.setStyle(Paint.Style.STROKE);
			tPaint.setStrokeWidth(1);
			int tCenterX = (int) (tPaint.measureText(hms) / 2);

			canvas.drawText(hms, centerX - tCenterX, centerY + 350, tPaint);
		}

		if (!bmp) {
			Paint cPaint = new Paint();
			cPaint.setAntiAlias(true);
			cPaint.setDither(true);
			cPaint.setColor(0xffff0000);
			cPaint.setStyle(Paint.Style.STROKE);
			cPaint.setStrokeWidth(6);

			canvas.drawCircle(centerX, centerY, (float) (radius * 0.98), cPaint);

			for (int i = 0; i < 360; i += (360 / 12)) {
				int posX, posY;
				posX = (int) (centerX + Math.sin(Math.toRadians(i)) * radius
						* 0.95);
				posY = (int) (centerY - Math.cos(Math.toRadians(i)) * radius
						* 0.95);

				canvas.drawPoint(posX, posY, cPaint);
			}
		}

		chHour.draw(canvas);
		chMinute.draw(canvas);
		chSecond.draw(canvas);
	}
}
