package com.luxoft.model;

import com.sun.beans.decoder.ValueObject;

/**
 * Created by aeliseev on 14.04.2014
 */
public class MRResult implements ValueObject {

    private String id;

    private Object value;

    public String getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean isVoid() {
        return false;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueObject [id=" + id + ", value=" + value + "]";
    }
}
