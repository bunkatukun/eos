package jp.bunkatusoft.explorersofsettlement.battle.core;

import jp.bunkatusoft.explorersofsettlement.battle.unit.Unit;
import jp.bunkatusoft.explorersofsettlement.battle.skill.Skill;

/**
 * 1ターンの行動情報
 */
public class TurnAction {
	private Unit actUnit;
	private Skill skill;
	private Unit targetUnit;

	public TurnAction(Unit actUnit, Skill skill, Unit targetUnit) {
		this.actUnit = actUnit;
		this.skill = skill;
		this.targetUnit = targetUnit;
	}

	public Unit getActUnit() {
		return actUnit;
	}

	public Skill getSkill() {
		return skill;
	}

	public Unit getTargetUnit() {
		return targetUnit;
	}
}
