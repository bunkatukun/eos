package jp.bunkatusoft.explorersofsettlement.system;

import java.util.NoSuchElementException;

// TODO もっといい名前に変更
public enum ButtonGroupEnum {
	ENTER(0),
	MEMBERS(1),
	ITEMS(2),
	INFO(3),
	ACTIONS(4),
	LEAVE(5),

	LOAD(6),
	SAVE(7),
	CONFIGS(8),
	RETURN_TITLE(9);

	private int value;

	ButtonGroupEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static SystemMenuEnum valueOf(int phase) {
		SystemMenuEnum[] enumArray = SystemMenuEnum.values();
		for (SystemMenuEnum singleEnum : enumArray) {
			if (phase == singleEnum.phase) {
				return singleEnum;
			}
		}
		throw new NoSuchElementException();
	}
}
