package jp.dip.tako4652.clockstudy;

import jp.dip.tako4652.custom_analogclock_view.ClockView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	public static final int MENU_SELECT_A = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new ClockView(this);
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
