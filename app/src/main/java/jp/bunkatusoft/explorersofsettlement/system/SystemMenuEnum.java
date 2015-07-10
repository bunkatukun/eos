package jp.bunkatusoft.explorersofsettlement.system;

import java.util.NoSuchElementException;

import jp.bunkatusoft.explorersofsettlement.R;

public enum SystemMenuEnum {
	SAVE(R.string.menu_save),
	LOAD(R.string.menu_load),
	SAVE_AND_LOAD(R.string.menu_save_and_load),
	SETTINGS(R.string.menu_setting),
	ACHIEVEMENTS(R.string.menu_achievements),
	VERSION_INFO(R.string.menu_versioninfo),
	RETURN_TITLE(R.string.menu_return_title),
	DEBUG(R.string.menu_start_debug),
	QUIT(R.string.menu_quit);

	int phase;

	SystemMenuEnum(int phase){
		this.phase = phase;
	}

	public int getPhase(){
		return phase;
	}

	public static SystemMenuEnum valueOf(int phase) {
		SystemMenuEnum[] enumArray = SystemMenuEnum.values();
		for (SystemMenuEnum singleEnum : enumArray){
			if(phase == singleEnum.phase){
				return singleEnum;
			}
		}
		throw new NoSuchElementException();
	}
}
