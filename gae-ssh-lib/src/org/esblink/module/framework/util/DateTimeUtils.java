package org.esblink.module.framework.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

final public class DateTimeUtils {

	private static final Logger logger = Logger.getLogger(DateTimeUtils.class);
	public static final InnerDateFormat YEAR_MONTH = new InnerDateFormat(
			"yyyy-MM");
	public static final InnerDateFormat YEAR_MONTH_DAY = new InnerDateFormat(
			"yyyy-MM-dd");
	public static final InnerDateFormat YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = new InnerDateFormat(
			"yyyy-MM-dd hh:mm:ss");
	public static final InnerDateFormat DEFAULT_FORMAT = YEAR_MONTH_DAY;

	public static String getDateString(Date date, InnerDateFormat format) {
		return DateFormatUtils.format(date, format.getFormat());
	}

	public static Date getDate(String date, InnerDateFormat format) {
		try {
			return DateUtils.parseDate(date,
					new String[] { format.getFormat() });
		} catch (ParseException e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 任何数据如果小于规定范围的最小值则抛异常。 如果不小于规定范围的最小值则其行为由java.util.Calendar类确定。
	 * 
	 * @param year
	 * @param month
	 *            1~12
	 * @param date
	 *            1~31
	 * @param hourOfDay
	 *            0~23
	 * @param minute
	 *            0~59
	 * @param second
	 *            0~59
	 * @return
	 */
	static public Date getDate(int year, int month, int date, int hourOfDay,
			int minute, int second) {
		if (month < 1 || date < 1 || hourOfDay < 0 || minute < 0 || second < 0)
			throw new RuntimeException();
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, date, hourOfDay, minute, second);
		return c.getTime();
	}

	static public Date getDate(int year, int month, int date, int hourOfDay,
			int minute) {
		return getDate(year, month, date, hourOfDay, minute, 0);
	}

	static public Date getDate(int year, int month, int date, int hourOfDay) {
		return getDate(year, month, date, hourOfDay, 0);
	}

	static public Date getDate(int year, int month, int date) {
		return getDate(year, month, date, 0);
	}

	static public Date getDate(int year, int month) {
		return getDate(year, month, 0);
	}

	static public Date getDate(int year) {
		return getDate(year, 0);
	}

	final static private class InnerDateFormat {
		final String format;

		InnerDateFormat(String format) {
			this.format = format;
		}

		String getFormat() {
			return this.format;
		}
	}
}
