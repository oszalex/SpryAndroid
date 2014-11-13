package com.getbro.meetmeandroid.remote.serialize;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by rich on 13.11.14.
 */
public abstract class BaseSerializer<T> {

    private T target;
    private Map<String,String> remoteResolutionMap;

    public BaseSerializer(T target) {
        this.target = target;
        remoteResolutionMap = getRemoteResolutionMap();
    }

    protected abstract java.util.Map<String, String> getRemoteResolutionMap();

    public T deserialize(JsonObject object) {
        return null;
    }

    public void setObjectProperty(String property, Object value) {
        try {
            Field field = target.getClass().getField(property);
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(target, value);
            field.setAccessible(accessible);
        } catch (NoSuchFieldException e) {
            throw new SerializeException(e);
        } catch (IllegalAccessException e) {
            throw new SerializeException(e);
        }
    }

    public Object getObjectProperty(String property) {
        try {
            Field field = target.getClass().getField(property);
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Object value = field.get(target);
            field.setAccessible(accessible);
            return value;
        } catch (NoSuchFieldException e) {
            throw new SerializeException(e);
        } catch (IllegalAccessException e) {
            throw new SerializeException(e);
        }
    }
}
