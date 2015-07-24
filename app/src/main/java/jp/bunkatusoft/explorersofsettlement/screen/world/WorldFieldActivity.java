package jp.bunkatusoft.explorersofsettlement.screen.world;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.HorizontalScrollView;
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
import jp.bunkatusoft.explorersofsettlement.event.EventView.OnEventPhaseListener;
import jp.bunkatusoft.explorersofsettlement.screen.ScreenActivity;
import jp.bunkatusoft.explorersofsettlement.screen.localmap.LocalMapActivity;
import jp.bunkatusoft.explorersofsettlement.screen.title.TitleActivity;
import jp.bunkatusoft.explorersofsettlement.screen.world.DynamicCommandGroup.OnDynamicCommandClickListener;
import jp.bunkatusoft.explorersofsettlement.screen.world.StaticCommandGroup.OnStaticCommandClickListener;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialogView;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuView;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuView.OnMenuChoiceListener;
import jp.bunkatusoft.explorersofsettlement.system.inventory.Inventory;
import jp.bunkatusoft.explorersofsettlement.system.inventory.InventoryView;
import jp.bunkatusoft.explorersofsettlement.system.inventory.InventoryView.OnInventoryActionListener;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationEnum;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationUtil;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;


public class WorldFieldActivity extends ScreenActivity
		implements View.OnTouchListener {

	// レイアウト系
	StaticCommandGroup mStaticCommandGroup;
	DynamicCommandGroup mDynamicCommandGroup;

	// システムメニュー
	SystemMenuView mMenuView;
	boolean isOpenMenu;
	boolean isBlockTouch;

	// イベント
	EventView mCommonEventView;
	List<Event> mEvents;

	// インベントリ
	Inventory mItemInventory;
	InventoryView mInventoryView;

	// フィールドデータ用
	List<FieldPiece> mFieldPieces;
	List<FieldRoad> mFieldRoads;

	// アニメーション
	AnimationSet mInAnimation;
	AnimationSet mOutAnimation;
	AnimationSet mProtrudeAnimation;
	AnimationSet mRecedeAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// データの読み込み
		loadInventoryData();
		loadFieldData();
		loadEventData();

		// アニメーションの設定
		mInAnimation = CustomAnimationUtil.generateCustomAnimation(this, CustomAnimationEnum.SLIDE_IN_FROM_RIGHT_10P);
		mOutAnimation = CustomAnimationUtil.generateCustomAnimation(this, CustomAnimationEnum.SLIDE_OUT_TO_RIGHT_10P);
		mProtrudeAnimation = CustomAnimationUtil.generateCustomAnimation(this, CustomAnimationEnum.PROTRUDE_IN_FROM_CENTER);
		mRecedeAnimation = CustomAnimationUtil.generateCustomAnimation(this, CustomAnimationEnum.RECEDE_OUT_TO_CENTER);

		// たくさん初期化
		initBlockTouch();
		initBackgroundLayout();
		initSurfaceViewLayout();
		initUILayout();
		initSystemMenuView();
		initCommandGroups();
		initEventView();
		initInventoryView();
	}

	@Override
	protected void initBackgroundLayout() {
		//TODO Background処理を追加
	}

	@Override
	protected void initSurfaceViewLayout() {
		WorldSurfaceView surfaceView = new WorldSurfaceView(this);
		surfaceView.setOnClickListener(mSurfaceViewClickListener);
		mRootLayout.addView(surfaceView);
	}

	@Override
	protected void initUILayout() {
		mUILayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.ui_activity_field, null);
		mRootLayout.addView(mUILayout);
		ImageButton openSettingButton = (ImageButton) mUILayout.findViewById(R.id.world_part_settingsButton);
		openSettingButton.setOnClickListener(mSettingButtonClickListener);
	}

	/**
	 * SurfaceViewのクリックリスナ
	 */
	OnClickListener mSurfaceViewClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			//特に決まってない
		}
	};

	/**
	 * UI_設定ボタンのクリックリスナ
	 */
	OnClickListener mSettingButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			mMenuView.startAnimation(isOpenMenu);
			isOpenMenu = !isOpenMenu;
		}
	};

	/**
	 * システムメニュー項目のリスナ
	 */
	OnMenuChoiceListener mSystemMenuClickListener = new OnMenuChoiceListener() {
		@Override
		public void onSelectMenu(SystemMenuEnum select) {
			SystemDialogView systemDialogView;
			setBlockTouch(true);
			mMenuView.setBlockTouch(true);
			//TODO メニュー閉じる
			mMenuView.startAnimation(isOpenMenu);
			isOpenMenu = !isOpenMenu;
			switch (select) {
				case LOAD:
					systemDialogView = new SystemDialogView(mContext, mRootLayout, mOnDialogClickListener, getString(R.string.sys_msg_wip), getString(R.string.back), null, select);
					systemDialogView.startAnimation(true);
					break;
				case SAVE:
					systemDialogView = new SystemDialogView(mContext, mRootLayout, mOnDialogClickListener, getString(R.string.sys_msg_wip), getString(R.string.back), null, select);
					systemDialogView.startAnimation(true);
					break;
				case SETTINGS:
					systemDialogView = new SystemDialogView(mContext, mRootLayout, mOnDialogClickListener, getString(R.string.sys_msg_wip), getString(R.string.back), null, select);
					systemDialogView.startAnimation(true);
					break;
				case RETURN_TITLE:
					systemDialogView = new SystemDialogView(mContext, mRootLayout, mOnDialogClickListener, getString(R.string.sys_msg_confirm_back_title), getString(R.string.yes), getString(R.string.no), select);
					systemDialogView.startAnimation(true);
					break;
				default:
					//未実装の定義は省略
					break;
			}
		}
	};

	/**
	 * システムダイアログ操作のリスナ
	 */
	SystemDialogView.OnDialogClickListener mOnDialogClickListener = new SystemDialogView.OnDialogClickListener() {
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
	};

	/**
	 * アクションコマンド(固定枠)操作のリスナ
	 */
	OnDynamicCommandClickListener mDynamicCommandClickListener = new OnDynamicCommandClickListener() {
		@Override
		public void OnDynamicCommandClick(DynamicCommandEnum command) {
			if (isBlockTouch || isOpenMenu) {
				return;
			}
			switch (command) {
				case ENTER:
					break;
				case MINE:
					break;
				case GATHER:
					break;
				case FISHING:
					break;
				case ACTIVATE:
					startCommandGroupsAnimation(mOutAnimation);
					setCommandGroupsVisibility(View.INVISIBLE);
					mCommonEventView.startAnimation(mProtrudeAnimation);
					mCommonEventView.setVisibility(View.VISIBLE);
					mCommonEventView.startEvent(mEvents);
					break;
				default:
					break;
			}
		}
	};

	/**
	 * アクションコマンド(固定枠)操作のリスナ
	 */
	OnStaticCommandClickListener mStaticCommandClickListener = new OnStaticCommandClickListener() {
		@Override
		public void OnStaticCommandClick(StaticCommandEnum command) {
			if (isBlockTouch || isOpenMenu) {
				return;
			}
			switch (command) {
				case PARTY:
					break;
				case INVENTORY:
					startCommandGroupsAnimation(mOutAnimation);
					setCommandGroupsVisibility(View.INVISIBLE);
					mInventoryView.startAnimation(mProtrudeAnimation);
					mInventoryView.setVisibility(View.VISIBLE);
					break;
				case MOVE:
					startActivity(new Intent(WorldFieldActivity.this, LocalMapActivity.class));
					finish();
					break;
				default:
					break;
			}
		}
	};

	OnEventPhaseListener mOnEventPhaseListener = new OnEventPhaseListener() {
		@Override
		public void onEventFinish() {
			mCommonEventView.startAnimation(mRecedeAnimation);
			mCommonEventView.setVisibility(View.GONE);
			startCommandGroupsAnimation(mInAnimation);
			setCommandGroupsVisibility(View.VISIBLE);
		}
	};

	OnInventoryActionListener mOnInventoryActionListener = new OnInventoryActionListener() {
		@Override
		public void onInventoryClose() {
			mInventoryView.startAnimation(mRecedeAnimation);
			mInventoryView.setVisibility(View.GONE);
			startCommandGroupsAnimation(mInAnimation);
			setCommandGroupsVisibility(View.VISIBLE);
		}
	};

	/**
	 * システムメニューの初期化、追加
	 */
	private void initSystemMenuView() {
		List<SystemMenuEnum> systemMenuList = new ArrayList<SystemMenuEnum>();
		systemMenuList.add(SystemMenuEnum.LOAD);
		systemMenuList.add(SystemMenuEnum.SAVE);
		systemMenuList.add(SystemMenuEnum.SETTINGS);
		systemMenuList.add(SystemMenuEnum.RETURN_TITLE);

		mMenuView = new SystemMenuView(this, (RelativeLayout) mUILayout, mSystemMenuClickListener, R.id.world_part_settingsButton, (ArrayList) systemMenuList);
		isOpenMenu = false;
	}

	/**
	 * コマンド群の初期化、追加
	 */
	private void initCommandGroups() {
		mDynamicCommandGroup = new DynamicCommandGroup(this, mUILayout, mDynamicCommandClickListener);
		mDynamicCommandGroup.setCommandList(createDynamicCommandList());
		mStaticCommandGroup = new StaticCommandGroup(this, (RelativeLayout) mUILayout, mStaticCommandClickListener);
		setCommandArea();
	}

	/**
	 * コマンド群を登録する親レイアウトの設定
	 */
	private void setCommandArea() {
		LinearLayout staticCommandAreaLayout = (LinearLayout) mUILayout.findViewById(R.id.world_part_worldStaticCommandAreaLayout);
		LinearLayout.LayoutParams staticLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		staticCommandAreaLayout.addView(mStaticCommandGroup.getBaseLayout(), staticLayoutParam);

		HorizontalScrollView dynamicCommandScrollView = (HorizontalScrollView) mUILayout.findViewById(R.id.world_part_worldDynamicCommandAreaView);
		LinearLayout.LayoutParams dynamicLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		dynamicCommandScrollView.addView(mDynamicCommandGroup.getBaseLayout(), dynamicLayoutParam);
	}

	/**
	 * 動的コマンドの項目を設定する
	 *
	 * @return 追加するコマンド群リスト
	 */
	private List<DynamicCommandEnum> createDynamicCommandList() {
		List<DynamicCommandEnum> resultList = new ArrayList<DynamicCommandEnum>();

		resultList.add(DynamicCommandEnum.ENTER);
		resultList.add(DynamicCommandEnum.MINE);
		resultList.add(DynamicCommandEnum.GATHER);
		resultList.add(DynamicCommandEnum.FISHING);
		resultList.add(DynamicCommandEnum.ACTIVATE);

		return resultList;
	}

	/**
	 * イベント用オーバーレイ画面の初期化、追加
	 */
	private void initEventView() {
		mCommonEventView = new EventView(this, mRootLayout, mOnEventPhaseListener);
		mCommonEventView.setVisibility(View.GONE);
	}

	/**
	 * インベントリオーバレイ画面の初期化、追加
	 */
	private void initInventoryView() {
		mInventoryView = new InventoryView(this, mRootLayout, mOnInventoryActionListener, mItemInventory);
		mInventoryView.setVisibility(View.GONE);
	}

	/**
	 * フィールドデータを読み込む
	 */
	private void loadFieldData() {
		mFieldPieces = new ArrayList<FieldPiece>();
		mFieldRoads = new ArrayList<FieldRoad>();
		try {
			mFieldPieces = WorldFieldUtil.loadFieldPieceData(this, "data/field_pieces.json");
		} catch (IOException e) {
			LogUtil.e(e);
		}
		try {
			mFieldRoads = WorldFieldUtil.loadFieldRoadData(this, "data/field_roads.json");
		} catch (IOException e) {
			LogUtil.e(e);
		}
	}

	/**
	 * インベントリデータを読み込む
	 */
	private void loadInventoryData() {
		mItemInventory = WorldFieldUtil.initItemInventory();
	}

	/**
	 * イベントデータを読み込む
	 */
	private void loadEventData() {
		//TODO コモンイベント+該当パートのイベントのみ読み込んでいる状態とするべし
		mEvents = new ArrayList<Event>();
		try {
			mEvents = EventUtil.loadEventsJSONData(this, "events/event0004.json");
		} catch (IOException e) {
			LogUtil.e(e);
		}
	}

	private void initBlockTouch() {
		isBlockTouch = false;
	}

	private void setBlockTouch(boolean block) {
		isBlockTouch = block;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return isBlockTouch;
	}

	private void setCommandGroupsVisibility(int visibility) {
		mDynamicCommandGroup.setVisibility(visibility);
		mStaticCommandGroup.setVisibility(visibility);
	}

	private void startCommandGroupsAnimation(AnimationSet animationSet) {
		mDynamicCommandGroup.startAnimation(animationSet);
		mStaticCommandGroup.startAnimation(animationSet);
	}
}