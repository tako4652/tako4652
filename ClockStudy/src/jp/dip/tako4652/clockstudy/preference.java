package jp.dip.tako4652.clockstudy;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

public class preference extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);

		CheckBoxPreference justDisp = (CheckBoxPreference) getPreferenceScreen().findPreference("justDisp");
		if (justDisp.isChecked()) {
			justDisp.setSummary(R.string.justDispOn);
		} else {
			justDisp.setSummary(R.string.justDispOff);
		}
		CheckBoxPreference halfDisp = (CheckBoxPreference) getPreferenceScreen().findPreference("justDisp");
		if (halfDisp.isChecked()) {
			halfDisp.setSummary(R.string.halfDispOn);
		} else {
			halfDisp.setSummary(R.string.halfDispOff);
		}
		CheckBoxPreference bmpDisp = (CheckBoxPreference) getPreferenceScreen().findPreference("bmpDisp");
		if (bmpDisp.isChecked()) {
			bmpDisp.setSummary(R.string.bmpDispOn);
		} else {
			bmpDisp.setSummary(R.string.bmpDispOff);
		}
		CheckBoxPreference handsRotation = (CheckBoxPreference) getPreferenceScreen().findPreference("handsRotation");
		if (handsRotation.isChecked()) {
			handsRotation.setSummary(R.string.handsRotationOn);
		} else {
			handsRotation.setSummary(R.string.handsRotationOff);
		}
		ListPreference timeSelect = (ListPreference) getPreferenceScreen().findPreference("timeSelect");
		timeSelect.setSummary(timeSelect.getEntry());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		CheckBoxPreference justDisp = (CheckBoxPreference) getPreferenceScreen().findPreference("justDisp");
		if (justDisp.isChecked()) {
			justDisp.setSummary(R.string.justDispOn);
		} else {
			justDisp.setSummary(R.string.justDispOff);
		}
		CheckBoxPreference halfDisp = (CheckBoxPreference) getPreferenceScreen().findPreference("justDisp");
		if (halfDisp.isChecked()) {
			halfDisp.setSummary(R.string.halfDispOn);
		} else {
			halfDisp.setSummary(R.string.halfDispOff);
		}
		CheckBoxPreference bmpDisp = (CheckBoxPreference) getPreferenceScreen().findPreference("bmpDisp");
		if (bmpDisp.isChecked()) {
			bmpDisp.setSummary(R.string.bmpDispOn);
		} else {
			bmpDisp.setSummary(R.string.bmpDispOff);
		}
		CheckBoxPreference handsRotation = (CheckBoxPreference) getPreferenceScreen().findPreference("handsRotation");
		if (handsRotation.isChecked()) {
			handsRotation.setSummary(R.string.handsRotationOn);
		} else {
			handsRotation.setSummary(R.string.handsRotationOff);
		}
		ListPreference timeSelect = (ListPreference) getPreferenceScreen().findPreference("timeSelect");
		timeSelect.setSummary(timeSelect.getEntry());
		CheckBoxPreference sound = (CheckBoxPreference) getPreferenceScreen().findPreference("sound");
		if (sound.isChecked()) {
			sound.setSummary(R.string.soundOn);
		} else {
			sound.setSummary(R.string.soundOff);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
}
