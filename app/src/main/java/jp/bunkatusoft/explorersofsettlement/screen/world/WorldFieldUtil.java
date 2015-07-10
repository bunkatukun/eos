package jp.bunkatusoft.explorersofsettlement.screen.world;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.system.item.Inventory;
import jp.bunkatusoft.explorersofsettlement.system.item.InventoryFilterEnum;
import jp.bunkatusoft.explorersofsettlement.system.item.Item;
import jp.bunkatusoft.explorersofsettlement.system.item.ItemCategoryEnum;
import jp.bunkatusoft.explorersofsettlement.system.item.QualityEnum;
import jp.bunkatusoft.explorersofsettlement.util.Util;

public class WorldFieldUtil {
	/**
	 * フィールドのチップデータを読み込むメソッド
	 *
	 * @return 読み込んだチップデータ
	 */
	public static List<FieldPiece> loadFieldPieceData(Context context, String filePath) throws IOException {
		return new ObjectMapper().readValue(Util.getAssetsJSONText(context, filePath),
				new TypeReference<ArrayList<FieldPiece>>() {
				});
	}

	public static List<FieldRoad> loadFieldRoadData(Context context, String filePath) throws IOException {
		return new ObjectMapper().readValue(Util.getAssetsJSONText(context, filePath),
				new TypeReference<ArrayList<FieldRoad>>() {
				});
	}

	//TODO 不要になった動作用メソッドは速やかに削除すべし
	/* 動作用(≠test)メソッド */
	public static Inventory initItemInventory() {
		List<Item> itemList = new ArrayList<Item>();

		itemList.add(initItem(1,0,QualityEnum.NORMAL,"アイテム1",ItemCategoryEnum.CATEGORY1,InventoryFilterEnum.EQUIPMENT,5,false,true));
		itemList.add(initItem(2,1,QualityEnum.NORMAL,"アイテム2",ItemCategoryEnum.CATEGORY2,InventoryFilterEnum.SUPPLIES,1,true,true));
		itemList.add(initItem(3,1,QualityEnum.POOR,"アイテム2",ItemCategoryEnum.CATEGORY2,InventoryFilterEnum.SUPPLIES,1,true,true));
		itemList.add(initItem(4,1,QualityEnum.NORMAL,"アイテム2",ItemCategoryEnum.CATEGORY2,InventoryFilterEnum.SUPPLIES,1,true,true));
		itemList.add(initItem(5,2,QualityEnum.NORMAL,"アイテム3",ItemCategoryEnum.CATEGORY3,InventoryFilterEnum.MATERIAL,2,false,true));
		itemList.add(initItem(6,2,QualityEnum.POOR,"アイテム3",ItemCategoryEnum.CATEGORY3,InventoryFilterEnum.MATERIAL,2,false,true));
		itemList.add(initItem(7,3,QualityEnum.NORMAL,"アイテム4",ItemCategoryEnum.CATEGORY2,InventoryFilterEnum.EQUIPMENT,7,false,true));
		itemList.add(initItem(8,4,QualityEnum.NORMAL,"アイテム5",ItemCategoryEnum.CATEGORY1,InventoryFilterEnum.MISCELLANEOUS,2,true,true));
		itemList.add(initItem(9,4,QualityEnum.NORMAL,"アイテム5",ItemCategoryEnum.CATEGORY1,InventoryFilterEnum.MISCELLANEOUS,2,true,true));
		itemList.add(initItem(10,4,QualityEnum.GOOD,"アイテム5",ItemCategoryEnum.CATEGORY1,InventoryFilterEnum.MISCELLANEOUS,2,true,true));

		return new Inventory(itemList);
	}

	public static Item initItem(int id, int imageId, QualityEnum quality, String name, ItemCategoryEnum category, InventoryFilterEnum filter, int weight, boolean canUse, boolean canTrash) {
		Item resultItem = new Item();
		resultItem.id = id;
		resultItem.imageID =imageId;
		resultItem.quality = quality;
		resultItem.name = name;
		resultItem.category = category;
		resultItem.filter = filter;
		resultItem.weight = weight;
		resultItem.canUse = canUse;
		resultItem.canTrash = canTrash;
		return resultItem;
	}
}
