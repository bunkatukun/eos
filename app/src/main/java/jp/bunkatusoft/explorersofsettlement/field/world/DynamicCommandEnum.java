package jp.bunkatusoft.explorersofsettlement.field.world;

import jp.bunkatusoft.explorersofsettlement.R;

public enum DynamicCommandEnum {
	ENTER(R.drawable.icon_enter),
	MINE(R.drawable.icon_mining),
	GATHER(R.drawable.icon_gather),
	FISHING(R.drawable.icon_wip),
	ACTIVATE(R.drawable.icon_wip);

	private int mCommand;

	DynamicCommandEnum(int command){
		mCommand = command;
	}

	public int getCommandResourceId(){
		return mCommand;
	}
}
