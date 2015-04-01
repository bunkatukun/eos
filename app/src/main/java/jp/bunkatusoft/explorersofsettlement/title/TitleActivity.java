package jp.bunkatusoft.explorersofsettlement.title;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.debug.DebugActivity;
import jp.bunkatusoft.explorersofsettlement.field.settlement.SettlementFieldActivity;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialogView;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.util.Util;

//OverlayLayoutが出現する場合、下層のClickListenerを解除する必要がある
public class TitleActivity extends FragmentActivity implements View.OnClickListener, SystemDialogView.OnDialogClickListener, View.OnTouchListener {

	Context mContext;
	FrameLayout mRootLayout;
	RelativeLayout mMainLayout;

	boolean isOverlayNow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		isOverlayNow = false;
		//requestWindowFeature(Window.FEATURE_NO_TITLE);

		mRootLayout = new FrameLayout(this);
		setContentView(mRootLayout);

		setMainLayout();
	}

	private void setMainLayout() {
		mMainLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_title, null);
		mRootLayout.addView(mMainLayout);

		Button newStartButton = (Button) findViewById(R.id.part_title_newStartButton);
		newStartButton.setOnTouchListener(this);
		newStartButton.setOnClickListener(this);
		Button continueButton = (Button) findViewById(R.id.part_title_continueButton);
		continueButton.setOnTouchListener(this);
		continueButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.title_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_title_achievements:
				showSystemDialog(SystemMenuEnum.ACHIEVEMENTS);
				break;
			case R.id.menu_title_settings:
				showSystemDialog(SystemMenuEnum.SETTINGS);
				break;
			case R.id.menu_title_version_info:
				showSystemDialog(SystemMenuEnum.VERSION_INFO);
				break;
			case R.id.menu_title_start_debug:
				showSystemDialog(SystemMenuEnum.DEBUG);
				break;
			case R.id.menu_title_quit:
				showSystemDialog(SystemMenuEnum.QUIT);
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSystemDialog(SystemMenuEnum menuEnum) {
		SystemDialogView systemDialogView;
		isOverlayNow = true;
		switch (menuEnum) {
			case ACHIEVEMENTS:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				systemDialogView.startAnimation(true);
				break;
			case SETTINGS:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				systemDialogView.startAnimation(true);
				break;
			case VERSION_INFO:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, String.format(getString(R.string.title_text_version_info), Util.getPackageVersion(mContext)), getString(R.string.back), null, menuEnum);
				systemDialogView.startAnimation(true);
				break;
			case DEBUG:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, getString(R.string.sys_msg_confirm_run_debug), getString(R.string.yes), getString(R.string.no), menuEnum);
				systemDialogView.startAnimation(true);
				break;
			case QUIT:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, getString(R.string.sys_msg_confirm_gameend), getString(R.string.yes), getString(R.string.no), menuEnum);
				systemDialogView.startAnimation(true);
				break;
			default:
				//未実装の定義は省略
				break;
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.part_title_newStartButton:
				startActivity(new Intent(TitleActivity.this, SettlementFieldActivity.class));
				finish();
				break;
			case R.id.part_title_continueButton:
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void onPositiveClick(SystemMenuEnum menu) {
		isOverlayNow = false;
		switch (menu) {
			case ACHIEVEMENTS:
				break;
			case SETTINGS:
				break;
			case VERSION_INFO:
				break;
			case DEBUG:
				startActivity(new Intent(TitleActivity.this, DebugActivity.class));
				finish();
				break;
			case QUIT:
				finish();
				break;
		}
	}

	@Override
	public void onNegativeClick(SystemMenuEnum menu) {
		isOverlayNow = false;
		switch (menu) {
			case ACHIEVEMENTS:
				break;
			case SETTINGS:
				break;
			case VERSION_INFO:
				break;
			case DEBUG:
				break;
			case QUIT:
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return isOverlayNow;
	}
}
