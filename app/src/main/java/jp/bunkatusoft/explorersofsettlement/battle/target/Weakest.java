package jp.bunkatusoft.explorersofsettlement.battle.target;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.battle.unit.Unit;
import jp.bunkatusoft.explorersofsettlement.util.Util;

/**
 * LifePointが一番低いユニットを狙う
 */
public class Weakest {
	/**
	 * @param unitList	攻撃対象リスト
	 */
	protected Unit determine(List<Unit> unitList) {
		if (unitList.size() == 0) {
			throw new IllegalArgumentException();
		}

		Collections.sort(unitList, new UnitLifePointComp());
		return unitList.get(0);
	}

	private class UnitLifePointComp implements Comparator<Unit> {
		@Override
		public int compare(Unit lhs, Unit rhs) {
			return Util.intCompare(lhs.getLifePoint(), rhs.getLifePoint());
		}
	}
}
