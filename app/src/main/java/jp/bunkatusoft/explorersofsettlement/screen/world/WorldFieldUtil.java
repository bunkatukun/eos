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

		Item item1 = new Item();
		item1.id = 1;
		item1.imageID =0;
		item1.quality = QualityEnum.NORMAL;
		item1.name = "アイテム1";
		item1.category = ItemCategoryEnum.CATEGORY1;
		item1.filter = InventoryFilterEnum.EQUIPMENT;
		item1.num = 1;
		item1.weight = 5;
		item1.canUse = false;
		item1.canTrash = true;
		itemList.add(item1);

		Item item2 = new Item();
		item2.id = 2;
		item2.imageID =1;
		item2.quality = QualityEnum.NORMAL;
		item2.name = "アイテム2";
		item2.category = ItemCategoryEnum.CATEGORY2;
		item2.filter = InventoryFilterEnum.SUPPLIES;
		item2.num = 3;
		item2.weight = 1;
		item2.canUse = true;
		item2.canTrash = true;
		itemList.add(item2);

		Item item3 = new Item();
		item3.id = 3;
		item3.imageID =2;
		item3.quality = QualityEnum.POOR;
		item3.name = "アイテム3";
		item3.category = ItemCategoryEnum.CATEGORY3;
		item3.filter = InventoryFilterEnum.MATERIAL;
		item3.num = 2;
		item3.weight = 2;
		item3.canUse = false;
		item3.canTrash = true;
		itemList.add(item3);

		Item item4 = new Item();
		item4.id = 4;
		item4.imageID =3;
		item4.quality = QualityEnum.NORMAL;
		item4.name = "アイテム4";
		item4.category = ItemCategoryEnum.CATEGORY2;
		item4.filter = InventoryFilterEnum.EQUIPMENT;
		item4.num = 1;
		item4.weight = 7;
		item4.canUse = false;
		item4.canTrash = true;
		itemList.add(item4);

		Item item5 = new Item();
		item5.id = 5;
		item5.imageID =4;
		item5.quality = QualityEnum.NORMAL;
		item5.name = "アイテム5";
		item5.category = ItemCategoryEnum.CATEGORY1;
		item5.filter = InventoryFilterEnum.MISCELLANEOUS;
		item5.num = 2;
		item5.weight =2;
		item5.canUse = true;
		item5.canTrash = true;
		itemList.add(item5);

		Item item6 = new Item();
		item6.id = 6;
		item6.imageID =5;
		item6.quality = QualityEnum.GOOD;
		item6.name = "アイテム5";
		item6.category = ItemCategoryEnum.CATEGORY1;
		item6.filter = InventoryFilterEnum.MISCELLANEOUS;
		item6.num = 1;
		item6.weight =2;
		item6.canUse = true;
		item6.canTrash = true;
		itemList.add(item6);

		return new Inventory(itemList);
	}
}
