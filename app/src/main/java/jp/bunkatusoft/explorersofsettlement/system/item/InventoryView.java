package jp.bunkatusoft.explorersofsettlement.system.item;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.ExploreOfSettlementApplication;
import jp.bunkatusoft.explorersofsettlement.R;

public class InventoryView implements OnClickListener, AdapterView.OnItemClickListener {

	//TODO データ読み込み・反映
	//TODO アクション追加

	//TODO インベントリを閉じた際の、保持する値、初期化する値を決めること

	public interface OnInventoryActionListener {
		void onInventoryClose();
	}

	public static final int NO_SELECT = -1;

	Context mContext;
	OnInventoryActionListener mListener;
	Inventory mItemInventory;

	RelativeLayout mBaseLayout;

	// アイテムリスト関連
	ListView mItemListView;
	InventoryItemAdapter mInventoryItemAdapter;
	int mSelectItemPosition;

	// コマンドボタン
	Button mOpenDetailButton;
	Button mOpenUseButton;
	Button mTrashButton;
	Button mCloseButton;

	// アイテムリストのフィルタ関連
	InventoryFilterEnum mSelectFilter;
	Button mFilterAllButton;
	Button mFilterEquipmentButton;
	Button mFilterSuppliesButton;
	Button mFilterMiscellaneousButton;
	Button mFilterMaterialButton;
	Button mFilterImportantButton;

	/**
	 * コンストラクタ
	 * @param context	コンテキスト
	 * @param rootLayout	Viewを追加する親FrameLayout
	 * @param listener	アクションリスナ
	 * @param inventory	インベントリデータ
	 */
	public InventoryView(Context context, FrameLayout rootLayout, OnInventoryActionListener listener, Inventory inventory) {
		mContext = context;
		mListener = listener;
		mItemInventory = inventory;

		initBaseLayout(rootLayout);
		initCommandButtons();
		initFilterButtons();
		initInventoryAdapter();
		setSelectItemPosition(NO_SELECT);
	}

	/**
	 * ベースとなるレイアウトを設定する
	 * @param rootLayout	Viewを追加する親FrameLayout
	 */
	private void initBaseLayout(FrameLayout rootLayout) {
		mBaseLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.overlay_inventory, null);
		rootLayout.addView(mBaseLayout);
	}

	/**
	 * コマンドボタンを初期化する
	 */
	private void initCommandButtons(){
		mOpenDetailButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_openDetailButton);
		mOpenDetailButton.setOnClickListener(this);
		mOpenDetailButton.setEnabled(false);
		mOpenUseButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_openUseButton);
		mOpenUseButton.setOnClickListener(this);
		mOpenUseButton.setEnabled(false);
		mTrashButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_trashButton);
		mTrashButton.setOnClickListener(this);
		mTrashButton.setEnabled(false);
		mCloseButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_closeInventoryButton);
		mCloseButton.setOnClickListener(this);
	}

	/**
	 * リストのフィルタボタンを初期化する
	 */
	private void initFilterButtons() {
		mFilterAllButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_filterAllButton);
		mFilterAllButton.setOnClickListener(this);
		mFilterEquipmentButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_filterEquipmentButton);
		mFilterEquipmentButton.setOnClickListener(this);
		mFilterSuppliesButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_filterSuppliesButton);
		mFilterSuppliesButton.setOnClickListener(this);
		mFilterMiscellaneousButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_filterMiscellaneousButton);
		mFilterMiscellaneousButton.setOnClickListener(this);
		mFilterMaterialButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_filterMaterialButton);
		mFilterMaterialButton.setOnClickListener(this);
		mFilterImportantButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_filterImportantButton);
		mFilterImportantButton.setOnClickListener(this);

		changeFilter(InventoryFilterEnum.ALL);
	}

	/**
	 * アイテムリストを初期化する
	 */
	private void initInventoryAdapter() {
		mItemListView = (ListView) mBaseLayout.findViewById(R.id.part_inventory_itemList);
		mItemListView.setOnItemClickListener(this);

		mInventoryItemAdapter = new InventoryItemAdapter(mContext, new ArrayList<Item>());
		mItemListView.setAdapter(mInventoryItemAdapter);

		updateInventoryAdapter();
	}

	/**
	 * ItemListを更新する
	 */
	private void updateInventoryAdapter() {
		if (mInventoryItemAdapter != null) {
			mInventoryItemAdapter.clear();
			if (mItemInventory != null) {
				ArrayList<Item> itemList = (ArrayList) mItemInventory.getItemList();
				if(itemList != null && itemList.size()>0) {
					for (Item item : itemList) {
						if (mSelectFilter.equals(InventoryFilterEnum.ALL) || item.filter.equals(mSelectFilter)) {
							mInventoryItemAdapter.add(item);
						}
					}
				}
			}
			mInventoryItemAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * InventoryViewの全パーツに対して可視性を設定する
	 *
	 * @param visibility 可視性
	 */
	public void setVisibility(int visibility) {
		mBaseLayout.setVisibility(visibility);
	}

	/**
	 * InventoryViewの全パーツに対してアニメーションを開始する
	 *
	 * @param animationSet アニメーション
	 */
	public void startAnimation(AnimationSet animationSet) {
		mBaseLayout.startAnimation(animationSet);
	}

	/**
	 * ItemListのアイテムを選択中であるかどうかを設定する
	 *
	 * @param position アイテムを選択中
	 */
	private void setSelectItemPosition(int position) {
		mSelectItemPosition = position;

		if(position != -1){
			mOpenDetailButton.setEnabled(true);
			mOpenUseButton.setEnabled(true);
			mTrashButton.setEnabled(true);
		} else {
			mOpenDetailButton.setEnabled(false);
			mOpenUseButton.setEnabled(false);
			mTrashButton.setEnabled(false);
		}
	}

	/**
	 * ItemListのフィルタを切り替える
	 * @param selectFilter	使用するフィルタ
	 */
	private void changeFilter(InventoryFilterEnum selectFilter) {
		mSelectFilter = selectFilter;

		//TODO これもっと何とかしたい
		if (selectFilter.equals(InventoryFilterEnum.ALL)) {
			mFilterAllButton.setBackgroundResource(R.drawable.window_frame_pressed);
		} else {
			mFilterAllButton.setBackgroundResource(R.drawable.window_frame);
		}
		if (selectFilter.equals(InventoryFilterEnum.EQUIPMENT)) {
			mFilterEquipmentButton.setBackgroundResource(R.drawable.window_frame_pressed);
		} else {
			mFilterEquipmentButton.setBackgroundResource(R.drawable.window_frame);
		}
		if (selectFilter.equals(InventoryFilterEnum.SUPPLIES)) {
			mFilterSuppliesButton.setBackgroundResource(R.drawable.window_frame_pressed);
		} else {
			mFilterSuppliesButton.setBackgroundResource(R.drawable.window_frame);
		}
		if (selectFilter.equals(InventoryFilterEnum.MISCELLANEOUS)) {
			mFilterMiscellaneousButton.setBackgroundResource(R.drawable.window_frame_pressed);
		} else {
			mFilterMiscellaneousButton.setBackgroundResource(R.drawable.window_frame);
		}
		if (selectFilter.equals(InventoryFilterEnum.MATERIAL)) {
			mFilterMaterialButton.setBackgroundResource(R.drawable.window_frame_pressed);
		} else {
			mFilterMaterialButton.setBackgroundResource(R.drawable.window_frame);
		}
		if (selectFilter.equals(InventoryFilterEnum.IMPORTANT)) {
			mFilterImportantButton.setBackgroundResource(R.drawable.window_frame_pressed);
		} else {
			mFilterImportantButton.setBackgroundResource(R.drawable.window_frame);
		}
	}

	@Override
	public void onClick(View view) {
		int viewID = view.getId();
		switch (viewID) {
			case R.id.part_inventory_closeInventoryButton:
				mListener.onInventoryClose();
				break;
			case R.id.part_inventory_openDetailButton:
				break;
			case R.id.part_inventory_openUseButton:
				break;
			case R.id.part_inventory_trashButton:
				break;

			case R.id.part_inventory_filterAllButton:
				changeFilter(InventoryFilterEnum.ALL);
				setSelectItemPosition(NO_SELECT);
				updateInventoryAdapter();
				break;
			case R.id.part_inventory_filterEquipmentButton:
				changeFilter(InventoryFilterEnum.EQUIPMENT);
				setSelectItemPosition(NO_SELECT);
				updateInventoryAdapter();
				break;
			case R.id.part_inventory_filterSuppliesButton:
				changeFilter(InventoryFilterEnum.SUPPLIES);
				setSelectItemPosition(NO_SELECT);
				updateInventoryAdapter();
				break;
			case R.id.part_inventory_filterMiscellaneousButton:
				changeFilter(InventoryFilterEnum.MISCELLANEOUS);
				setSelectItemPosition(NO_SELECT);
				updateInventoryAdapter();
				break;
			case R.id.part_inventory_filterMaterialButton:
				changeFilter(InventoryFilterEnum.MATERIAL);
				setSelectItemPosition(NO_SELECT);
				updateInventoryAdapter();
				break;
			case R.id.part_inventory_filterImportantButton:
				changeFilter(InventoryFilterEnum.IMPORTANT);
				setSelectItemPosition(NO_SELECT);
				updateInventoryAdapter();
				break;
			default:
				break;
		}
	}

	private class InventoryItemAdapter extends ArrayAdapter<Item> {

		private Context mContext;
		private final LayoutInflater mInflater;

		/**
		 * コンストラクタ
		 * @param context	コンテキスト
		 * @param objects	アイテムリスト
		 */
		public InventoryItemAdapter(Context context, List<Item> objects) {
			super(context, 0, objects);
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView itemIcon;
			LinearLayout itemNameArea;
			TextView itemCategory;
			TextView itemNum;
			TextView itemWeight;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listitem_item, null);
			}
			Item item = getItem(position);
			if (item != null) {
				if(mSelectItemPosition != position) {
					convertView.setBackgroundColor(Color.argb(255, 255, 204, 170));
				}
				// アイテムアイコン
				itemIcon = (ImageView) convertView.findViewById(R.id.part_inventory_list_itemImageView);
				itemIcon.setImageBitmap(ExploreOfSettlementApplication.getItemIconBitmap(item.imageID));

				// アイテム名称
				itemNameArea = (LinearLayout) convertView.findViewById(R.id.part_inventory_list_itemNameLayout);
				itemNameArea.removeAllViews();
				TextView itemNameText = new TextView(mContext);
				itemNameText.setText(setItemName(item.name,item.quality));
				itemNameText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
				//TODO 不確定名の挿入が可能
				//TODO 属性・状態アイコンの挿入が可能
				//TODO つまりもっとフレキシブルになれる
				itemNameArea.addView(itemNameText);

				// アイテム種別
				itemCategory = (TextView) convertView.findViewById(R.id.part_inventory_list_itemCategoryText);
				itemCategory.setText(item.category.getCategory());

				// アイテム個数
				itemNum = (TextView) convertView.findViewById(R.id.part_inventory_list_itemNumText);
				itemNum.setText(" x " + item.num);

				// アイテム単重量
				itemWeight = (TextView) convertView.findViewById(R.id.part_inventory_list_itemWeightText);
				itemWeight.setText("wgt : " + item.weight);
			}
			return convertView;
		}

		/**
		 * アイテム名を設定する
		 * @param name	デフォルト名
		 * @param quality	品質
		 * @return	各種ステータスを加味した表示名
		 */
		private String setItemName(String name, QualityEnum quality){
			String resultName = name;
			if(!quality.equals(QualityEnum.NORMAL)){
				resultName += "(" + mContext.getString(quality.getQuality()) + ")";
			}
			return resultName;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		//リストがタップされた時のあれこれ
		setSelectItemPosition(position);
		view.setBackgroundColor(Color.argb(255, 255, 238, 204));

		updateInventoryAdapter();
	}
}
