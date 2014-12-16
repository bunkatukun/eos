package jp.bunkatusoft.explorersofsettlement.time;

/**
 * Enum
 */
public enum GameMonthEnum {
	//TODO 独自定義の月名と差し替え
	JANUARY(1),
	FEBRUARY(2),
	MARCH(3),
	APRIL(4),
	MAY(5),
	JUNE(6),
	JULY(7),
	AUGUST(8),
	SEPTEMBER(9),
	OCTOBER(10),
	NOVEMBER(11),
	DECEMBER(12);

	int month;

	GameMonthEnum(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public static GameMonthEnum valueOf(int month) {
		GameMonthEnum[] enumArray = GameMonthEnum.values();
		for (GameMonthEnum singleEnum : enumArray) {
			if (month == singleEnum.month) {
				return singleEnum;
			}
		}
		return null;
	}
}