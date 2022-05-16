package com.yuan.AircraftWarMobile.factory.prop;

import com.yuan.AircraftWarMobile.prop.AbstractProp;
import com.yuan.AircraftWarMobile.prop.BombProp;

public class BombPropFactory implements AbstractPropFactory {
    @Override
    public AbstractProp create(int x, int y) {
        return new BombProp(x, y, 0, 6);
    }
}
