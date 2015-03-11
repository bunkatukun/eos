package jp.bunkatusoft.explorersofsettlement.event;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(Enclosed.class)
public class EventUtilTest {

	@RunWith(AndroidJUnit4.class)
	public static class loadEventsJSONDataを使って {
		@Test
		public void JSONを読むことができる() throws Exception {
			List<Event> actual = EventUtil.loadEventsJSONData(getContext(), "test_events/normal.json");
			assertThat(actual.get(0).id, is(1));
			assertThat(actual.get(0).action, is(EventActionEnum.TEXT));
			assertThat(actual.get(0).value, is("JSONでテキストを表示します。"));

			assertThat(actual.get(1).id, is(2));
			assertThat(actual.get(1).action, is(EventActionEnum.CLEAN));
			assertThat(actual.get(1).value, is("text"));
		}

		@Test
		public void 選択肢があるJSONを読むことができる() throws Exception {
			List<Event> actual = EventUtil.loadEventsJSONData(getContext(), "test_events/choices.json");
			assertThat(actual.get(0).id, is(1));
			assertThat(actual.get(0).action, is(EventActionEnum.SELECTOR));

			assertThat(actual.get(0).choices.get(0).jump, is(5));
			assertThat(actual.get(0).choices.get(0).text, is("１つ目の選択肢"));
			assertThat(actual.get(0).choices.get(1).jump, is(8));
			assertThat(actual.get(0).choices.get(1).text, is("２つ目の選択肢"));
		}
	}
}