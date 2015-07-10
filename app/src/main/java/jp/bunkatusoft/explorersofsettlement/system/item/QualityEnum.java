package jp.bunkatusoft.explorersofsettlement.system.item;


import jp.bunkatusoft.explorersofsettlement.R;

public enum QualityEnum {
	GOOD(R.string.sys_item_quality_good),
	NORMAL(R.string.sys_item_quality_normal),
	POOR(R.string.sys_item_quality_poor);

	private int quality;

	QualityEnum(int quality){
		this.quality = quality;
	}

	public int getQuality(){
		return quality;
	}
}
