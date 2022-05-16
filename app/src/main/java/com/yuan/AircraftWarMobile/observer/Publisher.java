package com.yuan.AircraftWarMobile.observer;

public interface Publisher {
    void notifySubscriber();
    void subscribe(Subscriber subscriber);
}
