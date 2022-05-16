package com.yuan.AircraftWarMobile.thread;

import java.util.concurrent.ThreadFactory;

public class SimpleThreadfactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
