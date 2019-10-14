package com.joyin.sendrule1.chain;


import com.joyin.sendrule1.MyCronExpression;
import java.util.Map;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 19:22
 */
public abstract class SendRuleHandler {

    abstract boolean sendLatch(Map sendRule);

    abstract void echo(Map sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain);

    public void handle(Map sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {
        if (sendLatch(sendRule)) {
            echo(sendRule, cronExpression, handlerChain);
        } else {
            handlerChain.handle(sendRule, cronExpression, handlerChain);
        }
    }
}
