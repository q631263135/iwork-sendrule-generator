package com.joyin.sendrule2.chain;


import com.joyin.sendrule2.MyCronExpression;
import com.joyin.sendrule2.util.BaseDto;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 19:22
 */
public abstract class SendRuleHandler {

    abstract boolean sendLatch(BaseDto sendRule);

    abstract void echo(BaseDto sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain);

    public void handle(BaseDto sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {
        if (sendLatch(sendRule)) {
            echo(sendRule, cronExpression, handlerChain);
        } else {
            handlerChain.handle(sendRule, cronExpression, handlerChain);
        }
    }
}
