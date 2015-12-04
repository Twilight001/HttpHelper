package com.twilight.http.library;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


/**
 * Created by twilight on 12/4/15.
 */
public abstract class AbstractResponseHandler {

    public  void onStart(){}

    public abstract void onSuccess(Response response);

    public abstract void onFailure(Request request, Exception e);

    public  void onCancel(){}

}
