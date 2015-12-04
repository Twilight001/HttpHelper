package com.twilight.http.library;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by twilight on 12/4/15.
 */
public class RequestHelper {
    private static OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null)
            mOkHttpClient = new OkHttpClient();
        if (mDelivery == null)
            mDelivery = new Handler(Looper.getMainLooper());
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setRetryOnConnectionFailure(true);
        return mOkHttpClient;

    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    public void post(String url, RequestParams params, final AbstractResponseHandler handler) {
        post(null, url, params, handler);
    }

    public void post(Object tag, String url, RequestParams params, final AbstractResponseHandler handler) {
        createRequest(tag, RequestMethod.METHOD_POST, url, params, handler);
    }

    public void get(Object tag, String url, AbstractResponseHandler handler) {
        createRequest(tag, RequestMethod.METHOD_GET, url, null, handler);
    }

    public void get(String url, AbstractResponseHandler handler) {
        get(null, url, handler);
    }

    private void createRequest(Object tag, String method, String url, RequestParams params, AbstractResponseHandler handler) {
        Request.Builder builder = new Request.Builder();
        if (tag != null)
            builder.tag(tag);
        builder.url(url);
        if (params != null) {
            MultipartBuilder paramsBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
            Map<String, String> strMap = params.getStringParams();
            Map<String, RequestParams.FileWrapper> fileMap = params.getFileParams();
            if (strMap.size() > 0) {
                for (Map.Entry<String, String> entry : strMap.entrySet()) {
                    paramsBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
            RequestBody fileBody = null;
            if (fileMap.size() > 0) {
                for (Map.Entry<String, RequestParams.FileWrapper> entry : fileMap.entrySet()) {
                    File file = entry.getValue().file;
                    String fileName = file.getName();
                    fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                    paramsBuilder.addPart(Headers.of("Content-Disposition",
                                    "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + fileName + "\""),
                            fileBody);
                }
            }
            builder.method(method, paramsBuilder.build());
        } else {
            builder.method(method, null);
        }
        Request request = builder.build();
        sendRequest(request, handler);
    }

    private void sendRequest(final Request request, final AbstractResponseHandler handler) {
        //start
        handler.onStart();
        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailureMsg(request, e, handler);
            }

            @Override
            public void onResponse(Response response) {
                if (response.code() == 200) {
                    try {
                        sendSuccessMsg(response, handler);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        sendFailureMsg(response.request(), new RuntimeException(response.body().string()), handler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void sendFailureMsg(final Request request, final Exception throwable, final AbstractResponseHandler handler) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                handler.onFailure(request, throwable);
            }
        });
    }

    private void sendSuccessMsg(final Response response, final AbstractResponseHandler handler) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                handler.onSuccess(response);
            }
        });
    }

    public void cancel(Object tag) {
        getOkHttpClient().cancel(tag);
    }
}
