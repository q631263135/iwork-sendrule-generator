package com.joyin.sendrule1.chain;

import com.joyin.sendrule1.MyCronExpression;
import com.joyin.sendrule1.SendRuleUnit;
import java.util.Map;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 19:23
 */
public class DailySendRuleHandler extends SendRuleHandler {

    String sendRuleUnit = SendRuleUnit.DAILY.toString();

    @Override
    boolean sendLatch(Map sendRule) {
        return sendRuleUnit.equals(sendRule.get("SEND_RULE_UNIT"));
    }

    @Override
    public void echo(Map sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {
        System.out.println("DailySendRuleHandler");
        handlerChain.handle(sendRule, cronExpression, handlerChain);
    }
}
