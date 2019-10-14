package com.joyin.sendrule2.chain;


import com.joyin.sendrule2.MyCronExpression;
import com.joyin.sendrule2.SendRuleUnit;
import com.joyin.sendrule2.util.BaseDto;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 20:24
 */
public class WeeklySendRuleHandler extends SendRuleHandler {
    String sendRuleUnit = SendRuleUnit.WEEKLY.toString();

    @Override
    boolean sendLatch(BaseDto sendRule) {
        return sendRuleUnit.equals(sendRule.get("SEND_RULE_UNIT"));
    }

    @Override
    public void echo(BaseDto sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {

        String day_of_week = (String) sendRule.get("DAY_OF_WEEK");
        cronExpression.setDaysOfMonth("?");
        cronExpression.setDaysOfWeek(day_of_week);

        System.out.println("WeeklySendRuleHandler");
        handlerChain.handle(sendRule, cronExpression, handlerChain);
    }
}
