package jp.bunkatusoft.explorersofsettlement.field.settlement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import jp.bunkatusoft.explorersofsettlement.BaseSurfaceView;
import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.field.GameTime;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.time.GameMonthEnum;
import jp.bunkatusoft.explorersofsettlement.time.GameTimeEnum;
import jp.bunkatusoft.explorersofsettlement.time.NoSuchTimeException;
import jp.bunkatusoft.explorersofsettlement.time.TimeUtil;
import jp.bunkatusoft.explorersofsettlement.screen.title.TitleActivity;
import jp.bunkatusoft.explorersofsettlement.util.Util;

public class SettlementFieldActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener, View.OnClickListener {
	FrameLayout mBaseLayout;
	BaseSurfaceView mSurfaceView;

	View mUIView;

	LinearLayout mStatusLayout;
	TextView mSettlementName;
	TextView mSettlementTime;

	LinearLayout mCommandLayout;
	Button mCommandButton1;
	Button mCommandButton2;
	Button mCommandButton3;
	Button mCommandButton4;
	Button mCommandButton5;
	Button mCommandButton6;

	Settlement mSettlement;
	GameTime mGameTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//無地のFrameLayoutを作成し、Activityに設定
		mBaseLayout = new FrameLayout(this);
		setContentView(mBaseLayout);

		mGameTime = new GameTime();
		mSettlement = new Settlement();

		//SurfaceViewをまず追加
		mSurfaceView = new BaseSurfaceView(this);
		mBaseLayout.addView(mSurfaceView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		//XMLで作成したUIViewを追加
		mUIView = getLayoutInflater().inflate(R.layout.activity_field_settlement, null);
		mBaseLayout.addView(mUIView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		//TODO 追加するドン
		mStatusLayout = (LinearLayout) mUIView.findViewById(R.id.settle_part_settleStatusLayout);
		mSettlementName = (TextView) mUIView.findViewById(R.id.settle_part_part_settlementNameText);
		mSettlementTime = (TextView) mUIView.findViewById(R.id.settle_part_part_settlementTimeText);
		InitStatusWindow();

		mCommandLayout = (LinearLayout) mUIView.findViewById(R.id.settle_part_settleCommandLayout);
		mCommandButton1 = (Button) mUIView.findViewById(R.id.settle_part_part_command1Button);
		mCommandButton2 = (Button) mUIView.findViewById(R.id.settle_part_part_command2Button);
		mCommandButton3 = (Button) mUIView.findViewById(R.id.settle_part_part_command3Button);
		mCommandButton4 = (Button) mUIView.findViewById(R.id.settle_part_part_command4Button);
		mCommandButton5 = (Button) mUIView.findViewById(R.id.settle_part_part_command5Button);
		mCommandButton6 = (Button) mUIView.findViewById(R.id.settle_part_part_command6Button);

		mStatusLayout.setVisibility(View.INVISIBLE);
		mCommandLayout.setVisibility(View.INVISIBLE);

		//UIViewの中でIDを探すのに注意
		Button button = (Button) mUIView.findViewById(R.id.part_settle_testButton);
		button.setOnClickListener(this);
	}

	private void InitStatusWindow() {
		mGameTime = setGameTime();
		mSettlementTime.setText(String.format(getString(R.string.settle_state_time_format), mGameTime.getYear(), TimeUtil.getGameMonthName(this, mGameTime.getMonth()), mGameTime.getDay(), TimeUtil.getGameTimeName(this, mGameTime.getTime())));

		mSettlement = setSettlement();
		mSettlementName.setText(mSettlement.getName());
	}

	private GameTime setGameTime() {
		//データを読み込み
		int yearBuf = 1;
		GameMonthEnum monthBuf = GameMonthEnum.JANUARY;
		int dayBuf = 1;
		GameTimeEnum timeBuf = GameTimeEnum.MIDNIGHT;

		//TODO FilePathはどこかへまとめる
		//TODO Keyもまとめる
		String rawJSONText = Util.getAssetsJSONText(this, "save/general.json");
		try {
			JSONObject elementObj = new JSONObject(rawJSONText);

			if (elementObj.has("year")) {
				yearBuf = elementObj.getInt("year");
			}
			if (elementObj.has("month")) {
				monthBuf = GameMonthEnum.valueOf(elementObj.getInt("month"));
			}
			if (elementObj.has("day")) {
				dayBuf = elementObj.getInt("day");
			}
			if (elementObj.has("time")) {
				timeBuf = GameTimeEnum.valueOf(elementObj.getInt("time"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchTimeException e) {
			e.printStackTrace();
		}

		return new GameTime(yearBuf, monthBuf, dayBuf, timeBuf);
	}

	private Settlement setSettlement(){
		String nameBuf = "neo-Saitama";

		//TODO FilePathはどこかへまとめる
		//TODO Keyもまとめる
		String rawJSONText = Util.getAssetsJSONText(this, "save/settlement.json");
		try {
			JSONObject elementObj = new JSONObject(rawJSONText);

			if (elementObj.has("name")) {
				nameBuf = elementObj.getString("name");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new Settlement(nameBuf);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settlement_field_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_settle_field_load:
				showSystemDialog(SystemMenuEnum.LOAD);
				break;
			case R.id.menu_settle_field_save:
				showSystemDialog(SystemMenuEnum.SAVE);
				break;
			case R.id.menu_settle_field_setting:
				showSystemDialog(SystemMenuEnum.SETTINGS);
				break;
			case R.id.menu_settle_field_return_title:
				showSystemDialog(SystemMenuEnum.RETURN_TITLE);
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSystemDialog(SystemMenuEnum menuEnum) {
		SystemDialog systemDialog;
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (menuEnum) {
			case LOAD:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog, null);
				ft.commitAllowingStateLoss();
				break;
			case SAVE:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog, null);
				ft.commitAllowingStateLoss();
				break;
			case SETTINGS:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog, null);
				ft.commitAllowingStateLoss();
				break;
			case RETURN_TITLE:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_confirm_back_title), getString(R.string.yes), getString(R.string.no), menuEnum);
				ft.add(systemDialog, null);
				ft.commitAllowingStateLoss();
				break;
			default:
				//未実装の定義は省略
				break;
		}
	}

	@Override
	public void OnPositiveClickListener(SystemMenuEnum menuEnum) {
		switch (menuEnum) {
			case ACHIEVEMENTS:
				break;
			case SETTINGS:
				break;
			case VERSION_INFO:
				break;
			case RETURN_TITLE:
				startActivity(new Intent(SettlementFieldActivity.this, TitleActivity.class));
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void OnNegativeClickListener(SystemMenuEnum menuEnum) {
		switch (menuEnum) {
			case ACHIEVEMENTS:
				break;
			case SETTINGS:
				break;
			case VERSION_INFO:
				break;
			case RETURN_TITLE:
				break;
			default:
				break;
		}
	}

	@Override
	public void onClick(View view) {
		if (mSurfaceView.getFlag()) {
			mSurfaceView.setFlag(false);
		} else {
			mSurfaceView.setFlag(true);
		}

		if (mStatusLayout.getVisibility() == View.VISIBLE) {
			//TODO ここでトグる
			mStatusLayout.setVisibility(View.INVISIBLE);
		} else {
			mStatusLayout.setVisibility(View.VISIBLE);
		}

		if (mCommandLayout.getVisibility() == View.VISIBLE) {
			//TODO ここでトグる
			mCommandLayout.setVisibility(View.INVISIBLE);
		} else {
			mCommandLayout.setVisibility(View.VISIBLE);
		}
	}
}
