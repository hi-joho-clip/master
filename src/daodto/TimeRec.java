package daodto;
import java.util.Calendar;
public class TimeRec{

	private String datetime = null;

	public String insertDate(java.util.Date ult_date){
		 Calendar cal1 = Calendar.getInstance();  //(1)オブジェクトの生成
		 cal1.setTime(ult_date);

		    int year = cal1.get(Calendar.YEAR);        //(2)現在の年を取得
		    int month = cal1.get(Calendar.MONTH) + 1;  //(3)現在の月を取得
		    int day = cal1.get(Calendar.DATE);         //(4)現在の日を取得
		    int hour = cal1.get(Calendar.HOUR_OF_DAY); //(5)現在の時を取得
		    int minute = cal1.get(Calendar.MINUTE);    //(6)現在の分を取得
		    int second = cal1.get(Calendar.SECOND);    //(7)現在の秒を取得
		    String ms = "0";
		    String datetime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + "." + ms;

		    return datetime;
	}
}

