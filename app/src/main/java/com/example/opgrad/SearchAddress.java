package com.example.opgrad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.ContentValues.TAG;

public class SearchAddress extends Activity {
    private WebView webView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.search_address);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView_search_address);
        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        //webView.getSettings().setSupportMultipleWindows(true);

        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");



        //  MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        //webView.setWebChromeClient(myWebChromeClient);


        // web client 를 chrome 으로 설정
        //webView.setWebChromeClient(myWebChromeClient);
        // webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new HelloWebVeiwClient());

        //webView.setWebViewClient(new WebViewClient());

        // webview url load. php 파일 주소
        webView.loadUrl(MainActivity.searchAddressUrl);

    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //   init_webView();
                    // WebView를 초기화 하지않으면 재사용할 수 없음

                    Intent intent = new Intent();
                    intent.putExtra("addressnum", String.format("%s", arg1));
                    intent.putExtra("address", String.format("%s %s", arg2, arg3));
                    setResult(RESULT_OK, intent);

                    finish();
                }
            });
        }
    }
}

class HelloWebVeiwClient extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url){
        view.loadUrl(url);
        return true;
    }
}

