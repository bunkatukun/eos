package jp.bunkatusoft.explorersofsettlement.system.item;

public enum InventoryFilterEnum {
	ALL(0),
	EQUIPMENT(1),
	SUPPLIES(2),
	MISCELLANEOUS(3),
	MATERIAL(4),
	IMPORTANT(5);

	private int filter;

	InventoryFilterEnum(int filter) {
		this.filter = filter;
	}

	public int getFilter() {
		return filter;
	}
}
