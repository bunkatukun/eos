package jp.bunkatusoft.explorersofsettlement.field.world;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class WorldFieldUtilTest {
	@Test
	public void loadFieldRoadDataでJSONがパースできる() throws Exception {
		ArrayList<FieldRoad> fieldRoads = WorldFieldUtil.loadFieldRoadData(getContext(), "world-field-util/parse-json.json");

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