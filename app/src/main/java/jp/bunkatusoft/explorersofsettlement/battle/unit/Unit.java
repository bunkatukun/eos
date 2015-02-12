package jp.bunkatusoft.explorersofsettlement.battle.unit;

import android.content.Context;
import android.graphics.Bitmap;

import jp.bunkatusoft.explorersofsettlement.battle.skill.Skill;

/**
 * 戦闘を行うすべてのユニットはこのクラスを継承する
 */
public abstract class Unit {
	private String id;
	private String name;
	private int lifePoint;
	private int actionPoint;
	private int attack;
	private int guard;
	private int agility;
	private Relation relation;

	public Unit(String id, String name, int lifePoint,
				int actionPoint, int attack, int guard, int agility, Relation relation) {
		this.id = id;
		this.name = name;
		this.lifePoint = lifePoint;
		this.actionPoint = actionPoint;
		this.attack = attack;
		this.guard = guard;
		this.agility = agility;
		this.relation = relation;
	}

	public String getId() {
		return id;
	}

	public void damage(int damage) {
		lifePoint -= damage;
	}

	public boolean isDead() {
		return lifePoint <= 0;
	}

	public int getAgility() {
		return agility;
	}

	public String getName() {
		return name;
	}

	public int getLifePoint() {
		return lifePoint;
	}

	public int getActionPoint() {
		return actionPoint;
	}

	public int getAttack() {
		return attack;
	}

	public int getGuard() {
		return guard;
	}

	public Relation getRelation() {
		return relation;
	}

	public interface EnemyAction {
		public Skill getSkill();
	}

	public abstract Bitmap getImage(Context context);
}
