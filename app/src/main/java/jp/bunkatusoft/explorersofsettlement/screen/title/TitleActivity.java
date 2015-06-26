package jp.bunkatusoft.explorersofsettlement.screen.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.debug.DebugActivity;
import jp.bunkatusoft.explorersofsettlement.screen.ScreenActivity;
import jp.bunkatusoft.explorersofsettlement.screen.localmap.LocalMapActivity;
import jp.bunkatusoft.explorersofsettlement.screen.playdata.PlayDataActivity;
import jp.bunkatusoft.explorersofsettlement.screen.record.RecordActivity;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialogView;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuView;
import jp.bunkatusoft.explorersofsettlement.util.Util;

public class TitleActivity extends ScreenActivity implements View.OnClickListener, SystemDialogView.OnDialogClickListener, View.OnTouchListener, SystemMenuView.OnMenuChoiceListener {

	SystemMenuView mMenuView;
	boolean isOpenMenu;

	boolean isBlockTouch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initBlockTouch();
		initBackgroundLayout();
		initUILayout();
		initSystemMenuView();
	}

	@Override
	protected void initBackgroundLayout() {
		//TODO 状況に応じ、複数のBGを切り替えるようにする
		//TODO アニメーションするBGに対応
		mRootLayout.setBackgroundResource(R.drawable.background_title_test);
	}

	@Override
	protected void initSurfaceViewLayout() {

	}

	@Override
	protected void initUILayout() {
		mUILayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.ui_activity_title, null);
		mRootLayout.addView(mUILayout);

		Button newStartButton = (Button) findViewById(R.id.title_part_newStartButton);
		newStartButton.setOnTouchListener(this);
		newStartButton.setOnClickListener(this);
		Button continueButton = (Button) findViewById(R.id.title_part_continueButton);
		continueButton.setOnTouchListener(this);
		continueButton.setOnClickListener(this);
		ImageButton settingButton = (ImageButton) findViewById(R.id.title_part_settingsButton);
		settingButton.setOnTouchListener(this);
		settingButton.setOnClickListener(this);
	}

	private void initSystemMenuView() {
		List<SystemMenuEnum> systemMenuList = new ArrayList<SystemMenuEnum>();
		systemMenuList.add(SystemMenuEnum.ACHIEVEMENTS);
		systemMenuList.add(SystemMenuEnum.SETTINGS);
		systemMenuList.add(SystemMenuEnum.DEBUG);
		systemMenuList.add(SystemMenuEnum.VERSION_INFO);
		systemMenuList.add(SystemMenuEnum.QUIT);

		mMenuView = new SystemMenuView(this, (RelativeLayout) mUILayout, this, R.id.title_part_settingsButton, (ArrayList) systemMenuList);
		isOpenMenu = false;
	}

	private void initBlockTouch() {
		isBlockTouch = false;
	}

	private void setBlockTouch(boolean block) {
		isBlockTouch = block;
	}

	@Override
	public void onPositiveClick(SystemMenuEnum menu) {
		setBlockTouch(false);
		mMenuView.setBlockTouch(false);
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
		setBlockTouch(false);
		mMenuView.setBlockTouch(false);
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
		return isBlockTouch;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.title_part_newStartButton:
				startActivity(new Intent(TitleActivity.this, LocalMapActivity.class));
				finish();
				break;
			case R.id.title_part_continueButton:
				Intent playDataIntent = new Intent(TitleActivity.this, PlayDataActivity.class);
				playDataIntent.putExtra("callActivity","TitleActivity");
				startActivity(playDataIntent);
				finish();
				break;
			case R.id.title_part_settingsButton:
				mMenuView.startAnimation(isOpenMenu);
				isOpenMenu = !isOpenMenu;
				break;
			default:
				break;
		}
	}

	@Override
	public void onSelectMenu(SystemMenuEnum menuEnum) {
		SystemDialogView systemDialogView;
		setBlockTouch(true);
		mMenuView.setBlockTouch(true);
		mMenuView.startAnimation(isOpenMenu);
		isOpenMenu = !isOpenMenu;
		switch (menuEnum) {
			case ACHIEVEMENTS:
				startActivity(new Intent(TitleActivity.this, RecordActivity.class));
				finish();
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
}
