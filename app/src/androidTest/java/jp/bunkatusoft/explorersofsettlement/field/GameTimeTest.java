package jp.bunkatusoft.explorersofsettlement.field;

import android.test.suitebuilder.annotation.SmallTest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
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

	@Test
	public void 時刻が任意の値に設定できる() {
		GameTime actual = new GameTime(1, GameMonthEnum.APRIL, 30, GameTimeEnum.AFTERNOON);
		assertThat(actual.getYear(), is(1));
		assertThat(actual.getMonth(), is(GameMonthEnum.APRIL));
		assertThat(actual.getDay(), is(30));
		assertThat(actual.getTime(), is(GameTimeEnum.AFTERNOON));
	}

	@Test
	public void 時刻がリセットできる() {
		GameTime actual = new GameTime();
		actual.resetTime(1, GameMonthEnum.APRIL, 30, GameTimeEnum.AFTERNOON);
		assertThat(actual.getYear(), is(1));
		assertThat(actual.getMonth(), is(GameMonthEnum.APRIL));
		assertThat(actual.getDay(), is(30));
		assertThat(actual.getTime(), is(GameTimeEnum.AFTERNOON));
	}

	@Test
	public void 年を進めることができる() {
		GameTime actual = new GameTime(1, GameMonthEnum.JANUARY, 1, GameTimeEnum.MIDNIGHT);
		actual.incrementYear();
		assertThat(actual.getYear(), is(2));
	}

	@Test
	public void 月を進めることができる() {
		GameTime actual = new GameTime(1, GameMonthEnum.JANUARY, 1, GameTimeEnum.MIDNIGHT);
		actual.incrementMonth();
		assertThat(actual.getMonth(), is(GameMonthEnum.FEBRUARY));
	}

	@Test
	public void 月がDECEMBERの時に進めた場合JANUARYに戻るか() {
		GameTime actual = new GameTime(1, GameMonthEnum.DECEMBER, 1, GameTimeEnum.MIDNIGHT);
		actual.incrementMonth();
		assertThat(actual.getMonth(), is(GameMonthEnum.JANUARY));
	}

	@Test
	public void 日を進めることができる() {
		GameTime actual = new GameTime(1, GameMonthEnum.JANUARY, 1, GameTimeEnum.MIDNIGHT);
		actual.incrementDay();
		assertThat(actual.getDay(), is(2));
	}

	@Test
	public void 日が30の時に進めた場合次の月になり日が1になる() {
		GameTime actual = new GameTime(1, GameMonthEnum.JANUARY, 30, GameTimeEnum.MIDNIGHT);
		actual.incrementDay();
		assertThat(actual.getMonth(), is(GameMonthEnum.FEBRUARY));
		assertThat(actual.getDay(), is(30));
	}

	@Test
	public void 時間帯を進めることができる() {
		GameTime actual = new GameTime(1, GameMonthEnum.JANUARY, 1, GameTimeEnum.MIDNIGHT);
		actual.incrementTime();
		assertThat(actual.getTime(), is(GameTimeEnum.MORNING));
	}

	@Test
	public void 時間帯がNIGHTの時に進めるとMIDNIGHTになり日が進む() {
		GameTime actual = new GameTime(1, GameMonthEnum.JANUARY, 1, GameTimeEnum.NIGHT);
		actual.incrementTime();
		assertThat(actual.getDay(), is(2));
		assertThat(actual.getTime(), is(GameTimeEnum.MIDNIGHT));
	}
}