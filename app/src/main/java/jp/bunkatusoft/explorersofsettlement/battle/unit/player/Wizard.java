package jp.bunkatusoft.explorersofsettlement.battle.unit.player;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.battle.Order;
import jp.bunkatusoft.explorersofsettlement.battle.core.TurnAction;
import jp.bunkatusoft.explorersofsettlement.battle.skill.Attack;
import jp.bunkatusoft.explorersofsettlement.battle.target.TargetRuler;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Enemy;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Player;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Unit;
import jp.bunkatusoft.explorersofsettlement.util.Util;

/**
 * 兵士
 */
public class Wizard extends Player {
	private static final String NAME = "Wizard";
	private static final int LIFE_POINT = 15;
	private static final int ACTION_POINT = 6;
	private static final int ATTACK_POINT = 3;
	private static final int GUARD_POINT = 6;
	private static final int AGILITY_POINT = 3;

	private static final int IMAGE = R.drawable.wizard_f;

	public Wizard(String id) {
		super(id, NAME, LIFE_POINT, ACTION_POINT, ATTACK_POINT, GUARD_POINT, AGILITY_POINT);
	}

	@Override
	public TurnAction determineAction(Order order, Integer randomAction, List<Enemy> unitList) {
		//FIXME ランダム相手に攻撃しかしない
		return new TurnAction(this, new Attack("攻撃", ATTACK_POINT),
					TargetRuler.RANDOM.determine(new ArrayList<Unit>(unitList)));
	}

	@Override
	public Bitmap getImage(Context context) {
		return Util.loadResourceBitmapImage(context, IMAGE);
	}
}
