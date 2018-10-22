package com.googosoft.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 日期处理工具
 * @author  作者:wxm 
 * @date 创建时间：2016年9月5日 上午9:35:53 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class DateUtil {
	public final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	
	public final static SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

	public final static SimpleDateFormat sdfDate = new SimpleDateFormat("dd");
	
	public final static SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyy-MM");
	
	public final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	
	public final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	public final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取YYYY格式年份
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}
	
	/**
	 * 获取MM格式月份
	 * 
	 * @return
	 */
	public static String getMonth() {
		return sdfMonth.format(new Date());
	}
	
	/**
	 * 获取dd格式月份
	 * @return
	 */
	public static String getDate() {
		return sdfDate.format(new Date());
	}

	/**
	 * 获取YYYY-MM格式年月
	 * 
	 * @return
	 */
	public static String getYearMonth() {
		return sdfYearMonth.format(new Date());
	}
	/**
	 * 获取YYYY-MM-DD格式日期
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式日期
	 * 
	 * @return
	 */
	public static java.sql.Date getDay(String datestr) {
	 	try{
	    	Date date = sdfDay.parse(datestr);
	    	return new java.sql.Date(date.getTime());
    	}catch(Exception e){
	    	e.printStackTrace();
	    	return null;
    	}
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	  /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
        try {
			beginDate = format.parse(beginDateStr);
			endDate= format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
      
        return day;
    }
    
    /**
     * 得到n年之前的年份
     * @param days
     * @return
     */
    public static String getBeforeYear(String years) {
    	int yearInt = Integer.parseInt(years);
    	
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(Calendar.YEAR, -yearInt);
        Date date = canlendar.getTime();
        return sdfYear.format(date);
    }
    
    /**
     * 得到n年之后的年份
     * @param days
     * @return
     */
    public static String getAfterYear(String years) {
    	int yearInt = Integer.parseInt(years);
    	
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.add(Calendar.YEAR, yearInt);
    	Date date = canlendar.getTime();
    	return sdfYear.format(date);
    }
    /**
     * 得到n天之前的日期
     * @param days
     * @return
     */
    public static String getBeforeDay(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(Calendar.DATE, -daysInt);
        Date date = canlendar.getTime();
        return sdfDay.format(date);
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDay(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(Calendar.DATE, daysInt);
        Date date = canlendar.getTime();
        return sdfDay.format(date);
    }
    
    /**
     * 得到n天之前的日期
     * @param days 天数
     * @param day  标准日期
     * @return
     */
    public static String getBeforeDay(String days,String day) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance();
        Date date = canlendar.getTime();
        
        String retdate = "";
        int xcts = 0;
        try{
        	xcts = (int)getDaySub(sdfDay.format(date),day);
            daysInt = xcts + daysInt;
            
            canlendar.add(Calendar.DATE, -daysInt);
            date = canlendar.getTime();
            retdate = sdfDay.format(date);
        }
        catch (Exception e) {
        	e.printStackTrace();
		}
        return retdate;
    }
    
    /**
     * 得到n天之后的日期
     * @param days 天数
     * @param day  标准日期
     * @return
     */
    public static String getAfterDay(String days,String day) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance();
        Date date = canlendar.getTime();
        
        String retdate = "";
        int xcts = 0;
        try{
        	xcts = (int)getDaySub(sdfDay.format(date),day);
            daysInt = xcts + daysInt;
            
            canlendar.add(Calendar.DATE, daysInt);
            date = canlendar.getTime();
            retdate = sdfDay.format(date);
        }
        catch (Exception e) {
        	e.printStackTrace();
		}
        return retdate;
    }
    
    /**
     * 得到n天之前的日期时间
     * @param days
     * @return
     */
    public static String getBeforeDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, -daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        return sdfTime.format(date);
    }
    
    /**
     * 得到n天之后的日期时间
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        return sdfTime.format(date);
    }
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到本周一的日期
     * @param days
     * @return
     */
    public static String getMonday() {
        Calendar canlendar = Calendar.getInstance();
        int week = canlendar.get(Calendar.DAY_OF_WEEK);//获得今天是周几
        week = week - 1;//由于Calendar类中1是周日2是周一，所以想要获取正确的数字就得减一
        if(week == 0){//将周日改成7
        	week = 7;
        }
        canlendar.add(Calendar.DATE, -(week - 1));//直接减week会获得上周天的日期，所以要再减一
        Date date = canlendar.getTime();//获取计算之后的日期
        
        return sdfDay.format(date);
    }
    /**
     * 得到本周日的日期
     * @param days
     * @return
     */
    public static String getSunday() {
        Calendar canlendar = Calendar.getInstance();
        int week = canlendar.get(Calendar.DAY_OF_WEEK);//获得今天是周几
        week = week - 1;//由于Calendar类中1是周日2是周一，所以想要获取正确的数字就得减一
        if(week == 0){//将周日改成7
        	week = 7;
        }
        canlendar.add(Calendar.DATE, (7 - week));//直接减week会获得上周天的日期，所以要再减一
        Date date = canlendar.getTime();//获取计算之后的日期
        
        return sdfDay.format(date);
    }
    
    /**
     * 获取本月第一天
     * @return
     */
    public static String getMonthMinDay(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.set(canlendar.get(Calendar.YEAR), (canlendar.get(Calendar.MONTH)), 1);
    	return sdfDay.format(canlendar.getTime());
    }
    /**
     * 获取本月第一天是周几
     * @return
     */
    public static String getMonthMinDayOfWeek(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.set(Calendar.DATE,1);
    	int start = canlendar.get(Calendar.DAY_OF_WEEK);
    	start = start - 1;//由于Calendar类中1是周日2是周一，所以想要获取正确的数字就得减一
        if(start == 0){//将周日改成7
        	start = 7;
        }
    	return String.valueOf(start);
    }
    /**
     * 获取本月最后一天 
     * @return
     */
    public static String getMonthMaxDay(){
    	Calendar canlendar = Calendar.getInstance();
    	int maxDay = canlendar.getActualMaximum(Calendar.DATE);
    	canlendar.set(canlendar.get(Calendar.YEAR), (canlendar.get(Calendar.MONTH)), maxDay);
    	return sdfDay.format(canlendar.getTime());
    }
    /**
     * 获取本月最后一天 是周几
     * @return
     */
    public static String getMonthMaxDayOfWeek(){
    	Calendar canlendar = Calendar.getInstance();
    	int maxDay = canlendar.getActualMaximum(Calendar.DATE);
    	canlendar.set(Calendar.DATE,maxDay);
    	int end = canlendar.get(Calendar.DAY_OF_WEEK);
    	end = end - 1;//由于Calendar类中1是周日2是周一，所以想要获取正确的数字就得减一
        if(end == 0){//将周日改成7
        	end = 7;
        }
    	return String.valueOf(end);
    }
    
    /**
     * 获取本年第一天
     * @return
     */
    public static String getYearMinDay(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.set(canlendar.get(Calendar.YEAR),0, 1);
    	return sdfDay.format(canlendar.getTime());
    }
    /**
     * 获取本年第一个月
     * @return
     */
    public static String getYearMinMonth(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.set(canlendar.get(Calendar.YEAR),0, 1);
    	return sdfYearMonth.format(canlendar.getTime());
    }
    /**
     * 获取本年第一天是周几
     * @return
     */
    public static String getYearMinDayOfWeek(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.set(canlendar.get(Calendar.YEAR),0, 1);
    	int start = canlendar.get(Calendar.DAY_OF_WEEK);
    	start = start - 1;//由于Calendar类中1是周日2是周一，所以想要获取正确的数字就得减一
        if(start == 0){//将周日改成7
        	start = 7;
        }
    	return String.valueOf(start);
    }
    /**
     * 获取本年最后一天 
     * @return
     */
    public static String getYearMaxDay(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.set(canlendar.get(Calendar.YEAR),11, 31);
    	return sdfDay.format(canlendar.getTime());
    }
    /**
     * 获取本年最后一天 是周几
     * @return
     */
    public static String getYearMaxDayOfWeek(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.set(canlendar.get(Calendar.YEAR),11, 31);
    	int end = canlendar.get(Calendar.DAY_OF_WEEK);
    	end = end - 1;//由于Calendar类中1是周日2是周一，所以想要获取正确的数字就得减一
        if(end == 0){//将周日改成7
        	end = 7;
        }
    	return String.valueOf(end);
    }

    /**
     * 获取最近一个月的开始日期
     * @return
     */
    public static String getLatelyMonthBeginDate(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.add(Calendar.MONTH, -1);
    	canlendar.add(Calendar.DATE,1);
    	return sdfDay.format(canlendar.getTime());
    }

    /**
     * 获取最近一年的开始日期
     * @return
     */
    public static String getLatelyYearBeginDate(){
    	Calendar canlendar = Calendar.getInstance();
    	canlendar.add(Calendar.YEAR, -1);
    	canlendar.add(Calendar.DATE,1);
    	return sdfDay.format(canlendar.getTime());
    }
  
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

    /**
     * 获取当前日期时间
     * @param format 要获取的时间格式（区分大小写）
     * @return 格式化后的日期时间
     */
    public static String timeMillis2Date(String format){
    	return timeMillis2Date(System.currentTimeMillis(),format);
    }
    /**
     * 根据long类型的字段获取时间日期
     * @param timeMillis 时间的long类型
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String timeMillis2Date(long timeMillis){
    	return timeMillis2Date(timeMillis,"yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 根据long类型的字段获取时间日期
     * @param timeMillis 时间的long类型
     * @param format 要获取的时间格式（区分大小写）
     * @return 格式化后的日期时间
     */
    public static String timeMillis2Date(long timeMillis,String format){
    	Date date = new Date(timeMillis);
    	SimpleDateFormat dateformat = new SimpleDateFormat(format);
    	return dateformat.format(date);
    }

}
