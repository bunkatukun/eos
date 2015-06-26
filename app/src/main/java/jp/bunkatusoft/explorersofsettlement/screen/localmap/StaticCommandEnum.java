package jp.bunkatusoft.explorersofsettlement.screen.localmap;

import jp.bunkatusoft.explorersofsettlement.R;

public enum StaticCommandEnum {
	PARTY(R.drawable.icon_members),
	INVENTORY(R.drawable.icon_inventory),
	MOVE(R.drawable.icon_wip);

	private int mCommand;

	StaticCommandEnum(int command){
		mCommand = command;
	}

	public int getCommandResourceId(){
		return mCommand;
	}
}
