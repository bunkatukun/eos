package jp.bunkatusoft.explorersofsettlement.system.item;

import java.util.List;

public class Inventory {
	private int mTotalWeight;
	private List<Item> mItemList;

	public Inventory(List<Item> itemList){
		mItemList = itemList;
		updateTotalWeight();
	}

	public List<Item> getItemList(){
		return mItemList;
	}

	public int getTotalWeight(){
		return mTotalWeight;
	}

	public int updateTotalWeight(){
		int resultWeight = -1;

		if(mItemList != null){
			resultWeight = 0;
			for(Item item : mItemList){
				resultWeight += item.weight;
			}
		}
		mTotalWeight = resultWeight;
		return resultWeight;
	}
}