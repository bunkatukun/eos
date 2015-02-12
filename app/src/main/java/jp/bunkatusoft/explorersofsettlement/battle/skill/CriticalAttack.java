package jp.bunkatusoft.explorersofsettlement.battle.skill;

/**
 * クリティカル攻撃。
 * 通常攻撃より少し強い攻撃。
 * <p/>
 * 倍率は外部から指定。
 */
public class CriticalAttack extends Attack {
	public CriticalAttack(String name, int attack, float magnification) {
		super(name, (int) (attack * magnification));
	}
}
