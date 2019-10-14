package com.joyin.sendrule2.chain;

import com.joyin.sendrule2.MyCronExpression;
import com.joyin.sendrule2.SendRuleUnit;
import com.joyin.sendrule2.util.BaseDto;
import com.joyin.sendrule2.util.StringUtil;
import java.text.ParseException;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 20:24
 */
public class MonthlySendRuleHandler extends SendRuleHandler {

    String sendRuleUnit = SendRuleUnit.MONTHLY.toString();

    @Override
    boolean sendLatch(BaseDto sendRule) {
        return sendRuleUnit.equals(sendRule.get("SEND_RULE_UNIT"));
    }

    @Override
    public void echo(BaseDto sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {

        String month = sendRule.getAsString("MONTH");
        String day_of_month = sendRule.getAsString("DAY_OF_MONTH");
        String week_of_month = sendRule.getAsString("WEEK_OF_MONTH");
        String day_of_week = sendRule.getAsString("DAY_OF_WEEK");

        // 第几个周的周几
        if (week_of_month.contains(",") || day_of_week.contains(",")) {
            try {
                String[] week_of_months = week_of_month.split(",");
                for (int i = 0; i < week_of_months.length; i++) {
                    week_of_month = week_of_months[i];

                    if (StringUtil.isNotEmpty(week_of_month)) {
                        String[] day_of_weeks = day_of_week.split(",");

                        for (int j = 0; j < day_of_weeks.length; j++) {
                            MyCronExpression myExp = new MyCronExpression("0 0 0 * * ? *");
                            myExp.setMonths(month);
                            myExp.setDaysOfMonth("?");

                            myExp.setDaysOfWeek((day_of_weeks[j] + "#" + week_of_month).replace("#L", "L"));
                            cronExpression.getMyCronExpressions().add(myExp);
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            cronExpression.setMonths(month);

            if (StringUtil.isNotEmpty(day_of_month)) {
                cronExpression.setDaysOfMonth(day_of_month);
                cronExpression.setDaysOfWeek("?");
            }
            if (StringUtil.isNotEmpty(week_of_month)) {
                cronExpression.setDaysOfMonth("?");
                cronExpression.setDaysOfWeek((day_of_week + "#" + week_of_month).replace("#L", "L"));
            }
        }

        System.out.println("MonthlySendRuleHandler->getCronExpression" + cronExpression.getCronExpression());
        System.out.println("MonthlySendRuleHandler->getMyCronExpressions" + cronExpression.getMyCronExpressions());
        handlerChain.handle(sendRule, cronExpression, handlerChain);
    }
}