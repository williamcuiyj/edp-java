package eop.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    public static final int MICROSECONDS_OF_HOUR = 3600000;

    public static final int MICROSECONDS_OF_DAY = MICROSECONDS_OF_HOUR * 24;

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计算两个时间的时间差(毫秒)
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static long differ(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return date2.getTime() - date1.getTime();
        }
        return 0;
    }

    public static long getTimeDelta(Date date1, Date date2) {
        // Fix dst date diff problem.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        long date1Time = calendar.getTimeInMillis() + calendar.getTimeZone().getOffset(calendar.getTimeInMillis());
        long date2Time = calendar2.getTimeInMillis() + calendar2.getTimeZone().getOffset(calendar2.getTimeInMillis());

        long offset = date1Time - date2Time;
        return offset;
    }

    public static Date trimDate(Date date) {
        return trimDate(date, Calendar.DATE);
    }

    public static Date trimDate(Date date, int type) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        switch (type) {
        case Calendar.YEAR:
            cal.set(Calendar.MONTH, 0);
        case Calendar.MONTH:
            cal.set(Calendar.DATE, 0);
        case Calendar.DATE:
            cal.set(Calendar.HOUR_OF_DAY, 0);
        case Calendar.HOUR_OF_DAY:
            break;
        case Calendar.HOUR:
            break;
        case Calendar.MINUTE:
            break;
        default:
            cal.set(Calendar.HOUR_OF_DAY, 0);
        }

        if (Calendar.MINUTE != type) {
            cal.set(Calendar.MINUTE, 0);
        }
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return new Timestamp(cal.getTimeInMillis());
    }

    public static long compareTo(Date date1, Date date2) {
        return compareTo(date1, date2, Calendar.DATE);
    }

    public static long compareTo(Date date1, Date date2, int field) {
        if (date1 == null) {
            return Integer.MIN_VALUE;
        } else if (date2 == null) {
            return Integer.MAX_VALUE;
        } else if (date1 == date2 || date1.equals(date2)) {
            return 0;
        } else {
            if (field == Calendar.HOUR || field == Calendar.HOUR_OF_DAY) {
                return getTimeDelta(trimDate(date1, Calendar.HOUR_OF_DAY), trimDate(date2, Calendar.HOUR_OF_DAY))
                    / MICROSECONDS_OF_HOUR;
            } else if (field == Calendar.DATE) {
                return getTimeDelta(trimDate(date1), trimDate(date2)) / MICROSECONDS_OF_DAY;
            } else if (field == Calendar.MONTH) {
                Calendar fromCal = Calendar.getInstance();
                Calendar toCal = Calendar.getInstance();
                if (date1.before(date2)) {
                    fromCal.setTime(date1);
                    toCal.setTime(date2);
                } else {
                    fromCal.setTime(date2);
                    toCal.setTime(date1);
                }
                int fromYear = fromCal.get(Calendar.YEAR);
                int fromMonth = fromCal.get(Calendar.MONTH);
                int toYear = toCal.get(Calendar.YEAR);
                int toMonth = toCal.get(Calendar.MONTH);

                long months = (toYear - fromYear + 1) * 12 - fromMonth - (12 - toMonth);
                if (date1.before(date2)) {
                    return -months;
                } else {
                    return months;
                }
            } else if (field == Calendar.YEAR) {
                return getDateFieldValue(date1, Calendar.YEAR) - getDateFieldValue(date2, Calendar.YEAR);
            } else if (field == Calendar.MINUTE) {
                return getTimeDelta(date1, date2) / (1000 * 60);
            } else if (field == Calendar.DAY_OF_WEEK) {
                date1 = eop.util.DateUtil.getNextDate(date1, Calendar.DATE,
                    1 - eop.util.DateUtil.getDateFieldValue(date1, Calendar.DAY_OF_WEEK));
                date2 = eop.util.DateUtil.getNextDate(date2, Calendar.DATE,
                    1 - eop.util.DateUtil.getDateFieldValue(date2, Calendar.DAY_OF_WEEK));
                return getTimeDelta(trimDate(date1), trimDate(date2)) / (MICROSECONDS_OF_DAY * 7);
            } else if (field == Calendar.DAY_OF_YEAR) {
            	Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date2);
                return calendar.get(Calendar.YEAR) - 1900 - calendar2.get(Calendar.YEAR) - 1900;
            } else {
                return date1.compareTo(date2);
            }
        }
    }

    public static int getDateFieldValue(Date date, int field) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(field);
    }

    public static Date getNextDate(Date date, int type) {
        return getNextDate(date, type, 1);
    }

    /**
     * 获取某日的后一天日期
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+1);

        String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }


    /**
     * update by xiaoming(a)wallaw.cn 2015-05-23
     * 返回日期数据偏移后的结果
     * @param date
     * @param type 计时单位
     * @param duration 偏移量
     * @return
     */
    public static Date getNextDate(Date date, int type, int duration) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, duration);

        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获得指定年份的所有周末
     * @param year
     * @return
     */
    public static List<Date> getWeekends(int year){
        List<Date> list = new ArrayList();
        Calendar calendar = new GregorianCalendar(year, 0, 1);
        int i = 1;
        while (calendar.get(Calendar.YEAR) < year + 1) {
            calendar.set(Calendar.WEEK_OF_YEAR, i++);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if (calendar.get(Calendar.YEAR) == year) {
                list.add(calendar.getTime());
            }
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            if (calendar.get(Calendar.YEAR) == year) {
                list.add(calendar.getTime());
            }
        }
        return list;
    }

    /**
     * 当日是周末
     * @param date
     * @return
     */
    public static boolean todayIsWeekend(Date date){
        int year = Integer.parseInt(eop.util.DateUtil.format(date, "yyyy"));
        List<Date> list = eop.util.DateUtil.getWeekends(year);
        for (Date holiday : list){
            long compare = eop.util.DateUtil.compareTo(date,holiday);
            if (compare == 0L){//当日是周末
                return true;
            }
        }
        return false;
    }

}
