package com.example.opgrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private AlertDialog dialog;

    private TextView joinBtn;

    private EditText editId;
    private EditText editPw;

    private Button loginBtn;

    private String id;
    private String pw;

    static public String memresult="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editId = findViewById(R.id.id_login);
        editPw = findViewById(R.id.password_login);

        joinBtn = findViewById(R.id.joinBtn_login);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn = findViewById(R.id.loginBtn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = editId.getText().toString();
                pw = editPw.getText().toString();

                boolean IDOK = false;
                boolean PWOK = false;
                try {
                    JSONObject root = new JSONObject(memresult);
                    JSONArray memInfo = root.getJSONArray("logresult");

                    for (int i = 0; i <memInfo.length(); i++) {
                        JSONObject jsonObject = memInfo.getJSONObject(i);
                        // Pulling items from the array
                        String item1 = jsonObject.getString("identity");
                        String item2 = jsonObject.getString("password");

                        if(id.equals(item1)){
                            IDOK = true;
                            if (pw.equals(item2)) {
                                PWOK = true;
                                Toast.makeText(getApplicationContext(),"로그인 성공.",Toast.LENGTH_SHORT).show();
                                MainActivity.userID = jsonObject.getInt("member_id");
                                finish();
                            }
                        }
                    }
                    if(!IDOK) {
                        showNegativeDialog("아이디가 존재하지 않습니다.");
                        IDOK = false;
                    } else if(!PWOK) {
                        showNegativeDialog("비밀번호가 일치하지 않습니다.");
                        PWOK = false;
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        /***************PhP 회원 정보 받아오기*********************/
        URLConnector conn = new URLConnector(MainActivity.loginUrl);

        conn.start();

        try {
            conn.join();
        } catch(InterruptedException e) {

        }
        memresult = conn.getResult();

        /***************************************************************/
    }

    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void showNegativeDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.setMessage(msg)
                .setNegativeButton("확인", null)
                .create();
        dialog.show();
        return;
    }
}
