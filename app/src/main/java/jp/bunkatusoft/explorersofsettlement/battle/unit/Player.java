package jp.bunkatusoft.explorersofsettlement.battle.unit;

import java.util.List;

import jp.bunkatusoft.explorersofsettlement.battle.Order;
import jp.bunkatusoft.explorersofsettlement.battle.core.TurnAction;

public abstract class Player extends Unit {
	public Player(String id, String name, int lifePoint,
				  int actionPoint, int attack, int guard, int agility) {
		super(id, name, lifePoint, actionPoint, attack, guard, agility, Relation.PLAYER);
	}

	public abstract TurnAction determineAction(Order order, Integer randomAction, List<Enemy> unitList);
}
