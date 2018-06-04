package com.example.master.lesgo;

import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class LoginCheck { // 로그인 검사

    private String str, result;
    private URLConnector urlc;

    LoginCheck() {
        str = "http://192.168.0.33/proj.php";
        urlc = new URLConnector(str);
        urlc.start();

        try {
            urlc.join();
            System.out.println("waiting...");
        } catch (InterruptedException e) {
        }

        result = urlc.getResult();
        //System.out.println(result);
    }

    public boolean checkInput(String myId, String myPass) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("login");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString("id");
                String pass = item.getString("pass");
                String name = item.getString("name");

                if (id.equals(myId) && pass.equals(myPass)) { // 입력값과 비교
                    UseData.getInstance().setId(id);
                    UseData.getInstance().setName(name);
                    return true; // 일치 시 참 리턴
                }
            }
        } catch (JSONException e) {
        }
        return false; // 불일치 시 거짓 리턴
    }
}

