package com.zexi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.peanut.commons.utils.constants.CommonCst;

/**
 * 日期相关工具类
 * 
 */
public class DateUtil {
	/** 单例 */
	private static final DateUtil instance = new DateUtil();

	private DateUtil() {
	}

	/**
	 * 取得该类唯一实例
	 * 
	 * @return 该类唯实例
	 */
	public static DateUtil instance() {
		return instance;
	}

	/**
	 * 将yyyy-MM-dd格式的字符串转换为日期对象
	 * 
	 * @param date
	 *            待转换字符串
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 * @see com.peanut.commons.utils.constants.CommonCst#DEFAULT_DATE_FORMAT
	 */
	public static Date getDate(String date) {
		return getDate(date, CommonCst.DEFAULT_DATE_FORMAT, null);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的字符串转换为日期对象
	 * 
	 * @param date
	 *            待转换字符串
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 * @see com.peanut.commons.utils.constants.CommonCst#DEFAULT_DATETIME_FORMAT
	 */
	public static Date getDateTime(String date) {
		return getDate(date, CommonCst.DEFAULT_DATETIME_FORMAT, null);
	}

	/**
	 * 将指定格式的字符串转换为日期对象
	 * 
	 * @param date
	 *            待转换字符串
	 * @param format
	 *            日期格式
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 */
	public static Date getDate(String date, String format) {
		return getDate(date, format, null);
	}

	/**
	 * 将指定格式的字符串转换为日期对象
	 * 
	 * @param date
	 *            日期对象
	 * @param format
	 *            日期格式
	 * @param defVal
	 *            转换失败时的默认返回值
	 * @return 转换后的日期对象
	 */
	public static Date getDate(String date, String format, Date defVal) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format))
			return null;
		Date d;
		try {
			d = new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			d = defVal;
		}
		return d;
	}

	/**
	 * 将日期对象格式化成yyyy-MM-dd格式的字符串
	 * 
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 * @see com.peanut.commons.utils.constants.CommonCst#DEFAULT_DATE_FORMAT
	 */
	public static String formatDate(Date date) {
		return formatDate(date, CommonCst.DEFAULT_DATE_FORMAT, null);
	}

	/**
	 * 将日期对象格式化成yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 * @see com.peanut.commons.utils.constants.CommonCst#DEFAULT_DATETIME_FORMAT
	 */
	public static String forDatetime(Date date) {
		return formatDate(date, CommonCst.DEFAULT_DATETIME_FORMAT, null);
	}
	/**
	 * 将日期对象格式化成yyyyMMdd 格式的字符串
	 * 
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 * @see com.peanut.commons.utils.constants.CommonCst#DEFAULT_DATETIME_FORMAT
	 */
	public static String forDefaultDatetime(Date date) {
		return formatDate(date, CommonCst.DBTABLE_DATE_FORMAT, null);
	}

	/**
	 * 将日期对象格式化成yyyyMMddHHmm格式的字符串
	 * 
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 * @see com.peanut.commons.utils.constants.CommonCst#DEFAULT_DATETIME_FORMAT
	 */
	public static String forDatetimeDayHour(Date date) {
		return formatDate(date, CommonCst.DAY_HOUR_DATETIME, null);
	}

	/**
	 * 将日期对象格式化成HH:mm:ss格式的字符串
	 * 
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 * @see com.peanut.commons.utils.constants.CommonCst#DEFAULT_TIME_FORMAT
	 */
	public static String formatTime(Date date) {
		return formatDate(date, CommonCst.DEFAULT_TIME_FORMAT, null);
	}

	/**
	 * 将日期对象格式化成指定类型的字符串
	 * 
	 * @param date
	 *            待格式化日期对象
	 * @param format
	 *            格式化格式
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 */
	public static String formatDate(Date date, String format) {
		return formatDate(date, format, null);
	}

	/**
	 * 将日期对象格式化成指定类型的字符串
	 * 
	 * @param date
	 *            待格式化日期对象
	 * @param format
	 *            格式化格式
	 * @param defVal
	 *            格式化失败时的默认返回值
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date, String format, String defVal) {
		if (date == null || StringUtils.isEmpty(format))
			return defVal;
		String ret;
		try {
			ret = new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			ret = defVal;
		}
		return ret;
	}

	/**
	 * 获得两个日期之间相差的天数(返回值去掉了小数部分，即只返回)。（date1 - date2）
	 * 
	 * @param date1
	 * @param date2
	 * @return 返回两个日期之间的天数差，如果date1晚于date2，则返回正数，否则返回负数或者0
	 */
	public static int intervalDays(Date date1, Date date2) {
		long intervalMillSecond = setToDayStartTime(date1).getTime() - setToDayStartTime(date2).getTime();
		// 相差的天数 = 相差的毫秒数 / 每天的毫秒数 (小数位采用去尾制)
		return (int) (intervalMillSecond / CommonCst.MILLION_SECOND_PER_DAY);
	}

	/**
	 * 将时间调整到当天的0：0：0
	 * 
	 * @param date
	 * @return
	 */
	public static Date setToDayStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得两个日期之间相差的分钟数。（date1 - date2）
	 * 
	 * @param date1
	 * @param date2
	 * @return 返回两个日期之间相差的分钟数。
	 */
	public static int intervalMinutes(Date date1, Date date2) {
		long intervalMillSecond = date1.getTime() - date2.getTime();
		// 相差的分钟数 = 相差的毫秒数 / 每分钟的毫秒数 (小数位采用进位制处理，即大于0则加1)
		return (int) (intervalMillSecond / CommonCst.MILLIONSECONDS_PER_MINUTE
				+ (intervalMillSecond % CommonCst.MILLIONSECONDS_PER_MINUTE > 0 ? 1 : 0));
	}

	/**
	 * 获得两个日期之间相差的秒数。（date1 - date2）
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int intervalSeconds(Date date1, Date date2) {
		long intervalMillSecond = date1.getTime() - date2.getTime();
		return (int) (intervalMillSecond / CommonCst.MILLIONSECONDS_PER_SECOND
				+ (intervalMillSecond % CommonCst.MILLIONSECONDS_PER_SECOND > 0 ? 1 : 0));
	}

	/**
	 * 将指定日期对象格式化成指定生日日期对象 生日日期范围 [1000-01-01 00:00:00, 9999-12-31 23:59:59]
	 * 
	 * @param origalBirthday
	 * @return
	 */
	public static Date formatBirthday(Date origalBirthday) {
		if (origalBirthday == null) {
			return DateUtil.getDate("1000-01-01 00:00:00");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(origalBirthday);
		int year = calendar.get(Calendar.YEAR);
		if (year > 9999 || year < 1000) {
			calendar.set(Calendar.YEAR, Math.max(1000, year % 10000));
		}
		return calendar.getTime();
	}

	/**
	 * 从Calendar类中获得“年”。
	 * 
	 * @param c
	 *            Calendar实例
	 * @return 返回指定Calendar的年
	 */
	public static int getYear(Calendar c) {
		return c.get(Calendar.YEAR);
	}

	/**
	 * 从Calendar中获得当年的月数，从1到12。 注意：这里的结果比Calendar实际值大一，因为Calendar的月从0开始。
	 * 
	 * @param c
	 *            Calendar实例
	 * @return 返回指定的Calendar的月
	 */
	public static int getMonth(Calendar c) {
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 从Calendar中获得当月的日期。
	 * 
	 * @param c
	 *            Calendar实例
	 * @return 返回指定Calendar的当月的日期
	 */
	public static int getDay(Calendar c) {
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 从Calendar中获得时间，24小时制表示。
	 * 
	 * @param c
	 *            Calendar的实例
	 * @return 返回指定的Calendar的时间
	 */
	public static int getHour(Calendar c) {
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 从Calendar中获得分钟数。
	 * 
	 * @param c
	 *            Calendar实例
	 * @return 返回指定的Calendar的分钟数
	 */
	public static int getMinute(Calendar c) {
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 从Calendar中获得秒数。
	 * 
	 * @param c
	 *            Calendar实例
	 * @return 返回指定的Calendar的秒数
	 */
	public static int getSecond(Calendar c) {
		return c.get(Calendar.SECOND);
	}

	/**
	 * 判断两个日期是不是同一天。
	 * 
	 * @param c1
	 *            第一个日期
	 * @param c2
	 *            第二个日期
	 * @return 如果是同一天则返回true；否则返回false
	 */
	public static boolean isSameDay(Calendar c1, Calendar c2) {
		if (getYear(c1) == getYear(c2) && getMonth(c1) == getMonth(c2) && getDay(c1) == getDay(c2)) {
			return true;
		}

		return false;
	}

	// -------------------------------------------

	/**
	 * 获取指定日期时间几天后的凌晨日期时间（时分秒都为0）
	 * 
	 * @param date
	 * @param intervalDay
	 *            正数，几天之后 负数，几天之前
	 * @return
	 */
	public static Date getZeroDateTimeAfterSomeDay(Date date, int intervalDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, intervalDay);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期时间几小时后的整点时间（分秒都为0）
	 * 
	 * @param date
	 * @param intervalDay
	 *            正数，几小时之后 负数，几小时之前
	 * @return
	 */
	public static Date getHourStartTimeAfterSomeHour(Date date, int intervalHour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.add(Calendar.HOUR_OF_DAY, intervalHour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取当钱时间几小时后的整点时间（分秒都为0）
	 * 
	 * @param date
	 * @param intervalDay
	 *            正数，几小时之后 负数，几小时之前
	 * @return
	 */
	public static String getAfterSomeHourDate(Date date, int intervalHour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.add(Calendar.HOUR_OF_DAY, intervalHour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return forDatetimeDayHour(calendar.getTime());
	}

	/**
	 * 获取指定日期时间几周后的凌晨日期时间（时分秒都为0）
	 * 
	 * @param date
	 * @param intervalDay
	 *            正数，几周之后 负数，几周之前
	 * @return
	 */
	public static Date getZeroDateTimeAfterSomeWeek(Date date, int intervalWeek) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.add(Calendar.WEEK_OF_MONTH, intervalWeek);
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期时间几月后的凌晨日期时间（时分秒都为0）
	 * 
	 * @param date
	 * @param intervalDay
	 *            正数，几月之后 负数，几月之前
	 * @return
	 */
	public static Date getZeroDateTimeAfterSomeMonth(Date date, int intervalMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.add(Calendar.MONTH, intervalMonth);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	

	/**
	 * 返回多少分钟以后的日期格式
	 * 
	 * @param maintainMinute
	 * @return
	 */
	public static Date calulateMaintainDateTime(int maintainMinute) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(now.getTime());
		calendar.add(Calendar.MINUTE, maintainMinute);
		return calendar.getTime();
	}

	/**
	 * 传入时间是否早于当前时间
	 * 
	 * @param when
	 * @return
	 */
	public static boolean beforeNow(Date when) {
		return getCurrentDate().after(when);
	}

	/**
	 * 获取当前时间对象
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	public static Date getFutureDate(long offset) {
		return new Date(System.currentTimeMillis() + offset);
	}

	/**
	 * 获取两个毫秒值之间相差多少毫秒
	 * 
	 * @param big
	 * @param small
	 * @return
	 */
	public static long getUnsignedOffset(long big, long small) {
		return Math.max(big, small) - Math.min(big, small);
	}

	public static long getUnsignedOffset(Date big) {
		return getUnsignedOffset(big.getTime());
	}

	public static long getUnsignedOffset(long big) {
		return getUnsignedOffset(big, System.currentTimeMillis());
	}

	/**
	 * 获取传入时间与当前时间的毫秒差值
	 * 
	 * @param big
	 * @param small
	 * @return
	 */
	public static long getOffset(long big) {
		return System.currentTimeMillis() - big;
	}

	/**
	 * 获得当前时间距离明天凌晨的秒数
	 * 
	 * @author
	 * @return
	 */
	public static long getNowRemainTomorrowSeconds() {
		return DateUtil.intervalSeconds(getZeroDateTimeAfterSomeDay(new java.util.Date(), 1), new java.util.Date());
	}

	/**
	 * 获得当前时间距离下个小时的秒数
	 * 
	 * @return
	 */
	public static long getNowRemainNextHourSeconds() {
		return DateUtil.intervalSeconds(getHourStartTimeAfterSomeHour(new java.util.Date(), 1), new java.util.Date());
	}

	/**
	 * 获得当前时间距离下周的秒数
	 * 
	 * @return
	 */
	public static long getNowRemainNextWeekSeconds() {
		return DateUtil.intervalSeconds(getZeroDateTimeAfterSomeWeek(new java.util.Date(), 1), new java.util.Date());
	}

	/**
	 * 获得当前时间距离下个月的秒数
	 * 
	 * @return
	 */
	public static long getNowRemainNextMonthSeconds() {
		return DateUtil.intervalSeconds(getZeroDateTimeAfterSomeMonth(new java.util.Date(), 1), new java.util.Date());
	}

	/**
	 * 获得今天日期时间的字符串形式
	 * 
	 * @return
	 */
	public static String getTodayDateTimeStr() {
		return DateUtil.forDatetime(getCurrentDate());
	}

	/**
	 * 获得今天日期时间的字符串形式
	 * 
	 * @return
	 */
	public static String getTodayDateTimeDayHourStr() {
		return DateUtil.forDatetimeDayHour(getCurrentDate());
	}

	/**
	 * 获得今天日期的字符串形式
	 * 
	 * @return
	 */
	public static String getTodayStr() {
		return DateUtil.formatDate(getCurrentDate());
	}

	/**
	 * 获得今天日期的字符串形式, 偏移offset分钟
	 * 
	 * @param offset
	 *            偏移（分钟）
	 * @return
	 */
	public static String getTodayStr(int offset) {
		return DateUtil.formatDate(DateUtils.addMinutes(new Date(), offset));
	}

	/**
	 * 获取昨天字符串,yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getYestrdayStr() {
		return getSomeDayStr(-1, CommonCst.DEFAULT_DATE_FORMAT);
	}
	/**
	 * 获取昨天字符串,yyyyMMddHH
	 * 
	 * @return
	 */
	public static String getYesterdayStrForHour() {
		return getSomeDayStr(-1, CommonCst.DAY_HOUR_DATETIME);
	}
	/**
	 * 获取今天零点字符串,yyyyMMddHH
	 * 
	 * @return
	 */
	public static String getTodayStrForHour() {
		return getSomeDayStr(-1, CommonCst.DAY_HOUR_DATETIME);
	}
	/**
	 * 获取昨天字符串,yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDefaultYesterdayStr() {
		return getSomeDayStr(-1, CommonCst.DBTABLE_DATE_FORMAT);
	}
	
	
	
	/**
	 * 获取今天零点字符串,yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDefaultZeroTodayStr() {
		return getSomeDayStr(0, CommonCst.DBTABLE_DATE_FORMAT);
	}
	
	
	public static String getLastHourDateStr() {
		return getTodayDateTimeDayHourStr();
	}

	/**
	 * 获取明天字符串,yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getTomorrowStr() {
		return getSomeDayStr(1, CommonCst.DEFAULT_DATE_FORMAT);
	}

	/**
	 * 获取今天前几天或者后几天的字符串形式
	 * 
	 * @param intervalDay
	 *            正数，几天之后 负数，几天之前
	 * @param format
	 *            格式
	 * @return
	 */
	public static String getSomeDayStr(int intervalDay, String format) {
		return DateUtil.formatDate(getZeroDateTimeAfterSomeDay(getCurrentDate(), intervalDay), format);
	}

	/**
	 * 获取今天前几天或者后几天的字符串形式
	 * 
	 * @param intervalDay
	 *            正数，几天之后 负数，几天之前
	 * @param format
	 *            格式
	 * @return
	 */
	public static String getSomeDayStr(int intervalDay) {
		return DateUtil.formatDate(getZeroDateTimeAfterSomeDay(getCurrentDate(), intervalDay),
				CommonCst.DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 获取今天前几天或者后几天的字符串形式
	 * 
	 * @param intervalDay
	 *            正数，几天之后 负数，几天之前
	 * @param format
	 *            格式
	 * @return
	 */
	public static String getSomeDayStrForHour(int intervalDay) {
		return DateUtil.formatDate(getZeroDateTimeAfterSomeDay(getCurrentDate(), intervalDay),
				CommonCst.DAY_HOUR_DATETIME);
	}

	/**
	 * 获取今天日期的整数形式，例如：2011-06-01，则整数形式为20110601
	 * 
	 * @return
	 */
	public static int getTodayDate() {
		String todayStr = getTodayStr();
		try {
			return Integer.parseInt(todayStr.trim().replace("-", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取当天日期（除时分秒）的毫秒值 注意： 1、可以通过调用 set 方法来设置日历字段值。在需要计算时间值（距历元所经过的毫秒）或日历字段值之前，
	 * 不会解释 Calendar 中的所有字段值设置。调用 get、getTimeInMillis、getTime、add 和 roll 涉及此类计算；
	 * 2、在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间；
	 * 
	 * @return
	 */
	public static long getTodayDateInMillis() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTimeInMillis();
	}

	/**
	 * 获得两个日期之间相差的小时数。
	 * 
	 * @param date1
	 * @param date2
	 * @return 返回两个日期之间相差的分钟数。
	 */
	public static int intervalHour(Date date1) {
		long intervalMillSecond = System.currentTimeMillis() - date1.getTime();
		return (int) (intervalMillSecond / CommonCst.MILLIONSECONDS_PER_HOUR);
	}

	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatDuring(long mss) {
		StringBuilder sb = new StringBuilder("[ ");
		long days = mss / (1000 * 60 * 60 * 24);
		if (days > 0)
			sb.append(days).append(" days ");
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		if (hours > 0)
			sb.append(hours).append(" hours ");
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		if (minutes > 0)
			sb.append(minutes).append(" minutes ");
		long seconds = (mss % (1000 * 60)) / 1000;
		if (seconds > 0)
			sb.append(seconds).append(" seconds ");
		sb.append(" ]");
		return sb.toString();
	}
	

	/**
	 * 
	 * @param begin
	 *            时间段的开始
	 * @param end
	 *            时间段的结束
	 * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
	 * @author fy.zhang
	 */
	public static String formatDuring(Date begin, Date end) {
		return formatDuring(end.getTime() - begin.getTime());
	}

	/**
	 * 解析excel中定义的日期字符串
	 * 
	 * @param excelDateTime
	 *            在excel中定义的日期的字符串形式，非标准(20110401120000)，去掉了-、空格和冒号
	 * @return
	 */
	public static String parseExcelDateTimeStr(String excelDateTime) {
		String dateTime = "";
		try {
			dateTime = excelDateTime.substring(0, 4) + "-" + excelDateTime.substring(4, 6) + "-"
					+ excelDateTime.substring(6, 8) + " " + excelDateTime.substring(8, 10) + ":"
					+ excelDateTime.substring(10, 12) + ":" + excelDateTime.substring(12, 14);
		} catch (Exception e) {
			return "";
		}

		return dateTime;
	}

	/**
	 * 获取指定的日期是一周中的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取今天是一周第几天
	 * 
	 * @return
	 */
	public static int getTodayOfWeek() {
		Calendar c = Calendar.getInstance();
		c.setTime(getCurrentDate());
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 判断两个日期是否同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDay(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		return DateUtil.isSameDay(c1, c2);
	}

	/**
	 * 将秒数解析为HH:mm:ss格式
	 * 
	 * @param second
	 * @return
	 */
	public static String getCountdown(int second) {
		int hour = 0;
		int min = 0;
		int sec = 0;
		int temp = second % 3600;
		if (second > 3600) {
			hour = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					min = temp / 60;
					if (temp % 60 != 0) {
						sec = temp % 60;
					}
				} else {
					sec = temp;
				}
			}
		} else {
			min = second / 60;
			if (second % 60 != 0) {
				sec = second % 60;
			}
		}
		return hour + ":" + min + ":" + sec;
	}

	/**
	 * 获得当前时间的yyyyMMddHHmmss格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatFullDateTime(Date date) {
		return DateUtil.formatDate(date, CommonCst.FULL_DATETIME, null);
	}
	/**
	 * 将毫秒转换成时间 格式  
	 * @param times
	 * @return
	 */
	public static String formatDateDurring(long times){
		Date date = new Date(times);
		return DateUtil.formatDate(date,CommonCst.DAY_HOUR_DATETIME,null);
	}
	public static void main(String[] args) {
		System.out.println(getDefaultYesterdayStr());
		System.out.println(getDefaultZeroTodayStr());
		System.out.println(formatFullDateTime(new Date()));
	}
}
