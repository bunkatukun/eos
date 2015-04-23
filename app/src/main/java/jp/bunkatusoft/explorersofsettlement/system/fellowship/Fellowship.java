package jp.bunkatusoft.explorersofsettlement.system.fellowship;

import java.util.List;

import jp.bunkatusoft.explorersofsettlement.system.actor.Actor;

public abstract class Fellowship {
	public int id;
	public AffiliationEnum affiliation;
	public List<Actor> memberList;

	public abstract void addMember(Actor actor);
	public abstract void removeMember(Actor actor);
}
