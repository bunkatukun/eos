package jp.bunkatusoft.explorersofsettlement.battle.unit;


import java.util.List;

import jp.bunkatusoft.explorersofsettlement.battle.core.TurnAction;

public abstract class Enemy extends Unit {

	public Enemy(String id, String name, int lifePoint, int actionPoint, int attack, int guard,
				 int agility) {
		super(id, name, lifePoint, actionPoint,attack, guard, agility, Relation.ENEMY);
	}

	/**
	 * 行動の選択方法とターゲット選択対象を実装する
	 */
	public abstract TurnAction determineAction(Integer randomAction, List<Player> unitList);
}
