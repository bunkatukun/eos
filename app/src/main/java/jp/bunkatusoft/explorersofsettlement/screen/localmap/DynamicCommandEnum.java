package jp.bunkatusoft.explorersofsettlement.screen.localmap;

import jp.bunkatusoft.explorersofsettlement.R;

public enum DynamicCommandEnum {
	INN(R.drawable.icon_inn02),
	TAVERN(R.drawable.icon_tavern02),
	MARKET(R.drawable.icon_wip),
	BLACKSMITH(R.drawable.icon_forging),
	FOUNTAIN(R.drawable.icon_wip),
	ALLEY(R.drawable.icon_alley02),
	HOUSE(R.drawable.icon_wip);

	private int mCommand;

	DynamicCommandEnum(int command){
		mCommand = command;
	}

	public int getCommandResourceId(){
		return mCommand;
	}
}
