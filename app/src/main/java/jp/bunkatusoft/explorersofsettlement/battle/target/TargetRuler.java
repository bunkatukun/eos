package jp.bunkatusoft.explorersofsettlement.battle.target;

import java.util.List;
import java.util.Random;

import jp.bunkatusoft.explorersofsettlement.battle.unit.Unit;

public enum TargetRuler {
	/**
	 * ランダムに選択
	 */
	RANDOM {
		@Override
		public Unit determine(List<Unit> playerList) {
			return playerList.get(new Random().nextInt(playerList.size()));
		}
	},
	/**
	 * 一番HPが弱っているターゲットを選択
	 */
	WEAKEST {
		@Override
		public Unit determine(List<Unit> playerList) {
			return new Weakest().determine(playerList);
		}
	};

	public abstract Unit determine(List<Unit> playerList);
}
