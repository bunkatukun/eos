package jp.bunkatusoft.explorersofsettlement.battle.core;

import java.util.Random;

public class BattleUtil {
	/**
	 * @return 0 - 99までのランダムな値
	 */
	protected static int calcActionValue() {
		return new Random().nextInt(100);
	}
}
