package com.yipin.commons.utils;


import java.io.Serializable;

public class AccessWay<T> {

    private static final ThreadLocal<Integer> ways = new ThreadLocal<>();

    public static void set(Integer integer){
           ways.set(integer);
    }

    public static Integer get(){
        return ways.get();
    }


    public static enum Way implements Serializable {
        API(1),MANUAL(2);

        private Integer way;

        Way(Integer way) {
            this.way = way;
        }

        public Integer getWay() {
            return way;
        }


    }
}
