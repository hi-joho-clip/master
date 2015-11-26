package daodto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateEncode {

	/**
	 * データベースから取得した日付文字列をjava.util.Date型へ変換
	 * @param str
	 * @return
	 * @throws java.text.ParseException
	 */
	public static java.util.Date toDate(String str) throws java.text.ParseException {

		java.util.Date date = null;
		if (str != null) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = format.parse(str);
		}
		return date;
	}


	/**
	 * java.util.DateをStringへ変換
	 * @param ult_date
	 * @return
	 */
	public static String insertDate(java.util.Date ult_date) {
		Calendar cal1 = Calendar.getInstance(); //(1)オブジェクトの生成
		cal1.setTime(ult_date);
		String datetime = null;
		int year = cal1.get(Calendar.YEAR); //(2)現在の年を取得
		int month = cal1.get(Calendar.MONTH) + 1; //(3)現在の月を取得
		int day = cal1.get(Calendar.DATE); //(4)現在の日を取得
		int hour = cal1.get(Calendar.HOUR_OF_DAY); //(5)現在の時を取得
		int minute = cal1.get(Calendar.MINUTE); //(6)現在の分を取得
		int second = cal1.get(Calendar.SECOND); //(7)現在の秒を取得
		String ms = "0";
		datetime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + "." + ms;

		return datetime;
	}

	public java.sql.Date getNow() {
		Calendar cal = Calendar.getInstance();
		java.util.Date util_date = cal.getTime();
		java.sql.Date sql_date = DateToSQL(util_date);
		return sql_date;

	}

	/**
	 * @param args
	 */
	public java.sql.Date DateToSQL(java.util.Date util_date) {

		java.util.Date d = util_date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date d2 = new java.sql.Date(cal.getTimeInMillis());
		return d2;
	}

	/**
	 *
	 */
	public java.util.Date DateToUTIL(java.sql.Date sql_date) {

		java.util.Date d = sql_date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		java.sql.Time t = new java.sql.Time(cal.getTimeInMillis());

		return t;

	}
}
