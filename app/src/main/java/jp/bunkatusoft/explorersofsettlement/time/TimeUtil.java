package jp.bunkatusoft.explorersofsettlement.time;

import android.content.Context;

import jp.bunkatusoft.explorersofsettlement.R;

/**
 * 時間に関する処理をまとめたい
 */
public class TimeUtil {

	/**
	 * Enum形式の「月」データから、対応するリソーステキストを取得
	 * @param context	コンテキスト
	 * @param month	変換対象の月
	 * @return	対応する文字列
	 */
	public static String getGameMonthName(Context context, GameMonthEnum month) {
		String result = "";
		switch (month) {
			case JANUARY:
				result = context.getString(R.string.month_january);
				break;
			case FEBRUARY:
				result = context.getString(R.string.month_february);
				break;
			case MARCH:
				result = context.getString(R.string.month_march);
				break;
			case APRIL:
				result = context.getString(R.string.month_april);
				break;
			case MAY:
				result = context.getString(R.string.month_may);
				break;
			case JUNE:
				result = context.getString(R.string.month_june);
				break;
			case JULY:
				result = context.getString(R.string.month_july);
				break;
			case AUGUST:
				result = context.getString(R.string.month_august);
				break;
			case SEPTEMBER:
				result = context.getString(R.string.month_september);
				break;
			case OCTOBER:
				result = context.getString(R.string.month_october);
				break;
			case NOVEMBER:
				result = context.getString(R.string.month_november);
				break;
			case DECEMBER:
				result = context.getString(R.string.month_December);
				break;
			default:
				break;
		}
		return result;
	}

	/**
	 * Enum形式の「時間帯」データから、対応するリソーステキストを取得
	 * @param context	コンテキスト
	 * @param time	変換対象の時間帯
	 * @return	対応する文字列
	 */
	public static String getGameTimeName(Context context, GameTimeEnum time) {
		String result = "";
		switch (time) {
			case MIDNIGHT:
				result = context.getString(R.string.time_midnight);
				break;
			case MORNING:
				result = context.getString(R.string.time_morning);
				break;
			case NOON:
				result = context.getString(R.string.time_noon);
				break;
			case AFTERNOON:
				result = context.getString(R.string.time_afternoon);
				break;
			case EVENING:
				result = context.getString(R.string.time_evening);
				break;
			case NIGHT:
				result = context.getString(R.string.time_night);
				break;
			default:
				break;
		}
		return result;
	}
}
