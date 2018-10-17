package com.yipin.commons.utils;

public enum  CodeStatus implements Status{

     WEIXINCODE(1, "wxcode"),WEIXINJS(2, "wxjs"),ALICODE(3, "alicode"),ALIJS(4, "alijs");

    private Integer value;

    private String display;

   private CodeStatus(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public static CodeStatus ofName(String name){
        for (CodeStatus codeStatus:CodeStatus.values()){
            if (codeStatus.getDisplay().equals(name)){
                return codeStatus;
            }
        }
        throw new IllegalArgumentException("非法的code码"+name);
     }
}
