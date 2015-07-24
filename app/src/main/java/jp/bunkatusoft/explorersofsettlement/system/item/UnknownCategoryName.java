package jp.bunkatusoft.explorersofsettlement.system.item;

public interface UnknownCategoryName {
	//不確定名
	public static final String UNKNOWN_ORE = "鉱石";
	public static final String UNKNOWN_SIMPLE_SWORD = "長剣";
	public static final String UNKNOWN_SIMPLE_LEATHER_ARMOR = "革鎧";
	public static final String UNKNOWN_COOKED_MEAT = "調理された肉";
	public static final String UNKNOWN_SMALL_STONE_STATUE = "小さな石像";


	public String toUnknownName();
	public Filter toFilter();
}
