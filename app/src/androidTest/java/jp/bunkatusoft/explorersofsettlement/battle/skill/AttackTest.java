package jp.bunkatusoft.explorersofsettlement.battle.skill;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AttackTest {
	@Test
	public void 攻撃力が力のままか(){
		Attack actual = new Attack("攻撃", 30);
		assertThat(actual.getAttack(), is(30));
	}
}