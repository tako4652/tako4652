package jp.dip.tako4652.auctioncalc_view;

import jp.dip.tako4652.auctioncalc.R;
import jp.dip.tako4652.auctioncalc_control_adapter.AuctionGuildAdapter;
import jp.dip.tako4652.auctioncalc_control_adapter.AuctionItemAdapter;
import jp.dip.tako4652.auctioncalc_model_dto.AuctionGuildDTO;
import jp.dip.tako4652.auctioncalc_model_dto.AuctionItemDTO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends Activity {

	public final static int TOAST_SHORT = Toast.LENGTH_SHORT;
	public final static int TOAST_LONG = Toast.LENGTH_SHORT;

	private Activity act;

	private TextView tvMissionName;
	private TextView tvMissionDateTime;
	private TextView tvGuildCount;
	private TextView tvMemberCount;
	private TextView tvTotal;
	private TextView tvPayd;

	private LinearLayout base;
	private LinearLayout guild;
	private LinearLayout item;
	private LinearLayout ll;

	@SuppressLint("Registered")
	public MainView(Activity activity) {
		act = activity;
		act.setContentView(R.layout.main);

		tvMissionName = (TextView) act.findViewById(R.id.MissionName);
		tvMissionDateTime = (TextView) act.findViewById(R.id.MissionDateTime);
		tvGuildCount = (TextView) act.findViewById(R.id.GuildCount);
		tvMemberCount = (TextView) act.findViewById(R.id.MemberCount);
		tvTotal = (TextView) act.findViewById(R.id.Total);
		tvPayd = (TextView) act.findViewById(R.id.Payed);

		base = (LinearLayout) act.findViewById(R.id.mBase);
		guild = (LinearLayout) base.findViewById(R.id.guild);
		item = (LinearLayout) base.findViewById(R.id.item);
	}

	@SuppressLint("InflateParams")
	public void setGuildDetail(AuctionGuildAdapter aga, int maxGuildDisp) {
		LayoutInflater lf = (LayoutInflater) act
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		LinearLayout[] guildLL = new LinearLayout[aga.size() + 1];
		guild.removeAllViews();
		int gCnt = 0;
		for (int i = 0; i < aga.size(); i++) {
			AuctionGuildDTO gDTO = aga.get(i);
			guildLL[i] = (LinearLayout) (lf.inflate(
					R.layout.auction_guild_list, null));
			((TextView) guildLL[i].findViewById(R.id.gNo)).setText(String
					.format("%d", i + 1));
			((TextView) guildLL[i].findViewById(R.id.gGuildName)).setText(gDTO
					.getGuildName());
			((TextView) guildLL[i].findViewById(R.id.gMemberCount))
					.setText(String.format("%d名", gDTO.getMemberCount()));
			((TextView) guildLL[i].findViewById(R.id.gGuildPayd))
					.setText(String.format("%dM", gDTO.getGuildPayd()));
			ll = ((LinearLayout) guildLL[i].findViewById(R.id.guildList));
			ll.setTag("Guild_" + String.format("%d", i));
			ll.setOnLongClickListener((OnLongClickListener) act);
			guild.addView(guildLL[i]);
			gCnt++;
		}
		guildLL[gCnt] = (LinearLayout) (lf.inflate(R.layout.add_button, null));
		guild.addView(guildLL[gCnt]);

		ScrollView guildSpace;
		guildSpace = (ScrollView) act.findViewById(R.id.guildSpace);

		if (gCnt >= maxGuildDisp) {
			guildSpace.getLayoutParams().height = 37 * maxGuildDisp;
		} else {
			guildSpace.getLayoutParams().height = 37 * gCnt + 75;
		}
		guildSpace.requestLayout();
	}

	@SuppressLint("InflateParams")
	public void setItemDetail(AuctionItemAdapter AIA, int height) {
		LayoutInflater lf = (LayoutInflater) act
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		LinearLayout[] itemLL = new LinearLayout[AIA.size()];
		item.removeAllViews();
		for (int i = 0; i < AIA.size(); i++) {
			AuctionItemDTO iDTO = AIA.get(i);
			itemLL[i] = (LinearLayout) (lf.inflate(R.layout.auction_item_list,
					null));
			((TextView) itemLL[i].findViewById(R.id.iNo)).setText(String
					.format("%d", i + 1));
			((TextView) itemLL[i].findViewById(R.id.iItemName)).setText(iDTO
					.getItemName());
			((TextView) itemLL[i].findViewById(R.id.iBid)).setText(String
					.format("%dM", iDTO.getBid()));
			ll = ((LinearLayout) itemLL[i].findViewById(R.id.itemList));
			ll.setTag("Item_" + String.format("%d", i));
			ll.setOnLongClickListener((OnLongClickListener) act);
			item.addView(itemLL[i]);
		}
		ScrollView itemSpace;
		itemSpace = (ScrollView) act.findViewById(R.id.itemSpace);
		itemSpace.getLayoutParams().height = height - 700;
		itemSpace.requestLayout();
	}

	public void setMissionName(String missionName) {
		((TextView) tvMissionName).setText(missionName);
	}

	public void setMissionDateTime(String missionDateTime) {
		((TextView) tvMissionDateTime).setText(missionDateTime);
	}

	public void setGuildCount(int guildCount) {
		if (guildCount == 0) {
			((TextView) tvGuildCount).setText("");
		} else if (guildCount == 1) {
			((TextView) tvGuildCount).setText("1党");
		} else {
			((TextView) tvGuildCount).setText(String
					.format("%d党合同", guildCount));
		}

	}

	public void setMemberCount(int memberCount) {
		((TextView) tvMemberCount).setText(String.format("%d名", memberCount));
	}

	public void setTotal(int total) {
		((TextView) tvTotal).setText(String.format("%d", total));
	}

	public void setDevided(int devided) {
		((TextView) tvPayd).setText(String.format("%d", devided));

	}

	public void dispToast(String str, int torstDelay) {
		Toast.makeText(act, str, torstDelay).show();
	}
}
