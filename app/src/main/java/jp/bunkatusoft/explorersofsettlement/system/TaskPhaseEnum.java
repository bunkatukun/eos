package jp.bunkatusoft.explorersofsettlement.system;

/**
 * Created by m_kagaya on 2015/01/28.
 */
public enum TaskPhaseEnum {
	INITIALIZE(0),
	STARTING(1),
	RUNNING(2),
	STOPPING(3),
	FINALIZE(4),
	END(5);

	int phase;

	TaskPhaseEnum(int phase) {
		this.phase = phase;
	}

	public int getPhase() {
		return phase;
	}

	public static SystemMenuEnum valueOf(int phase) {
		SystemMenuEnum[] enumArray = SystemMenuEnum.values();
		for (SystemMenuEnum singleEnum : enumArray) {
			if (phase == singleEnum.phase) {
				return singleEnum;
			}
		}
		return null;
	}
}
