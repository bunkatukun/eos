package jp.bunkatusoft.explorersofsettlement.system.fellowship;

public enum PartyCategoryEnum {
	STATUS(0),
	EQUIPMENT(1),
	COMMANDS(2);

	private int mCategory;

	PartyCategoryEnum(int category) {
		mCategory = category;
	}

	public int getCategory() {
		return mCategory;
	}
}
