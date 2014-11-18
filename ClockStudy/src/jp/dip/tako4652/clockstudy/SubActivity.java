package jp.dip.tako4652.clockstudy;

import java.util.Random;

import jp.dip.tako4652.custom_analogclock_view.ClockView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SubActivity extends Activity {

	private Handler handler = new Handler();

	private static long CLOCK_INTERVAL = 1000 * 10;

	private Runnable runnable;

	private ClockView cv;

	private int h = 0;
	private int m = 0;
	private int s = 0;
	private String hms = "00:00:00";

	private boolean digitalDisp = false;

	private int level = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cv = new ClockView(this);
		setContentView(cv);

		Bundle extras = getIntent().getExtras();
		level = Integer.valueOf(extras.getString("Level"));

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		boolean justDisp = sharedPreferences.getBoolean("justDisp", true);
		if (justDisp) {
			cv.setJust(true);
		} else {
			cv.setJust(false);
		}

		boolean halfDisp = sharedPreferences.getBoolean("halfDisp", true);
		if (halfDisp) {
			cv.setHalf(true);
		} else {
			cv.setHalf(false);
		}

		boolean bmpDisp = sharedPreferences.getBoolean("bmpDisp", false);
		if (bmpDisp) {
			cv.setBmp(true);
		} else {
			cv.setBmp(false);
		}

		cv.setHandsVisible(true, true, false);
		cv.setDispDigital(false);

		final Random rnd = new Random();
		runnable = new Runnable() {

			@Override
			public void run() {
				if (digitalDisp) {
					digitalDisp = false;
					cv.setDispDigital(true);
					cv.setMessage("");
					cv.invalidate();
					handler.postDelayed(this, CLOCK_INTERVAL / 2);
				} else {
					digitalDisp = true;
					switch (level) {
					case 0:
						h = rnd.nextInt(12);
						break;
					case 1:
						h = rnd.nextInt(12);
						m = rnd.nextInt(2) * 30;
						break;
					case 2:
						h = rnd.nextInt(12);
						m = rnd.nextInt(4) * 15;
						break;
					case 3:
						h = rnd.nextInt(12);
						m = rnd.nextInt(6) * 10;
						break;
					case 4:
						h = rnd.nextInt(12);
						m = rnd.nextInt(12) * 5;
						break;
					case 5:
						h = rnd.nextInt(12);
						m = rnd.nextInt(60);
						break;
					}
					hms = String.format("%02d:%02d:%02d", h, m, s);
					cv.setDispDigital(false);
					cv.setMessage("これはなんじ？");
					cv.setTime(hms);
					cv.invalidate();
					handler.postDelayed(this, CLOCK_INTERVAL);
				}
			}
		};
		handler.postDelayed(runnable, 0);
	}
}
