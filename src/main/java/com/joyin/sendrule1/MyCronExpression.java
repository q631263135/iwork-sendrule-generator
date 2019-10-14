package com.joyin.sendrule1;

import com.joyin.sendrule1.util.DateUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import org.quartz.CronExpression;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 19:51
 */
public class MyCronExpression extends CronExpression {
    private String secondsExp;
    private String minutesExp;
    private String hoursExp;
    private String daysOfMonthExp;
    private String monthsExp;
    private String daysOfWeekExp;

    private List<String> allValidTime = new ArrayList<String>();
    private Calendar calendar = Calendar.getInstance();

    public MyCronExpression(String cronExpression) throws ParseException {
        super(cronExpression);
        StringTokenizer exprsTok = new StringTokenizer(cronExpression, " \t", false);
        secondsExp = exprsTok.nextToken().trim();
        minutesExp = exprsTok.nextToken().trim();
        hoursExp = exprsTok.nextToken().trim();
        daysOfMonthExp = exprsTok.nextToken().trim();
        monthsExp = exprsTok.nextToken().trim();
        daysOfWeekExp = exprsTok.nextToken().trim();
    }

    @Override
    public String getCronExpression() {
        return secondsExp + " " + minutesExp + " " + hoursExp + " " + daysOfMonthExp + " " + monthsExp + " " +
                daysOfWeekExp;
    }

    public List<String> getAllValidTime(Date startDate, Date deadline) {
        while (DateUtil.CompareDate(startDate, deadline) == -1) {
            startDate = getNextValidTimeAfter(startDate);
            if (DateUtil.CompareDate(startDate, deadline) == -1) {
                allValidTime.add(DateUtil.formatDateString(startDate));
            }
        }

        return allValidTime;
    }

    public String getSecondsExp() {
        return secondsExp;
    }

    public void setSecondsExp(String secondsExp) {
        this.secondsExp = secondsExp;
    }

    public String getMinutesExp() {
        return minutesExp;
    }

    public void setMinutesExp(String minutesExp) {
        this.minutesExp = minutesExp;
    }

    public String getHoursExp() {
        return hoursExp;
    }

    public void setHoursExp(String hoursExp) {
        this.hoursExp = hoursExp;
    }

    public String getDaysOfMonthExp() {
        return daysOfMonthExp;
    }

    public void setDaysOfMonthExp(String daysOfMonthExp) {
        this.daysOfMonthExp = daysOfMonthExp;
    }

    public String getMonthsExp() {
        return monthsExp;
    }

    public void setMonthsExp(String monthsExp) {
        this.monthsExp = monthsExp;
    }

    public String getDaysOfWeekExp() {
        return daysOfWeekExp;
    }

    public void setDaysOfWeekExp(String daysOfWeekExp) {
        this.daysOfWeekExp = daysOfWeekExp;
    }
}
