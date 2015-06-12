package jp.bunkatusoft.explorersofsettlement.screen.world;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.system.item.Inventory;
import jp.bunkatusoft.explorersofsettlement.system.item.InventoryFilterEnum;
import jp.bunkatusoft.explorersofsettlement.system.item.Item;
import jp.bunkatusoft.explorersofsettlement.system.item.ItemCategoryEnum;
import jp.bunkatusoft.explorersofsettlement.system.item.QualityEnum;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Enclosed.class)
@MediumTest
public class WorldFieldUtilTest {

	@RunWith(AndroidJUnit4.class)
	public static class loadFieldRoadDataを使って {
		@Test
		public void loadFieldRoadDataでJSONがパースできる() throws Exception {
			List<FieldRoad> fieldRoads = WorldFieldUtil.loadFieldRoadData(getContext(), "world-field-util/parse-json.json");

			assertThat(fieldRoads.get(0).id, is(1));
			assertThat(fieldRoads.get(0).connectA, is(1));
			assertThat(fieldRoads.get(0).connectB, is(2));
			assertThat(fieldRoads.get(0).directivity.getId(), is(0));

			assertThat(fieldRoads.get(1).id, is(2));
			assertThat(fieldRoads.get(1).connectA, is(2));
			assertThat(fieldRoads.get(1).connectB, is(3));
			assertThat(fieldRoads.get(1).directivity.getId(), is(0));

			assertThat(fieldRoads.get(2).id, is(3));
			assertThat(fieldRoads.get(2).connectA, is(2));
			assertThat(fieldRoads.get(2).connectB, is(4));
			assertThat(fieldRoads.get(2).directivity.getId(), is(0));

			assertThat(fieldRoads.get(3).id, is(4));
			assertThat(fieldRoads.get(3).connectA, is(3));
			assertThat(fieldRoads.get(3).connectB, is(4));
			assertThat(fieldRoads.get(3).directivity.getId(), is(1));
		}
	}

	@RunWith(AndroidJUnit4.class)
	public static class initItemInventoryを使って {
		@Test
		public void ItemInventoryが初期化できる() throws Exception {
			Inventory inventory = WorldFieldUtil.initItemInventory();
			List<Item> itemList = inventory.getItemList();
			int totalWeight = inventory.getTotalWeight();

			assertThat(itemList.get(0).id,is(1));
			assertThat(itemList.get(0).imageID,is(0));
			assertThat(itemList.get(0).quality,is(QualityEnum.NORMAL));
			assertThat(itemList.get(0).name,is("アイテム1"));
			assertThat(itemList.get(0).category,is(ItemCategoryEnum.CATEGORY1));
			assertThat(itemList.get(0).filter,is(InventoryFilterEnum.EQUIPMENT));
			assertThat(itemList.get(0).num,is(1));
			assertThat(itemList.get(0).weight,is(5));
			assertThat(itemList.get(0).canUse,is(false));
			assertThat(itemList.get(0).canTrash,is(true));

			assertThat(itemList.get(1).id,is(2));
			assertThat(itemList.get(1).imageID,is(1));
			assertThat(itemList.get(1).quality,is(QualityEnum.NORMAL));
			assertThat(itemList.get(1).name,is("アイテム2"));
			assertThat(itemList.get(1).category,is(ItemCategoryEnum.CATEGORY2));
			assertThat(itemList.get(1).filter,is(InventoryFilterEnum.SUPPLIES));
			assertThat(itemList.get(1).num,is(3));
			assertThat(itemList.get(1).weight,is(1));
			assertThat(itemList.get(1).canUse,is(true));
			assertThat(itemList.get(1).canTrash,is(true));

			assertThat(itemList.get(2).id,is(3));
			assertThat(itemList.get(2).imageID,is(2));
			assertThat(itemList.get(2).quality,is(QualityEnum.POOR));
			assertThat(itemList.get(2).name,is("アイテム3"));
			assertThat(itemList.get(2).category,is(ItemCategoryEnum.CATEGORY3));
			assertThat(itemList.get(2).filter,is(InventoryFilterEnum.MATERIAL));
			assertThat(itemList.get(2).num,is(2));
			assertThat(itemList.get(2).weight,is(2));
			assertThat(itemList.get(2).canUse,is(false));
			assertThat(itemList.get(2).canTrash,is(true));

			assertThat(itemList.get(3).id,is(4));
			assertThat(itemList.get(3).imageID,is(3));
			assertThat(itemList.get(3).quality,is(QualityEnum.NORMAL));
			assertThat(itemList.get(3).name,is("アイテム4"));
			assertThat(itemList.get(3).category,is(ItemCategoryEnum.CATEGORY2));
			assertThat(itemList.get(3).filter,is(InventoryFilterEnum.EQUIPMENT));
			assertThat(itemList.get(3).num,is(1));
			assertThat(itemList.get(3).weight,is(7));
			assertThat(itemList.get(3).canUse,is(false));
			assertThat(itemList.get(3).canTrash,is(true));

			assertThat(itemList.get(4).id,is(5));
			assertThat(itemList.get(4).imageID,is(4));
			assertThat(itemList.get(4).quality,is(QualityEnum.NORMAL));
			assertThat(itemList.get(4).name,is("アイテム5"));
			assertThat(itemList.get(4).category,is(ItemCategoryEnum.CATEGORY1));
			assertThat(itemList.get(4).filter,is(InventoryFilterEnum.MISCELLANEOUS));
			assertThat(itemList.get(4).num,is(2));
			assertThat(itemList.get(4).weight,is(2));
			assertThat(itemList.get(4).canUse,is(true));
			assertThat(itemList.get(4).canTrash,is(true));

			assertThat(itemList.get(5).id,is(6));
			assertThat(itemList.get(5).imageID,is(5));
			assertThat(itemList.get(5).quality,is(QualityEnum.GOOD));
			assertThat(itemList.get(5).name,is("アイテム5"));
			assertThat(itemList.get(5).category,is(ItemCategoryEnum.CATEGORY1));
			assertThat(itemList.get(5).filter,is(InventoryFilterEnum.MISCELLANEOUS));
			assertThat(itemList.get(5).num,is(1));
			assertThat(itemList.get(5).weight,is(2));
			assertThat(itemList.get(5).canUse,is(true));
			assertThat(itemList.get(5).canTrash,is(true));

			assertThat(totalWeight,is(25));
		}
	}

	@RunWith(AndroidJUnit4.class)
	public static class loadFieldPieceDataを使って {
		@Test
		public void JSONがパースできる() throws Exception {
			List<FieldPiece> fieldPieces = WorldFieldUtil.loadFieldPieceData(getContext(), "world-field-util/field-pieces.json");

			assertThat(fieldPieces.get(0).id, is(1));
			assertThat(fieldPieces.get(0).x, is(10));
			assertThat(fieldPieces.get(0).y, is(10));
			assertThat(fieldPieces.get(0).type, is(PieceTypeEnum.valueOf(1)));
			List<Integer> expected0 = new ArrayList<Integer>() {{
				add(1);
			}};
			assertThat(fieldPieces.get(0).connects, is(expected0));

			assertThat(fieldPieces.get(1).id, is(2));
			assertThat(fieldPieces.get(1).x, is(30));
			assertThat(fieldPieces.get(1).y, is(30));
			assertThat(fieldPieces.get(1).type, is(PieceTypeEnum.valueOf(0)));
			List<Integer> expected1 = new ArrayList<Integer>() {{
				add(1);
				add(2);
				add(3);
			}};
			assertThat(fieldPieces.get(1).connects, is(expected1));

			assertThat(fieldPieces.get(2).id, is(3));
			assertThat(fieldPieces.get(2).x, is(50));
			assertThat(fieldPieces.get(2).y, is(30));
			assertThat(fieldPieces.get(2).type, is(PieceTypeEnum.valueOf(0)));
			List<Integer> expected2 = new ArrayList<Integer>() {{
				add(2);
				add(4);
			}};
			assertThat(fieldPieces.get(2).connects, is(expected2));

			assertThat(fieldPieces.get(3).id, is(4));
			assertThat(fieldPieces.get(3).x, is(50));
			assertThat(fieldPieces.get(3).y, is(30));
			assertThat(fieldPieces.get(3).type, is(PieceTypeEnum.valueOf(4)));
			List<Integer> expected3 = new ArrayList<Integer>() {{
				add(3);
				add(4);
			}};
			assertThat(fieldPieces.get(3).connects, is(expected3));
		}
	}
}