package jp.dip.tako4652.auctioncalc_control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jp.dip.tako4652.auctioncalc.R;
import jp.dip.tako4652.auctioncalc_control_adapter.SelectAdapter;
import jp.dip.tako4652.auctioncalc_model_dao.GuildNameDuplicateException;
import jp.dip.tako4652.auctioncalc_model_dto.SelectDTO;
import jp.dip.tako4652.auctioncalc_view.SelectView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class SelectViewControl extends Activity implements OnLongClickListener {

	private boolean debug = true;

	private SelectView sView;
	private SelectAdapter SA;

	private String Tag;
	private String MissionName;
	private String externalStoragePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle data = getIntent().getExtras();
		Tag = data.getString(MainViewControl.TAG);

		sView = new SelectView(this);

		setResult(Activity.RESULT_CANCELED);

		externalStoragePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator
				+ "AuctionCalc"
				+ File.separator;

		TextView sTitle = (TextView) findViewById(R.id.sTitle);

		SA = new SelectAdapter();
		if (Tag.equals(MainViewControl.MISSION)) {
			MissionName = "";
			sView.setTitle(getResources().getString(R.string.mission_list));
			sTitle.setBackgroundColor(getResources()
					.getColor(R.color.darkgreen));
			sTitle.setTextColor(getResources().getColor(R.color.text_light));
			sView.setButtonTitle(getResources().getString(R.string.missionAdd));
		} else if (Tag.equals(MainViewControl.GUILD)) {
			MissionName = "";
			sView.setTitle(getResources().getString(R.string.guild_list));
			sTitle.setBackgroundColor(getResources().getColor(
					R.color.time_bgcolor));
			sView.setButtonTitle(getResources().getString(R.string.guildAdd));
		} else if (Tag.equals(MainViewControl.ITEM)) {
			MissionName = data.getString(MainViewControl.MISSION);
			sView.setTitle(getResources().getString(R.string.item_list));
			sTitle.setBackgroundColor(getResources().getColor(R.color.silver));
			sTitle.setTextColor(getResources().getColor(R.color.black));
			sView.setButtonTitle(getResources().getString(R.string.itemAdd));
		}
		dataLoad();
		disp();
	}

	@SuppressLint({ "SimpleDateFormat", "RtlHardcoded" })
	public void onTouch(View v) {
		if (v.getId() == R.id.sButton) {
			final EditText editView = new EditText(this);
			new AlertDialog.Builder(this)
					.setTitle(Tag + "　追加")
					.setView(editView)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String name = editView.getText().toString();
									try {
										SA.add(new SelectDTO(Tag, name));
										disp();
										dataSave();
									} catch (GuildNameDuplicateException e) {
										int resId = 0;
										if (Tag.equals(MainViewControl.MISSION)) {
											resId = R.string.missionDuplicate;
										} else if (Tag
												.equals(MainViewControl.GUILD)) {
											resId = R.string.guildDuplicate;
										} else if (Tag
												.equals(MainViewControl.ITEM)) {
											resId = R.string.itemDuplicate;
										}
										sView.dispToast(getResources()
												.getString(resId),
												SelectView.TOAST_SHORT);
									}
								}
							}).show();
		} else {
			Calendar execCalendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String appExecDateTime = sdf.format(execCalendar.getTime());

			String text = v.getTag().toString();
			String[] str = text.split("_");

			final Intent intent = new Intent();
			intent.putExtra(MainViewControl.NAME, str[2]);
			intent.putExtra(MainViewControl.DATE_TIME, appExecDateTime);

			if (str[0].equals(MainViewControl.GUILD)) {
				final EditText editView = new EditText(this);
				editView.setGravity(Gravity.RIGHT);
				new AlertDialog.Builder(this)
						.setTitle("参加人数")
						.setMessage(str[2] + "\n（未定の場合はそのままOK）")
						.setView(editView)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										String et = editView.getText()
												.toString();
										if (et.equals("")) et = "0";
										intent.putExtra(MainViewControl.NUMBER,
												et);
										setResult(Activity.RESULT_OK, intent);
										finish();
									}
								}).show();
			} else {
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		}
	}

	@Override
	public boolean onLongClick(View v) {
		String text = v.getTag().toString();
		String[] str = text.split("_");
		final int i = Integer.valueOf(str[1]);
		new AlertDialog.Builder(this)
		.setTitle("削除確認")
		.setMessage(str[2])
		.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int whichButton) {
						SA.remove(i);
						disp();
						dataSave();
					}
				})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}).show();
		disp();
		return true;
	}

	private void disp() {
		sView.setSelectDetail(SA);
		if (debug)
			debug(SA);
	}

	private void dataLoad() {
		String fileName = externalStoragePath + Tag + ".txt";
		if (!MissionName.equals("")) {
			fileName = externalStoragePath + Tag + "_" + MissionName + ".txt";
		}

		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis, "SHIFT_JIS");
			BufferedReader br = new BufferedReader(isr);
			String data;
			while (true) {
				data = br.readLine();
				Log.d("アイテム", data);
				if (data == null)
					break;
				SA.add(new SelectDTO(Tag, data));
			}
			br.close();
		} catch (Exception e) {

		}
	}

	private void dataSave() {
		String fileName = externalStoragePath + Tag + ".txt";
		if (!MissionName.equals("")) {
			fileName = externalStoragePath + Tag + "_" + MissionName + ".txt";
		}

		File file = new File(fileName);
		file.getParentFile().mkdir();

		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "SHIFT_JIS");
			BufferedWriter bw = new BufferedWriter(osw);
			for (int i = 0; i < SA.size(); i++) {
				String str = SA.get(i).getName();
				bw.write(str + "\n");
				bw.flush();
			}
			bw.close();
		} catch (Exception e) {

		}
	}

	private void debug(SelectAdapter sa) {
		Log.d("GuildList", "==================== List ====================");
		for (int i = 0; i < sa.size(); i++) {
			Log.d("List", String.format("----- No.%d -----", i + 1));
			Log.d("SelectDTO", sa.get(i).toString());
		}
	}
}
