package com.example.master.lesgo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    private ImageButton main_ib1, main_ib2, main_ib3; // 메인버튼
    private ImageButton sub_ib1,sub_ib2,sub_ib3,sub_ib4; // 서브버튼

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(UseData.getInstance().getName()+"님 환영합니다!");

        getImageButton();
        listener();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){ // 로그아웃
        int id = item.getItemId();
        switch (id){
            case  R.id.logout:
                UseData.getInstance().setId("null");
                UseData.getInstance().setName("null");
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent); // 로그인화면으로
        }
        return super.onOptionsItemSelected(item);
    }

    public void getImageButton() {
        main_ib1 = (ImageButton) findViewById(R.id.f0);
        main_ib2 = (ImageButton) findViewById(R.id.f1);
        main_ib3 = (ImageButton) findViewById(R.id.f2);

        sub_ib1 = (ImageButton) findViewById(R.id.f3);
        sub_ib2 = (ImageButton) findViewById(R.id.f4);
        sub_ib3 = (ImageButton) findViewById(R.id.f5);
        sub_ib4 = (ImageButton) findViewById(R.id.f6);
    }

    public void listener(){

        main_ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //좌석현황
                Intent intent = new Intent(MenuActivity.this, SeatShowActivity.class);
                startActivity(intent);
            }
        });

        main_ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 예약
                Intent intent = new Intent(MenuActivity.this, ReserActivity.class);
                startActivity(intent);
            }
        });

        main_ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 내 좌석 확인
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dli.kangwon.ac.kr:8443/board/list.jsp?bcs=1"));
                startActivity(intent);
            }
        });

        sub_ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 공지사항
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dli.kangwon.ac.kr:8443/board/list.jsp?bcs=1"));
                startActivity(intent);
            }
        });

        sub_ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 검색
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dli.kangwon.ac.kr/DLiWeb25/comp/search/advance.aspx?srv=86&m_var=478&cntperpage=10"));
                startActivity(intent);
            }
        });

        sub_ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 고객선터
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dli.kangwon.ac.kr:8443/board/list.jsp?bcs=1"));
                startActivity(intent);
            }
        });

        sub_ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 도서관 안내
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dli.kangwon.ac.kr:8443/intro2/location.jsp"));
                startActivity(intent);
            }
        });
    }

}