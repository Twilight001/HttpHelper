package com.twilight.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.twilight.http.library.JsonResponseHandler;
import com.twilight.http.library.RequestHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResult = (TextView) findViewById(R.id.mResult);
    }

    @Override
    public void onClick(View v) {
        RequestHelper helper=new RequestHelper();
        helper.get("", new JsonResponseHandler<User>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(User user) {

            }
        });
    }
}
