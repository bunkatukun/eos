package jp.bunkatusoft.explorersofsettlement.battle.core;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.battle.Order;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Enemy;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Player;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;

/**
 * 戦闘の進行を管理するクラス
 */
public class Battle {
	private int mTurn;

	private final List<Player> mPlayerList;
	private final List<Enemy> mEnemyList;

	private boolean mBattleEnd = false;

	public Battle(final List<Player> playerList, final List<Enemy> enemyList) {
		mPlayerList = playerList;
		mEnemyList = enemyList;
	}

	public void exec() {
		LogUtil.i("戦闘開始");

		for (int i = 0; i < BattleConfig.MAX_TURN; i++) {
			during();

			if (mBattleEnd) {
				LogUtil.i("戦闘終了");
				return;
			}
		}
	}

	public BattleResult getResult() {
		if (!mBattleEnd) {
			throw new IllegalAccessError();
		}

		return new BattleResult(mTurn);
	}

	private void during() {
		mTurn++;
		LogUtil.i("mTurn :" + mTurn);

		List<TurnAction> turnActionList = new ArrayList<TurnAction>();
		//TODO プレイヤーの方針をコマンド選択 今決めうち
		Order order = Order.ATTACK;

		// プレイヤーサイドの行動選択選択
		for (Player player : mPlayerList) {
			TurnAction playerAction =
					player.determineAction(order, BattleUtil.calcActionValue(), mEnemyList);
			turnActionList.add(playerAction);
		}

		// 敵サイドの行動選択
		for (Enemy enemy : mEnemyList) {
			TurnAction enemyAction =
					enemy.determineAction(BattleUtil.calcActionValue(), mPlayerList);
			turnActionList.add(enemyAction);
		}

		// 素早い順に行動
		final List<TurnAction> sortedAgiList = new AgilityManager(turnActionList).calc();

		for (TurnAction turnAction : sortedAgiList) {
			LogUtil.i("攻撃 " + turnAction.getActUnit().getName() + " -> " + turnAction.getTargetUnit().getName());

			// 戦闘計算
			int damage = BattleCalculator.calcDamage(turnAction.getSkill().getAttack(),
					turnAction.getTargetUnit().getGuard());

			// HPから減らす処理
			turnAction.getTargetUnit().damage(damage);

			LogUtil.i("ダメージ: " + damage + " 残りLP:" + turnAction.getTargetUnit().getLifePoint());

			// 死亡確認
			if (turnAction.getTargetUnit().isDead()) {
				LogUtil.i(turnAction.getTargetUnit().getName() + " is dead.");
				//TODO どちらか一方が全滅したか確認する処理を入れる

				//FIXME
				mBattleEnd = true;
				return;
			}
		}
	}
}
