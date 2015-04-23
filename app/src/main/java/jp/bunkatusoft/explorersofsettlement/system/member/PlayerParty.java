package jp.bunkatusoft.explorersofsettlement.system.member;

import jp.bunkatusoft.explorersofsettlement.system.actor.Actor;
import jp.bunkatusoft.explorersofsettlement.system.fellowship.Fellowship;

public class PlayerParty extends Fellowship {

	public static final int MAX_MEMBER_NUM = 5;

	@Override
	public void addMember(Actor actor) {
		if (memberList != null
				&& memberList.size() > 0
				&& memberList.size() < MAX_MEMBER_NUM) {
			memberList.add(actor);
		}
	}

	@Override
	public void removeMember(Actor actor) {
		if(memberList != null && memberList.size()>0){
			memberList.remove(actor);
		}
	}
}
