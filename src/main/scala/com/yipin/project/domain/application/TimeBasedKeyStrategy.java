package com.yipin.project.domain.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeBasedKeyStrategy  {

    private static volatile long lastTimeLong = 0l;

    private static AtomicInteger sequence = new AtomicInteger(0);


    private Date date;
    private static final  String PATTERN ="yyyyMMdd";
    private String prefix;

    private TimeBasedKeyStrategy(){
        this.date = new Date();
        this.prefix = new SimpleDateFormat(PATTERN).format(date);
    }


    public synchronized static String id(String fix){
        long current = currentTimeLong();
        if (lastTimeLong> current) throw new IllegalArgumentException("非法调用");
        if(lastTimeLong == current) {
            sequence.getAndIncrement();
        }else {
            sequence.set(0);
        }
        lastTimeLong = currentTimeLong();

        TimeBasedKeyStrategy strategy = new TimeBasedKeyStrategy();
        StringBuilder sb = new StringBuilder();
        sb.append(strategy.prefix);
        sb.append(fix);
        sb.append(sequence.get());
        sb.append(lastTimeLong);
        return sb.toString();
    }

    private static long currentTimeLong(){return System.currentTimeMillis();}


}
