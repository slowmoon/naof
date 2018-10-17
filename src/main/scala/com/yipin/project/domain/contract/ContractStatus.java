package com.yipin.project.domain.contract;

import com.yipin.commons.utils.Status;

public enum  ContractStatus implements Status {

    OPEN(1, "打开"), CLOSED(2, "关闭"), HALFOPEN(3, "半关");  //此种条件下可以创建订单，但是不可以出款。

    private Integer value;

    private String display;

     ContractStatus(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setDisplay(String display) {
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

    public boolean canCreateOrder(){
         return !this.equals(CLOSED);
    }

    public boolean canRemit(){
         return this.equals(OPEN);
    }
}
