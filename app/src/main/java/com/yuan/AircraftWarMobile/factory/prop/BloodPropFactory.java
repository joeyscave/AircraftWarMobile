package com.yuan.AircraftWarMobile.factory.prop;

import com.yuan.AircraftWarMobile.prop.AbstractProp;
import com.yuan.AircraftWarMobile.prop.BloodProp;

public class BloodPropFactory implements AbstractPropFactory {
    @Override
    public AbstractProp create(int x, int y) {
        return new BloodProp(x, y, 0, 6, 30);
    }
}
