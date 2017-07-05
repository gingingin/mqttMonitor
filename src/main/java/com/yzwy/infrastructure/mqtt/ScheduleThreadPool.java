package com.yzwy.infrastructure.mqtt;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by gin on 2017/5/2.
 */
public class ScheduleThreadPool {
    private static ScheduleThreadPool singleton = new ScheduleThreadPool();

    private ScheduledExecutorService executor;

    private ScheduleThreadPool(){
        int cpuNums = Runtime.getRuntime().availableProcessors();
        executor = Executors.newScheduledThreadPool(3, new RealTimeFactory());
//        executor =  Executors.newFixedThreadPool(cpuNums * 2,new RealTimeThreadFactory());
    }


    public static ScheduleThreadPool getInstance(){
        return singleton;
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }
}

