package jp.bunkatusoft.explorersofsettlement.system;

import java.util.NoSuchElementException;

import jp.bunkatusoft.explorersofsettlement.R;

// TODO もっといい名前に変更
public enum ButtonGroupEnum {
	ENTER(R.string.world_command_enter),
	MEMBERS(R.string.world_command_members),
	ITEMS(R.string.world_command_inventory),
	INFO(R.string.world_command_info),
	ACTIONS(R.string.world_command_action),
	LEAVE(R.string.world_command_leave);

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
