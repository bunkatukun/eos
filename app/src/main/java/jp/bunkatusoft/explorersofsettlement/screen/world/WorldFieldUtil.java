package jp.bunkatusoft.explorersofsettlement.screen.world;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.system.inventory.Inventory;
import jp.bunkatusoft.explorersofsettlement.system.item.Category;
import jp.bunkatusoft.explorersofsettlement.system.item.Item;
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

		itemList.add(initItem(1,0,QualityEnum.NORMAL,"鉄の長剣",Category.SWORD,5,false,true));
		itemList.add(initItem(2,1,QualityEnum.NORMAL,"干し肉",Category.PROCESSED_MEAT,true,1,true,true));
		itemList.add(initItem(3,1,QualityEnum.POOR,"干し肉",Category.PROCESSED_MEAT,1,true,true));
		itemList.add(initItem(4,1,QualityEnum.NORMAL,"干し肉",Category.PROCESSED_MEAT,1,true,true));
		itemList.add(initItem(5,2,QualityEnum.NORMAL,"鉄鉱石",Category.ORE,2,false,true));
		itemList.add(initItem(6,2,QualityEnum.POOR,"鉄鉱石",Category.ORE,2,false,true));
		itemList.add(initItem(7,3,QualityEnum.NORMAL,"革鎧",Category.LEATHER_ARMOR,7,false,true));
		itemList.add(initItem(8,4,QualityEnum.NORMAL,"謎の石像",Category.STATUE,2,true,true));
		itemList.add(initItem(9,4,QualityEnum.NORMAL,"謎の石像",Category.STATUE,2,true,true));
		itemList.add(initItem(10,4,QualityEnum.GOOD,"謎の石像",Category.STATUE,2,true,true));

		return new Inventory(itemList);
	}

	public static Item initItem(int id, int imageId, QualityEnum quality, String name, Category category, int weight, boolean canUse, boolean canTrash) {
		return initItem(id, imageId, quality, name, category, false, weight, canUse, canTrash);
	}

	public static Item initItem(int id, int imageId, QualityEnum quality, String name, Category category, boolean isKnown, int weight, boolean canUse, boolean canTrash) {
		Item resultItem = new Item();
		resultItem.id = id;
		resultItem.imageID =imageId;
		resultItem.quality = quality;
		resultItem.name = name;
		resultItem.category = category;
		resultItem.isKnown = isKnown;
		resultItem.weight = weight;
		resultItem.canUse = canUse;
		resultItem.canTrash = canTrash;
		return resultItem;
	}
}
