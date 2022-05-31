package com.yuan.AircraftWarMobile.factory.prop;

import com.yuan.AircraftWarMobile.prop.AbstractProp;
import com.yuan.AircraftWarMobile.prop.BulletProp;

public class BulletPropFactory implements AbstractPropFactory {
    @Override
    public AbstractProp create(int x, int y) {
        return new BulletProp(x, y, 0, 6, "scattering");
    }
}
