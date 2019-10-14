package com.joyin.sendrule2.chain;


import com.joyin.sendrule2.MyCronExpression;
import com.joyin.sendrule2.util.BaseDto;
import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 19:26
 */
public class SendRuleHandlerChain extends SendRuleHandler {

    private List<SendRuleHandler> ruleHandlers = new ArrayList<SendRuleHandler>();
    private int idx = 0;

    public SendRuleHandlerChain addHandler(SendRuleHandler handler) {
        ruleHandlers.add(handler);
        return this;
    }

    @Override
    boolean sendLatch(BaseDto sendRule) {
        return true;
    }

    @Override
    public void echo(BaseDto sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {
        if (idx == ruleHandlers.size()) {
            return;
        }
        SendRuleHandler sendRuleHandler = ruleHandlers.get(idx);
        idx++;
        sendRuleHandler.handle(sendRule, cronExpression, handlerChain);
    }
}
