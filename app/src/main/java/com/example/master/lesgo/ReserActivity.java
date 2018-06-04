package com.example.master.lesgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReserActivity extends AppCompatActivity {
    private boolean[] b1 = new boolean[37];
    private boolean[] b2 = new boolean[145];

    private boolean select;
    private String str, result;
    private URLConnector urlc;
    private RadioButton rb1, rb2;
    private TextView tv;
    private Spinner sp;
    private String[] arStr;
    private int pos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser);

        tv = (TextView) findViewById(R.id.selectTv); // 선택한 좌석

        for (int i = 1; i < 37; i++) { // 칸막이
            b1[i] = true; // 예약 가능 상태로 초기화
        }
        for (int i = 1; i < 145; i++) { // 오픈
            b2[i] = true; // 예약 가능 상태로 초기화
        }

        ImageView iv = (ImageView) findViewById(R.id.toSeat);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReserActivity.this, SeatShowActivity.class);
                startActivity(intent); // 좌석 현황 확인으로 이동
            }
        });

        rb1 = (RadioButton) findViewById(R.id.s_rb1);
        rb2 = (RadioButton) findViewById(R.id.s_rb2);

        rb1.setOnClickListener(new View.OnClickListener() { // 칸막이 선택하면
            @Override
            public void onClick(View v) {
                select = true;
                rb2.setChecked(false); // 오픈 선택 취소
                check(); // 좌석읽어오기
                arStr = new String[37];
                arStr[0] = "선택하세요";
                for (int i = 1; i < 37; i++) {
                    if (b1[i]) { // 예약가능 좌석
                        arStr[i] = String.valueOf(i);
                    } else { // 예약불가 좌석
                        arStr[i] = "X";
                    }
                }
                setSpinner(); // 피스너 다시 적용
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() { // 오픈 선택하면
            @Override
            public void onClick(View v) {
                select = false;
                rb1.setChecked(false); // 칸막이 선택 취소
                check(); // 좌석읽어오기
                arStr = new String[145];
                arStr[0] = "선택하세요";
                for (int i = 1; i < 145; i++) {
                    if (b2[i]) { // 예약가능 좌석
                        arStr[i] = String.valueOf(i);
                    } else { // 예약불가 좌석
                        arStr[i] = "X";
                    }
                }
                setSpinner(); // 피스너 다시 적용
            }
        });
    }

    public void setSpinner() { // 피스너 적용
        sp = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arStr);
        sp.setAdapter(aa);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText("" + sp.getItemAtPosition(position));
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void check() {
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
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(s);

            for (int i = 0; i < jsonArray.length(); i++) { // db에 기록되어 있으면 예약불가로

                JSONObject item = jsonArray.getJSONObject(i);

                String num = item.getString("num");
                String id = item.getString("id");

                if (select) {
                    b1[Integer.parseInt(num)] = false; // 칸막이 예약불가
                } else {
                    b2[Integer.parseInt(num)] = false; // 오픈 예약불가
                }

            }
        } catch (JSONException e) {
        }
    }

}
