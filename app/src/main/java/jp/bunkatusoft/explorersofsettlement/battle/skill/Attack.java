package jp.bunkatusoft.explorersofsettlement.battle.skill;

/**
 * 攻撃は攻撃力がそのまま適用される
 */
public class Attack extends Skill {
	private int attack;

	public Attack(String name, int attack) {
		super(name);
		this.attack = attack;
	}

	@Override
	public int getAttack() {
		return attack;
	}
}
