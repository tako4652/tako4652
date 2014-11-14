package jp.dip.tako4652.auctioncalc_view;

import jp.dip.tako4652.auctioncalc.R;
import jp.dip.tako4652.auctioncalc_control_adapter.SelectAdapter;
import jp.dip.tako4652.auctioncalc_model_dto.SelectDTO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectView extends Activity {

	public final static int TOAST_SHORT = Toast.LENGTH_SHORT;
	public final static int TOAST_LONG = Toast.LENGTH_SHORT;

	private Activity act;
	private LinearLayout base;
	private LinearLayout sItem;

	private TextView sTitle;
	private Button sButton;

	@SuppressLint("Registered")
	public SelectView(Activity activity) {
		act = activity;
		act.setContentView(R.layout.select);

		base = (LinearLayout) act.findViewById(R.id.sBase);
		sItem = (LinearLayout) base.findViewById(R.id.sItem);

		sTitle = (TextView) act.findViewById(R.id.sTitle);
		sButton = (Button) act.findViewById(R.id.sButton);
	}

	// Setter
	public void setTitle(CharSequence title) {
		((TextView) sTitle).setText(title);
	}

	public void setButtonTitle(CharSequence buttonTitle) {
		((Button) sButton).setText(buttonTitle);
	}

	@SuppressLint("InflateParams")
	public void setSelectDetail(SelectAdapter sa) {
		LayoutInflater lf = (LayoutInflater) act
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		LinearLayout[] selectLL = new LinearLayout[sa.size()];
		sItem.removeAllViews();
		for (int i = 0; i < sa.size(); i++) {
			SelectDTO sDTO = sa.get(i);
			selectLL[i] = (LinearLayout) (lf
					.inflate(R.layout.select_list, null));
			((TextView) selectLL[i].findViewById(R.id.sNo)).setText(String
					.format("%d", i + 1));
			((TextView) selectLL[i].findViewById(R.id.sName)).setText(sDTO
					.getName());
			LinearLayout ll = ((LinearLayout) selectLL[i]
					.findViewById(R.id.sList));
			ll.setTag(sDTO.getTag() + "_" + String.format("%d", i) + "_"
					+ sDTO.getName());
			ll.setOnLongClickListener((OnLongClickListener) act);
			sItem.addView(selectLL[i]);
			base.invalidate();
		}

	}

	public void dispToast(String str, int torstDelay) {
		Toast.makeText(act, str, torstDelay).show();
	}
}
