package jp.bunkatusoft.explorersofsettlement.battle.skill;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CriticalAttackTest {
	@Test
	public void 通常攻撃の設定値倍の切り捨て数値になる() {
		CriticalAttack actual = new CriticalAttack("強攻撃", 15, 1.5f);
		// 15 * 1.5 = 22.5 の切り捨て
		assertThat(actual.getAttack(), is(22));
	}

	@Test
	public void 攻撃値が0のとき0になる() {
		CriticalAttack actual = new CriticalAttack("強攻撃", 0, 2f);
		assertThat(actual.getAttack(), is(0));
	}
}
