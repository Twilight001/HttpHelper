package com.twilight.http.library;

import com.twilight.http.library.utils.GsonUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by twilight on 12/4/15.
 */
@Deprecated
public class DefaultConvert<T> implements IConvertString<T>{
    @Override
    public T convert(String s) {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            return GsonUtils.deserialiFromJson(s, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
