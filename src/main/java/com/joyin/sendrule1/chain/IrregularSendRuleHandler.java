package com.joyin.sendrule1.chain;

import com.joyin.fyzg.domain.proxy.sendrule.MyCronExpression;
import com.joyin.fyzg.domain.proxy.sendrule.SendRuleUnit;
import com.joyin.platform.common.bean.metatype.impl.BaseDto;
import com.joyin.platform.common.util.BaseUtils;

import com.joyin.sendrule2.chain.SendRuleHandler;
import com.joyin.sendrule2.chain.SendRuleHandlerChain;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/22 16:52
 */
public class IrregularSendRuleHandler extends SendRuleHandler {
    String sendRuleUnit = SendRuleUnit.NONE.toString();

    @Override
    boolean sendLatch(BaseDto sendRule) {
        return sendRuleUnit.equals(sendRule.get("SEND_RULE_UNIT"));
    }

    @Override
    void echo(BaseDto sendRule, MyCronExpression cronExpression, SendRuleHandlerChain handlerChain) {
        String[] days = sendRule.getAsString("DAYS").split(";");
        Pattern pattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");

        StringBuffer daysOfMonth = new StringBuffer();
        StringBuffer months = new StringBuffer();
//        StringBuffer years = new StringBuffer(); // 暂不支持
        if (BaseUtils.isNotEmpty(days)) {
            try {
                for (String day : days) {
                    Matcher matcher = pattern.matcher(day);
                    matcher.find();
                    MyCronExpression myExp = new MyCronExpression("0 0 0 * * ?");
                    myExp.setDaysOfMonth(matcher.group(3));
                    myExp.setMonths(matcher.group(2));
                    cronExpression.getMyCronExpressions().add(myExp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        System.out.println("IrregularSendRuleHandler");
        handlerChain.handle(sendRule, cronExpression, handlerChain);
    }
}
