package com.yipin.commons.utils;
import java.io.Serializable;

public class ValueRegion<T extends Comparable> implements Serializable {
    private T begin;

    private T end;

    public ValueRegion(T begin, T end) {
        validateBeginAndEnd(begin, end);
    }

    private void validateBeginAndEnd(T begin, T end){
        if (begin!=null
                && end!=null
                && end.compareTo(begin)<0) throw new IllegalArgumentException("after param "+end+" must lower than before"+begin);
        this.begin = begin;
        this.end = end;
    }

    public boolean apply(T var1){
        return isLeftPadding(var1) || isRightPadding(var1);
    }


    private boolean isLeftPadding(T var1){
        if (var1==null) throw new IllegalArgumentException("argument "+var1+"can not be null!");
        if (begin==null) return true;
        else return var1.compareTo(begin)>=0;
    }

    private boolean isRightPadding(T var1){
        if (var1==null) throw new IllegalArgumentException("argument "+var1+"can not be null!");
        if (end==null) return true;
        else return var1.compareTo(end)<0;
    }

    public ValueRegion(){
        this(null ,null);
    }

    public T getBegin() {
        return begin;
    }

    public void setBegin(T begin) {
        this.begin = begin;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(T end) {
        this.end = end;
    }
}
