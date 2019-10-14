package com.joyin.sendrule2;

import com.joyin.sendrule2.util.DateUtil;
import java.text.ParseException;
import java.util.ArrayList;
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
    private String seconds;
    private String minutes;
    private String hours;
    private String daysOfMonth;
    private String months;
    private String daysOfWeek;
    private List<String> allValidTime = new ArrayList<String>();

    private List<MyCronExpression> myCronExpressions = new ArrayList<MyCronExpression>();

    public MyCronExpression(String cronExpression) throws ParseException {
        super(cronExpression);
        StringTokenizer tokenizer = new StringTokenizer(cronExpression, " \t", false);
        seconds = tokenizer.nextToken().trim();
        minutes = tokenizer.nextToken().trim();
        hours = tokenizer.nextToken().trim();
        daysOfMonth = tokenizer.nextToken().trim();
        months = tokenizer.nextToken().trim();
        daysOfWeek = tokenizer.nextToken().trim();
    }

    @Override
    public String getCronExpression() {
        return seconds + " " + minutes + " " + hours + " " + daysOfMonth + " " + months + " " +
                daysOfWeek;
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

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setDaysOfMonth(String daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public List<MyCronExpression> getMyCronExpressions() {
        return myCronExpressions;
    }
}
