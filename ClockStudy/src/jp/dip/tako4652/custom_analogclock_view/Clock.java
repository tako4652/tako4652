package jp.dip.tako4652.custom_analogclock_view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Clock {

	private int centerX;
	private int centerY;
	private int radius;

	private String digitalClockFormat;
	private boolean digitalClockDisp12h;
	private int digitalClockSize;
	private int digitalClockColor;
	private int digitalClockOffsetX;
	private int digitalClockOffsetY;
	private boolean digitalClockVisible = true;

	private ClockHands chHour;
	private ClockHands chMinute;
	private ClockHands chSecond;

	private boolean just = true;
	private boolean half = true;

	private Bitmap bgImage;
	private int bgImageCenterX;
	private int bgImageCenterY;
	private int bgImageOffsetX;
	private int bgImageOffsetY;
	private boolean bgImageVisible = false;

	private boolean centerAdjustMode;

	/**
	 * コンストラクター
	 *
	 * 時計針簡易設定
	 *
	 */
	Clock() {
		chHour = new ClockHands(ClockHands.HAND_HOUR);
		chMinute = new ClockHands(ClockHands.HAND_MINUTE);
		chSecond = new ClockHands(ClockHands.HAND_SECOND);
		setDefalutDigigalClock();
	}

	/**
	 * コンストラクター
	 *
	 * 時計針詳細設定
	 * @param hourHand 各種設定を定義したClockHands
	 * @param miniteHand 各種設定を定義したClockHands
	 * @param secondHand 各種設定を定義したClockHands
	 */
	Clock(ClockHands hourHand, ClockHands miniteHand, ClockHands secondHand) {
		chHour = hourHand;
		chMinute = miniteHand;
		chSecond = secondHand;
		setDefalutDigigalClock();
	}

	/**
	 * draw 時計を描く
	 *
	 * @param canvas
	 */
	void draw(Canvas canvas) {

		if (bgImageVisible) canvas.drawBitmap(bgImage, centerX - bgImageCenterX + bgImageOffsetX, centerY - bgImageCenterY + bgImageOffsetY, null);

		if (digitalClockVisible) {
			String hms = digitalFormatter();
			Paint dPaint = new Paint();
			dPaint.setAntiAlias(true);
			dPaint.setDither(true);
			dPaint.setColor(digitalClockColor);
			dPaint.setTextSize(digitalClockSize);
			int tCenterX = (int) (dPaint.measureText(hms) / 2);

			canvas.drawText(hms, centerX - tCenterX + digitalClockOffsetX, centerY + digitalClockOffsetY, dPaint);
		}

		if (!bgImageVisible) {
			Paint cPaint = new Paint();
			cPaint.setAntiAlias(true);
			cPaint.setDither(true);
			cPaint.setColor(0xff000000);
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

		if (centerAdjustMode) {
			Paint aPaint = new Paint();
			aPaint.setAntiAlias(true);
			aPaint.setDither(true);
			aPaint.setColor(0xffff0000);
			aPaint.setStyle(Paint.Style.STROKE);
			aPaint.setStrokeWidth(3);

			canvas.drawCircle(centerX, centerY, (float) (radius * 0.98), aPaint);

			for (int i = 0; i < 360; i += (360 / 12)) {
				int posX, posY;
				posX = (int) (centerX + Math.sin(Math.toRadians(i)) * radius
						* 0.95);
				posY = (int) (centerY - Math.cos(Math.toRadians(i)) * radius
						* 0.95);

				canvas.drawPoint(posX, posY, aPaint);
			}
		}

		chHour.draw(canvas);
		chMinute.draw(canvas);
		chSecond.draw(canvas);
	}

	/**
	 * digitalFormatter デジタル時計のフォーマット
	 * 「かな」で「○じ○ふん」の形式へ。
	 * その他はString.formatの書式に準ずる。
	 *
	 * @return String hms = formatted string
	 */
	@SuppressLint("DefaultLocale")
	private String digitalFormatter() {
		String hms = "";

		int hour = chHour.getTime();
		if (digitalClockDisp12h && hour == 0) hour = 12;

		if (digitalClockFormat.equals("かな")){
			boolean check = false;
			int minute = chMinute.getTime();
			String strM = String.valueOf(minute);
			if (strM.substring(strM.length() - 1).equals("0")) check = true;
			if (strM.substring(strM.length() - 1).equals("1")) check = true;
			if (strM.substring(strM.length() - 1).equals("2")) check = false;
			if (strM.substring(strM.length() - 1).equals("3")) check = true;
			if (strM.substring(strM.length() - 1).equals("4")) check = true;
			if (strM.substring(strM.length() - 1).equals("5")) check = false;
			if (strM.substring(strM.length() - 1).equals("6")) check = true;
			if (strM.substring(strM.length() - 1).equals("7")) check = false;
			if (strM.substring(strM.length() - 1).equals("8")) check = true;
			if (strM.substring(strM.length() - 1).equals("9")) check = false;

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
			}
		} else {
			if (chHour.getVisible() && chMinute.getVisible() && chSecond.getVisible()) {
				hms = String.format(digitalClockFormat, hour, chMinute.getTime(), chSecond.getTime());
			} else if (chHour.getVisible() && chMinute.getVisible()) {
				hms = String.format(digitalClockFormat, chHour.getTime(), chMinute.getTime());
			}
		}
		return hms;
	}

	/**
	 * 以下の要素がセットされる。
	 *
	 * digitalClockFormat = "%02d:%02d:%02d";
	 * digitalClockDisp12h = false;
	 * digitalClockSize = 48;
	 * digitalClockColor = 0xffff0000;
	 */
	void setDefalutDigigalClock() {
		this.digitalClockFormat = "%02d:%02d:%02d";
		this.digitalClockDisp12h = false;
		this.digitalClockSize = 48;
		this.digitalClockColor = 0xffff0000;
	}
	void setDigitalClockFormat(int size, int color) {
		this.digitalClockSize = size;
		this.digitalClockColor = color;
	}
	void setDigitalDisp12h(boolean disp12h) { this.digitalClockDisp12h = disp12h; }
	void setDigitalClockStringFormat(String format) { this.digitalClockFormat = format; }
	void setDigitalClockOffset(int offsetX, int offsetY) {
		this.digitalClockOffsetX = offsetX;
		this.digitalClockOffsetY = offsetY;
	}
	void setDigitalClockVisible(boolean visible) { this.digitalClockVisible = visible; }

	void setBgImage(Bitmap image) {
		this.bgImage = image;
		bgImageCenterX = image.getWidth() / 2;
		bgImageCenterY = image.getHeight() /2;
	}
	void setBgImageOffset(int offsetX, int offsetY) {
		this.bgImageOffsetX = offsetX;
		this.bgImageOffsetY = offsetY;
	}
	void setBgImageVisible(boolean visible) { this.bgImageVisible = visible; }

	void setCenter(int centerX, int centerY, int radius) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;

		chHour.setHands(centerX, centerY, radius);
		chMinute.setHands(centerX, centerY, radius);
		chSecond.setHands(centerX, centerY, radius);
	}

	void setHandsVisible(boolean hHandDisp, boolean mHandDisp, boolean sHandDisp) {
		chHour.setVisible(hHandDisp);
		chMinute.setVisible(mHandDisp);
		chSecond.setVisible(sHandDisp);
	}

	void setJust(boolean just) { this.just = just; }
	void setHalf(boolean half) { this.half = half; }

	void setCenterAdjustMode(boolean adjustMode) { this.centerAdjustMode = adjustMode; }

	void setTime(String time) {
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
}
