package jp.bunkatusoft.explorersofsettlement.system.item;

public class Item {
	public int id;
	//TODO 将来的にCategoryと統一したい
	public int imageID;
	public String name;
	//TODO 種別はenum化した方が良い気がする
	public int category;
	//TODO filter は category と統一したい
	public InventoryFilterEnum filter;
	public int weight;
	public int num;
	//TODO 属性や効果を追加する日がくるかも
	public boolean canUse;
	public boolean canTrash;
}
