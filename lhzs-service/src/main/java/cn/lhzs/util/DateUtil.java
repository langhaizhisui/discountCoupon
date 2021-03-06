package cn.lhzs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_NO_TIME_FROMAT = "yyyy-MM-dd";

	/**
	 * 获取当前时间点的指定格式字符串
	 * 
	 * @param format
	 *            时间格式
	 * @return 指定格式的字符串
	 */
	public static String getNowStr(String format) {
		Date now = getCurDate();
		return formatDate(now, format);
	}

	/**
	 * 获取当前时间的"yyyy-MM-dd HH:mm:ss"形式字符串
	 * 
	 * @return
	 */
	public static String getNowDefaultFormatStr() {
		return getNowStr(DEFAULT_FORMAT);
	}

	/**
	 * 获取时间的指定格式字符串
	 * 
	 * @param date
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取当前时间统一使用该方法，方便后期修改
	 * 
	 * @return
	 */
	public static Date getCurDate() {
		// TODO 分布式系统后修改该时间获取规则
		Date now = new Date();
		return now;
	}

	/**
	 * 获取当前时间戳的string形式（当前时间戳只精确到秒）
	 * 
	 * @return
	 */
	public static String getNowTimeStampStr() {
		long time = getCurDate().getTime();
		return String.valueOf(time / 1000);
	}
	
	public static long getTimeStampStr(Date date){
		return date.getTime() / 1000;
	}
	
	public static Long getCurrentTimeStamp() {
		long time = getCurDate().getTime();
        return time / 1000;
    }

	/**
	 * 以指定的格式是解析时间
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static Date getStrDate(String format, String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * 以默认的时间格式解析时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStrDateByDefault(String date) {
		if (StringUtil.isEmptyString(date)) {
			return null;
		}
		return getStrDate(DEFAULT_FORMAT, date);
	}

	/**
	 * yyyy-MM-dd 日期转化
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStrDateNoTime(String date) {
		if (StringUtil.isEmptyString(date)) {
			return null;
		}
		return getStrDate(DEFAULT_NO_TIME_FROMAT, date);
	}

	/**
	 * 获取时间的默认格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getDefaultFormatDate(Date date) {
		return formatDate(date, DEFAULT_FORMAT);
	}
	
	public static Date rollDate(Date curDate, char unit, int value){
		Calendar c = Calendar.getInstance();
		c.setTime(curDate);
		
		switch( unit ){
			case 'H' :
				c.add(Calendar.HOUR, value);
				break;
			case 'D' :
				c.add(Calendar.DATE, value);
				break;
		}
		
		curDate = c.getTime();
		return curDate;
	}
	
	/**
	 * 获取指定一天的最后时间
	 * @param date
	 * @return
	 */
	public static Date getDayLastTime(Date date){
		if (null == date){
			return null;
		}
	   Calendar ca = Calendar.getInstance();  
	   ca.setTime(date);
       ca.set(Calendar.HOUR_OF_DAY, 23);  
       ca.set(Calendar.MINUTE, 59);  
       ca.set(Calendar.SECOND, 59); 
       //目前数据库只精确到秒的, 毫秒会四舍五入，需要把毫秒设为0
       ca.set(Calendar.MILLISECOND, 0);
       return ca.getTime();
	}

	/** 
	 */
	public static Date getBeginTimeOfDate(Date date) {
		Calendar ca = Calendar.getInstance();  
 	    ca.setTime(date);
        ca.set(Calendar.HOUR_OF_DAY, 0);  
        ca.set(Calendar.MINUTE, 0);  
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
	}
    
    /** 
     * 获取某年某月的第一天 的00:00:00
     * @param date  
     * @return 
     */  
    public static Date getFirstDayOfMonth(Date date){
    	Date dateFirst = setDays(date, 1);  
    	return getBeginTimeOfDate(dateFirst);
    }
    
    /** 
     * 获取某年某月的最后一天 的23:59:59
     * @param date  
     * @return 
     */  
    public static Date getLastDayOfMonth(Date date){
    	Date nextMonthFirstDate = setDays(addMonths(date, 1),1);
    	Date curMonthlastDay  = addDays(nextMonthFirstDate, -1);
    	return getDayLastTime(curMonthlastDay);
    }
    /** 
     * 获取某年某月的前一年的开始时间
     * @param date  
     * @return 
     */  
    public static Date getLastYearStartTime(Date date){
    	Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, -1);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);  
        c.set(Calendar.MINUTE, 0);  
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    	return c.getTime();
    }
    
    /** 
     * 获取某年的 年的最后时间
     * @param date  
     * @return 
     */  
    public static Date getYearStartTime(Date date){
    	Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, Calendar.JANUARY);
    	return getFirstDayOfMonth(c.getTime());
    }
    /** 
     * 获取某年的 年的最后时间
     * @param date  
     * @return 
     */  
    public static Date getYearEndTime(Date date){
    	Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, Calendar.DECEMBER);
    	return getLastDayOfMonth(c.getTime());
    }
    /** 
     * 获取某年某月的第一天 
     * @param date  
     * @return 
     */  
    public static Date getFirstDayOfLastMonth(Date date){
    	Date lastMonthDate = addMonths(date, -1);
    	return getBeginTimeOfDate(setDays(lastMonthDate, 1));
    }
	public static Date setDays(Date date, int amount) {
		return set(date, Calendar.DAY_OF_MONTH, amount);
	}
	
	private static Date set(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.setTime(date);
		c.set(calendarField, amount);
		return c.getTime();
	}
    /** 
     * 获取今天是几号
     * @param date  
     * @return 
     */  
    public static int getDayOfMonth(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
        return c.get(Calendar.DATE);
    }
    
    /** 
     * 获取今天是几月
     * @param date  
     * @return 
     */  
    public static int getMonthOfYear(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
        return c.get(Calendar.MONTH) +1;
    }
    /** 
     * 获取当前时间是本月第几周
     * @param date  
     * @return 
     */  
    public static int getWeekOfMonth(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
        return c.get(Calendar.WEEK_OF_MONTH);
    }
    
    
    /** 
     * 获取今天是第几季度
     * @param date  
     * @return 
     */  
    public static int getSeasonOfYear(Date date){
    	int month = getMonthOfYear(date);
    	int season = 0;
    	if (month > 0 && month < 4){
    		season = 1;
		}else if (month >= 4 && month < 7){
			season = 2;
		}else if (month >= 7 && month < 10){
			season = 3;
		}else{
			season = 4;
		}
    	 
        return season;
    }
    
    /**
     * 获取当前季度第一天
     * @param date
     * @return
     */
    public static Date getStartTimeOfSeason(Date date){
    	int season = getSeasonOfYear(date);
    	int month = Calendar.JANUARY;
    	if (season ==1){
    		month = Calendar.JANUARY;
		}else if (month ==2){
    		month = Calendar.MAY;
		}else if (month ==3){
    		month = Calendar.AUGUST;
		}else if (month ==4){
    		month = Calendar.NOVEMBER;
		}
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.MONTH, month);
        return getFirstDayOfMonth(c.getTime());
    }
    
    /**
     * 获取传入时间的周一
     * 
     * @param date 当前时间
     * @return 返回传入时间当周星期一
     */
    public static Date getNowWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        cal.add(Calendar.DAY_OF_MONTH, -1); 
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        
        return getBeginTimeOfDate(cal.getTime());
    }
    
    /**
     * 获取当前星期几
     * 
     * @param date 当前时间
     * @return  "0-星期日", "1-星期一", "2-星期二", "3-星期三", "4-星期四", "5-星期五", "6-星期六
     */
    public static Integer getWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
        	w = 0;
        }
        return w;
    }
    
    /**
     * 获取当前星期几
     * 
     * @param date 当前时间
     * @return  "0-星期日", "1-星期一", "2-星期二", "3-星期三", "4-星期四", "5-星期五", "6-星期六
     */
    public static String getWeekDayStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
        	w = 0;
        }
        
        String weekDayStr = "";
        switch (w) {
		case 0:
			weekDayStr = "星期天";
			break;
		
		case 1:
			weekDayStr = "星期一";
			break;
		case 2:
			weekDayStr = "星期二";
			break;
		case 3:
			weekDayStr = "星期三";
			break;
		case 4:
			weekDayStr = "星期四";
			break;
		case 5:
			weekDayStr = "星期五";
			break;
		case 6:
			weekDayStr = "星期六";
			break;
		default:
			break;
		}
        return weekDayStr;
    }
    
    public static int getDayDiff(Date d1,Date d2){
    	 Long t1 = d1.getTime();
    	 Long t2 = d2.getTime();
    	 int diff = (int)((t1-t2)/(24*60*60*1000));
    	return diff;
    }
    
    /**
     * 获取传入时间的上周一
     * 
     * @param date 当前时间
     * @return 返回上周一时间
     */
    public static Date getLastWeekMonday(Date date) {
        Date a = addDays(date, -1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(a);
        cal.add(Calendar.WEEK_OF_YEAR, -1);// 一周
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
         
        return getBeginTimeOfDate(cal.getTime());
    }

	/**
	 * 获取传入时间的周一的开始时间
	 *
	 * @param date 当前时间
	 * @return
	 */
	public static Date getWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, -1);// 一周
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getBeginTimeOfDate(cal.getTime());
	}
    
 
    /**
     * 获取传入时间的上周星期几的时间   
     * @param date
     * @param week Calendar的常量  星期一用  Calendar.MONDAY 
     * @return
     */
    public static Date getLastWeekDay(Date date,int week) {
        Date a = addDays(date, -1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(a);
        cal.add(Calendar.WEEK_OF_YEAR, -1);// 一周
        cal.set(Calendar.DAY_OF_WEEK, week);
         
        return getBeginTimeOfDate(cal.getTime());
    }
    
    /**
     * 获取传入时间下周一
     * @param date 当前时间
     * @return 返回下周一日期
     */
    public static Date	getNextWeekMonday(Date date) {
        Date a = addDays(date, -1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(a);
        cal.add(Calendar.WEEK_OF_YEAR, 1);// 一周
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
         
        return getBeginTimeOfDate(cal.getTime());
    } 
    
    public static Date getNextDayStartTime(Date date) {
        Date a = addDays(date, 1);
        return getBeginTimeOfDate(a);
    } 

	public static Date addYears(Date date, int amount) {
		return add(date, 1, amount);
	}

	public static Date addMonths(Date date, int amount) {
		return add(date, 2, amount);
	}

	public static Date addWeeks(Date date, int amount) {
		return add(date, 3, amount);
	}

	public static Date addDays(Date date, int amount) {
		return add(date, 5, amount);
	}

	public static Date addHours(Date date, int amount) {
		return add(date, 11, amount);
	}

	public static Date addMinutes(Date date, int amount) {
		return add(date, 12, amount);
	}

	public static Date addSeconds(Date date, int amount) {
		return add(date, 13, amount);
	}
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(calendarField, amount);
			return c.getTime();
		}
	}
}