package com.example.opgrad;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterFragmentFirst extends Fragment {
    private EditText idText;
    private EditText passwordText;
    private EditText passwordConfirmText;

    private String id;
    private String password;
    private String passwordConfirm;

    private Button isOverlapBtn;
    private Button nextBtn;
    static public String memresult="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_first, null);

        idText = view.findViewById(R.id.id_register_first);
        passwordText = view.findViewById(R.id.password_register_first);
        passwordConfirmText = view.findViewById(R.id.passwordConfirm_register_first);

        isOverlapBtn = view.findViewById(R.id.isOverlapBtn_register_first);
        isOverlapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이디 빈칸 검사하기
                if(idText.getText().toString().equals("")) {
                    ((RegisterActivity)getActivity()).showNegativeDialog("아이디는 빈칸일 수 없습니다.");
                }
                //아이디 공백 검사하기
                else if(idText.getText().toString().contains(" ")) {
                    ((RegisterActivity)getActivity()).showNegativeDialog("아이디에 공백이 포함될 수 없습니다.");
                }
                //아이디 중복 검사하기
                else {
                    boolean overlap=false;  //중복여부확인
                    try {
                        /***************PhP 회원 정보 받아오기*********************/
                        URLConnector conn = new URLConnector(MainActivity.comidUrl);

                        conn.start();

                        try {
                            conn.join();
                        } catch(InterruptedException e) {

                        }
                        memresult = conn.getResult();
                        JSONObject root = new JSONObject(memresult);
                        JSONArray memInfo = root.getJSONArray("idresult");

                        for (int i = 0; i <memInfo.length(); i++) {
                            JSONObject jsonObject = memInfo.getJSONObject(i);
                            String item1 = jsonObject.getString("identity");
                            if(idText.getText().toString().equals(item1))
                                overlap=true;
                        }
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                    if(!overlap) {
                        idText.setEnabled(false);
                        isOverlapBtn.setEnabled(false);

                        idText.setBackgroundColor(Color.rgb(204, 204, 204));
                        isOverlapBtn.setBackgroundColor(Color.rgb(150, 150, 150));
                    } else {
                        ((RegisterActivity)getActivity()).showNegativeDialog("중복되는 아이디가 존재합니다.");
                    }
                }
            }
        });

        nextBtn = (Button) view.findViewById(R.id.nextBtn_register_first);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getActivity()).reomveKeyboard();

                id = idText.getText().toString();
                password = passwordText.getText().toString();
                passwordConfirm = passwordConfirmText.getText().toString();

                if(id.equals("") || password.equals("") ||passwordConfirm.equals("")) {
                    ((RegisterActivity)getActivity()).showNegativeDialog("빈 칸 없이 입력해주세요.");
                } else if(isOverlapBtn.isEnabled()) {
                    ((RegisterActivity)getActivity()).showNegativeDialog("아이디 중복확인을 해주세요.");
                } else if(!password.equals(passwordConfirm)) {
                    ((RegisterActivity)getActivity()).showNegativeDialog("비밀번호가 일치하지 않습니다.");
                } else {
                    ((RegisterActivity)getActivity()).userID = id;
                    ((RegisterActivity)getActivity()).userPW = password;
                    if(!((RegisterActivity)getActivity()).isFirstNotice)
                        ((RegisterActivity)getActivity()).showFirstNotice();
                }
            }
        });

        return view;
    }
}