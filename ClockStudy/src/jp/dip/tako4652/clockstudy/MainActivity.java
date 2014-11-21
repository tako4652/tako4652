package jp.dip.tako4652.clockstudy;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final int MENU_SELECT_A = 0;

	private SoundPool soundPool;
	private int soundId = -1;
	private boolean sound = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		boolean snd = sharedPreferences.getBoolean("sound", true);
		if (snd) {
			this.sound = true;
		} else {
			this.sound = false;
		}

		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

		try{
			AssetManager assetManager = getAssets();
			AssetFileDescriptor descriptor;
			descriptor = assetManager.openFd("se_maoudamashii_magical01.wav");
			soundId = soundPool.load(descriptor, 1);
		} catch (IOException e) {
			Toast.makeText(this, "Couldn't load sound effect from asset, " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		soundPool.unload(soundId);
	}

	public void onClick(View v) {
		int level = 0;
		switch (v.getId()) {
		case R.id.button0:
			level = 0;
			break;
		case R.id.button1:
			level = 1;
			break;
		case R.id.button2:
			level = 2;
			break;
		case R.id.button3:
			level = 3;
			break;
		case R.id.button4:
			level = 4;
			break;
		case R.id.button5:
			level = 5;
			break;
		}

		if (soundId != -1 && sound) soundPool.play(soundId, 1, 1, 0, 0, 1);

		Intent intent = new Intent(this, SubActivity.class);
		intent.putExtra("Level", String.valueOf(level));
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SELECT_A, 0, "設定").setIcon(
				android.R.drawable.ic_menu_manage);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SELECT_A:
			Intent intent = new Intent(this, preference.class);
			startActivity(intent);
			return true;
		}
		return false;
	}
}
