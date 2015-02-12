package jp.bunkatusoft.explorersofsettlement.battle.core;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BattleCalculatorTest {
	@Test
	public void ダメージ計算が行われる() {
		int actual = BattleCalculator.calcDamage(10, 10);

		// 10 - 10/2 = 5
		int expect = 5;

		assertThat(actual, is(expect));
	}

	@Test
	public void 係数が0を下回る場合ダメージが0になる() {
		int actual = BattleCalculator.calcDamage(10, 30);

		assertThat(actual, is(0));
	}
}