package com.yuan.AircraftWarMobile.factory.prop;

import com.yuan.AircraftWarMobile.prop.AbstractProp;

public interface AbstractPropFactory {
    public AbstractProp create(int x, int y);
}
