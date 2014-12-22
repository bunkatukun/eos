package jp.bunkatusoft.explorersofsettlement.debug;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import jp.bunkatusoft.explorersofsettlement.field.SettlementFieldActivity;

/**
 * このActivity および関連項目のみ、可能な限りresourceに分担させないようにする<br>
 * レイアウトや文言はソース中で定義
 */
public class DebugActivity extends FragmentActivity implements View.OnClickListener {

	Context mContext;
	LinearLayout mBaseLayout;

	//TODO 追加・削除に強くする
	Button mButton1;
	Button mButton2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		mBaseLayout = new LinearLayout(mContext);
		mBaseLayout.setOrientation(LinearLayout.VERTICAL);
		setContentView(mBaseLayout);

		mButton1 = new Button(mContext);
		mButton1.setTag("start_settlement");
		mButton1.setText("Start Settlement Test");
		mButton1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		mButton1.setOnClickListener(this);
		mBaseLayout.addView(mButton1);

		mButton2 = new Button(mContext);
		mButton2.setTag("start_field");
		mButton2.setText("Start Field Test");
		mButton2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		mButton2.setOnClickListener(this);
		mBaseLayout.addView(mButton2);
	}

	@Override
	public void onClick(View view) {
		String tag = view.getTag().toString();
		if("start_settlement".equals(tag)){
			startActivity(new Intent(DebugActivity.this, SettlementFieldActivity.class));
			finish();
		} else if("start_field".equals(tag)){
			//TODO ワールドマップへ遷移
		}
	}
}
