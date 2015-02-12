package jp.bunkatusoft.explorersofsettlement.battle.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.util.Util;

/**
 * 素早さを管理する
 *
 * 判定条件が複雑になりそうなため分割している
 */
public class AgilityManager {
	private List<TurnAction> mTurnActionList;

	public AgilityManager(List<TurnAction> turnActionList1) {
		mTurnActionList = turnActionList1;
	}

	public List<TurnAction> calc(){
		List<TurnAction> turnActionList = mTurnActionList;
		Collections.sort(turnActionList, new UnitAgiComp());
		Collections.reverse(turnActionList);
		return turnActionList;
	}

	private class UnitAgiComp implements Comparator<TurnAction> {
		@Override
		public int compare(TurnAction lhs, TurnAction rhs) {
			return Util.intCompare(lhs.getActUnit().getAgility(), rhs.getActUnit().getAgility());
		}
	}
}
