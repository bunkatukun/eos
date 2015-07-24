package jp.bunkatusoft.explorersofsettlement.screen.world;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

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