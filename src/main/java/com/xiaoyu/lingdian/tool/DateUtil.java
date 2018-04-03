package com.xiaoyu.lingdian.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class DateUtil {

	public static String DEFAULT_PATTERN = "yyyy-MM-dd";
	public static String DIR_PATTERN = "yyyy/MM/dd/";
	public static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static String TIMES_PATTERN = "HH:mm:ss";
	public static String NOCHAR_PATTERN = "yyyyMMddHHmmss";
	public static String CHINESE_PATTEN = "yyyy年MM月dd日";
	
	/**
	 * 日期之间的日差
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @return
	 */
	public static Long daysDiff(Date firstDay, Date lastDay) {
		if (firstDay == null || lastDay == null) {
			return null;
		}
		long allDays = (lastDay.getTime() - (firstDay.getTime())) / (1000 * 24 * 60 * 60);

		return allDays;
	}

	/**
	 * 日期之间的年差
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @return
	 */
	public static Long yearDiff(Date firstDay, Date lastDay) {
		if (firstDay == null || lastDay == null)
			return null;
		Calendar first = Calendar.getInstance();
		first.setTime(firstDay);
		Calendar last = Calendar.getInstance();
		last.setTime(lastDay);
		long years = last.get(Calendar.YEAR) - first.get(Calendar.YEAR);
		return years;
	}

	/**
	 * 日期之间的秒差
	 * 
	 * @param date Date
	 * @param date1 Date
	 * @return int
	 */
	public static Long diffDateTime(Date date, Date date1) {
		return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
	}
	
	public static Date addMinute(Date src, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(src);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	public static Date addSecond(Date src, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(src);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}
	
	public static Date addDay(Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		if (dt != null) {
			cal.setTime(dt);
		}
		cal.add(5, days);
		return cal.getTime();
	}

	public static String getToday(String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	public static Date getToday() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	public static String getYesterday(String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		DateFormat df = new SimpleDateFormat(format);
		return df.format(c.getTime());

	}

	public static Date getYesterday() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c.getTime();
	}

	public static Date parseDate(String strDate, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			return getYesterday();
		}
	}

	public static Date parseDefaultDate(String strDate) {
		DateFormat df = new SimpleDateFormat(TIMESTAMP_PATTERN);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			return getYesterday();
		}
	}

	/**
	 * 日期转换为字符串
	 */ 
	public static String formatDate(String format, Date dt) {
		if (dt == null) {
			return "";
		}
		if (StringUtils.isBlank(format)) {
			format = DEFAULT_PATTERN;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(dt);
	}

	public static String formatDefaultDate(Date dt) {
		if (dt == null) {
			return "";
		}
		SimpleDateFormat fmt = new SimpleDateFormat(TIMESTAMP_PATTERN);
		return fmt.format(dt);
	}
	
	/**
	 * 时间戳转换时间字符串
	 * @param timestampString	时间戳
	 * @param formats	时间格式
	 * 
	 * @return
	 */
	public static String timeStampToDate(String timestampString, String formats) {
		String date="";
		if(StringUtils.isNotBlank(timestampString)){
			try {
				Long timestamp = Long.parseLong(timestampString) * 1000;
				date = new SimpleDateFormat(formats).format(new Date(timestamp));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		return date;
	}

	/**
	 * sql Date 转 util Date
	 * 
	 * @param date java.sql.Date日期
	 * @return java.util.Date
	 */
	public static Date parseUtilDate(java.sql.Date date) {
		return date;
	}

	/**
	 * util Date 转 sql Date
	 * 
	 * @param date
	 *            java.sql.Date日期
	 * @return
	 */
	public static java.sql.Date parseSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取星期
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}

	/**
	 * 获取日期(多少号)
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前时间(小时)
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前时间(分)
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取当前时间(秒)
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取当前毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 获得本日星期数,星期一:1,星期日:7
	 * 如果传入null则默认为本日
	 * @return
	 */
	public static int getDayOfWeek(Calendar calendar){
		int today;
		if(calendar!=null){
			today=calendar.get(Calendar.DAY_OF_WEEK);
		}else{
			today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		}
		if(today==1)
			return 7;
		else
			return today-1;
	}
	
	/**
	 * 获取指定时间一周的第一天
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date){
		if(date == null){
			date = getToday();
		}
		int week = getWeek(date);
		
		return addDay(date, 1-week);
	}
	
	/**
	 * 获取unix从1970-01-01 00:00:00开始的秒数
	 * 
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if( null == date ) {
			return 0;
		}		
		return date.getTime()/1000;
	}

}