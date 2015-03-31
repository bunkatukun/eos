package jp.bunkatusoft.explorersofsettlement.system.item;

import jp.bunkatusoft.explorersofsettlement.R;


public enum ItemCategoryEnum {
	CATEGORY1(R.string.sys_item_category_1),
	CATEGORY2(R.string.sys_item_category_2),
	CATEGORY3(R.string.sys_item_category_3);

	private int category;

	ItemCategoryEnum(int category) {
		this.category = category;
	}

	public int getCategory() {
		return category;
	}
}
