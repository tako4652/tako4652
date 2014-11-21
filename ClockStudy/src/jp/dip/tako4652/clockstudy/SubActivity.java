package jp.dip.tako4652.clockstudy;

import java.io.IOException;

import jp.dip.tako4652.custom_analogclock_view.ClockView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SubActivity extends Activity {
	private final int CLOCK_OFFSET_X = 0;
	private final int CLOCK_OFFSET_Y = -100;
	private final int BG_IMAGE_OFFSET_X = 4;
	private final int BG_IMAGE_OFFSET_Y = 0;
	private final int DIGITAL_CLOCK_OFFSET_X = 0;
	private final int DIGITAL_CLOCK_OFFSET_Y = 400;
	private final int MESSAGE_OFFSET_X = 0;
	private final int MESSAGE_OFFSET_Y = 400;

	private final int TEXT_SIZE = 48;
	private final int TEXT_COLOR = 0xff000000;

	private final int DIGITAL_CLOCK_SIZE = TEXT_SIZE;
	private final int DIGITAL_CLOCK_COLOR = TEXT_COLOR;
	private final int MESSAGE_SIZE = TEXT_SIZE;
	private final int MESSAGE_COLOR = TEXT_COLOR;

	private final long CLOCK_INTERVAL = 1000;

	private final int QuestionGenerate = 0;
	private final int HandsMove = 1;
	private final int QuestionDisp = 2;
	private final int AnswerDisp = 3;

	private SoundPool soundPool;
	private int[] soundId = {-1, -1, -1};
	private boolean sound = true;

	private int state = QuestionGenerate;

	private Handler handler = new Handler();
	private Runnable runnable;

	private ClockView cv;

	private int hh = 0;
	private int mm = 0;
	private int ss = 0;
	private String hhmmss = "00:00:00";

	private int h = 0;
	private int m = 0;
	private int s = 0;
	private String hms = "00:00:00";

	private boolean rotation = true;
	private long timeInterval;

	private int level = 0;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cv = new ClockView(this);
		setContentView(cv);

		cv.setClockOffset(CLOCK_OFFSET_X, CLOCK_OFFSET_Y);
		cv.setBgImageOffset(BG_IMAGE_OFFSET_X, BG_IMAGE_OFFSET_Y);
		cv.setDigitalClockOffset(DIGITAL_CLOCK_OFFSET_X, DIGITAL_CLOCK_OFFSET_Y);
		cv.setMseeageOffset(MESSAGE_OFFSET_X, MESSAGE_OFFSET_Y);
		cv.setHandsVisible(true, true, false);
		cv.setDigitalDisp12h(true);
		cv.setDigitalClockFormat(DIGITAL_CLOCK_SIZE, DIGITAL_CLOCK_COLOR);
		cv.setDigitalClockVisible(false);
		cv.setMessage("これはなんじ？");
		cv.setMessageFormat(MESSAGE_SIZE, MESSAGE_COLOR);
		cv.setMessageVisible(false);
		cv.setDigitalClockStringFormat("かな");
		cv.setCenterAdjustMode(false);

		Bundle extras = getIntent().getExtras();
		level = Integer.valueOf(extras.getString("Level"));

		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

		try{
			AssetManager assetManager = getAssets();
			AssetFileDescriptor descriptor;
			descriptor = assetManager.openFd("se_maoudamashii_onepoint20.wav");
			soundId[0] = soundPool.load(descriptor, 1);
			descriptor = assetManager.openFd("se_maoudamashii_onepoint30.wav");
			soundId[1] = soundPool.load(descriptor, 1);
			descriptor = assetManager.openFd("se_maoudamashii_onepoint16.wav");
			soundId[2] = soundPool.load(descriptor, 1);
		} catch (IOException e) {
			Toast.makeText(this, "Couldn't load sound effect from asset, " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}

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
			cv.setBgImageVisible(true);
		} else {
			cv.setBgImageVisible(false);
		}

		boolean handsRotation = sharedPreferences.getBoolean("handsRotation", true);
		if (handsRotation) {
			rotation = true;
		} else {
			rotation = false;
		}

		String timeSelect = sharedPreferences.getString("timeSelect", "10");
		timeInterval = CLOCK_INTERVAL * Integer.valueOf(timeSelect);

		boolean snd = sharedPreferences.getBoolean("sound", true);
		if (snd) {
			this.sound = true;
		} else {
			this.sound = false;
		}

		runnable = new Runnable() {
			@Override
			public void run() {
				switch (state) {
				case QuestionGenerate:
					hh = (int) (Math.random() * 12);
					switch (level) {
					case 0:
						break;
					case 1:
						mm = (int) (Math.random() * 2) * 30;
						break;
					case 2:
						mm = (int) (Math.random() * 4) * 15;
						break;
					case 3:
						mm = (int) (Math.random() * 6) * 10;
						break;
					case 4:
						mm = (int) (Math.random() * 12) * 5;
						break;
					case 5:
						mm = (int) (Math.random() * 60);
						break;
					}
					hhmmss = String.format("%02d:%02d:%02d", hh, mm, ss);
					if(rotation) {
						state = HandsMove;
					} else {
						state = QuestionDisp;
					}
					if (soundId[0] != -1 && sound) soundPool.play(soundId[0], 0.5f, 0.5f, 0, 0, 1);
					handler.postDelayed(this, 0);
					break;
				case HandsMove:
					hms = String.format("%02d:%02d:%02d", h, m, s);
					cv.setTime(hms);
					cv.setDigitalClockVisible(false);
					cv.setMessageVisible(false);
					cv.invalidate();
					m++;
					if (s > 59) {
						m++;
						s = 0;
					}
					if (m > 59) {
						h++;
						m = 0;
					}
					if(h > 11) {
						h = 0;
					}
					if (hhmmss.equals(hms)) {
						state = QuestionDisp;
						handler.postDelayed(this, 500);
						break;
					}
					handler.postDelayed(this, 0);
					break;
				case QuestionDisp:
					cv.setTime(hhmmss);
					cv.setDigitalClockVisible(false);
					cv.setMessageVisible(true);
					cv.invalidate();
					if (soundId[1] != -1 && sound) soundPool.play(soundId[1], 1, 1, 0, 0, 1);
					state = AnswerDisp;
					handler.postDelayed(this, timeInterval);
					break;
				case AnswerDisp:
					cv.setDigitalClockVisible(true);
					cv.setMessageVisible(false);
					cv.invalidate();
					if (soundId[2] != -1 && sound) soundPool.play(soundId[2], 1, 1, 0, 0, 1);
					state = QuestionGenerate;
					handler.postDelayed(this, timeInterval / 2);
					break;
				}
			}
		};
		handler.postDelayed(runnable, 1000);
	}

	@Override
	protected void onPause() {
		super.onPause();

		soundPool.unload(soundId[0]);
		soundPool.unload(soundId[1]);
		soundPool.unload(soundId[2]);
	}

}
