package com.joyin.sendrule1;

import com.joyin.sendrule1.chain.DailySendRuleHandler;
import com.joyin.sendrule1.chain.MonthlySendRuleHandler;
import com.joyin.sendrule1.chain.SendRuleHandlerChain;
import com.joyin.sendrule1.chain.WeeklySendRuleHandler;
import com.joyin.sendrule1.util.DateUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.quartz.CronExpression;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 16:29
 */
public class Client {

    public static void main(String[] args) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        MyCronExpression cronExpression = new MyCronExpression("0 0 0 * * ?");

        SendRuleHandlerChain handlerChain = new SendRuleHandlerChain();
        handlerChain.addHandler(new DailySendRuleHandler())
                .addHandler(new WeeklySendRuleHandler())
                .addHandler(new MonthlySendRuleHandler());

//        Map Map = sendRule_daily();
//        Map Map = sendRule_weekly();
        Map Map = sendRule_monthly1();
//        Map Map = sendRule_monthly2();

        handlerChain.handle(Map, cronExpression, handlerChain);
        System.out.println("CronExpression: " + cronExpression.getCronExpression());

        if (CronExpression.isValidExpression(cronExpression.getCronExpression())) {
            MyCronExpression myExp = new MyCronExpression(cronExpression.getCronExpression());
            List<String> validTimeList = myExp.getAllValidTime(DateUtil.getCurrentDay(), DateUtil.getLastDayOfYear());

            System.out.println("营业日调整前： " + validTimeList);

            List<String> datesAdjustTo = DateUtil.getActiveDatesAdjustTo(validTimeList, holidayList(), Integer.valueOf(Map
                    .get("DAY_ADJUST").toString()));

            System.out.println("营业日调整后： " + datesAdjustTo);
        }

    }

    private static Map sendRule_daily() {
        Map Map = new HashMap();
        Map.put("SEND_RULE_CODE", UUID.randomUUID());
        Map.put("SEND_RULE_UNIT", "0"); // 0-每天；1-每周；2-每月；99-不规则

        // >>> 周
        Map.put("DAY_OF_WEEK", "0"); // 每周几

        // >>> 月
        Map.put("MONTH", ""); // 哪几个月（1-12，*表示所有月）
        Map.put("DAY_OF_MONTH", ""); // 每月的哪一天（1-31，L表示最后一天）

        Map.put("WEEK_OF_MONTH", ""); // 每月第几周（1-4，L表示最后一周）
        Map.put("WEEK_OF_MONTH", ""); // 每月的第几周周几

        Map.put("DAY_ADJUST", "0"); // 0-不调整；1-向前；2-向后；3-调整的向后
        Map.put("CRON_EXPRESSION", "");

        return Map;
    }

    private static Map sendRule_weekly() {
        Map Map = new HashMap();

        Map.put("SEND_RULE_CODE", UUID.randomUUID());
        Map.put("SEND_RULE_UNIT", "1"); // 0-每天；1-每周；2-每月；99-不规则

        // >>> 周
        Map.put("DAY_OF_WEEK", "SUN,TUE"); // 每周几

        // >>> 月
        Map.put("MONTH", ""); // 哪几个月（1-12，*表示所有月）
        Map.put("DAY_OF_MONTH", ""); // 每月的哪一天（1-31，L表示最后一天）

        Map.put("WEEK_OF_MONTH", ""); // 每月第几周（1-4，L表示最后一周）
        Map.put("WEEK_OF_MONTH", ""); // 每月的第几周周几

        Map.put("DAY_ADJUST", "0"); // 0-不调整；1-向前；2-向后；3-调整的向后
        Map.put("CRON_EXPRESSION", "");

        return Map;
    }

    private static Map sendRule_monthly1() {
        Map Map = new HashMap();
        Map.put("SEND_RULE_CODE", UUID.randomUUID());
        Map.put("SEND_RULE_UNIT", "2"); // 0-每天；1-每周；2-每月；99-不规则

        // >>> 周
        Map.put("DAY_OF_WEEK", ""); // 每周几

        // >>> 月
        Map.put("MONTH", "*"); // 哪几个月（1-12，*表示所有月）
        Map.put("DAY_OF_MONTH", "30"); // 每月的哪一天（1-31，L表示最后一天）
        Map.put("WEEK_OF_MONTH", ""); // 每月第几周（1-4，L表示最后一周）
        Map.put("DAY_OF_WEEK", ""); // 每周几

        Map.put("DAY_ADJUST", "3"); // 0-不调整；1-向前；2-向后；3-调整的向后
        Map.put("CRON_EXPRESSION", "");

        return Map;
    }

    private static Map sendRule_monthly2() {
        Map Map = new HashMap();
        Map.put("SEND_RULE_CODE", UUID.randomUUID());
        Map.put("SEND_RULE_UNIT", "2"); // 0-每天；1-每周；2-每月；99-不规则

        // >>> 周
        Map.put("DAY_OF_WEEK", ""); // 每周几

        // >>> 月
        Map.put("MONTH", "*"); // 哪几个月（1-12，*表示所有月）
        Map.put("DAY_OF_MONTH", ""); // 每月的哪一天（1-31，L表示最后一天）

        Map.put("WEEK_OF_MONTH", "L"); // 每月第几周（1-4，L表示最后一周）
        Map.put("WEEK_OF_MONTH", "1"); // 每月的第几周周几

        Map.put("DAY_ADJUST", "0"); // 0-不调整；1-向前；2-向后；3-调整的向后
        Map.put("CRON_EXPRESSION", "");

        return Map;
    }

    private static List<String> holidayList() {
        List<String> holidayList = new ArrayList();
        holidayList.add("2019-08-24");
        holidayList.add("2019-08-25");
        holidayList.add("2019-08-30");
        holidayList.add("2019-08-31");
        holidayList.add("2019-09-07");
        holidayList.add("2019-09-08");
        holidayList.add("2019-09-14");
        holidayList.add("2019-09-15");
        holidayList.add("2019-09-21");
        holidayList.add("2019-09-22");
        holidayList.add("2019-09-28");
        holidayList.add("2019-09-29");
        holidayList.add("2019-10-01");
        holidayList.add("2019-10-02");
        holidayList.add("2019-10-03");
        holidayList.add("2019-10-04");
        holidayList.add("2019-10-05");
        holidayList.add("2019-10-06");
        holidayList.add("2019-10-07");

        return holidayList;
    }
}
