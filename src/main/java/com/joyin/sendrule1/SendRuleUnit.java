package com.joyin.sendrule1;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/8/21 20:07
 */
public enum SendRuleUnit {
    DAILY("0"), WEEKLY("1"), MONTHLY("2"), NONE("99");
    private String SendRuleUnitVal;

    SendRuleUnit(String s) {
        this.SendRuleUnitVal = s;
    }

    @Override
    public String toString() {
        return SendRuleUnitVal;
    }
}
