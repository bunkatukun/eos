package jp.bunkatusoft.explorersofsettlement.time;

/**
 * 時間帯のEnum
 */
public enum GameTimeEnum {
	MIDNIGHT(0),
	MORNING(2),
	NOON(3),
	AFTERNOON(4),
	EVENING(5),
	NIGHT(6);

	int time;

	GameTimeEnum(int time) {this.time = time;}

	public int getTime(){return time;}

	public static GameTimeEnum valueOf(int time) throws NoSuchTimeException {
		GameTimeEnum[] enumArray = GameTimeEnum.values();
		for(GameTimeEnum singleEnum : enumArray){
			if(time == singleEnum.time){
				return singleEnum;
			}
		}
		throw new NoSuchTimeException();
	}
}
