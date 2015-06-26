package jp.bunkatusoft.explorersofsettlement.screen.localmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.event.Event;
import jp.bunkatusoft.explorersofsettlement.event.EventView;
import jp.bunkatusoft.explorersofsettlement.screen.playdata.PlayDataActivity;
import jp.bunkatusoft.explorersofsettlement.screen.title.TitleActivity;
import jp.bunkatusoft.explorersofsettlement.screen.localmap.StaticCommandGroup.OnStaticCommandClickListener;
import jp.bunkatusoft.explorersofsettlement.screen.localmap.DynamicCommandGroup.OnDynamicCommandClickListener;
import jp.bunkatusoft.explorersofsettlement.screen.world.WorldFieldActivity;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialogView;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuView;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuView.OnMenuChoiceListener;
import jp.bunkatusoft.explorersofsettlement.system.item.Inventory;
import jp.bunkatusoft.explorersofsettlement.system.item.InventoryView;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationEnum;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationUtil;

public class LocalMapActivity extends FragmentActivity {
	Context mContext;

	FrameLayout mRootLayout;
	RelativeLayout mUILayout;
	StaticCommandGroup mStaticCommandGroup;
	DynamicCommandGroup mDynamicCommandGroup;

	// システムメニュー
	SystemMenuView mMenuView;
	boolean isOpenMenu;

	// イベント
	EventView mEventView;
	List<Event> mEvents;

	// インベントリ
	Inventory mItemInventory;
	InventoryView mInventoryView;

	boolean isBlockTouch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mRootLayout = new FrameLayout(this);
		setContentView(mRootLayout);

		initBlockTouch();
		initScreenBackground();
		initSurfaceViewLayout();
		initMainLayout();
		initSystemMenuIndexView();
		initCommandGroups();
		initEventView();
		initInventoryView();
	}

	private void initScreenBackground() {
		mRootLayout.setBackgroundResource(R.drawable.background_localmap_test169);
	}

	private void initSurfaceViewLayout() {
		//TODO SurfaceViewを設定
	}

	private void initMainLayout() {
		mUILayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.ui_activity_localmap, null);
		mRootLayout.addView(mUILayout);
		ImageButton settingsButton = (ImageButton) mUILayout.findViewById(R.id.localMap_part_settingsButton);
		settingsButton.setOnClickListener(mSettingsButtonListener);
	}

	private void initSystemMenuIndexView() {
		List<SystemMenuEnum> systemMenuList = new ArrayList<SystemMenuEnum>();
		systemMenuList.add(SystemMenuEnum.SAVE_AND_LOAD);
		systemMenuList.add(SystemMenuEnum.SETTINGS);
		systemMenuList.add(SystemMenuEnum.RETURN_TITLE);

		mMenuView = new SystemMenuView(this, mUILayout, mSystemMenuListener, R.id.localMap_part_settingsButton, (ArrayList) systemMenuList);
		isOpenMenu = false;
	}

	private void initCommandGroups() {
		mDynamicCommandGroup = new DynamicCommandGroup(this, mUILayout, mDynamicCommandClickListener);
		mDynamicCommandGroup.setCommandList(createDynamicCommandList());
		mStaticCommandGroup = new StaticCommandGroup(this, mUILayout, mStaticCommandClickListener);
		setCommandArea();
	}

	private List<DynamicCommandEnum> createDynamicCommandList() {
		List<DynamicCommandEnum> resultList = new ArrayList<DynamicCommandEnum>();

		resultList.add(DynamicCommandEnum.INN);
		resultList.add(DynamicCommandEnum.TAVERN);
		resultList.add(DynamicCommandEnum.MARKET);
		resultList.add(DynamicCommandEnum.BLACKSMITH);
		resultList.add(DynamicCommandEnum.FOUNTAIN);
		resultList.add(DynamicCommandEnum.ALLEY);
		resultList.add(DynamicCommandEnum.HOUSE);

		return resultList;
	}

	private void setCommandArea() {
		LinearLayout staticCommandAreaLayout = (LinearLayout) mUILayout.findViewById(R.id.localMap_part_localMapStaticCommandAreaLayout);
		LinearLayout.LayoutParams staticLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		staticCommandAreaLayout.addView(mStaticCommandGroup.getBaseLayout(), staticLayoutParam);

		HorizontalScrollView dynamicCommandScrollView = (HorizontalScrollView) mUILayout.findViewById(R.id.localMap_part_localMapDynamicCommandAreaView);
		LinearLayout.LayoutParams dynamicLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		dynamicCommandScrollView.addView(mDynamicCommandGroup.getBaseLayout(), dynamicLayoutParam);
	}

	private void initBlockTouch() {
		isBlockTouch = false;
	}

	private void setBlockTouch(boolean block) {
		isBlockTouch = block;
	}

	OnClickListener mSettingsButtonListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			mMenuView.startAnimation(isOpenMenu);
			isOpenMenu = !isOpenMenu;
		}
	};

	OnMenuChoiceListener mSystemMenuListener = new OnMenuChoiceListener() {
		@Override
		public void onSelectMenu(SystemMenuEnum select) {
			SystemDialogView systemDialogView;
			setBlockTouch(true);
			mMenuView.setBlockTouch(true);
			//TODO メニュー閉じる
			mMenuView.startAnimation(isOpenMenu);
			isOpenMenu = !isOpenMenu;
			switch (select) {
				case SAVE_AND_LOAD:
					Intent playDataIntent = new Intent(LocalMapActivity.this, PlayDataActivity.class);
					playDataIntent.putExtra("callActivity","LocalMapActivity");
					startActivity(playDataIntent);
					finish();
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

	SystemDialogView.OnDialogClickListener mOnDialogClickListener = new SystemDialogView.OnDialogClickListener() {
		@Override
		public void onPositiveClick(SystemMenuEnum menu) {
			setBlockTouch(false);
			mMenuView.setBlockTouch(false);
			switch (menu) {
				case SETTINGS:
					break;
				case RETURN_TITLE:
					startActivity(new Intent(LocalMapActivity.this, TitleActivity.class));
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
				case SETTINGS:
					break;
				case RETURN_TITLE:
					break;
				default:
					break;
			}
		}
	};

	private void initEventView() {
		mEventView = new EventView(this, mRootLayout, mOnEventPhaseListener);
		mEventView.setVisibility(View.GONE);
	}

	private void initInventoryView() {
		mInventoryView = new InventoryView(this, mRootLayout, mOnInventoryActionListener, mItemInventory);
		mInventoryView.setVisibility(View.GONE);
	}

	OnDynamicCommandClickListener mDynamicCommandClickListener = new OnDynamicCommandClickListener() {
		@Override
		public void OnDynamicCommandClick(DynamicCommandEnum command) {
			if (isBlockTouch || isOpenMenu) {
				return;
			}
			switch (command) {
				case INN:
					break;
				case TAVERN:
					break;
				case MARKET:
					break;
				case BLACKSMITH:
					break;
				case FOUNTAIN:
					break;
				case ALLEY:
					break;
				case HOUSE:
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
					startCommandGroupsAnimation(CustomAnimationUtil.generateCustomAnimation(mContext, CustomAnimationEnum.RECEDE_OUT_TO_CENTER));
					setCommandGroupsVisibility(View.INVISIBLE);
					mInventoryView.startAnimation(CustomAnimationUtil.generateCustomAnimation(mContext, CustomAnimationEnum.PROTRUDE_IN_FROM_CENTER));
					mInventoryView.setVisibility(View.VISIBLE);
					break;
				case MOVE:
					startActivity(new Intent(LocalMapActivity.this, WorldFieldActivity.class));
					finish();
					break;
				default:
					break;
			}
		}
	};

	EventView.OnEventPhaseListener mOnEventPhaseListener = new EventView.OnEventPhaseListener() {
		@Override
		public void onEventFinish() {
			mEventView.startAnimation(CustomAnimationUtil.generateCustomAnimation(mContext, CustomAnimationEnum.RECEDE_OUT_TO_CENTER));
			mEventView.setVisibility(View.GONE);
			startCommandGroupsAnimation(CustomAnimationUtil.generateCustomAnimation(mContext, CustomAnimationEnum.PROTRUDE_IN_FROM_CENTER));
			setCommandGroupsVisibility(View.VISIBLE);
		}
	};

	InventoryView.OnInventoryActionListener mOnInventoryActionListener = new InventoryView.OnInventoryActionListener() {
		@Override
		public void onInventoryClose() {
			mInventoryView.startAnimation(CustomAnimationUtil.generateCustomAnimation(mContext, CustomAnimationEnum.RECEDE_OUT_TO_CENTER));
			mInventoryView.setVisibility(View.GONE);
			startCommandGroupsAnimation(CustomAnimationUtil.generateCustomAnimation(mContext, CustomAnimationEnum.PROTRUDE_IN_FROM_CENTER));
			setCommandGroupsVisibility(View.VISIBLE);
		}
	};

	private void setCommandGroupsVisibility(int visibility) {
		mDynamicCommandGroup.setVisibility(visibility);
		mStaticCommandGroup.setVisibility(visibility);
	}

	private void startCommandGroupsAnimation(AnimationSet animationSet) {
		mDynamicCommandGroup.startAnimation(animationSet);
		mStaticCommandGroup.startAnimation(animationSet);
	}
}
