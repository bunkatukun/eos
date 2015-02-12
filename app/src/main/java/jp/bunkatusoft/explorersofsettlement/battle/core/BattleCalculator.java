package jp.bunkatusoft.explorersofsettlement.battle.core;

public class BattleCalculator {
	/**
	 * @param from	攻撃者の攻撃力と技で計算した攻撃系数
	 * @param to	防御者の防御力と技で計算した防御系数
	 */
	protected static int calcDamage(int from, int to) {
		int result = from - to / 2;
		return result < 0 ? 0 : result;
	}
}
