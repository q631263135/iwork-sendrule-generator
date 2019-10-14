package com.joyin.sendrule2.chain;

import com.joyin.sendrule2.MyCronExpression;
import com.joyin.sendrule2.SendRuleUnit;
import com.joyin.sendrule2.util.BaseDto;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 19:23
 */
public class DailySendRuleHandler extends SendRuleHandler {

    String sendRuleUnit = SendRuleUnit.DAILY.toString();

    @Override
    boolean sendLatch(BaseDto sendRule) {
        return sendRuleUnit.equals(sendRule.get("SEND_RULE_UNIT"));
    }

    @Override
    public void echo(BaseDto sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {
        System.out.println("DailySendRuleHandler");
        handlerChain.handle(sendRule, cronExpression, handlerChain);
    }
}
