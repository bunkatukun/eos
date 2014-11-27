package jp.bunkatusoft.explorersofsettlement.system;

/**
 * Created by m_kagaya on 2014/11/21.
 */
public enum SystemMenuEnum {
	save(0),
	load(1),
	save_and_load(2),
	settings(3),
	achievements(4),
	version_info(5),
	quit(6);

	int mPhase;

	SystemMenuEnum(int phase){
		this.mPhase = phase;
	}

	public int getPhaseValue(){
		return mPhase;
	}

	public static SystemMenuEnum getEnum(int phase) {
		SystemMenuEnum[] enumArray = SystemMenuEnum.values();
		for (SystemMenuEnum singleEnum : enumArray){
			if(phase == new Integer(singleEnum.mPhase)){
				return singleEnum;
			}
		}
		return null;
	}
}
