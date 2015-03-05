package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.ButtonGroup;
import jp.bunkatusoft.explorersofsettlement.system.ButtonGroupEnum;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationEnum;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationUtil;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;


public class WorldFieldActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener, View.OnClickListener, ButtonGroup.OnClickListener {

	Context mContext;

	/**
	 * フィールドデータ用
	 */
	List<FieldPiece> mFieldPieces;
	List<FieldRoad> mFieldRoads;

	/** フィールドコマンドのレイアウト */
	LinearLayout mCommandLayout;

	/** オプションコマンドのレイアウト */
	LinearLayout mOptionLayout;
	boolean isOpenOption = false;

	View mOverlayWindow;

	AnimationSet mInAnimation;
	AnimationSet mOutAnimation;
	AnimationSet mProtrudeAnimation;
	AnimationSet mRecedeAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mContext = this;

		// データの読み込み
		mFieldPieces = new ArrayList<FieldPiece>();
		mFieldRoads = new ArrayList<FieldRoad>();
		mFieldPieces = WorldFieldUtil.loadFieldPieceData(this, "data/field_piece.json");
		try {
			mFieldRoads = WorldFieldUtil.loadFieldRoadData(this, "data/field_road.json");
		} catch (IOException e) {
			LogUtil.e(e);
		}

		// アニメーションの設定
		mInAnimation = CustomAnimationUtil.generateCustomAnimation(this, CustomAnimationEnum.SLIDE_IN_FROM_RIGHT_10P);
		mOutAnimation = CustomAnimationUtil.generateCustomAnimation(this,CustomAnimationEnum.SLIDE_OUT_TO_RIGHT_10P);
		mProtrudeAnimation = CustomAnimationUtil.generateCustomAnimation(this,CustomAnimationEnum.PROTRUDE_IN_FROM_CENTER);
		mRecedeAnimation = CustomAnimationUtil.generateCustomAnimation(this,CustomAnimationEnum.RECEDE_OUT_TO_CENTER);

		FrameLayout rootLayout = new FrameLayout(this);
		setContentView(rootLayout);

		//SurfaceViewをまず追加
		WorldSurfaceView surfaceView = new WorldSurfaceView(this);
		surfaceView.setOnClickListener(this);
		rootLayout.addView(surfaceView);

		//UIレイアウトを追加
		RelativeLayout UILayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.part_activity_field_uis, null);
		rootLayout.addView(UILayout);
		ImageButton openSettingButton = (ImageButton) UILayout.findViewById(R.id.world_part_settingsButton);
		openSettingButton.setOnClickListener(this);

		// オプションコマンドを作成して追加、初期表示設定
		createMenuList(UILayout);
		mOptionLayout.setVisibility(View.GONE);

		// フィールドコマンドを作成して追加、初期表示設定
		createCommandList(UILayout);
		mCommandLayout.setVisibility(View.VISIBLE);

		//ポップアップウインドウは初期状態で非表示とする
		mOverlayWindow = getLayoutInflater().inflate(R.layout.overlay_window_base, null);
		rootLayout.addView(mOverlayWindow);
		mOverlayWindow.setVisibility(View.GONE);
		Button closeOverlayButton = (Button) mOverlayWindow.findViewById(R.id.general_part_closeOverlayButton);
		closeOverlayButton.setOnClickListener(this);
	}

	/**
	 * オプションコマンドレイアウトの作成と追加を行う
	 * @param UILayout	作成したレイアウトを追加する親レイアウト
	 */
	public void createMenuList(RelativeLayout UILayout) {
		mOptionLayout = new LinearLayout(mContext);
		mOptionLayout.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams optionLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

		LinearLayout firstLineLayout = new LinearLayout(mContext);
		firstLineLayout.setOrientation(LinearLayout.VERTICAL);
		firstLineLayout.setGravity(Gravity.BOTTOM);
		LinearLayout.LayoutParams firstLineLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		//TODO 文言のResource化
		ButtonGroup optionSaveGroup = new ButtonGroup(mContext, ButtonGroupEnum.SAVE, this, R.drawable.system_icon_save, "セーブ");
		ButtonGroup optionLoadGroup = new ButtonGroup(mContext, ButtonGroupEnum.LOAD, this, R.drawable.system_icon_load, "ロード");
		firstLineLayout.addView(optionSaveGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		firstLineLayout.addView(optionLoadGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		firstLineLayoutParam.gravity = Gravity.BOTTOM;
		firstLineLayoutParam.setMargins(0, 0, 0, getDensityPoint(mContext, 16));
		mOptionLayout.addView(firstLineLayout, firstLineLayoutParam);

		LinearLayout secondLineLayout = new LinearLayout(mContext);
		secondLineLayout.setOrientation(LinearLayout.VERTICAL);
		secondLineLayout.setGravity(Gravity.BOTTOM);
		LinearLayout.LayoutParams secondLineLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		//TODO 文言のResource化
		ButtonGroup settingConfigGroup = new ButtonGroup(mContext, ButtonGroupEnum.CONFIGS, this, R.drawable.system_icon_dummy, "設定");
		ButtonGroup settingReturnTitleGroup = new ButtonGroup(mContext, ButtonGroupEnum.RETURN_TITLE, this, R.drawable.system_icon_home, "タイトルへ");
		secondLineLayout.addView(settingConfigGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		secondLineLayout.addView(settingReturnTitleGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		secondLineLayoutParam.gravity = Gravity.BOTTOM;
		secondLineLayoutParam.setMargins(getDensityPoint(mContext, 16), 0, 0, 0);
		mOptionLayout.addView(secondLineLayout, secondLineLayoutParam);

		optionLayoutParam.addRule(RelativeLayout.BELOW, R.id.world_part_settingsButton);
		optionLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		optionLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		UILayout.addView(mOptionLayout, optionLayoutParam);
	}

	/**
	 * フィールドレイアウトコマンドの作成と追加を行う
	 * @param UILayout	作成したレイアウトを追加する親レイアウト
	 */
	public void createCommandList(RelativeLayout UILayout) {
		mCommandLayout = new LinearLayout(mContext);
		mCommandLayout.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams commandLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

		LinearLayout firstLineLayout = new LinearLayout(mContext);
		firstLineLayout.setOrientation(LinearLayout.VERTICAL);
		firstLineLayout.setGravity(Gravity.BOTTOM);
		LinearLayout.LayoutParams firstLineLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		//TODO 文言のResource化
		ButtonGroup commandEnterGroup = new ButtonGroup(mContext, ButtonGroupEnum.ENTER, this, R.drawable.icon_enter, "進入");
		ButtonGroup commandMembersGroup = new ButtonGroup(mContext, ButtonGroupEnum.MEMBERS, this, R.drawable.icon_members, "メンバー");
		ButtonGroup commandItemsGroup = new ButtonGroup(mContext, ButtonGroupEnum.ITEMS, this, R.drawable.icon_items, "荷物");
		firstLineLayout.addView(commandEnterGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		firstLineLayout.addView(commandMembersGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		firstLineLayout.addView(commandItemsGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		firstLineLayoutParam.gravity = Gravity.BOTTOM;
		firstLineLayoutParam.setMargins(0, 0, 0, getDensityPoint(mContext, 16));
		mCommandLayout.addView(firstLineLayout, firstLineLayoutParam);

		LinearLayout secondLineLayout = new LinearLayout(mContext);
		secondLineLayout.setOrientation(LinearLayout.VERTICAL);
		secondLineLayout.setGravity(Gravity.BOTTOM);
		LinearLayout.LayoutParams secondLineLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		//TODO 文言のResource化
		ButtonGroup commandInfoGroup = new ButtonGroup(mContext, ButtonGroupEnum.INFO, this, R.drawable.icon_info, "情報");
		ButtonGroup commandActionsGroup = new ButtonGroup(mContext, ButtonGroupEnum.ACTIONS, this, R.drawable.icon_actions, "アクション");
		ButtonGroup commandLeaveGroup = new ButtonGroup(mContext, ButtonGroupEnum.LEAVE, this, R.drawable.icon_exit, "他の場所へ");
		secondLineLayout.addView(commandInfoGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		secondLineLayout.addView(commandActionsGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		secondLineLayout.addView(commandLeaveGroup.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		secondLineLayoutParam.gravity = Gravity.BOTTOM;
		secondLineLayoutParam.setMargins(getDensityPoint(mContext, 16), 0, 0, 0);
		mCommandLayout.addView(secondLineLayout, secondLineLayoutParam);

		commandLayoutParam.addRule(RelativeLayout.BELOW, R.id.world_part_settingsButton);
		commandLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		commandLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		UILayout.addView(mCommandLayout, commandLayoutParam);
	}

	//TODO DensityPointはいずれ他の場所でも使うと思われるので、その場合はUtil化すべし
	private int getDensityPoint(Context context, int value) {
		return (int) (value * context.getResources().getDisplayMetrics().density);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.world_field_activity, menu);
		return true;
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
			case LOAD:
				break;
			case SAVE:
				break;
			case SETTINGS:
				break;
			case RETURN_TITLE:
				startActivity(new Intent(WorldFieldActivity.this, TitleActivity.class));
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void OnNegativeClickListener(SystemMenuEnum menuEnum) {
		switch (menuEnum) {
			case LOAD:
				break;
			case SAVE:
				break;
			case SETTINGS:
				break;
			case RETURN_TITLE:
				break;
			default:
				break;
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			//将来的にボタンごとの動作を実装するため、分岐は残しておく
			case R.id.world_part_worldSurfaceViewLayout:
				break;
			case R.id.general_part_closeOverlayButton:
				//ポップアップウインドウはゲーム内の拡張動作画面として使う予定
				mOverlayWindow.startAnimation(mRecedeAnimation);
				mOverlayWindow.setVisibility(View.INVISIBLE);
				mCommandLayout.startAnimation(mInAnimation);
				mCommandLayout.setVisibility(View.VISIBLE);
				break;
			case R.id.world_part_settingsButton:
				if (!isOpenOption) {
					mCommandLayout.startAnimation(mOutAnimation);
					mCommandLayout.setVisibility(View.INVISIBLE);
					mOptionLayout.startAnimation(mInAnimation);
					mOptionLayout.setVisibility(View.VISIBLE);
					isOpenOption = true;
				} else {
					mOptionLayout.startAnimation(mOutAnimation);
					mOptionLayout.setVisibility(View.INVISIBLE);
					mCommandLayout.startAnimation(mInAnimation);
					mCommandLayout.setVisibility(View.VISIBLE);
					isOpenOption = false;
				}
				break;
		}
	}

	@Override
	public void onClick(ButtonGroupEnum buttonID) {
		switch (buttonID) {
			case ENTER:
				LogUtil.i("ENTERが押された");
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mOverlayWindow.startAnimation(mProtrudeAnimation);
				mOverlayWindow.setVisibility(View.VISIBLE);
				break;
			case MEMBERS:
				LogUtil.i("MEMBERSが押された");
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mOverlayWindow.startAnimation(mProtrudeAnimation);
				mOverlayWindow.setVisibility(View.VISIBLE);
				break;
			case ITEMS:
				LogUtil.i("ITEMSが押された");
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mOverlayWindow.startAnimation(mProtrudeAnimation);
				mOverlayWindow.setVisibility(View.VISIBLE);
				break;
			case INFO:
				LogUtil.i("INFOが押された");
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mOverlayWindow.startAnimation(mProtrudeAnimation);
				mOverlayWindow.setVisibility(View.VISIBLE);
				break;
			case ACTIONS:
				LogUtil.i("ACTIONSが押された");
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mOverlayWindow.startAnimation(mProtrudeAnimation);
				mOverlayWindow.setVisibility(View.VISIBLE);
				break;
			case LEAVE:
				LogUtil.i("LEAVEが押された");
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mOverlayWindow.startAnimation(mProtrudeAnimation);
				mOverlayWindow.setVisibility(View.VISIBLE);
				break;
			case SAVE:
				showSystemDialog(SystemMenuEnum.SAVE);
				break;
			case LOAD:
				showSystemDialog(SystemMenuEnum.LOAD);
				break;
			case CONFIGS:
				showSystemDialog(SystemMenuEnum.SETTINGS);
				break;
			case RETURN_TITLE:
				showSystemDialog(SystemMenuEnum.RETURN_TITLE);
				break;
			default:
				break;
		}
	}
}