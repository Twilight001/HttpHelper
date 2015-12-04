package com.twilight.http.library;

import com.squareup.okhttp.Response;

/**
 * Created by twilight on 12/4/15.
 */
public abstract class JsonResponseHandler<T> extends AbstractResponseHandler {
    public abstract void onSuccess(T t);

    @Override
    public final void onSuccess(Response response) {
        IConvertString convertString = new DefaultConvert();
        try {
            T t = (T) convertString.convert(response.body().string());
            onSuccess(t);
        } catch (Exception e) {
            onFailure(response.request(), e);
        }
    }
}
