package cn.robotium.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Time;

/**
 * 取得当前时间并且转化为字符串
 * @author Administrator
 *
 */
public class AndTimeUtils {
	
	public static String getCurrentTimeToString() {
//		Time time = new Time();
//		time.setToNow();
//		int year = time.year;
//		int month = time.month+1;
//		int day = time.monthDay;
//		int hour = time.hour;
//		int minute = time.minute;
//		int second = time.second;
//		return time.toString();
//		String timeStr = month+"-"+day+" "+hour+":"+minute;
//		return new TimeBean(year, month, day, hour, minute, second).toString();
//		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyy年MM月dd日 HH:mm:ss");       
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间        
		String str = formatter.format(curDate); 
		return str;
	}
	public static String getCurrentTimeForFile(){
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH-mm-ss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间        
		String str = formatter.format(curDate); 
		return str;
	}
}

class TimeBean{
	int year;
	int month;
	int day;
	int hour;
	int minute;
	int second;
	
	public TimeBean(){
	}
	
	public TimeBean(int year, int month, int day, int hour, int minute,
			int second) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	@Override
	public String toString() {
		return "TimeBean [year=" + year + ", month=" + month + ", day=" + day
				+ ", hour=" + hour + ", minute=" + minute + ", second="
				+ second + "]";
	}
}
