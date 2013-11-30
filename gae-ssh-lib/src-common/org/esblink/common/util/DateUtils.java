package org.esblink.common.util;
 
 import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.esblink.dev.util.CommMethod;
 
 public class DateUtils
 {
   private static Log log = LogFactory.getLog(DateUtils.class);
 
   private static final String[] FORMATS = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "HH:mm", "HH:mm:ss", "yyyy-MM" };
   
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
 
   public static Date convert(String str)
   {
     if ((str != null) && (str.length() > 0)) {
       if ((str.length() > 10) && (str.charAt(10) == 'T'))
         str = str.replace('T', ' ');
       for (String format : FORMATS) {
         if (str.length() == format.length()) {
           try {
             log.debug("convert " + str + " to date format " + format);
 
             Date date = new SimpleDateFormat(format).parse(str);
             log.debug("****" + date + "****");
             return date;
           } catch (ParseException e) {
             log.warn(e.getMessage());
           }
         }
       }
     }
     return null;
   }
 
   public static String UDateToString(Date udate, String format)
   {
     String sdate = null;
     try {
       sdate = new SimpleDateFormat(format).format(udate);
     } catch (Exception e) {
       log.error("Can not convert [java.util.date] to [java.lang.String]. [FORMAT]:" + format, e);
     }
 
     return sdate;
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
 }


 
 
