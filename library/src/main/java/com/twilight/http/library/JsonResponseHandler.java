package com.twilight.http.library;

import com.squareup.okhttp.Response;
import com.twilight.http.library.utils.GsonUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by twilight on 12/4/15.
 */
public abstract class JsonResponseHandler<T> extends AbstractResponseHandler {
    public abstract void onSuccess(T t);

    @Override
    public final void onSuccess(Response response) {
        try {
            String str = response.body().string();
            if (!str.equals("")) {
                onSuccess(convert(str));
            }
        } catch (IOException e) {
            onFailure(response.request(), e);
        } catch (JsonFormatException e) {
            onFailure(response.request(), e);
        }
    }

    private T convert(String s) throws JsonFormatException {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            return GsonUtils.deserialiFromJson(s, type);
        } catch (Exception e) {
            throw new JsonFormatException(e.getMessage());
        }
    }
}
