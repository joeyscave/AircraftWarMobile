package com.yuan.AircraftWarMobile.prop;

import com.yuan.AircraftWarMobile.aircraft.AbstractAircraft;
import com.yuan.AircraftWarMobile.aircraft.HeroAircraft;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;
import com.yuan.AircraftWarMobile.observer.Publisher;
import com.yuan.AircraftWarMobile.observer.Subscriber;

import java.util.LinkedList;
import java.util.List;

public class BombProp extends AbstractProp implements Publisher {

    private List<Subscriber> subscribers = new LinkedList<>();

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft, List<AbstractAircraft> enemyAircraft, List<BaseBullet> enemyBullet) {
        notifySubscriber();
        System.out.println("BombPropActive!");
    }

    @Override
    public String getMusicType() {
        return "bomb_explosion";
    }


    @Override
    public void notifySubscriber() {
        for (Subscriber subscriber : subscribers) {
            subscriber.update();
        }
        subscribers.clear();
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }
}
