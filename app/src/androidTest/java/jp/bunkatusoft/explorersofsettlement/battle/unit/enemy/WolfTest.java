package jp.bunkatusoft.explorersofsettlement.battle.unit.enemy;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.battle.core.TurnAction;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Player;
import jp.bunkatusoft.explorersofsettlement.battle.unit.player.Wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class WolfTest {
	@Test
	public void 行動値が0のとき噛み付きが選ばれること() {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Wizard("1"));

		TurnAction actual = new Wolf("test-id").determineAction(0, playerList);
		assertThat(actual.getSkill(), is(Wolf.Action.BITE.getSkill()));
	}

	@Test
	public void 行動値が91のとき急所への噛み付きが選ばれること() {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Wizard("1"));

		TurnAction actual = new Wolf("test-id").determineAction(91, playerList);
		assertThat(actual.getSkill(), is(Wolf.Action.CRITICAL_BITE.getSkill()));
	}
}