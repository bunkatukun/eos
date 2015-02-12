package jp.bunkatusoft.explorersofsettlement.battle.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.battle.Order;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Enemy;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Player;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AgilityManagerTest {
	@Test
	public void 素早い順に並び替えされるか() {
		List<TurnAction> turnActionList = new ArrayList<TurnAction>();
		Player unit1 = new Player("p1", "p1", 1, 1, 1, 1, 3){
			@Override
			public TurnAction determineAction(Order order, Integer randomAction, List<Enemy> unitList) {
				return null;
			}

			@Override
			public Bitmap getImage(Context context) {
				return null;
			}
		};
		Player unit2 = new Player("p2", "p2", 1, 1, 1, 1, 1){
			@Override
			public TurnAction determineAction(Order order, Integer randomAction, List<Enemy> unitList) {
				return null;
			}

			@Override
			public Bitmap getImage(Context context) {
				return null;
			}
		};
		Player unit3 = new Player("p3", "p3", 1, 1, 1, 1, 2){
			@Override
			public TurnAction determineAction(Order order, Integer randomAction, List<Enemy> unitList) {
				return null;
			}

			@Override
			public Bitmap getImage(Context context) {
				return null;
			}
		};
		turnActionList.add(new TurnAction(unit1, null ,null));
		turnActionList.add(new TurnAction(unit2, null ,null));
		turnActionList.add(new TurnAction(unit3, null ,null));

		final List<TurnAction> sortedAgiList = new AgilityManager(turnActionList).calc();

		assertThat(sortedAgiList.get(0).getActUnit().getAgility(), is(3));
		assertThat(sortedAgiList.get(1).getActUnit().getAgility(), is(2));
		assertThat(sortedAgiList.get(2).getActUnit().getAgility(), is(1));
	}
}