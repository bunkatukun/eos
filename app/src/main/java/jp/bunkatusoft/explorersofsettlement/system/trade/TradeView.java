package jp.bunkatusoft.explorersofsettlement.system.trade;

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
import jp.bunkatusoft.explorersofsettlement.system.item.Inventory;
import jp.bunkatusoft.explorersofsettlement.system.item.Item;
import jp.bunkatusoft.explorersofsettlement.system.item.QualityEnum;

public class TradeView {

	public interface OnTradeActionListener {
		//TODO 様々なアクションをこここで
		void onTradeClose();
	}

	protected Context mContext;
	protected OnTradeActionListener mListener;

	protected Inventory mPlayerInventory;
	protected Inventory mOtherInventory;

	RelativeLayout mBaseLayout;

	protected Button mTradeButton;
	protected Button mCloseButton;

	ListView mPlayerInventoryListView;
	ListView mOtherInventoryListView;
	InventoryAdapter mPlayerInventoryAdapter;
	InventoryAdapter mOtherInventoryAdapter;
	int mPlayerSelectItemPosition;
	int mOtherSelectItemPosition;

	//TODO enumかなにかにする
	public static final int LIST_OWNER_PLAYER = 1;
	public static final int LIST_OWNER_OTHER = 2;

	public TradeView(Context context, FrameLayout rootLayout, OnTradeActionListener listener, Inventory playerInventory, Inventory otherInventory) {
		mContext = context;
		mListener = listener;
		mPlayerInventory = playerInventory;
		mOtherInventory = otherInventory;

		initBaseLayout(rootLayout);
		initCommandButtonsLayout();
		initInventoryAdapter();
	}

	private void initBaseLayout(FrameLayout rootLayout) {
		mBaseLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.overlay_trade, null);
		rootLayout.addView(mBaseLayout);
	}

	private void initCommandButtonsLayout() {
		mTradeButton = (Button) mBaseLayout.findViewById(R.id.overlay_trade_part_tradeButton);
		mTradeButton.setOnClickListener(mTradeButtonListener);
		mCloseButton = (Button) mBaseLayout.findViewById(R.id.overlay_trade_part_backButton);
		mCloseButton.setOnClickListener(mCloseButtonListener);
	}

	private void initInventoryAdapter() {
		mPlayerInventoryListView = (ListView) mBaseLayout.findViewById(R.id.overlay_trade_part_playerList);
		mPlayerInventoryListView.setOnItemClickListener(mPlayerInventoryItemListener);
		mPlayerInventoryListView.setOnItemLongClickListener(mPlayerInventoryLongItemListener);
		mOtherInventoryListView = (ListView) mBaseLayout.findViewById(R.id.overlay_trade_part_otherList);
		mOtherInventoryListView.setOnItemClickListener(mOtherInventoryItemListener);
		mOtherInventoryListView.setOnItemLongClickListener(mOtherInventoryLongItemListener);

		mPlayerInventoryAdapter = new InventoryAdapter(mContext, new ArrayList<Item>(), LIST_OWNER_PLAYER);
		mPlayerInventoryListView.setAdapter(mPlayerInventoryAdapter);
		mOtherInventoryAdapter = new InventoryAdapter(mContext, new ArrayList<Item>(), LIST_OWNER_OTHER);
		mOtherInventoryListView.setAdapter(mOtherInventoryAdapter);

		updateInventoryAdapter(mPlayerInventory, mPlayerInventoryAdapter);
		updateInventoryAdapter(mOtherInventory, mOtherInventoryAdapter);
	}

	private void updateInventoryAdapter(Inventory inventory, InventoryAdapter adapter) {
		if (adapter != null) {
			adapter.clear();
			if (inventory != null) {
				ArrayList<Item> itemList = (ArrayList) inventory.getItemList();
				if (!itemList.isEmpty()) {
					for (Item item : itemList) {
						adapter.add(item);
					}
				}
			}
			adapter.notifyDataSetChanged();
		}
	}

	OnClickListener mTradeButtonListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			//TODO 清算
		}
	};

	OnClickListener mCloseButtonListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			mListener.onTradeClose();
		}
	};

	AdapterView.OnItemClickListener mPlayerInventoryItemListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			mPlayerSelectItemPosition = position;
			view.setBackgroundColor(Color.argb(255, 255, 238, 204));

			updateInventoryAdapter(mPlayerInventory, mPlayerInventoryAdapter);
		}
	};

	AdapterView.OnItemLongClickListener mPlayerInventoryLongItemListener = new AdapterView.OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
			return false;
		}
	};

	AdapterView.OnItemClickListener mOtherInventoryItemListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			mOtherSelectItemPosition = position;
			view.setBackgroundColor(Color.argb(255, 255, 238, 204));

			updateInventoryAdapter(mOtherInventory, mOtherInventoryAdapter);
		}
	};

	AdapterView.OnItemLongClickListener mOtherInventoryLongItemListener = new AdapterView.OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
			return false;
		}
	};

	public void setVisibility(int visibility) {
		mBaseLayout.setVisibility(visibility);
	}

	public void startAnimation(AnimationSet animationSet) {
		mBaseLayout.startAnimation(animationSet);
	}

	//TODO closeしたあと、呼び出し元で実行するべし
	public void finalize(FrameLayout rootLayout) {
		rootLayout.removeView(mBaseLayout);
	}

	private class InventoryAdapter extends ArrayAdapter<Item> {

		private Context mContext;
		private final LayoutInflater mInflater;
		private int mOwner;

		/**
		 * コンストラクタ
		 *
		 * @param context コンテキスト
		 * @param objects アイテムリスト
		 */
		public InventoryAdapter(Context context, List<Item> objects, int owner) {
			super(context, 0, objects);
			mContext = context;
			mInflater = LayoutInflater.from(context);
			mOwner = owner;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView itemIcon;
			LinearLayout itemNameArea;
			TextView itemValue;
			TextView itemWeight;

			if (convertView == null) {
				//TODO レイアウト組むべし

				convertView = mInflater.inflate(R.layout.parts_listitem_item, null);
			}
			Item item = getItem(position);
			if (item != null) {

				//TODO この頭の悪い分岐をなんとかする
				if ((LIST_OWNER_PLAYER == mOwner && mPlayerSelectItemPosition != position)
						|| (LIST_OWNER_OTHER == mOwner && mOtherSelectItemPosition != position)) {
					convertView.setBackgroundColor(Color.argb(255, 255, 204, 170));
				}
				// アイテムアイコン
				itemIcon = (ImageView) convertView.findViewById(R.id.listItem_item_part_itemIconImage);
				itemIcon.setImageBitmap(ExploreOfSettlementApplication.getItemIconBitmap(item.imageID));

				// アイテム名称
				itemNameArea = (LinearLayout) convertView.findViewById(R.id.listItem_item_part_itemTextLayout);
				itemNameArea.removeAllViews();
				TextView itemNameText = new TextView(mContext);
				itemNameText.setText(setItemName(item.name, item.quality));
				itemNameText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
				//TODO 不確定名の挿入が可能
				//TODO 属性・状態アイコンの挿入が可能
				//TODO つまりもっとフレキシブルになれる
				itemNameArea.addView(itemNameText);

				// アイテム種別
				//TODO 種別はテキスト以外の方法で表記すべし

				// アイテム個数
				itemValue = (TextView) convertView.findViewById(R.id.listItem_item_part_itemValueText);
				itemValue.setText("99");

				// アイテム単重量
				itemWeight = (TextView) convertView.findViewById(R.id.listItem_item_part_itemWeightText);
				itemWeight.setText("" + item.weight);
			}
			return convertView;
		}

		/**
		 * アイテム名を設定する
		 *
		 * @param name    デフォルト名
		 * @param quality 品質
		 * @return 各種ステータスを加味した表示名
		 */
		private String setItemName(String name, QualityEnum quality) {
			String resultName = name;
			if (!quality.equals(QualityEnum.NORMAL)) {
				resultName += "(" + mContext.getString(quality.getQuality()) + ")";
			}
			return resultName;
		}
	}
}
