package com.hotslicerrpg.rpg.Entities;

import java.lang.reflect.Field;

public class NMSUtils {
    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;
        try
        {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return o;
    }
}
