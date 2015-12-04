package com.twilight.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.twilight.http.library.JsonResponseHandler;
import com.twilight.http.library.RequestHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG=MainActivity.class.getSimpleName();
    private static  String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=18.4.55.255";
    private TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResult = (TextView) findViewById(R.id.mResult);
        Button button = (Button) findViewById(R.id.mButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        RequestHelper helper=new RequestHelper();
        helper.get(url, new JsonResponseHandler<IPEntity>() {
            @Override
            public void onStart() {
                Toast.makeText(MainActivity.this, "start request", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "request onFailure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(IPEntity list) {
                Toast.makeText(MainActivity.this, "request onSuccess", Toast.LENGTH_SHORT).show();
                mResult.setText(list.toString());
            }
        });
    }
}
