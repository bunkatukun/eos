package jp.bunkatusoft.explorersofsettlement.field;

import jp.bunkatusoft.explorersofsettlement.time.GameMonthEnum;
import jp.bunkatusoft.explorersofsettlement.time.GameTimeEnum;

/**
 * 内部時間
 */
public class GameTime {
	/**
	 * 日付の最小値
	 */
	public static final int DAY_MIN = 1;
	/**
	 * 日付の最大値
	 */
	public static final int DAY_MAX = 30;

	private int year;
	private GameMonthEnum month;
	private int day;
	private GameTimeEnum time;

	/**
	 * コンストラクタ
	 */
	GameTime() {
		this(1,GameMonthEnum.JANUARY,1,GameTimeEnum.MIDNIGHT);
	}

	/**
	 * コンストラクタ
	 */
	GameTime(int year, GameMonthEnum month, int day, GameTimeEnum time){
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
	}

	/**
	 * 時刻を再設定する
	 *
	 * @param year  年
	 * @param month 月
	 * @param day   日付
	 * @param time  時間帯
	 */
	public void resetTime(int year, GameMonthEnum month, int day, GameTimeEnum time) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
	}

	/**
	 * 「年」を取得する
	 *
	 * @return int : 年
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * 「年」をインクリメントする
	 */
	public void incrementYear() {
		this.year++;
	}

	/**
	 * 「月」を取得する
	 *
	 * @return GameMonthEnum : 月
	 */
	public GameMonthEnum getMonth() {
		return this.month;
	}

	/**
	 * 「月」をインクリメントする
	 * 上限に達している場合「年」がインクリメントされ、「月」の値は先頭へ戻る
	 */
	public void incrementMonth() {
		GameMonthEnum nextMonth = GameMonthEnum.valueOf(this.month.getMonth() + 1);
		if (nextMonth == null) {
			incrementYear();
			this.month = GameMonthEnum.JANUARY;
		} else {
			this.month = nextMonth;
		}
	}

	/**
	 * 「日付」を取得する
	 *
	 * @return int : 日付
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * 「日付」をインクリメントする
	 * 上限に達している場合「月」がインクリメントされ、「日付」の値は先頭へ戻る
	 */
	public void incrementDay() {
		if (this.day >= DAY_MAX) {
			incrementMonth();
			this.day = DAY_MIN;
		} else {
			this.day++;
		}
	}

	/**
	 * 「時間帯」を取得する
	 *
	 * @return GameTimeEnum : 時間帯
	 */
	public GameTimeEnum getTime() {
		return this.time;
	}

	/**
	 * 「時間帯」をインクリメントする
	 * 上限に達している場合「日付」がインクリメントされ、「時間帯」の値は先頭へ戻る
	 */
	public void incrementTime() {
		GameTimeEnum nextTime = GameTimeEnum.valueOf(this.time.getTime() + 1);
		if (nextTime == null) {
			incrementDay();
			this.time = GameTimeEnum.MIDNIGHT;
		} else {
			this.time = nextTime;
		}
	}
}
