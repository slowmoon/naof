package com.yipin.commons.utils.exception;

import com.yipin.commons.utils.file.BaseUtils;

public class BaseException extends RuntimeException{

    private Level level;
    private Object[] args;
    private String businessKey;


    public BaseException(String businessKey,Object...args){
        this(Level.NORMAL, businessKey, args);
    }

    public BaseException(Level level, String businessKey,Object...args){
        super(BaseUtils.getResult(businessKey, args));
        this.level = level;
    }
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getCode(){
        return businessKey.substring(businessKey.lastIndexOf(".")+1);
    }

    public static enum Level {
        IGNORE,
        NORMAL,
        FATAL,

    }
}
