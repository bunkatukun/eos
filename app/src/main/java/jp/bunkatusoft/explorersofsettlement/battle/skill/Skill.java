package jp.bunkatusoft.explorersofsettlement.battle.skill;

/**
 * 戦闘ユニットが実行することができる技はこのクラスを継承する
 */
public abstract class Skill {
	private String name;

	protected Skill(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract int getAttack();
}
