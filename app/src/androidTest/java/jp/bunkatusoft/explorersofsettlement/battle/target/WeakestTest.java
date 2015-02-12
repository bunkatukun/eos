package jp.bunkatusoft.explorersofsettlement.battle.target;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.battle.Order;
import jp.bunkatusoft.explorersofsettlement.battle.core.TurnAction;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Enemy;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Unit;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class WeakestTest {
	@Test
	public void 一番HPが低いユニットを選択する() {
		List<Unit> unitList = new ArrayList<Unit>();
		unitList.add(new Player("", "p1", 20, 1, 1, 1, 1) {
			@Override
			public TurnAction determineAction(Order order, Integer randomAction, List<Enemy> unitList) {
				return null;
			}

			@Override
			public Bitmap getImage(Context context) {
				return null;
			}
		});
		unitList.add(new Player("", "p2", 10, 1, 1, 1, 1) {
			@Override
			public TurnAction determineAction(Order order, Integer randomAction, List<Enemy> unitList) {
				return null;
			}

			@Override
			public Bitmap getImage(Context context) {
				return null;
			}
		});
		unitList.add(new Player("", "p3", 30, 1, 1, 1, 1) {
			@Override
			public TurnAction determineAction(Order order, Integer randomAction, List<Enemy> unitList) {
				return null;
			}

			@Override
			public Bitmap getImage(Context context) {
				return null;
			}
		});

		Unit actual = new Weakest().determine(unitList);

		assertThat(actual.getLifePoint(), is(10));
	}
}