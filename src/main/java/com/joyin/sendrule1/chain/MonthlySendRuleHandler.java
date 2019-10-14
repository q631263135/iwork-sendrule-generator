package com.joyin.sendrule1.chain;

import com.joyin.sendrule1.MyCronExpression;
import com.joyin.sendrule1.SendRuleUnit;
import java.util.Map;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 20:24
 */
public class MonthlySendRuleHandler extends SendRuleHandler {
    String sendRuleUnit = SendRuleUnit.MONTHLY.toString();

    @Override
    boolean sendLatch(Map sendRule) {
        return sendRuleUnit.equals(sendRule.get("SEND_RULE_UNIT"));
    }

    @Override
    public void echo(Map sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {

        String month = (String) sendRule.get("MONTH");
        String day_of_month = (String) sendRule.get("DAY_OF_MONTH");
        String week_of_month = (String) sendRule.get("WEEK_OF_MONTH");
        String day_of_week  = (String) sendRule.get("DAY_OF_WEEK");

        cronExpression.setMonthsExp(month);

        if (day_of_month != "") {
            cronExpression.setDaysOfMonthExp(day_of_month);
            cronExpression.setDaysOfWeekExp("?");
        }
        if (week_of_month != "") {
            cronExpression.setDaysOfMonthExp("?");
            cronExpression.setDaysOfWeekExp((day_of_week + "#" + week_of_month).replace("#L", "L"));
        }

        System.out.println("MonthlySendRuleHandler");
        handlerChain.handle(sendRule, cronExpression, handlerChain);
    }
}
