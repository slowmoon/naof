package com.yipin.project.domain.application;

import com.yipin.commons.utils.Status;


public enum  ApplicationStatus implements Status {

    OPEN(1, "有效"), CLOSED(2, "失效"), HALFOPEN(3, "半开");

    private Integer value;

    private String display;

    ApplicationStatus(Integer value, String display) {
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


    public boolean canCreateOrder(){
        return !this.equals(CLOSED);
    }

    public boolean canRemit(){
        return this.equals(OPEN);
    }
}
