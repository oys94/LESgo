package com.example.master.lesgo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SeatShowActivity extends AppCompatActivity {
    private String str, result;
    private URLConnector urlc;
    private RadioButton rb1, rb2, rb3, rb4;
    private ImageButton ib1, ib2;
    private boolean select = true;
    private TextView[] tvK = new TextView[37];
    private TextView[] tvO = new TextView[145];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat1);

        set2(); // 초기 칸막이 선택으로
        check(); // db에서 좌석 읽어오기
    }

    public void set1() { // 칸막이 선택한 경우
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        Listener li = new Listener();
        rb3.setOnClickListener(li);

        ib2 = (ImageButton)findViewById(R.id.ib_reser2);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeatShowActivity.this,ReserActivity.class);
                startActivity(intent); // 예약화면으로
            }
        });
    }

    public void set2() { // 오픈 선택한 경우
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        Listener li = new Listener();
        rb2.setOnClickListener(li);

        ib1 = (ImageButton)findViewById(R.id.ib_reser1);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeatShowActivity.this,ReserActivity.class);
                startActivity(intent); // 예약화면으로
            }
        });
    }

    class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (select) { // 칸막에서 오픈으로
                setContentView(R.layout.activity_seat2);
                set1();
                select = false;
            } else { // 오픈에서 칸막이로
                setContentView(R.layout.activity_seat1);
                set2();
                select = true;
            }
           check(); // db에서 좌석 읽어오기
        }
    }


    public void check() { // db에서 좌석 읽어오기
        String s = null;
        if (select) {
            s = "seat1";

            str = "http://192.168.0.33/seat1.php";
            urlc = new URLConnector(str);
            urlc.start();

            try {
                urlc.join();
                System.out.println("waiting...");
            } catch (InterruptedException e) {
            }

            result = urlc.getResult();

            for (int i = 1; i < 37; i++) {
                tvK[i] = (TextView) findViewById(getResources().getIdentifier("seat1_" + i, "id", getPackageName()));
            }
        } else {
            s = "seat2";

            str = "http://192.168.0.33/seat2.php";
            urlc = new URLConnector(str);
            urlc.start();

            try {
                urlc.join();
                System.out.println("waiting...");
            } catch (InterruptedException e) {
            }

            result = urlc.getResult();

            for (int i = 1; i < 145; i++) {
                tvO[i] = (TextView) findViewById(getResources().getIdentifier("seat2_" + i, "id", getPackageName()));
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(s);


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String num = item.getString("num");
                String id = item.getString("id");

                if(select){ // 칸막이인 경우
                    if(id.equals("break")){ // 사용불가
                        tvK[Integer.parseInt(num)].setBackgroundColor(Color.BLACK);
                    }
                    else{ // 사용중
                        tvK[Integer.parseInt(num)].setBackgroundColor(Color.RED);
                    }
                }
                else{ // 오픈인 경우
                    if(id.equals("break")){ // 사용불가
                        tvO[Integer.parseInt(num)].setBackgroundColor(Color.BLACK);
                    }
                    else{ // 사용중
                        tvO[Integer.parseInt(num)].setBackgroundColor(Color.RED);
                    }
                }

            }
        } catch (JSONException e) {
        }
    }

}
