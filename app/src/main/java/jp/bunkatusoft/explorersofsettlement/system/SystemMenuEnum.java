package jp.bunkatusoft.explorersofsettlement.system;

/**
 * Created by m_kagaya on 2014/11/21.
 */
public enum SystemMenuEnum {
	SAVE(0),
	LOAD(1),
	SAVE_AND_LOAD(2),
	SETTINGS(3),
	ACHIEVEMENTS(4),
	VERSION_INFO(5),
	QUIT(6);

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
		return null;
	}
}
