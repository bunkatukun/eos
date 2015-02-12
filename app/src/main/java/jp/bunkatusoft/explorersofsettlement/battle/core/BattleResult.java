package jp.bunkatusoft.explorersofsettlement.battle.core;

/**
 * 戦闘の結果
 */
public class BattleResult {
	/**
	 * 経過ターン数
	 */
	private int elapsedTurn;

	public BattleResult(int elapsedTurn) {
		this.elapsedTurn = elapsedTurn;
	}

	public int getElapsedTurn() {
		return elapsedTurn;
	}
}
