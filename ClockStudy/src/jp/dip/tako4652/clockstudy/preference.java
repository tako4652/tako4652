package jp.dip.tako4652.clockstudy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;

public class preference extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
	}

	@SuppressWarnings("deprecation")
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		CheckBoxPreference justDisp = (CheckBoxPreference) getPreferenceScreen()
				.findPreference("justDisp");
		if (justDisp.isChecked()) {
			justDisp.setSummary(R.string.justDispOn);
		} else {
			justDisp.setSummary(R.string.justDispOff);
		}
		CheckBoxPreference halfDisp = (CheckBoxPreference) getPreferenceScreen()
				.findPreference("justDisp");
		if (halfDisp.isChecked()) {
			halfDisp.setSummary(R.string.halfDispOn);
		} else {
			halfDisp.setSummary(R.string.halfDispOff);
		}
		CheckBoxPreference bmpDisp = (CheckBoxPreference) getPreferenceScreen()
				.findPreference("bmpDisp");
		if (bmpDisp.isChecked()) {
			bmpDisp.setSummary(R.string.bmpDispOn);
		} else {
			bmpDisp.setSummary(R.string.bmpDispOff);
		}
	}
}
