package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.MotionEvent;
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
import jp.bunkatusoft.explorersofsettlement.event.Event;
import jp.bunkatusoft.explorersofsettlement.event.EventUtil;
import jp.bunkatusoft.explorersofsettlement.event.EventView;
import jp.bunkatusoft.explorersofsettlement.system.ButtonGroup;
import jp.bunkatusoft.explorersofsettlement.system.ButtonGroupEnum;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialogView;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuView;
import jp.bunkatusoft.explorersofsettlement.system.fellowship.Fellowship;
import jp.bunkatusoft.explorersofsettlement.system.item.Inventory;
import jp.bunkatusoft.explorersofsettlement.system.item.InventoryView;
import jp.bunkatusoft.explorersofsettlement.system.member.PartyView;
import jp.bunkatusoft.explorersofsettlement.system.member.PlayerParty;
import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationEnum;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationUtil;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;
import jp.bunkatusoft.explorersofsettlement.util.Util;


public class WorldFieldActivity extends FragmentActivity
		implements View.OnClickListener, View.OnTouchListener, ButtonGroup.OnClickListener, EventView.OnEventPhase, InventoryView.OnInventoryActionListener, SystemMenuView.OnMenuChoiceListener, SystemDialogView.OnDialogClickListener, PartyView.OnPartyActionListener {

	Context mContext;
	boolean isBlockTouch;

	/**
	 * フィールドデータ用
	 */
	List<FieldPiece> mFieldPieces;
	List<FieldRoad> mFieldRoads;


	FrameLayout mRootLayout;

	/** フィールドコマンドのレイアウト */
	LinearLayout mCommandLayout;
	List<ButtonGroup> mCommandButtonGroupList;

	/** オプションコマンドのレイアウト */
	SystemMenuView mMenuView;
	boolean isOpenMenu;

	View mOverlayWindow;

	AnimationSet mInAnimation;
	AnimationSet mOutAnimation;
	AnimationSet mProtrudeAnimation;
	AnimationSet mRecedeAnimation;

	EventView mEventView;
	List<Event> mEvents;

	Inventory mItemInventory;
	InventoryView mInventoryView;

	Fellowship mFellowship;
	PartyView mPartyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mContext = this;

		//TODO インベントリ定義＆初期化 本当はここじゃない方がいいかも？
		//TODO プレイヤーデータの設定タイミングを作ったら、そこへ引っ越すこと
		mItemInventory = WorldFieldUtil.initItemInventory();

		//TODO パーティの初期化はここで行う
		mFellowship = new PlayerParty();

		// データの読み込み
		mFieldPieces = new ArrayList<FieldPiece>();
		mFieldRoads = new ArrayList<FieldRoad>();
		try {
			mFieldPieces = WorldFieldUtil.loadFieldPieceData(this, "data/field_piece.json");
		} catch (IOException e) {
			LogUtil.e(e);
		}
		try {
			mFieldRoads = WorldFieldUtil.loadFieldRoadData(this, "data/field_road.json");
		} catch (IOException e) {
			LogUtil.e(e);
		}

		// イベントデータの読み込み
		//TODO コモンイベント+該当パートのイベント飲み読み込んでいる状態とするべし
		mEvents = new ArrayList<Event>();
		try {
			mEvents = EventUtil.loadEventsJSONData(this, "events/event0004.json");
		} catch (IOException e) {
			LogUtil.e(e);
		}

		// アニメーションの設定
		mInAnimation = CustomAnimationUtil.generateCustomAnimation(this, CustomAnimationEnum.SLIDE_IN_FROM_RIGHT_10P);
		mOutAnimation = CustomAnimationUtil.generateCustomAnimation(this,CustomAnimationEnum.SLIDE_OUT_TO_RIGHT_10P);
		mProtrudeAnimation = CustomAnimationUtil.generateCustomAnimation(this,CustomAnimationEnum.PROTRUDE_IN_FROM_CENTER);
		mRecedeAnimation = CustomAnimationUtil.generateCustomAnimation(this,CustomAnimationEnum.RECEDE_OUT_TO_CENTER);

		mRootLayout = new FrameLayout(this);
		setContentView(mRootLayout);

		//SurfaceViewをまず追加
		WorldSurfaceView surfaceView = new WorldSurfaceView(this);
		surfaceView.setOnClickListener(this);
		mRootLayout.addView(surfaceView);

		//UIレイアウトを追加
		RelativeLayout UILayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.ui_activity_field, null);
		mRootLayout.addView(UILayout);
		ImageButton openSettingButton = (ImageButton) UILayout.findViewById(R.id.world_part_settingsButton);
		openSettingButton.setOnClickListener(this);

		// フィールドコマンドを作成して追加、初期表示設定
		createCommandList(UILayout);
		mCommandLayout.setVisibility(View.VISIBLE);

		// オプションコマンドを作成して追加、初期表示設定
		initSystemMenuView(UILayout);
		initBlockTouch();

		//ポップアップウインドウは初期状態で非表示とする
		mOverlayWindow = getLayoutInflater().inflate(R.layout.overlay_window_base, null);
		mRootLayout.addView(mOverlayWindow);
		mOverlayWindow.setVisibility(View.GONE);
		Button closeOverlayButton = (Button) mOverlayWindow.findViewById(R.id.general_part_closeOverlayButton);
		closeOverlayButton.setOnClickListener(this);

		//TODO 必要に応じて読み込む方式に変更するべき？
		mEventView = new EventView(this,mRootLayout,this);
		mEventView.setVisibility(View.GONE);

		// パーティ画面の作成
		mPartyView = new PartyView(this,mRootLayout,this,mFellowship);
		mPartyView.setVisibility(View.GONE);

		// インベントリ画面の作成
		mInventoryView = new InventoryView(this,mRootLayout,this,mItemInventory);
		mInventoryView.setVisibility(View.GONE);


	}

	private void initSystemMenuView(RelativeLayout UILayout){
		List<SystemMenuEnum> systemMenuList = new ArrayList<SystemMenuEnum>();
		systemMenuList.add(SystemMenuEnum.SAVE);
		systemMenuList.add(SystemMenuEnum.LOAD);
		systemMenuList.add(SystemMenuEnum.SETTINGS);
		systemMenuList.add(SystemMenuEnum.RETURN_TITLE);

		mMenuView = new SystemMenuView(this,UILayout,this,R.id.world_part_settingsButton,(ArrayList)systemMenuList);
		isOpenMenu = false;
	}


	private void initCommandButton(){
		mCommandButtonGroupList = new ArrayList<ButtonGroup>();
		mCommandButtonGroupList.add(new ButtonGroup(mContext, ButtonGroupEnum.ENTER, this, R.drawable.icon_enter));
		mCommandButtonGroupList.add(new ButtonGroup(mContext, ButtonGroupEnum.MEMBERS, this, R.drawable.icon_members));
		mCommandButtonGroupList.add(new ButtonGroup(mContext, ButtonGroupEnum.ITEMS, this, R.drawable.icon_items));
		mCommandButtonGroupList.add(new ButtonGroup(mContext, ButtonGroupEnum.INFO, this, R.drawable.icon_info));
		mCommandButtonGroupList.add(new ButtonGroup(mContext, ButtonGroupEnum.ACTIONS, this, R.drawable.icon_actions));
		mCommandButtonGroupList.add(new ButtonGroup(mContext, ButtonGroupEnum.LEAVE, this, R.drawable.icon_exit));
	}

	/**
	 * フィールドレイアウトコマンドの作成と追加を行う
	 * @param UILayout	作成したレイアウトを追加する親レイアウト
	 */
	public void createCommandList(RelativeLayout UILayout) {
		mCommandLayout = new LinearLayout(mContext);
		mCommandLayout.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams commandLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		commandLayoutParam.addRule(RelativeLayout.BELOW, R.id.world_part_settingsButton);
		commandLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		commandLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		LinearLayout firstLineLayout = new LinearLayout(mContext);
		firstLineLayout.setOrientation(LinearLayout.VERTICAL);
		firstLineLayout.setGravity(Gravity.BOTTOM);
		LinearLayout.LayoutParams firstLineLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		firstLineLayoutParam.gravity = Gravity.BOTTOM;
		firstLineLayoutParam.setMargins(0, 0, 0, Util.getDensityPoint(mContext, 16));

		LinearLayout secondLineLayout = new LinearLayout(mContext);
		secondLineLayout.setOrientation(LinearLayout.VERTICAL);
		secondLineLayout.setGravity(Gravity.BOTTOM);
		LinearLayout.LayoutParams secondLineLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		secondLineLayoutParam.gravity = Gravity.BOTTOM;
		secondLineLayoutParam.setMargins(Util.getDensityPoint(mContext, 16), 0, 0, 0);

		initCommandButton();
		if(mCommandButtonGroupList != null && mCommandButtonGroupList.size()>0){
			boolean LeftRightToggle = true;
			for(ButtonGroup commandButton : mCommandButtonGroupList){
				if(LeftRightToggle){
					firstLineLayout.addView(commandButton.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				} else {
					secondLineLayout.addView(commandButton.getBaseLayout(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				}
				LeftRightToggle = !LeftRightToggle;
			}
		}
		mCommandLayout.addView(firstLineLayout, firstLineLayoutParam);
		mCommandLayout.addView(secondLineLayout, secondLineLayoutParam);
		UILayout.addView(mCommandLayout, commandLayoutParam);
	}

	private void initBlockTouch(){
		isBlockTouch = false;
	}

	private void setBlockTouch(boolean block){
		isBlockTouch = block;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return isBlockTouch;
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
				mMenuView.startAnimation(isOpenMenu);
				isOpenMenu = !isOpenMenu;
				break;
		}
	}

	@Override
	public void onClick(ButtonGroupEnum buttonID) {
		if(isBlockTouch || isOpenMenu){
			return;
		}
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
				//TODO パーティ管理を開いちゃう
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mPartyView.startAnimation(mProtrudeAnimation);
				mPartyView.setVisibility(View.VISIBLE);
				break;
			case ITEMS:
				LogUtil.i("ITEMSが押された");
				//TODO アイテムインベントリを開いちゃう
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mInventoryView.startAnimation(mProtrudeAnimation);
				mInventoryView.setVisibility(View.VISIBLE);
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
				//TODO OK、じゃあここを小イベントの起点としよう
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mEventView.startAnimation(mProtrudeAnimation);
				mEventView.setVisibility(View.VISIBLE);
				mEventView.startEvent(mEvents);
				break;
			case LEAVE:
				LogUtil.i("LEAVEが押された");
				mCommandLayout.startAnimation(mOutAnimation);
				mCommandLayout.setVisibility(View.INVISIBLE);
				mOverlayWindow.startAnimation(mProtrudeAnimation);
				mOverlayWindow.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}

	@Override
	public void onFinish() {
		mEventView.startAnimation(mRecedeAnimation);
		mEventView.setVisibility(View.GONE);
		mCommandLayout.startAnimation(mInAnimation);
		mCommandLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onInventoryClose() {
		//TODO 呼び出し元によって、ここの戻る画面を変化させる
		mInventoryView.startAnimation(mRecedeAnimation);
		mInventoryView.setVisibility(View.GONE);
		mCommandLayout.startAnimation(mInAnimation);
		mCommandLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPartyClose() {
		mPartyView.startAnimation(mRecedeAnimation);
		mPartyView.setVisibility(View.GONE);
		mCommandLayout.startAnimation(mInAnimation);
		mCommandLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onSelectMenu(SystemMenuEnum menuEnum) {
		SystemDialogView systemDialogView;
		setBlockTouch(true);
		mMenuView.setBlockTouch(true);
		//TODO メニュー閉じる
		mMenuView.startAnimation(isOpenMenu);
		isOpenMenu = !isOpenMenu;
		switch (menuEnum) {
			case LOAD:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				systemDialogView.startAnimation(true);
				break;
			case SAVE:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				systemDialogView.startAnimation(true);
				break;
			case SETTINGS:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				systemDialogView.startAnimation(true);
				break;
			case RETURN_TITLE:
				systemDialogView = new SystemDialogView(this, mRootLayout, this, getString(R.string.sys_msg_confirm_back_title), getString(R.string.yes), getString(R.string.no), menuEnum);
				systemDialogView.startAnimation(true);
				break;
			default:
				//未実装の定義は省略
				break;
		}
	}

	@Override
	public void onPositiveClick(SystemMenuEnum menu) {
		setBlockTouch(false);
		mMenuView.setBlockTouch(false);
		switch (menu) {
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
	public void onNegativeClick(SystemMenuEnum menu) {
		setBlockTouch(false);
		mMenuView.setBlockTouch(false);
		switch (menu) {
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
}