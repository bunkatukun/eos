package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.system.item.Item;
import jp.bunkatusoft.explorersofsettlement.system.item.ItemInventory;
import jp.bunkatusoft.explorersofsettlement.util.Util;

public class WorldFieldUtil {
	/**
	 * フィールドのチップデータを読み込むメソッド
	 * @return	読み込んだチップデータ
	 */
	public static List<FieldPiece> loadFieldPieceData(Context context, String filePath) throws IOException {
		return new ObjectMapper().readValue(Util.getAssetsJSONText(context, filePath),
				new TypeReference<ArrayList<FieldPiece>>() {});
	}

	public static List<FieldRoad> loadFieldRoadData(Context context, String filePath) throws IOException {
		return new ObjectMapper().readValue(Util.getAssetsJSONText(context, filePath),
				new TypeReference<ArrayList<FieldRoad>>() {});
	}

	//TODO 不要になった動作用メソッドは速やかに削除すべし
	/* 動作用(≠test)メソッド */
	public static ItemInventory initItemInventory(){
		ItemInventory resultInventory = new ItemInventory();
		resultInventory.itemList = new ArrayList<Item>();

		Item item1 = new Item();
		item1.id = 1;
		item1.name = "アイテム1";
		resultInventory.itemList.add(item1);

		Item item2 = new Item();
		item2.id = 2;
		item2.name = "アイテム2";
		resultInventory.itemList.add(item2);

		Item item3 = new Item();
		item1.id = 3;
		item1.name = "アイテム3";
		resultInventory.itemList.add(item3);

		return resultInventory;
	}
}
