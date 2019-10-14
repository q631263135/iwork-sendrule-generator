/**
 * @Title:  DateUtil.java
 * @Package package com.joyin.platform.common.util
 * @Description:
 * @author
 * @date  2014-7-10 下午4:05:33
 * @version V1.0
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.joyin.sendrule2.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil {
    public static final String Format_Date = "yyyy-MM-dd";
    public static final String Format_Time = "HH:mm:ss";
    public static final String Format_Time1 = "HHmmss";
    public static final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";
    public static final String Format_DateTimeStamp = "yyyy-MM-dd HH:mm:ss";
    //0001-01-01-00.00.00.000000

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    // //tabCalendar数据频率：0日频；1周频；2月频；3季频；4半年频；5年频
    // ///tabCalTag数据频率日期首尾：0第一天；1最后一天
    public static Collection<String> CalendarDateList(long tabCalendar, String tabCalTag, String beginDateString, String endDateString) {
        Collection<String> baseDate = new ArrayList<String>();
        Date beginDate = DateUtil.parse(beginDateString, DateUtil.Format_Date);
        Date endDate = DateUtil.parse(endDateString, DateUtil.Format_Date);
        if (tabCalendar == 0) {
            for (Date tmpDate = beginDate; tmpDate.compareTo(endDate) <= 0; tmpDate = addDay(
                    tmpDate, 1)) {
                baseDate.add(toString(tmpDate));
            }
            return baseDate;
        }

        if(tabCalendar == 1 ){
            if (tabCalTag.equals("0")) {
                beginDate = getweekFristDay(beginDate);
                endDate = getweekFristDay(endDate);
            } else {
                beginDate = getweekLastDay(beginDate);
                endDate = getweekLastDay(endDate);
            }

            for (Date tmpDate = beginDate; tmpDate.compareTo(endDate) <= 0; tmpDate = addWeek(tmpDate, 1)) {
                if (tabCalTag.equals("0")) {
                    baseDate.add(toString(getweekFristDay(tmpDate)));
                } else {
                    baseDate.add(toString(getweekLastDay(tmpDate)));
                }
            }
        }
        if (tabCalendar == 2) {
            if (tabCalTag.equals("0")) {
                beginDate = DateUtil.parse(getFirstDayOfMonth(beginDate));
                endDate = DateUtil.parse(getFirstDayOfMonth(endDate));

            } else {
                beginDate =  DateUtil.parse(getLastDayOfMonth(beginDate));
                endDate = DateUtil.parse(getLastDayOfMonth(endDate));
            }
            for (Date tmpDate = beginDate; tmpDate.compareTo(endDate) <= 0; tmpDate = addMonth(tmpDate, 1)) {
                if (tabCalTag.equals("0")) {
                    baseDate.add(getFirstDayOfMonth(tmpDate));
                } else {
                    baseDate.add(getLastDayOfMonth(tmpDate));
                }
            }
            return baseDate;
        }
        if (tabCalendar == 3) {
            if (tabCalTag.equals("0")) {

                int month = getQuarterInMonth(beginDate.getMonth(), true);
                beginDate.setMonth(month);
                beginDate = DateUtil.parse(getFirstDayOfMonth(beginDate));

                month = getQuarterInMonth(endDate.getMonth(), true);
                endDate.setMonth( month) ;
                endDate = DateUtil.parse(getFirstDayOfMonth(endDate));
            } else {
                int month = getQuarterInMonth(beginDate.getMonth(), false);
                beginDate.setMonth(month);
                beginDate = DateUtil.parse(getFirstDayOfMonth(beginDate));

                month = getQuarterInMonth(endDate.getMonth(), false);
                endDate.setMonth( month) ;
                endDate = DateUtil.parse(getFirstDayOfMonth(endDate));
            }
            for (Date tmpDate = beginDate; tmpDate.compareTo(endDate)<= 0; tmpDate = addMonth(tmpDate, 3)) {
                if (tabCalTag.equals("0")) {
                    baseDate.add(getFirstDayOfMonth(tmpDate));
                } else {
                    baseDate.add(getLastDayOfMonth(tmpDate));
                }
            }
            return baseDate;
        }
        return baseDate;
    }

    public static String getDate(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }


    public static String getDate(String format,String datestr) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        Date dt=new Date(datestr);
        return t.format(dt);
    }

    /**
     * 将Date类型字符串转化成特定日期字符串
     * @author shanqiyu
     * @param format
     * @param datestr
     * @return
     */
    public static String changeDateToString(String format,String datestr) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        try {
            Date date = sdf.parse(datestr);
            return t.format(date);
        } catch (ParseException e) {
            return t.format(new Date());
        }
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String getCurrentTime(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String getCurrentDateTime() {
        String format = "yyyy-MM-dd HH:mm:ss";
        return getCurrentDateTime(format);
    }
    public static Date getweekFristDay(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime( date);
        c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new Date(c1.getTimeInMillis());
    }

    public static Date getweekLastDay(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime( date);
        c1.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        return new Date(c1.getTimeInMillis());
    }

    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(7);
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(7);
    }

    public static int getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(5);
    }

    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    public static int getMaxDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(5);
    }



    // 返回第几个月份，不是几月
    // 季度一年四季， 第一季度：2月-4月， 第二季度：5月-7月， 第三季度：8月-10月， 第四季度：11月-1月
    public static int getQuarterInMonth(int month, boolean isQuarterStart) {
        int months[] = { 0, 3, 6, 9 };
        if (!isQuarterStart) {
            months = new int[] { 2, 5, 8, 11 };
        }
        if (month >= 0 && month <=  2)
            return months[0];
        else if (month >= 3 && month <= 5)
            return months[1];
        else if (month >= 6 && month <= 8)
            return months[2];
        else
            return months[3];
    }
    public static String getLastDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        cal.set(5, getMaxDayOfMonth(parse(date)));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
    public static String month(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        cal.set(5, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
    public static String getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, getMaxDayOfMonth(date));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
    public static String getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
    public static int getDayOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(6);
    }

    public static int getDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(6);
    }

    public static int getDayOfWeek(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(7);
    }

    public static int getDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(5);
    }

    public static int getDayOfYear(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(6);
    }

    /**
     * 获取当前月所在的季度
     * @return
     * @author zhan.hang
     */
    public static String getSeasonOfMonth(){
        String strSeason = "";
        int year, month, day;
        int season = 1;
        int array[][] = new int[][]{ {1,2,3}, {4,5,6}, {7,8,9}, {10,11,12} };

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONDAY) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        switch (month) {
            case 1:
            case 2:
            case 3:
                season = 4;
                break;
            case 4:
            case 5:
            case 6:
                season = 1;
                break;
            case 7:
            case 8:
            case 9:
                season = 2;
                break;
            case 10:
            case 11:
            case 12:
                season = 3;
                break;
            default:
                season = 1;
                break;
        }


        int start_month = array[season-1][0];
        int end_month = array[season-1][2];
        strSeason = year + "-" + month + "-" +day;
        strSeason += " in [ " + year + "-" + start_month + " : " + year + "-" + end_month + " ]";

        return season+"";
    }

    public static String getCurrentDateTime(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String toString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String toDateTimeString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String toString(Date date, String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(date);
    }

    public static String toTimeString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    public static int compare(String date1, String date2) {
        return compare(date1, date2, "yyyy-MM-dd");
    }

    public static int compareTime(String time1, String time2) {
        return compareTime(time1, time2, "HH:mm:ss");
    }

    public static int compare(String date1, String date2, String format) {
        Date d1 = parse(date1, format);
        Date d2 = parse(date2, format);
        return d1.compareTo(d2);
    }

    public static int compareTime(String time1, String time2, String format) {
        String[] arr1 = time1.split(":");
        String[] arr2 = time2.split(":");
        if (arr1.length < 2) {
            throw new RuntimeException("错误的时间值:" + time1);
        }
        if (arr2.length < 2) {
            throw new RuntimeException("错误的时间值:" + time2);
        }
        int h1 = Integer.parseInt(arr1[0]);
        int m1 = Integer.parseInt(arr1[1]);
        int h2 = Integer.parseInt(arr2[0]);
        int m2 = Integer.parseInt(arr2[1]);
        int s1 = 0;
        int s2 = 0;
        if (arr1.length == 3) {
            s1 = Integer.parseInt(arr1[2]);
        }
        if (arr2.length == 3) {
            s2 = Integer.parseInt(arr2[2]);
        }
        if ((h1 < 0) || (h1 > 23) || (m1 < 0) || (m1 > 59) || (s1 < 0)
                || (s1 > 59)) {
            throw new RuntimeException("错误的时间值:" + time1);
        }
        if ((h2 < 0) || (h2 > 23) || (m2 < 0) || (m2 > 59) || (s2 < 0)
                || (s2 > 59)) {
            throw new RuntimeException("错误的时间值:" + time2);
        }
        if (h1 != h2) {
            return h1 > h2 ? 1 : -1;
        }
        if (m1 == m2) {
            if (s1 == s2) {
                return 0;
            }
            return s1 > s2 ? 1 : -1;
        }
        return m1 > m2 ? 1 : -1;
    }

    public static boolean isTime(String time) {
        String[] arr = time.split(":");
        if (arr.length < 2) {
            return false;
        }
        try {
            int h = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            int s = 0;
            if (arr.length == 3) {
                s = Integer.parseInt(arr[2]);
            }
            if ((h < 0) || (h > 23) || (m < 0) || (m > 59) || (s < 0)
                    || (s > 59)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isDate(String date) {
        String[] arr = date.split("-");
        if (arr.length < 3) {
            return false;
        }
        try {
            int y = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            int d = Integer.parseInt(arr[2]);
            if ((y < 0) || (m > 12) || (m < 0) || (d < 0) || (d > 31)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int t = cal.get(7);
        if ((t == 7) || (t == 1)) {
            return true;
        }
        return false;
    }

    public static boolean isWeekend(String str) {
        return isWeekend(parse(str));
    }

    public static Date parse(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parse(String str, String format) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        try {
            SimpleDateFormat t = new SimpleDateFormat(format);
            return t.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateTime(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        if (str.length() <= 10) {
            return parse(str);
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateTime(String str, String format) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        try {
            SimpleDateFormat t = new SimpleDateFormat(format);
            return t.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date addMinute(Date date, int count) {
        return new Date(date.getTime() + 60000L * count);
    }

    public static Date addHour(Date date, int count) {
        return new Date(date.getTime() + 3600000L * count);
    }

    /*
     * 计算一个日期加上day天后的日期 date为“yyyy-MM-dd”的形式
     */
    public static Date addDate(Date date, int day) {
        Date d = date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 格式化日期
     *
     * @param dateTime
     *            Date类型时间
     * @return String类型时间
     * @author colin
     */
    public static String formatDateString(Date dateTime) {
        // 格式化形式
        DateFormat sdf = new SimpleDateFormat(Format_Date);
        // 定义返回值
        String sysDate = null;
        if (null == dateTime) {
            return null;
        } else {
            // 格式化
            // sdf = DateFormat.getDateInstance(DateFormat.MEDIUM);
            sysDate = sdf.format(dateTime);
        }
        return sysDate;
    }


    public static Date addDay(Date date, int count) {
        return new Date(date.getTime() + 86400000L * count);
    }

    public static Date addWeek(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(3, count);
        return c.getTime();
    }

    public static Date addMonth(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, count);
        return c.getTime();
    }

    public static Date addYear(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(1, count);
        return c.getTime();
    }

    public static String toDisplayDateTime(String date) {
        if (StringUtil.isEmpty(date)) {
            return null;
        }
        try {
            if (isDate(date)) {
                return toDisplayDateTime(parse(date));
            }
            SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = t.parse(date);
            return toDisplayDateTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "不是标准格式时间!";
    }

    public static String toDisplayDateTime(Date date) {
        long minite = (System.currentTimeMillis() - date.getTime()) / 60000L;
        if (minite < 60L) {
            return toString(date, "MM-dd") + " " + minite + "分钟前";
        }
        if (minite < 1440L) {
            return toString(date, "MM-dd") + " " + minite / 60L + "小时前";
        }
        return toString(date, "MM-dd") + " " + minite / 1440L + "天前";
    }

    /**
     * 功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author
     */
    public static long getDaySub(String beginDateStr,String endDateStr)
    {
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 说明：比较两个日期大小,Calendar格式<br>
     * 备注：cal1>cal2 返回1 ,cal1 = cal2 返回0 , cal1 <cal2 返回 -1<br>
     *
     * @param cal1
     *            日期1
     * @param cal2
     *            日期2
     * @return 比较结果
     */
    public static int CompareDate(Calendar cal1, Calendar cal2) {
        if (cal1.getTimeInMillis() > cal2.getTimeInMillis()) {
            return 1;
        } else if (cal1.getTimeInMillis() == cal2.getTimeInMillis()) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 说明：比较两个日期大小,Date格式<br>
     * 备注：date1>date2 返回1 ,date1 = date2 返回0 , date1 <date2 返回 -1<br>
     *
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @return 比较结果
     */
    public static int CompareDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() == date2.getTime()) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 说明：比较两个日期大小,yyyy-MM-dd HH:mm:ss格式<br>
     * 备注：d1>d2 返回1 ,d1 = d2 返回0 , d1 <d2 返回 -1<br>
     *
     * @param d1
     *            日期1
     * @return 比较结果
     * @throws ParseException
     *             格式转化异常
     */
    public static int CompareDate(String d1, String d2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Format_DateTime);
        Date date1 = sdf.parse(d1);
        Date date2 = sdf.parse(d2);
        return CompareDate(date1, date2);
    }


    /**
     * @Description: 获取时间戳
     * @param @return
     * @return String
     * @throws
     * @author yejingjiang
     * @date 2015-7-15 下午5:44:44
     */
    public static String getTimeStamp()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  currentTimeStamp= sdf.format(new Date());
        String  []tmStamp= currentTimeStamp.split(" ");
        String dateStr=tmStamp[0]+"-";
        String timeStr=tmStamp[1].replaceAll(":",".")+".000000";
        return dateStr+timeStr;
    }

    /**
     * 获  取 标 准 北 京 时 间 戳
     *
     * @author Mr. SQS
     * @throws
     * @time 2015-12-27  下午9:25:30
     */
    public static String getTimeStamp2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTimeStamp = sdf.format(new Date());
        return currentTimeStamp;
    }


    /**
     * 获取当前时间几天后的时间
     * @author haozt
     * @since 2019-6-6
     * @param day 天数
     * @return 几天前的日期
     */
    public static String getCurrentDateAfter(int day) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return new SimpleDateFormat(Format_Date).format(now.getTime());
    }

    public static Date getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Date getLastDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    public static Date getLastDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        return getLastDayOfYear(currentYear);
    }

    /**
     * 把一些日期的向前或向后调整为工作日
     *
     * @param workdays 待调整的日期
     * @param holidays 节假日
     * @param adjustTo 向前调整：1；向后调整：-1；不调整：0；3：向后向前
     * @return
     */
    public static List<String> getActiveDatesAdjustTo(List<String> workdays, List<String> holidays, Integer adjustTo) {
        for (int i = 0; i < workdays.size(); i++) {
            String dateStr =  workdays.get(i);
//            while (holidays.contains(dateStr)) {
//                try {
//                    dateStr = getDateAdjustTo(dateStr, adjustTo);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
            try {
                dateStr = getDateAdjustTo(dateStr, adjustTo, holidays);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            workdays.set(i, dateStr);
        }

        return workdays;
    }

    /**
     * 把一个日期（格式：yyyy-MM-dd）调整到另一个日期
     *
     * @param dateStr
     * @param adjustTo
     * @param holidays
     * @return
     * @throws ParseException
     */
    public static String getDateAdjustTo(String dateStr, Integer adjustTo, List<String> holidays) throws ParseException {
        if (adjustTo == null) {
            return dateStr;
        }
        switch (adjustTo) {
            case 0:
                return dateStr;
            case 1:
                return getDateAdjustForward(dateStr, holidays);
            case 2:
                return getDateAdjustBack(dateStr, holidays);
            case 3:
                return getDateAdjustRegret(dateStr, holidays);
            default:
                return dateStr;
        }
    }

    /**
     * 营业日向前
     *
     * @param dateStr
     * @param holidays
     * @return
     * @throws ParseException
     */
    public static String getDateAdjustForward(String dateStr, List<String> holidays) throws ParseException {
        while (holidays.contains(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    /**
     * 营业日向后
     *
     * @param dateStr
     * @param holidays
     * @return
     * @throws ParseException
     */
    public static String getDateAdjustBack(String dateStr, List<String> holidays) throws ParseException {
        while (holidays.contains(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date = calendar.getTime();
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    /**
     * 营业日向前向后调整
     *
     * @param dateStr
     * @param holidays
     * @return
     * @throws ParseException
     */
    public static String getDateAdjustRegret(String dateStr, List<String> holidays) throws ParseException {
        while (holidays.contains(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int month1 = calendar.get(Calendar.MONTH);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            int month2 = calendar.get(Calendar.MONTH);
            if (month2 > month1) {
                return getDateAdjustForward(dateStr, holidays);
            } else {
                date = calendar.getTime();
                dateStr = sdf.format(date);
            }
        }
        return dateStr;
    }

    public static Date getYesterday(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
}