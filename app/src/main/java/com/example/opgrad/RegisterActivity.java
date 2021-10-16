package com.example.opgrad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private ProgressDialog progressDialog;

    public boolean isFirstNotice = false;
    public boolean isSecondNotice = false;

    private FragmentManager fragmentManager;
    private RegisterFragmentFirst firstFragment;
    private RegisterFragmentSecond secondFragment;
    private RegisterFragmentThird thirdFragment;
    private FragmentTransaction fragmentTransaction;

    private TextView first;
    private TextView second;
    private TextView third;
    private View firstView;
    private View secondView;
    private View thirdView;

    /************************회원가입 시 입력되는 회원정보들****************************/
    public int memberID = 0;    //회원번호

    public String userID;       //아이디
    public String userPW;       //비밀번호

    public String userGender;   //성별
    public String userName;     //이름
    public String userPhone;    //휴대폰 번호
    public String userEMail;    //이메일
    public String userAge;      //나이
    public String userAddress;  //주소
    public String userLatitude; //위도
    public String userLongitude;//경도
    public String userHopeRole; //희망역할
    public List<Integer> userResponField;   //담당가능분야
    public String userClassification;       //응시 구분
    public String userNoE;  //Number Of Entries //참가 횟수

    /********************성격*********************/
    public String userMind;     //마음
    public String userEnergy;   //에너지
    public String userNature;   //본성
    public String userTactics;  //전술
    public String userEgo;     //자아

    /********************가상프로필평가점수*********************/
    public String evaluate1;                    //평가점수1
    public String evaluate2;                    //평가점수1
    public String evaluate3;                    //평가점수1
    public String evaluate4;                    //평가점수1
    public String evaluate5;                    //평가점수1

    /*********************상호간의 거리차이**********************/
    List<Double> interAddress=new ArrayList<Double>(); //가입자와 회원간의 상호 거리

    String pageNumber;

    static public String memresult="";

    /************************회원가입 시 입력되는 회원정보들****************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fragmentManager = getSupportFragmentManager();

        firstFragment = new RegisterFragmentFirst();
        secondFragment = new RegisterFragmentSecond();
        thirdFragment = new RegisterFragmentThird();
        
        pageNumber="1";
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_register, firstFragment).commitAllowingStateLoss();

        first = (TextView) findViewById(R.id.first_register);
        second = (TextView) findViewById(R.id.second_register);
        third = (TextView) findViewById(R.id.third_register);

        firstView = (View) findViewById(R.id.firstView_register);
        secondView = (View) findViewById(R.id.secondView_register);
        thirdView = (View) findViewById(R.id.thirdView_register);


        /***************PhP 회원 수 받아오기*********************/

        try {
            JSONObject root = new JSONObject(LoginActivity.memresult);
            JSONArray memInfo = root.getJSONArray("memresult");     //공모전명, 시작일, 종료일 담긴 json 배열
            memberID = memInfo.length();
        } catch(JSONException e) {
            e.printStackTrace();
        }
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
    protected void onDestroy(){
        super.onDestroy();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_register, fragment).commitAllowingStateLoss();

        if(fragment.toString().contains("First")) {
            first.setBackgroundResource(R.drawable.register_custom_dark);
            firstView.setBackgroundResource(R.color.colorPrimaryDark);
            second.setBackgroundResource(R.drawable.register_custom);
            secondView.setBackgroundResource(R.color.colorPrimary);
            third.setBackgroundResource(R.drawable.register_custom);
            thirdView.setBackgroundResource(R.color.colorPrimary);
            pageNumber="1";
        } else if(fragment.toString().contains("Second")) {
            first.setBackgroundResource(R.drawable.register_custom);
            firstView.setBackgroundResource(R.color.colorPrimary);
            second.setBackgroundResource(R.drawable.register_custom_dark);
            secondView.setBackgroundResource(R.color.colorPrimaryDark);
            third.setBackgroundResource(R.drawable.register_custom);
            thirdView.setBackgroundResource(R.color.colorPrimary);
            pageNumber="2";
        } else if(fragment.toString().contains("Third")) {
            first.setBackgroundResource(R.drawable.register_custom);
            firstView.setBackgroundResource(R.color.colorPrimary);
            second.setBackgroundResource(R.drawable.register_custom);
            secondView.setBackgroundResource(R.color.colorPrimary);
            third.setBackgroundResource(R.drawable.register_custom_dark);
            thirdView.setBackgroundResource(R.color.colorPrimaryDark);
            pageNumber="3";
        } else {
            System.out.println("Fragment Error");
        }
    }

    public void showFirstNotice() {
        //Notice
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout notice = (LinearLayout)inflater.inflate(R.layout.register_first_notice, null);
        notice.setBackgroundColor(Color.parseColor("#99000000"));
        final LinearLayout.LayoutParams param
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(notice, param);

        isFirstNotice = true;

        TextView mbtiPage = (TextView) findViewById(R.id.MBTIPage_register_first_notice);
        mbtiPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko/"));
                startActivity(intent);
            }
        });

        TextView PIAgreement = (TextView) findViewById(R.id.PIAgreement_register_first_notice);
        PIAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout agreement = (LinearLayout)inflater.inflate(R.layout.register_p_i_agreement, null);
                final LinearLayout.LayoutParams param
                        = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                addContentView(agreement, param);

                Button confirmBtn = (Button) findViewById(R.id.confirmBtn_register_p_i_agreement);
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        agreement.removeAllViews();
                    }
                });
            }
        });

        final RadioButton agreeBox = (RadioButton) findViewById(R.id.agreeBox_register_first_notice);
        final RadioButton disagreeBox = (RadioButton) findViewById(R.id.disagreeBox_register_first_notice);

        Button nextBtn = (Button) findViewById(R.id.nextBtn_register_first_notice);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(agreeBox.isChecked()) {
                    notice.setBackgroundColor(Color.parseColor("#00000000"));
                    notice.removeAllViews();
                    isFirstNotice = false;
                    changeFragment(secondFragment);
                } else {
                    Toast.makeText(getApplicationContext(), "개인정보 수집/이용에 동의해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showSecondNotice() {
        //Notice
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout notice = (LinearLayout)inflater.inflate(R.layout.register_second_notice, null);
        notice.setBackgroundColor(Color.parseColor("#99000000"));
        final LinearLayout.LayoutParams param
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(notice, param);

        isSecondNotice = true;

        Button nextBtn = (Button) findViewById(R.id.nextBtn_register_second_notice);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notice.setBackgroundColor(Color.parseColor("#00000000"));
                notice.removeAllViews();
                isSecondNotice = false;
                changeFragment(thirdFragment);
            }
        });
    }

    public void reomveKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    public void registerSuccess() {
        userToUserAddress();  //상호 거리 계산
        InsertData task = new InsertData();

        task.execute(MainActivity.insertUrl,Integer.toString(memberID+1), userName, userID, userPW,userPhone,userAddress,userNoE,
                userMind,userEnergy,userNature,userTactics,userEgo,userGender,userAge,userEMail,userHopeRole, userLatitude,userLongitude
                ,userResponField.toString(),evaluate1,evaluate2,evaluate3,evaluate4,evaluate5,userClassification,interAddress.toString());
    }
    public double getDistance(double lat1 , double lng1 , double lat2 , double lng2 ){
        double distance;

        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);

        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);

        distance = Math.round((locationA.distanceTo(locationB)/1000)*10)/10.0;  //km단위로 변환

        return distance;
    }
    public void userToUserAddress() {  //새로 회원가입하는 사람과 이전회원간의 거리를 UserToUser_Interaction DB에 저장
        try {
            /***************PhP 회원 정보 받아오기*********************/
            URLConnector conn = new URLConnector(MainActivity.registerUrl);

            conn.start();

            try {
                conn.join();
            } catch(InterruptedException e) {

            }
            memresult = conn.getResult();

            /***************************************************************/
            JSONObject root = new JSONObject(memresult);
            JSONArray memInfo = root.getJSONArray("regiresult");     //공모전명, 시작일, 종료일 담긴 json 배열
            for (int i = 0; i <memInfo.length(); i++) {
                JSONObject jsonObject = memInfo.getJSONObject(i);
                // Pulling items from the array
                String mem_id = jsonObject.getString("member_id");
                double otherlatitude = Double.parseDouble(jsonObject.getString("latitude"));
                double otherlongitude = Double.parseDouble( jsonObject.getString("longitude"));
                double distance=getDistance(Double.parseDouble(userLatitude),Double.parseDouble(userLongitude),otherlatitude,otherlongitude);
                if(distance>=200)  //거리차가 200이상일 경우 200으로 계산
                    distance=200;
                interAddress.add(distance);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {

        switch (pageNumber) {
            case "1":
                super.onBackPressed();
                break;
            case "2":
                changeFragment(firstFragment);
                break;
            case "3":
                changeFragment(secondFragment);
                break;
            default:
                super.onBackPressed();
        }
    }

    class InsertData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RegisterActivity.this, "Please Wait", "회원간의 선호도 점수 입력 중입니다.", true, true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.equals("TimeoutException")) {
                RegisterActivity.this.showNegativeDialog("잠시 후에 다시 이용해주세요.");
            } else {
                RegisterActivity.this.showNegativeDialog("가입 되었습니다.");
            }

            if(progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            RegisterActivity.this.finish();

        }

        @Override
        protected String doInBackground(String... params) {

            int member = Integer.parseInt(params[1]);           //회원 번호
            String name = (String)params[2];                    //회원 이름
            String identify = (String)params[3];                //회원 아이디
            String password = (String)params[4];                //회원 비밀번호
            String contact = (String)params[5];                 //회원 전화번호
            String address = (String)params[6];                 //회원 주소
            String participation_count = (String)params[7];     //회원 공모전 참가 횟수
            int mind = Integer.parseInt(params[8]);             //회원 성격1
            int energy = Integer.parseInt(params[9]);           //회원 성격2
            int nature = Integer.parseInt(params[10]);          //회원 성격3
            int tactics = Integer.parseInt(params[11]);         //회원 성격4
            int ego = Integer.parseInt(params[12]);             //회원 성격5
            String gender = (String)params[13];                 //회원 성별
            int age = Integer.parseInt(params[14]);             //회원 나이
            String email = (String)params[15];                  //회원 이메일
            String hope_role = (String)params[16];              //회원 희망 역할
            double latitude = Double.valueOf(params[17]);       //회원 위도
            double longitude = Double.valueOf(params[18]);      //회원 경도
            String r_f = (String)params[19];                    //회원 담당가능 분야
            int evaluate1 = Integer.parseInt(params[20]);             //가상프로필1 평가점수
            int evaluate2 = Integer.parseInt(params[21]);             //가상프로필2 평가점수
            int evaluate3 = Integer.parseInt(params[22]);             //가상프로필3 평가점수
            int evaluate4 = Integer.parseInt(params[23]);             //가상프로필4 평가점수
            int evaluate5 = Integer.parseInt(params[24]);             //가상프로필5 평가점수
            String classification = (String)params[25];     //회원 공모전 참가 횟수
            String i_d = (String)params[26];                    //회원가입자와 회원간의 거리 계산 리스트

            String[] array_respon_field = r_f.substring(1,r_f.length() - 1).split(",");
            String[] array_inter_distance = i_d.substring(1,i_d.length() - 1).split(",");
            JSONArray respon_field = new JSONArray();
            JSONArray inter_distance = new JSONArray();
            try {
                for (int i = 0; i < array_respon_field.length; i++) {  //회원 담당가능 분야 JSONArray형태로 변경
                    JSONObject object = new JSONObject();
                    object.put(Integer.toString(i), array_respon_field[i]);
                    respon_field.put(object);
                }
                for (int i = 0; i < array_inter_distance.length; i++) {  //회원 담당가능 분야 JSONArray형태로 변경
                    JSONObject object = new JSONObject();
                    object.put(Integer.toString(i), array_inter_distance[i]);
                    inter_distance.put(object);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            System.out.println(inter_distance);
            String serverURL = (String)params[0];
            //PHP로 POST
            String postParameters = "member_id=" + member + "&name=" + name+ "&identify=" + identify+ "&password=" + password+ "&contact=" + contact+ "&address=" + address
                    + "&participation_count=" + participation_count+ "&mind=" + mind+ "&energy=" + energy+ "&nature=" + nature+ "&tactics=" + tactics+ "&ego=" + ego+ "&gender=" + gender
                    + "&age=" + age+ "&email=" + email+ "&hope_role=" + hope_role+ "&latitude=" + latitude+ "&longitude=" + longitude
                    + "&respon_field=" + respon_field+ "&evaluate1=" + evaluate1+ "&evaluate2=" + evaluate2+ "&evaluate3=" + evaluate3+ "&evaluate4=" + evaluate4+ "&evaluate5=" + evaluate5
                    + "&job=" + classification+ "&inter_distance=" + inter_distance;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(600000);
                httpURLConnection.setConnectTimeout(600000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString();

            } catch (Exception e) {
                return new String("TimeoutException");
            }
        }
    }
}
