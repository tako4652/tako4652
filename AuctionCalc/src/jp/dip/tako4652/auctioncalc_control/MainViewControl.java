package jp.dip.tako4652.auctioncalc_control;

import jp.dip.tako4652.auctioncalc.R;
import jp.dip.tako4652.auctioncalc_control_adapter.AuctionGuildAdapter;
import jp.dip.tako4652.auctioncalc_control_adapter.AuctionItemAdapter;
import jp.dip.tako4652.auctioncalc_model_dao.GuildNameDuplicateException;
import jp.dip.tako4652.auctioncalc_model_dto.AuctionDTO;
import jp.dip.tako4652.auctioncalc_model_dto.AuctionGuildDTO;
import jp.dip.tako4652.auctioncalc_model_dto.AuctionItemDTO;
import jp.dip.tako4652.auctioncalc_view.MainView;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class MainViewControl extends Activity implements OnLongClickListener {

	private boolean debug = true;

	public final static String TAG = "AuctionCalc";

	private final static int MISSION_ID = 0;
	private final static int GUILD_ID = 1;
	private final static int ITEM_ID = 2;

	public final static String MISSION = "ミッション";
	public final static String GUILD = "党";
	public final static String ITEM = "アイテム";

	public final static String DATE_TIME = "MissionDateTime";
	public final static String NAME = "Name";
	public final static String NUMBER = "Number";

	private final int maxGuildDisp = 3;

	private MainView mView;
	private ImageView iView;
	private int height;

	private Intent intent;

	private AuctionDTO aDTO;
	private AuctionGuildAdapter AGA;
	private AuctionItemAdapter AIA;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = new MainView(this);

		iView = (ImageView) findViewById(R.id.mainImage);
		iView.setOnLongClickListener(this);
		Display display = getWindowManager().getDefaultDisplay();
		Point p = new Point();
		display.getSize(p);
		height = p.y;

		aDTO = new AuctionDTO();
		AGA = new AuctionGuildAdapter();
		AIA = new AuctionItemAdapter();

		disp();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {
			Bundle extras;
			String name = "";
			String member = "";
			extras = intent.getExtras();
			if (extras != null)
				name = extras.getString(NAME);
			switch (requestCode) {
			case MISSION_ID:
				String missionDateTime = "";
				if (extras != null)
					missionDateTime = extras.getString(DATE_TIME);
				aDTO.setMissionName(name);
				aDTO.setMissionDateTime(missionDateTime);
				break;
			case GUILD_ID:
				try {
					if (extras != null)
						member = extras.getString(NUMBER);
					int i = Integer.valueOf(member);
					if (i >= 0)
						AGA.add(new AuctionGuildDTO(name, i));
				} catch (GuildNameDuplicateException e) {
					mView.dispToast(
							getResources().getString(R.string.guildDuplicate),
							MainView.TOAST_SHORT);
				}
				break;
			case ITEM_ID:
				AIA.add(new AuctionItemDTO(name));
			}
		} else if (requestCode == MISSION_ID) {
			aDTO.clear();
			AGA.clear();
			AIA.clear();
		}
		disp();
	}

	@SuppressLint("RtlHardcoded")
	public void onTouch(View v) {
		switch (v.getId()) {
		case R.id.guildButton:
			if (aDTO.getMissionName().equals("")) {
				mView.dispToast(
						"Missonが選択されていません。\n上部のイメージをロングタップしてミッション名を選択してください。",
						MainView.TOAST_SHORT);
			} else {
				intent = new Intent(this, SelectViewControl.class);
				intent = intent.putExtra(TAG, GUILD);
				startActivityForResult(intent, GUILD_ID);
			}
			break;
		case R.id.itemButton:
			if (aDTO.getMissionName().equals("")) {
				mView.dispToast(
						"Missonが選択されていません。\n上部のイメージをロングタップしてミッション名を選択してください。",
						MainView.TOAST_SHORT);
			} else {
				intent = new Intent(this, SelectViewControl.class);
				intent = intent.putExtra(TAG, ITEM);
				intent = intent.putExtra(MISSION, aDTO.getMissionName());
				startActivityForResult(intent, ITEM_ID);
			}
			break;
		default:
			String text = v.getTag().toString();
			String[] str = text.split("_");
			int i = Integer.valueOf(str[1]);
			final AuctionItemDTO aDTO = AIA.get(i);
			final EditText editView = new EditText(this);
			editView.setGravity(Gravity.RIGHT);
			new AlertDialog.Builder(this)
					.setTitle("落札価格")
					.setMessage(aDTO.getItemName())
					.setView(editView)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									int Bid = 0;
									String et = editView.getText().toString();
									if (!et.equals(""))
										Bid = Integer.valueOf(et);
									aDTO.setBid(Bid);
									disp();
								}
							}).show();
		}
	}

	@SuppressLint("RtlHardcoded")
	@Override
	public boolean onLongClick(View v) {
		if (v.getId() == R.id.mainImage) {
			aDTO.clear();
			AGA.clear();
			AIA.clear();
			intent = new Intent(this, SelectViewControl.class);
			intent = intent.putExtra(TAG, MISSION);
			startActivityForResult(intent, MISSION_ID);
		} else {
			String text = v.getTag().toString();
			String[] str = text.split("_");
			final int i = Integer.valueOf(str[1]);
			final AuctionGuildDTO gDTO = AGA.get(i);
			final AuctionItemDTO iDTO = AIA.get(i);
			if (str[0].equals("Guild")) {
				final EditText editView = new EditText(this);
				editView.setGravity(Gravity.RIGHT);
				new AlertDialog.Builder(this)
						.setTitle("参加人数")
						.setMessage(gDTO.getGuildName() + "\n（そのままOKで削除）")
						.setView(editView)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										String et = editView.getText().toString();
										if (et.equals("")){
											AGA.remove(i);
										} else {
											int j = Integer.valueOf(et);
											if (j >= 0)
												gDTO.setMemberCount(j);
										}
										disp();
									}
								}).show();
			} else if (str[0].equals("Item")) {
				new AlertDialog.Builder(this)
				.setTitle("削除確認")
				.setMessage(iDTO.getItemName())
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
								AIA.remove(i);
								disp();
							}
						})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
			}
			disp();
		}
		return true;
	}

	private void disp() {
		int memberCount = 0;
		for (int i = 0; i < AGA.size(); i++) {
			AuctionGuildDTO gDTO = AGA.get(i);
			memberCount += gDTO.getMemberCount();
		}
		aDTO.setMemberCount(memberCount);

		int total = 0;
		for (int i = 0; i < AIA.size(); i++) {
			AuctionItemDTO iDTO = AIA.get(i);
			total += iDTO.getBid();
		}
		aDTO.setTotal(total);

		int devied = 0;
		if (memberCount > 0)
			devied = total / memberCount;
		aDTO.setDevided(devied);

		for (int i = 0; i < AGA.size(); i++) {
			AuctionGuildDTO gDTO = AGA.get(i);
			gDTO.setGuildPayd((devied * gDTO.getMemberCount()));
		}

		mView.setMissionName(aDTO.getMissionName());
		mView.setMissionDateTime(aDTO.getMissionDateTime());
		mView.setGuildCount(AGA.size());
		mView.setMemberCount(aDTO.getMemberCount());
		mView.setTotal(aDTO.getTotal());
		mView.setDevided(aDTO.getDevided());

		mView.setGuildDetail(AGA, maxGuildDisp);
		mView.setItemDetail(AIA, height);

		if (debug)
			debug(aDTO, AGA, AIA);
	}

	private void debug(AuctionDTO aDTO, AuctionGuildAdapter aga,
			AuctionItemAdapter aia) {
		Log.d("AuctionDTO",
				"==================== AuctionDTO ====================");
		Log.d("AuctionDTO", aDTO.toString());
		Log.d("GuildList",
				"==================== GuildList ====================");
		for (int i = 0; i < aga.size(); i++) {
			Log.d("GuildList", String.format("----- No.%d -----", i + 1));
			Log.d("AuctionGuildDTO", aga.get(i).toString());
		}
		Log.d("ItemList", "==================== ItemList ====================");
		for (int i = 0; i < aia.size(); i++) {
			Log.d("ItemList", String.format("----- No.%d -----", i + 1));
			Log.d("AuctionItemDTO", aia.get(i).toString());
		}
	}
}
