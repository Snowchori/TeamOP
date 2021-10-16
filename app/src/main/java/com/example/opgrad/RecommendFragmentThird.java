package com.example.opgrad;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecommendFragmentThird extends Fragment {

    private TextView compTitle;

    private LinearLayout firstBtn;
    private LinearLayout secondBtn;
    private LinearLayout thirdBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recommend_third, null);

        compTitle = (TextView) view.findViewById(R.id.compTitle_recommend_third);
        //공모전 제목이 너무 길 경우
        //뒷부분을 자르고 ...으로 처리
        compTitle.setText(lengthCount(((RecommendActivity)getActivity()).NOCP, 130));

        firstBtn = (LinearLayout) view.findViewById(R.id.firstBtn_recommend_third);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecommendActivity)getActivity()).checkedBtn = 1;
                ((RecommendActivity)getActivity()).recommendSuccess();
            }
        });
        secondBtn = (LinearLayout) view.findViewById(R.id.secondBtn_recommend_third);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecommendActivity)getActivity()).checkedBtn = 2;
                ((RecommendActivity)getActivity()).recommendSuccess();
            }
        });
        thirdBtn = (LinearLayout) view.findViewById(R.id.thirdBtn_recommend_third);
        thirdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecommendActivity)getActivity()).checkedBtn = 3;
                ((RecommendActivity)getActivity()).recommendSuccess();
            }
        });
        return view;
    }

    public String lengthCount(String text, int limit) {
        int cnt = 0;

        for(int index = 0; index<text.length(); index++) {
            char t = text.charAt(index);
            if(48 <= t && t <= 57) {    //숫자
                cnt += 4;
            } else if(65 <= t && t <= 90) { //대문자
                cnt += 4;
            } else if(97 <= t && t <= 122) { //소문자
                cnt += 3;
            } else if(t < 126) {    // 특수문자
                cnt += 3;
            } else {    //한글
                cnt += 6;
            }

            if(cnt > limit) {
                text = text.substring(0, index) + "...";
                break;
            }
        }
        return text;
    }
}