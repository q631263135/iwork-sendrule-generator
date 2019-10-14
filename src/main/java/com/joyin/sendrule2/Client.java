package com.joyin.sendrule2;

import com.joyin.sendrule2.chain.DailySendRuleHandler;
import com.joyin.sendrule2.chain.IrregularSendRuleHandler;
import com.joyin.sendrule2.chain.MonthlySendRuleHandler;
import com.joyin.sendrule2.chain.SendRuleHandlerChain;
import com.joyin.sendrule2.chain.WeeklySendRuleHandler;
import com.joyin.sendrule2.util.BaseDto;
import com.joyin.sendrule2.util.DateUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        MyCronExpression myCronExpression = new MyCronExpression("0 0 0 * * ?");

        SendRuleHandlerChain handlerChain = new SendRuleHandlerChain();
        // 链的原始用途是对于日、月、周年都分别做处理
        // 现在是每个处理之前都有条件判断：sendLatch；如果每个都需要走一遍处理，则关闭这块；
        handlerChain.addHandler(new DailySendRuleHandler())
                .addHandler(new WeeklySendRuleHandler())
                .addHandler(new MonthlySendRuleHandler())
                .addHandler(new IrregularSendRuleHandler());

//        BaseDto baseDto = sendRule_daily();
//        BaseDto baseDto = sendRule_weekly();
//        BaseDto baseDto = sendRule_monthly1();
        BaseDto baseDto = sendRule_monthly2();
//        BaseDto baseDto = sendRule_irregular();
        handlerChain.handle(baseDto, myCronExpression, handlerChain);

        if (myCronExpression.getMyCronExpressions().size() > 0) {
            for (MyCronExpression cronExpression : myCronExpression.getMyCronExpressions()) {
                printValidDate(baseDto, cronExpression);
            }
        }
        else {
            printValidDate(baseDto, myCronExpression);
        }
    }

    private static void printValidDate(BaseDto baseDto, MyCronExpression cronExpression) throws ParseException {
        String cronExpressionStr = cronExpression.getCronExpression();

        if (CronExpression.isValidExpression(cronExpressionStr)) {
            MyCronExpression myExp = new MyCronExpression(cronExpressionStr);
            List<String> validTimeList = myExp.getAllValidTime(DateUtil.getCurrentDay(), DateUtil.getLastDayOfYear());

            System.out.println("营业日调整前： " + validTimeList);

            List<String> datesAdjust = DateUtil.getActiveDatesAdjustTo(validTimeList, holidayList(), baseDto
                    .getAsInteger("DAY_ADJUST"));

            System.out.println("营业日调整后： " + datesAdjust);
        }
    }

    private static BaseDto sendRule_daily() {
        BaseDto baseDto = new BaseDto();
        baseDto.put("SEND_RULE_CODE", UUID.randomUUID());
        baseDto.put("SEND_RULE_UNIT", "0"); // 0-每天；1-每周；2-每月；99-不规则

        // >>> 周
        baseDto.put("DAY_OF_WEEK", "0"); // 每周几

        // >>> 月
        baseDto.put("MONTH", ""); // 哪几个月（1-12，*表示所有月）
        baseDto.put("DAY_OF_MONTH", ""); // 每月的哪一天（1-31，L表示最后一天）

        baseDto.put("WEEK_OF_MONTH", ""); // 每月第几周（1-4，L表示最后一周）
        baseDto.put("WEEK_OF_MONTH", ""); // 每月的第几周周几

        baseDto.put("DAY_ADJUST", "0"); // 0-不调整；1-向前；2-向后；3-调整的向后
        baseDto.put("CRON_EXPRESSION", "");

        return baseDto;
    }

    private static BaseDto sendRule_weekly() {
        BaseDto baseDto = new BaseDto();

        baseDto.put("SEND_RULE_CODE", UUID.randomUUID());
        baseDto.put("SEND_RULE_UNIT", "1"); // 0-每天；1-每周；2-每月；99-不规则

        // >>> 周
        baseDto.put("DAY_OF_WEEK", "SUN,TUE"); // 每周几

        // >>> 月
        baseDto.put("MONTH", ""); // 哪几个月（1-12，*表示所有月）
        baseDto.put("DAY_OF_MONTH", ""); // 每月的哪一天（1-31，L表示最后一天）

        baseDto.put("WEEK_OF_MONTH", ""); // 每月第几周（1-4，L表示最后一周）
        baseDto.put("WEEK_OF_MONTH", ""); // 每月的第几周周几

        baseDto.put("DAY_ADJUST", "0"); // 0-不调整；1-向前；2-向后；3-调整的向后
        baseDto.put("CRON_EXPRESSION", "");

        return baseDto;
    }

    private static BaseDto sendRule_monthly1() {
        BaseDto baseDto = new BaseDto();
        baseDto.put("SEND_RULE_CODE", UUID.randomUUID());
        baseDto.put("SEND_RULE_UNIT", "2"); // 0-每天；1-每周；2-每月；99-不规则

        // >>> 周
        baseDto.put("DAY_OF_WEEK", ""); // 每周几

        // >>> 月
        baseDto.put("MONTH", "*"); // 哪几个月（1-12，*表示所有月）
        baseDto.put("DAY_OF_MONTH", "30"); // 每月的哪一天（1-31，L表示最后一天）
        baseDto.put("WEEK_OF_MONTH", ""); // 每月第几周（1-4，L表示最后一周）
        baseDto.put("DAY_OF_WEEK", ""); // 每周几

        baseDto.put("DAY_ADJUST", "3"); // 0-不调整；1-向前；2-向后；3-调整的向后
        baseDto.put("CRON_EXPRESSION", "");

        return baseDto;
    }

    private static BaseDto sendRule_monthly2() {
        BaseDto baseDto = new BaseDto();
        baseDto.put("SEND_RULE_CODE", UUID.randomUUID());
        baseDto.put("SEND_RULE_UNIT", "2"); // 0-每天；1-每周；2-每月；99-不规则

        // >>> 周
        baseDto.put("DAY_OF_WEEK", "1"); // 每周几

        // >>> 月
        baseDto.put("MONTH", "*"); // 哪几个月（1-12，*表示所有月）
        baseDto.put("DAY_OF_MONTH", ""); // 每月的哪一天（1-31，L表示最后一天）

        baseDto.put("WEEK_OF_MONTH", "L"); // 每月第几周（1-4，L表示最后一周）

        baseDto.put("DAY_ADJUST", "0"); // 0-不调整；1-向前；2-向后；3-调整的向后
        baseDto.put("CRON_EXPRESSION", "");

        return baseDto;
    }

    private static BaseDto sendRule_irregular() {
        BaseDto baseDto = new BaseDto();
        baseDto.put("SEND_RULE_CODE", UUID.randomUUID());
        baseDto.put("SEND_RULE_UNIT", "99"); // 0-每天；1-每周；2-每月；99-不规则

        baseDto.put("DAYS", "2019-08-30;2019-09-27;");

        // >>> 周
        baseDto.put("DAY_OF_WEEK", ""); // 每周几

        // >>> 月
        baseDto.put("MONTH", ""); // 哪几个月（1-12，*表示所有月）
        baseDto.put("DAY_OF_MONTH", ""); // 每月的哪一天（1-31，L表示最后一天）

        baseDto.put("WEEK_OF_MONTH", ""); // 每月第几周（1-4，L表示最后一周）
        baseDto.put("WEEK_OF_MONTH", ""); // 每月的第几周周几

        baseDto.put("DAY_ADJUST", "3"); // 0-不调整；1-向前；2-向后；3-调整的向后
        baseDto.put("CRON_EXPRESSION", "");

        return baseDto;
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
        holidayList.add("2019-09-30");
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
