package com.yipin.project.domain.order;

import com.yipin.commons.utils.Status;


public enum  SettleStatus implements Status {
    UNSETTLE(0,"未结算"),SETTLED(1, "已结算"),FROZEN(2, "冻结");

    private Integer value;

    private String display;

    SettleStatus(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDisplay() {
        return display;
    }
}
