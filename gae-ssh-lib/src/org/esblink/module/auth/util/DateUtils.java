package org.esblink.module.auth.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.esblink.dev.util.CommMethod;



/*
 * 日期辅助类
 */
public class DateUtils {
	/**
	 * @deprecated
	 */
	public final static String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	/**
	 * @deprecated
	 */
	public final static String DATE_TIME24_ORACLE_FORMAT="yyyy-mm-dd hh24:mi:ss";
	/**
	 * @deprecated
	 */
	public final static String DATE_FORMAT="yyyy-MM-dd";
	public final static String FORMAT_DATE_TIME="yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_DATE_TIME24_ORACLE="yyyy-mm-dd hh24:mi:ss";
	public final static String FORMAT_DATE="yyyy-MM-dd";
	public final static String FORMAT_TIME="HH:mm:ss";
	//日期DATA加减得分钟
	public static final Long minuteAdd(Date startDate, Date endDate){
		Long minute=(endDate.getTime()-startDate.getTime())/60000;
		return minute;
		
	}
	
	/**
	 * 时间计算
	 * @param startDate
	 * @param endDate
	 * @param dateType java.util.Calendar.SECOND/MINUTE/HOUR/DATE
	 * @return
	 */
	public static final Long intervalDate(Date startDate, Date endDate, int dateType){
		int intTime=1;
		if(dateType==java.util.Calendar.SECOND){
			intTime=1000;
		}
		else if(dateType==java.util.Calendar.MINUTE){
			intTime=60000;
		}
		else if(dateType==java.util.Calendar.HOUR){
			intTime=3600000;
		}
		else if(dateType==java.util.Calendar.DATE){
			intTime=24*3600000;
		}
		if(startDate==null || endDate==null){
			return 0l;
		}
		
		Long minute=(endDate.getTime()-startDate.getTime())/intTime;
		return minute;
		
	}
	
	//日期加秒
	public static final Date dateAddSecond(Date d, Long milliSecond){
		java.util.Calendar cla = java.util.Calendar.getInstance();
		if(d!=null){
			cla.setTime(d);
			cla.add(java.util.Calendar.SECOND,  milliSecond.intValue());
		}
		else{
			return null;
		}
		return cla.getTime();
	}
	//日期加分钟
	public static final Date dateAddMinute(Date d, Long minute){
		java.util.Calendar cla = java.util.Calendar.getInstance();
		if(d!=null){
			cla.setTime(d);
			cla.add(java.util.Calendar.MINUTE,  minute.intValue());
		}
		else{
			return null;
		}
		return cla.getTime();
	}
	//日期加月
	public static final Date dateAddMonth(Date d, Long month){
		java.util.Calendar cla = java.util.Calendar.getInstance();
		if(d!=null){
			cla.setTime(d);
			cla.add(java.util.Calendar.MONTH,  month.intValue());
		}
		else{
			return null;
		}
		return cla.getTime();
	}
	
	public static final Date reduceDay(Date d, Long days){
		java.util.Calendar cla = java.util.Calendar.getInstance();
		if(d!=null){
			cla.setTime(d);
			cla.add(java.util.Calendar.DAY_OF_YEAR,  -days.intValue());
		}
		else{
			return null;
		}
		return cla.getTime();
	}
	
	public static final Date reduceHour(Date d, Long hours){
		java.util.Calendar cla = java.util.Calendar.getInstance();
		if(d!=null){
			cla.setTime(d);
			cla.add(java.util.Calendar.HOUR,  -hours.intValue());
		}
		else{
			return null;
		}
		return cla.getTime();
		
	}
	
	public static final Date reduceMinute(Date d, Long minute){
		java.util.Calendar cla = java.util.Calendar.getInstance();
		if(d!=null){
			cla.setTime(d);
			cla.add(java.util.Calendar.MINUTE,  -minute.intValue());
		}
		else{
			return null;
		}
		return cla.getTime();
		
	}
	
	public static int daysOfTwo(Date beginDate, Date endDate) {		
//	       Calendar aCalendar = Calendar.getInstance();
//	       aCalendar.setTime(beginDate);
//	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
//	       int year1 = aCalendar.get(Calendar.YEAR);
//	       aCalendar.setTime(endDate);
//	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);	       
//	       int year2 = aCalendar.get(Calendar.YEAR);
//	       int year = year2-year1;	       
//	       return year*365+day2 - day1;
		Long days= (endDate.getTime()-beginDate.getTime())/(3600000*24);
		return days.intValue();
	}


	
	/**
	 *注意formats数组中的字符串顺序,因yyyy-MM的格式能匹配yyyy-MM-dd的格式 
	 */
	private static final String[] formats = new String[]{
		"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM",
	};
	
	public static final SimpleDateFormat[] dateFormats = new SimpleDateFormat[formats.length];
	
	static {
		for (int i = 0; i < formats.length; i++) {
			dateFormats[i] = new SimpleDateFormat(formats[i]);
		}
	}
	
	/**
	 * 返回格式为yyyy-MM-dd的字符串
	 */
	public static String formatShort(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	
	public static String format(Date date){
		if(date==null){
			return null;
		}
		return formatShort(date);
	}
	
	/**
	 * calendar格式化，转成String
	 * 
	 * @param d
	 * @param expstr
	 *            ,日历正则表达式,如:MM-dd HH:mm 如果expstr为空，默认为MM-dd HH:mm
	 * @return
	 */
	public static String format(Calendar d, String expstr) {
		if (d == null) {
			return "";
		}
		if (CommMethod.isEmpty(expstr)) {
			expstr = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(expstr);
		java.util.Date date = new java.util.Date();
		date.setTime(d.getTimeInMillis());
		return sdf.format(date);
	}
	
	/**
	 * time格式化，转成String
	 * 
	 * @param time
	 * @return格式00:00:00
	 */
	public static String format(Time time) {
		if (time != null) {
			return time.toString();
		}
		return null;
	}
	
	/**
	 * String转Date 依据expstr的格式将字符串datestr转成Date
	 * 
	 * @param datestr
	 * @param expstr日期正则表达式如
	 *            :MM-dd HH:mm\yyyy-MM-dd, 如果expstr为空，默认为yyyy-MM-dd
	 * @return
	 */
	public static Date parseDate(String datestr, String expstr)
    {
        Date date = null;
        if(CommMethod.isEmpty(expstr))
            expstr = "yyyy-MM-dd";
        if(datestr == null || datestr.equals(""))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(expstr);
        try
        {
            date = sdf.parse(datestr);
        }
        catch(Exception e)
        {
            e.printStackTrace();          
        }
        return date;
    }
	
	/**
	 * 
	 * @deprecated
     * date格式化，转成String
     * @param d
     * @param expstr,日期正则表达式,如:MM-dd HH:mm\yyyy-MM-dd,
     * 如果expstr为空，默认为yyyy-MM-dd
     * @return
     */
    public static String formatDate(Date date, String expstr) {
//	    if (date == null) {
//	      return "";
//	    }
//	    if(CommMethod.isEmpty(expstr)){
//	    	expstr = "yyyy-MM-dd";
//	    }
//	    SimpleDateFormat sdf = new SimpleDateFormat(expstr);
//	    
//	    return sdf.format(date);
    	return formatDate(date, expstr, null);
	}
    
    public static String formatDate(Date date, String expstr, String timezone) {
	    if (date == null) {
	      return "";
	    }
	    if(CommMethod.isEmpty(expstr)){
	    	expstr = "yyyy-MM-dd";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat(expstr);
	    if(timezone!=null){
	    	sdf.setTimeZone(TimeZone.getTimeZone(timezone));
	    }
	    
	    return sdf.format(date);
	    
	}
	
	/**
	 * 返回格式为yyyy-MM-dd的字符串
	 */
	public static String formatShort(java.sql.Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * 返回格式为yyyy-MM-dd HH:mm:ss的字符串
	 */
	public static String formatLong(Date date) {
		if(date==null){
			return null;
		}
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 返回格式为yyyy-MM-dd HH:mm:ss的字符串
	 */
	public static String formatLong(java.sql.Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * @param source 原日期
	 * @param days 增加的天数
	 * @return 计算后的日期
	 */
	public static Date dateCalculate(Date source, int days) {
		long time = source.getTime() + days * 24L * 60L * 60L *  1000L;
		return new Date(time);
	}
	
	/**
	 * 根据字符串返回对应的日期,日期字符串格式为'yyyy-MM-dd HH:mm:ss,yyyy-MM-dd,yyyy-MM'
	 * 从前到后依次匹配一旦转化成功就返回
	 * 转化失败时返回null
	 */
	public static Date parseDate(String date) {
		Date result = null;
		for (int i = 0; i < dateFormats.length; i++) {
			try {
				result = dateFormats[i].parse(date);
			} catch (Exception e) {
				continue;
			}
			return result;
		}
		return result;
	}
	
	/**
	 * String转Time
	 * 
	 * @param timestr 00:00 (可自动补秒:00) / 00:00:00
	 * @return
	 */
	public static Time parseTime(String timestr) {
		Time time = null;
		
		if (CommMethod.isEmpty(timestr))
			return null;

		if(timestr.length()<"00:00:00".length()){			
			timestr = timestr+":00";
		}
		try {
			time = Time.valueOf(timestr);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return time;
	}
	
	/**
	 * String转Calendar
	 * 
	 * @param calstr
	 *            日历字符串:2006-09-01 12:12:12
	 * @param expstr
	 *            ,日历正则表达式:yyyy-MM-dd HH:mm:ss 如果expstr为空，则默认为yyyy-MM-dd
	 *            HH:mm:ss
	 * @return Calendar
	 */
	public static Calendar parseCalendar(String calstr, String expstr) {
		if (CommMethod.isEmpty(calstr))
			return null;
		if (CommMethod.isEmpty(expstr)) {
			expstr = "yyyy-MM-dd HH:mm:ss";
		}

		Calendar cal = null;
		String format = expstr;
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(calstr);
			cal = Calendar.getInstance();
			cal.setTimeInMillis(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return cal;
	}
	
	/**
	 * 取当前时间
	 * 
	 * @return
	 */
	public static Time getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return Time.valueOf(sdf.format(new Date()));
	}
	
	/**
	 * 取当前时间
	 * 
	 * @return
	 */
	public static String getCurrentDateStr() {
		return formatLong(new Date());
	}
	
	/**
	 * 日期英文格式化，如:'Fed-11,2011'
	 * @param date
	 * @return
	 */
	public static String formatEDate(Date date) {
//		Date date = new Date();
		  SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd,yyyy",Locale.US);
		  String times = sdf.format(date);
		  return times;
		}

	/**
	 * 英文转日期类型
	 * @param date
	 * @param expStr
	 * @return
	 */
	public static Date parseEDate(String date, String expStr) {
		SimpleDateFormat dateFormats = new SimpleDateFormat(expStr);
		Date result = null;
	
		try {
			result = dateFormats.parse(date);
		} catch (Exception e) {
		
		}
		return result;
		
	}
	
	/**
	 * 分钟换成时间格式
	 * 90-->01:30
	 * @param minute
	 * @return
	 */
	public static String getTimeHour(long minute){
		long h = minute/60;
		long m = minute%60;
		String str = "";
		if(h<10){
			str = "0"+h;
		}
		else{
			str = ""+h;
		}
		str = str+":";
		if(m<10){
			str = str+"0"+m;			
		}
		else{
			str = str+m;
		}
		return str;
	}
	
	/**
	 * 时间格式算成分算数
	 * @param timeHour,如01:20
	 * @return
	 */
	public static int getMinute(String timeHour){
		String hour = timeHour.substring(0, timeHour.indexOf(":"));
		String minute = timeHour.substring(timeHour.indexOf(":")+1);
		int h = Integer.parseInt(hour);
		int m = Integer.parseInt(minute);
		return h*60+m;
	}
	
	/**
	 * 根据生日计算年龄可以通过Calendar实现。最简单可以考虑get(Calendar.DAY_OF_YEAR)来简单修正年龄，
	 * 但是遇到生日在闰年的2月29之后，或者今年是闰年的2月29之后可能出现计算不准，误差一天。所以还是老实判断年月日好了。
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay, Date flightDate) throws Exception {
        Calendar cal = Calendar.getInstance();
        if(flightDate!=null){
        	cal.setTime(flightDate);
        }
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }
        return age;
    }

	/**
	 * 时间相差天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long compareToDate(String startDate,String endDate)
	{
		long days = 0;
		try   
		{    
		    Date d1 = DateUtils.parseDate(startDate,"yyyy-MM-dd HH:mm:ss");    
		    Date d2 = DateUtils.parseDate(endDate,"yyyy-MM-dd HH:mm:ss");   
		    
		    long diff = d2.getTime() - d1.getTime();
		    days = diff / (1000 * 60 * 60 * 24);
		}    
		catch (Exception e)    
		{    
			e.printStackTrace();
		} 
		return days;
	}
	
	/**
	 * 时间相差天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long compareToDate(Date startDate,Date endDate){
		long days = 0;
		long diff = endDate.getTime() - startDate.getTime();
	    days = diff / (1000 * 60 * 60 * 24);
	    return days;
	}
	
	/**
	 * 时间相差天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long compareToDateFormat(String startDate,String endDate,String format)
	{
		long days = 0;
		try   
		{    
		    Date d1 = DateUtils.parseDate(startDate,format);    
		    Date d2 = DateUtils.parseDate(endDate,format);   
		    
		    long diff = d2.getTime() - d1.getTime();
		    days = diff / (1000 * 60 * 60 * 24);
		}    
		catch (Exception e)    
		{    
			e.printStackTrace();
		} 
		return days;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(formatLong(dateCalculate(new Date(), 3)));
		System.out.println(formatShort(dateCalculate(new Date(), 3)));
		System.out.println(DateUtils.parseDate(null));
		System.out.println(formatLong(DateUtils.parseDate("2009-12-12 12:12:21")));
//		System.out.println(Long.valueOf(""));
	}
}