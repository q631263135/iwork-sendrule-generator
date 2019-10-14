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
public class WeeklySendRuleHandler extends SendRuleHandler {
    String sendRuleUnit = SendRuleUnit.WEEKLY.toString();

    @Override
    boolean sendLatch(Map sendRule) {
        return sendRuleUnit.equals(sendRule.get("SEND_RULE_UNIT"));
    }

    @Override
    public void echo(Map sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {

        String day_of_week = (String) sendRule.get("DAY_OF_WEEK");
        cronExpression.setDaysOfMonthExp("?");
        cronExpression.setDaysOfWeekExp(day_of_week);

        System.out.println("WeeklySendRuleHandler");
        handlerChain.handle(sendRule, cronExpression, handlerChain);
    }
}
