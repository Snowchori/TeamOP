package com.example.opgrad;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;

public class CustomCircleProgressDialog extends Dialog {
    private TextView progressCntTv;
    private TextView progressTextTv;

    public CustomCircleProgressDialog(@NonNull Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // No Title
        setContentView(R.layout.custom_circle_progress);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 3초가 지나면 다이얼로그 닫기
                TimerTask task = new TimerTask(){
                    @Override
                    public void run() {
                        dismiss();

                    }
                };

                Timer timer = new Timer();
                timer.schedule(task, 500);
            }
        });
        thread.start();*/
    }

}