package jp.bunkatusoft.explorersofsettlement.battle.core;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.battle.unit.Enemy;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Player;
import jp.bunkatusoft.explorersofsettlement.battle.unit.enemy.Wolf;
import jp.bunkatusoft.explorersofsettlement.battle.unit.player.Wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BattleTest {
	@Ignore("ログ吐き出しで確認するテストのため")
	@Test
	public void 戦闘を行うことができる() {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Wizard("p-id"));

		List<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(new Wolf("e-id"));

		Battle battle = new Battle(playerList, enemyList);
		battle.exec();

		assertThat(battle.getResult().getElapsedTurn(), is(3));
	}
}