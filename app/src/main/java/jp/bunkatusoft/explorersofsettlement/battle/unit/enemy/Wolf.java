package jp.bunkatusoft.explorersofsettlement.battle.unit.enemy;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.battle.core.TurnAction;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Enemy;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Player;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Unit;
import jp.bunkatusoft.explorersofsettlement.battle.skill.Skill;
import jp.bunkatusoft.explorersofsettlement.battle.skill.Attack;
import jp.bunkatusoft.explorersofsettlement.battle.skill.CriticalAttack;
import jp.bunkatusoft.explorersofsettlement.battle.target.TargetRuler;
import jp.bunkatusoft.explorersofsettlement.util.Util;

/**
 * 狼
 */
public class Wolf extends Enemy {
	private static final String NAME = "Wolf";
	private static final int LIFE_POINT = 10;
	private static final int ACTION_POINT = 3;
	private static final int ATTACK_POINT = 3;
	private static final int GUARD_POINT = 3;
	private static final int AGILITY_POINT = 10;

	private static final int IMAGE = R.drawable.wolf;

	public Wolf(String id) {
		super(id, NAME, LIFE_POINT, ACTION_POINT, ATTACK_POINT, GUARD_POINT, AGILITY_POINT);
	}

	@Override
	public Bitmap getImage(Context context) {
		return Util.loadResourceBitmapImage(context, IMAGE);
	}

	public enum Action implements EnemyAction {
		BITE(new Attack("噛み付く", ATTACK_POINT)),
		CRITICAL_BITE(new CriticalAttack("急所に噛み付く", ATTACK_POINT, 1.5f));

		@Override
		public Skill getSkill() {
			return skill;
		}

		Skill skill;

		Action(Skill skill) {
			this.skill = skill;
		}
	}

	@Override
	public TurnAction determineAction(Integer randomAction, List<Player> unitList) {
		if (randomAction < 90) {
			return new TurnAction(this, Wolf.Action.BITE.getSkill(),
					TargetRuler.RANDOM.determine(new ArrayList<Unit>(unitList)));
		} else {
			return new TurnAction(this, Wolf.Action.CRITICAL_BITE.getSkill(),
					TargetRuler.WEAKEST.determine(new ArrayList<Unit>(unitList)));
		}
	}
}
