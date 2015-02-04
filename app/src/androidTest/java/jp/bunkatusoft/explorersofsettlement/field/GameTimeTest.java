package jp.bunkatusoft.explorersofsettlement.field;

import android.test.suitebuilder.annotation.SmallTest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import jp.bunkatusoft.explorersofsettlement.time.GameMonthEnum;
import jp.bunkatusoft.explorersofsettlement.time.GameTimeEnum;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class GameTimeTest {
	@Test
	public void 初期値が1年1月1日Midnightであること() {
		GameTime actual = new GameTime();
		assertThat(actual.getYear(), is(1));
		assertThat(actual.getMonth(), is(GameMonthEnum.JANUARY));
		assertThat(actual.getDay(), is(1));
		assertThat(actual.getTime(), is(GameTimeEnum.MIDNIGHT));
	}
}