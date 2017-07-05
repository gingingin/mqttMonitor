package com.yzwy.infrastructure.mqtt;

import java.util.concurrent.ThreadFactory;

/**
 * Created by gin on 2017/5/2.
 */
public class RealTimeFactory implements ThreadFactory{
    private int i ;

    public RealTimeFactory() {
        this.i = 0;
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread t=new Thread(r);
//        t.setDaemon(true);
        t.setName("MqttConnectionCheckThread_"+i);
        i++;
        return t;
    }
}

