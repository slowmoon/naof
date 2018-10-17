package com.yipin.project.domain.order;

import com.yipin.commons.utils.Status;

public enum  OrderStatus implements Status {

    SUCESS(1, "支付成功"), FAIL(2, "支付失败"),REFUND(3, "已退款"),INVADLID(4, "已失效"),WAIT(5, "等待通知"),UNKNOW(9, "未知状态");

    private Integer value;

    private String display;

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    private OrderStatus(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDisplay() {
        return this.display;
    }
}
