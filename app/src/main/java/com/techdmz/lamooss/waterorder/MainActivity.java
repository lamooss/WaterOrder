package com.techdmz.lamooss.waterorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {



    RequestQueue queue;
    TextView ReceivedFCMMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button WaterListButton = (Button)findViewById(R.id.WaterListButton);
        final Button OrderMgrButton = (Button)findViewById(R.id.OrderMgrButton);
        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice);

        ReceivedFCMMsg = (TextView)findViewById(R.id.ReceivedFCMMsg);

        WaterListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                WaterListButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                OrderMgrButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new WaterListFragment());
                fragmentTransaction.commit();
            }
        });

        OrderMgrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                WaterListButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                OrderMgrButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new OrderMgrFragment());
                fragmentTransaction.commit();
            }
        });



        queue= Volley.newRequestQueue(getApplicationContext());

        Intent passedIntent =  getIntent();
        processIntent(passedIntent);
    }

    public void processIntent(Intent intent)
    {
        if(intent != null)
        {
            String from = intent.getStringExtra("from");
            String contents = intent.getStringExtra("contents");

            println("DATA : " + from + ", " + contents);

        }
    }

    public void println(String data)
    {

        ReceivedFCMMsg.append(data + "\n");
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed()
    {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }

        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
