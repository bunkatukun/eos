package jp.bunkatusoft.explorersofsettlement.system.item;

public class Item {
	public int id;
	//TODO 将来的にCategoryと統一したい
	public int imageID;
	public QualityEnum quality;
	public String name;
	public Category category;
	public boolean isKnown;
	public int weight;
	//TODO 属性や効果を追加する日がくるかも
	public boolean canUse;
	public boolean canTrash;

	public Item(){};

	public Item(int id, int imageId, String name, Category category, int weight) {
		this.id = id;
		this.imageID = imageId;
		this.quality = QualityEnum.NORMAL;
		this.name = name;
		this.category = Category.ORE;
		this.isKnown = false;
		this.weight = weight;
		this.canUse = true;
		this.canTrash = true;
	}
}
