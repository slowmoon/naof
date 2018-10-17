package com.yipin.project.domain.product;

import com.yipin.commons.utils.Status;

public enum  ProductStatus implements Status {

    VALID(1, "有效"), CLOSE(2, "无效");


    private Integer status;

    private String display;

     ProductStatus(Integer status, String display){
        this.status = status;
        this.display = display;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public Integer getValue() {
        return status;
    }

    @Override
    public String getDisplay() {
        return display;
    }
    public boolean isOpen(){
        return this.equals(VALID);
    }
}
