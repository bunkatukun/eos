package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.system.item.InventoryFilterEnum;
import jp.bunkatusoft.explorersofsettlement.system.item.Item;
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
	public static List<Item> initItemInventory() {
		List<Item> resultList = new ArrayList<Item>();

		Item item1 = new Item();
		item1.id = 1;
		item1.imageID =0;
		item1.name = "アイテム1";
		item1.category = 0;
		item1.filter = InventoryFilterEnum.EQUIPMENT;
		item1.num = 1;
		item1.weight = 5;
		item1.canUse = false;
		item1.canTrash = true;
		resultList.add(item1);

		Item item2 = new Item();
		item2.id = 2;
		item2.imageID =1;
		item2.name = "アイテム2";
		item2.category = 0;
		item2.filter = InventoryFilterEnum.SUPPLIES;
		item2.num = 3;
		item2.weight = 1;
		item2.canUse = true;
		item2.canTrash = true;
		resultList.add(item2);

		Item item3 = new Item();
		item3.id = 3;
		item3.imageID =2;
		item3.name = "アイテム3";
		item3.category = 0;
		item3.filter = InventoryFilterEnum.MATERIAL;
		item3.num = 2;
		item3.weight = 2;
		item3.canUse = false;
		item3.canTrash = true;
		resultList.add(item3);

		Item item4 = new Item();
		item4.id = 4;
		item4.imageID =3;
		item4.name = "アイテム4";
		item4.category = 0;
		item4.filter = InventoryFilterEnum.EQUIPMENT;
		item4.num = 1;
		item4.weight = 7;
		item4.canUse = false;
		item4.canTrash = true;
		resultList.add(item4);

		Item item5 = new Item();
		item5.id = 5;
		item5.imageID =4;
		item5.name = "アイテム5";
		item5.category = 0;
		item5.filter = InventoryFilterEnum.MISCELLANEOUS;
		item5.num = 2;
		item5.weight =2;
		item5.canUse = true;
		item5.canTrash = true;
		resultList.add(item5);

		return resultList;
	}
}
