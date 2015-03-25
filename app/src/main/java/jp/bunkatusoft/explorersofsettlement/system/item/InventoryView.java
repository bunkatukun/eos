package jp.bunkatusoft.explorersofsettlement.system.item;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;

public class InventoryView implements OnClickListener, AdapterView.OnItemClickListener {

	//TODO 画面の作成
	//TODO 画面の表示
	//TODO データ読み込み・反映
	//TODO アクション追加
	//TODO フィルタ追加

	public interface OnInventoryActionListener {
		void onInventoryClose();
	}

	Context mContext;
	OnInventoryActionListener mListener;
	ArrayList<Item> mItemList;

	RelativeLayout mBaseLayout;
	ListView mItemListView;
	InventoryAdapter mInventoryAdapter;

	Button mOpenDetailButton;
	Button mOpenUseButton;
	Button mTrashButton;
	Button mCloseButton;

	public InventoryView(Context context,  FrameLayout rootLayout,OnInventoryActionListener listener, ArrayList<Item> itemList) {
		mContext = context;
		mListener = listener;
		mItemList = itemList;

		initBaseLayout(rootLayout);
		initInventoryAdapter();
	}

	private void initBaseLayout(FrameLayout rootLayout) {
		mBaseLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.overlay_inventory, null);
		rootLayout.addView(mBaseLayout);

		mOpenDetailButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_openDetailButton);
		mOpenDetailButton.setOnClickListener(this);
		mOpenUseButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_openUseButton);
		mOpenUseButton.setOnClickListener(this);
		mTrashButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_trashButton);
		mTrashButton.setOnClickListener(this);
		mCloseButton = (Button) mBaseLayout.findViewById(R.id.part_inventory_closeInventoryButton);
		mCloseButton.setOnClickListener(this);
	}

	private void initInventoryAdapter(){
		mItemListView = (ListView) mBaseLayout.findViewById(R.id.part_inventory_itemList);
		mItemListView.setOnItemClickListener(this);

		mInventoryAdapter = new InventoryAdapter(mContext,new ArrayList<Item>());
		mItemListView.setAdapter(mInventoryAdapter);

		if(mInventoryAdapter != null) {
			mInventoryAdapter.clear();
			if(mItemList != null && mItemList.size()>0){
				for(Item item : mItemList){
					mInventoryAdapter.add(item);
				}
			}
			mInventoryAdapter.notifyDataSetChanged();
		}
	}

	public void setVisibility(int visibility) {
		mBaseLayout.setVisibility(visibility);
	}

	public void startAnimation(AnimationSet animationSet) {
		mBaseLayout.startAnimation(animationSet);
	}

	@Override
	public void onClick(View view) {
		int viewID = view.getId();
		switch (viewID) {
			case R.id.part_inventory_closeInventoryButton:
				mListener.onInventoryClose();
				break;
			default:
				break;
		}
	}

	private class InventoryAdapter extends ArrayAdapter<Item> {

		private Context mContext;
		private final LayoutInflater mInflater;

		public InventoryAdapter(Context context, List<Item> objects) {
			super(context, 0, objects);
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView itemIcon;
			TextView itemName;
			TextView itemCategory;
			TextView itemNum;
			TextView itemWeight;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listitem_item, null);
			}
			Item item = (Item) getItem(position);
			if (item != null) {
				// アイテムアイコン
				itemIcon = (ImageView) convertView.findViewById(R.id.part_inventory_list_itemImageView);
				//TODO 解析・メモリ展開済みの画像群を呼び出し、ID一致で当てはめる仕組みを作る
				itemIcon.setImageResource(R.drawable.stone01);

				// アイテム名称
				itemName = (TextView) convertView.findViewById(R.id.part_inventory_list_itemNameText);
				//TODO 種類・レアリティで文字色を変えるか？
				itemName.setText(item.name);

				// アイテム種別
				itemCategory = (TextView) convertView.findViewById(R.id.part_inventory_list_itemCategoryText);
				//TODO 種別idからテキストへ起こすメソッドの追加
				itemCategory.setText(String.valueOf(item.category));

				// アイテム個数
				itemNum = (TextView) convertView.findViewById(R.id.part_inventory_list_itemNumText);
				itemNum.setText(" x " + item.num);

				// アイテム単重量
				itemWeight = (TextView) convertView.findViewById(R.id.part_inventory_list_itemWeightText);
				itemWeight.setText("wgt : " + item.weight);
			}
			return convertView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		//リストがタップされた時のあれこれ
	}
}
