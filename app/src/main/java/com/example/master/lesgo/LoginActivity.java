package com.example.master.lesgo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    private LoginCheck check = new LoginCheck();
    private AutoCompleteTextView mNumberView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNumberView = (AutoCompleteTextView) findViewById(R.id.number);
        mPasswordView = (EditText) findViewById(R.id.password);

        ClickListener cl = new ClickListener();

        ImageButton ib = (ImageButton)findViewById(R.id.loginBt);
        ib.setOnClickListener(cl);
    }

    class ClickListener implements ImageButton.OnClickListener
    {
        @Override
        public void onClick(View v) {

            String id = mNumberView.getText().toString();
            String pass = mPasswordView.getText().toString();

            if(check.checkInput(id,pass)) // 아이디/비번 일치
            {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent); // 메뉴화면으로
            }
            else{ // 불일치
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setMessage("아이디 혹은 비밀번호가 틀립니다!");
                alert.show();
            }
        }
    }
}

