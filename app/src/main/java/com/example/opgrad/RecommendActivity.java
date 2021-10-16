package com.example.opgrad;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RecommendActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private ProgressDialog progressDialog;

    private LinearLayout.LayoutParams params;

    private FragmentManager fragmentManager;
    public RecommendFragmentFirst firstFragment;
    public RecommendFragmentSecond secondFragment;
    public RecommendFragmentThird thirdFragment;
    public RecommendFragmentFourth fourthFragment;
    private FragmentTransaction fragmentTransaction;

    private TextView pageNumber;
    private TextView subTitle;

    public static int NOTM = 2;     //Number Of Team Member
    public String NOCP;             //Name Of ComPetition
    public int essentialCnt;
    public int additionalCnt;

    static public String teamResult;
    static public String memresult="";


    /****************************추천 기능 실행시 넘겨주는 정보****************************/
    public int currentMemberID = MainActivity.userID; // 현재 로그인한 멤버 아이디
    public int numOfPeopleForTeam;  // 팀 결정 수
    public String competitionName;  // 원하는 공모전 명
    public List<String> capacityEssential; // 필수 역량 리스트
    public List<String> capacityPlus; // 추가 역량 리스트
    public int checkedBtn; // 세번째 Fragment에서 선택된 버튼 번호

    /******************************추천된 팀정보들을 DB보낼 떄 필요한 정보*************/
    public int competitionId;  // 원하는 공모전 번호
    public List<String> teamMemberId=new ArrayList<String>();; // 추천된 팀원아이디들
    public List<String> teamMemberResponsible=new ArrayList<String>();; // 추천된 팀원담당분야
    public List<String> teamMemberDistance=new ArrayList<String>();; // 추천된 팀원거리

    LinearLayout teamDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        fragmentManager = getSupportFragmentManager();

        firstFragment = new RecommendFragmentFirst();
        secondFragment = new RecommendFragmentSecond();
        thirdFragment = new RecommendFragmentThird();
        fourthFragment = new RecommendFragmentFourth();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_recommend, firstFragment).commitAllowingStateLoss();

    }

    @Override
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

    public void showNegativeDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.setMessage(msg)
                .setNegativeButton("확인", null)
                .create();
        dialog.show();
        return;
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_recommend, fragment).commitAllowingStateLoss();

        pageNumber = (TextView) findViewById(R.id.pageNumber_recommend);
        subTitle = (TextView) findViewById(R.id.subTitle_recommend);

        if(fragment.toString().contains("First")) {
            pageNumber.setText("1");
            subTitle.setText("팀원 수 / 공모전 결정");
        } else if(fragment.toString().contains("Second")) {
            pageNumber.setText("2");
            subTitle.setText("원하는 팀원 역량 결정");
        } else if(fragment.toString().contains("Third")) {
            pageNumber.setText("3");
            subTitle.setText("분류별 추천 결과 선택");
        } else if(fragment.toString().contains("Fourth")) {
            pageNumber.setText("4");
            subTitle.setText("최종 추천 결과");
        } else {
            System.out.println("Fragment Error");
        }
    }

    public void onBackPressed() {
        pageNumber = (TextView) findViewById(R.id.pageNumber_recommend);
        String page = pageNumber.getText().toString();

        switch (page) {
            case "2":
                changeFragment(firstFragment);
                break;
            case "3":
                changeFragment(secondFragment);
                break;
            case "4":
                if(teamDetail!=null){
                    teamDetail.setBackgroundColor(Color.parseColor("#00000000"));
                    teamDetail.removeAllViews();
                    teamDetail=null;}
                else
                    changeFragment(thirdFragment);
                break;
            default:
                super.onBackPressed();
        }
    }

    public void showTeamDetail() {
        //teamDetail
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        teamDetail = (LinearLayout)inflater.inflate(R.layout.recommend_detail, null);
        teamDetail.setBackgroundColor(Color.parseColor("#99000000"));
        final LinearLayout.LayoutParams param
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(teamDetail, param);
        /***********************팀원 정보**********************************************/
        TextView member1Name          = (TextView) findViewById(R.id.member1Name_recommend_detail);
        TextView member1Sex           = (TextView) findViewById(R.id.member1Sex_recommend_detail);
        TextView member1Age           = (TextView) findViewById(R.id.member1Age_recommend_detail);
        TextView member1Distance      = (TextView) findViewById(R.id.member1distance_recommend_detail);
        TextView member1Job           = (TextView) findViewById(R.id.member1Job_recommend_detail);
        TextView member1Participation = (TextView) findViewById(R.id.member1Participation_recommend_detail);
        TextView member1Hoperole      = (TextView) findViewById(R.id.member1Hoperole_recommend_detail);
        TextView member1Responsible   = (TextView) findViewById(R.id.member1responsible_recommend_detail);
        TextView member1mindA         = (TextView) findViewById(R.id.member1mindA_recommend_detail);
        TextView member1mindB         = (TextView) findViewById(R.id.member1mindB_recommend_detail);
        TextView member1energyA       = (TextView) findViewById(R.id.member1energyA_recommend_detail);
        TextView member1energyB       = (TextView) findViewById(R.id.member1energyB_recommend_detail);
        TextView member1natureA       = (TextView) findViewById(R.id.member1natureA_recommend_detail);
        TextView member1natureB       = (TextView) findViewById(R.id.member1natureB_recommend_detail);
        TextView member1tacticsA      = (TextView) findViewById(R.id.member1tacticsA_recommend_detail);
        TextView member1tacticsB      = (TextView) findViewById(R.id.member1tacticsB_recommend_detail);
        TextView member1selfA        = (TextView) findViewById(R.id.member1selfA_recommend_detail);
        TextView member1selfB        = (TextView) findViewById(R.id.member1selfB_recommend_detail);

        TextView member2Name          = (TextView) findViewById(R.id.member2Name_recommend_detail);
        TextView member2Sex           = (TextView) findViewById(R.id.member2Sex_recommend_detail);
        TextView member2Age           = (TextView) findViewById(R.id.member2Age_recommend_detail);
        TextView member2Distance      = (TextView) findViewById(R.id.member2distance_recommend_detail);
        TextView member2Job           = (TextView) findViewById(R.id.member2Job_recommend_detail);
        TextView member2Participation = (TextView) findViewById(R.id.member2Participation_recommend_detail);
        TextView member2Hoperole      = (TextView) findViewById(R.id.member2Hoperole_recommend_detail);
        TextView member2Responsible   = (TextView) findViewById(R.id.member2responsible_recommend_detail);
        TextView member2mindA         = (TextView) findViewById(R.id.member2mindA_recommend_detail);
        TextView member2mindB         = (TextView) findViewById(R.id.member2mindB_recommend_detail);
        TextView member2energyA       = (TextView) findViewById(R.id.member2energyA_recommend_detail);
        TextView member2energyB       = (TextView) findViewById(R.id.member2energyB_recommend_detail);
        TextView member2natureA       = (TextView) findViewById(R.id.member2natureA_recommend_detail);
        TextView member2natureB       = (TextView) findViewById(R.id.member2natureB_recommend_detail);
        TextView member2tacticsA      = (TextView) findViewById(R.id.member2tacticsA_recommend_detail);
        TextView member2tacticsB      = (TextView) findViewById(R.id.member2tacticsB_recommend_detail);
        TextView member2selfA        = (TextView) findViewById(R.id.member2selfA_recommend_detail);
        TextView member2selfB        = (TextView) findViewById(R.id.member2selfB_recommend_detail);

        TextView member3Name          = (TextView) findViewById(R.id.member3Name_recommend_detail);
        TextView member3Sex           = (TextView) findViewById(R.id.member3Sex_recommend_detail);
        TextView member3Age           = (TextView) findViewById(R.id.member3Age_recommend_detail);
        TextView member3Distance      = (TextView) findViewById(R.id.member3distance_recommend_detail);
        TextView member3Job           = (TextView) findViewById(R.id.member3Job_recommend_detail);
        TextView member3Participation = (TextView) findViewById(R.id.member3Participation_recommend_detail);
        TextView member3Hoperole      = (TextView) findViewById(R.id.member3Hoperole_recommend_detail);
        TextView member3Responsible   = (TextView) findViewById(R.id.member3responsible_recommend_detail);
        TextView member3mindA         = (TextView) findViewById(R.id.member3mindA_recommend_detail);
        TextView member3mindB         = (TextView) findViewById(R.id.member3mindB_recommend_detail);
        TextView member3energyA       = (TextView) findViewById(R.id.member3energyA_recommend_detail);
        TextView member3energyB       = (TextView) findViewById(R.id.member3energyB_recommend_detail);
        TextView member3natureA       = (TextView) findViewById(R.id.member3natureA_recommend_detail);
        TextView member3natureB       = (TextView) findViewById(R.id.member3natureB_recommend_detail);
        TextView member3tacticsA      = (TextView) findViewById(R.id.member3tacticsA_recommend_detail);
        TextView member3tacticsB      = (TextView) findViewById(R.id.member3tacticsB_recommend_detail);
        TextView member3selfA        = (TextView) findViewById(R.id.member3selfA_recommend_detail);
        TextView member3selfB        = (TextView) findViewById(R.id.member3selfB_recommend_detail);

        TextView member4Name          = (TextView) findViewById(R.id.member4Name_recommend_detail);
        TextView member4Sex           = (TextView) findViewById(R.id.member4Sex_recommend_detail);
        TextView member4Age           = (TextView) findViewById(R.id.member4Age_recommend_detail);
        TextView member4Distance      = (TextView) findViewById(R.id.member4distance_recommend_detail);
        TextView member4Job           = (TextView) findViewById(R.id.member4Job_recommend_detail);
        TextView member4Participation = (TextView) findViewById(R.id.member4Participation_recommend_detail);
        TextView member4Hoperole      = (TextView) findViewById(R.id.member4Hoperole_recommend_detail);
        TextView member4Responsible   = (TextView) findViewById(R.id.member4responsible_recommend_detail);
        TextView member4mindA         = (TextView) findViewById(R.id.member4mindA_recommend_detail);
        TextView member4mindB         = (TextView) findViewById(R.id.member4mindB_recommend_detail);
        TextView member4energyA       = (TextView) findViewById(R.id.member4energyA_recommend_detail);
        TextView member4energyB       = (TextView) findViewById(R.id.member4energyB_recommend_detail);
        TextView member4natureA       = (TextView) findViewById(R.id.member4natureA_recommend_detail);
        TextView member4natureB       = (TextView) findViewById(R.id.member4natureB_recommend_detail);
        TextView member4tacticsA      = (TextView) findViewById(R.id.member4tacticsA_recommend_detail);
        TextView member4tacticsB      = (TextView) findViewById(R.id.member4tacticsB_recommend_detail);
        TextView member4selfA        = (TextView) findViewById(R.id.member4selfA_recommend_detail);
        TextView member4selfB        = (TextView) findViewById(R.id.member4selfB_recommend_detail);

        View     member1mindABar = (View)                        findViewById(R.id.mindABar1_recommend_detail);
        View     member1mindBBar = (View)                        findViewById(R.id.mindBBar1_recommend_detail);
        View     member2mindABar = (View)                        findViewById(R.id.mindABar2_recommend_detail);
        View     member2mindBBar = (View)                        findViewById(R.id.mindBBar2_recommend_detail);
        View     member3mindABar = (View)                        findViewById(R.id.mindABar3_recommend_detail);
        View     member3mindBBar = (View)                        findViewById(R.id.mindBBar3_recommend_detail);
        View     member4mindABar = (View)                        findViewById(R.id.mindABar4_recommend_detail);
        View     member4mindBBar = (View)                        findViewById(R.id.mindBBar4_recommend_detail);

        View     member1energyABar = (View)                    findViewById(R.id.energyABar1_recommend_detail);
        View     member1energyBBar = (View)                    findViewById(R.id.energyBBar1_recommend_detail);
        View     member2energyABar = (View)                    findViewById(R.id.energyABar2_recommend_detail);
        View     member2energyBBar = (View)                    findViewById(R.id.energyBBar2_recommend_detail);
        View     member3energyABar = (View)                    findViewById(R.id.energyABar3_recommend_detail);
        View     member3energyBBar = (View)                    findViewById(R.id.energyBBar3_recommend_detail);
        View     member4energyABar = (View)                    findViewById(R.id.energyABar4_recommend_detail);
        View     member4energyBBar = (View)                    findViewById(R.id.energyBBar4_recommend_detail);

        View     member1natureABar = (View)                    findViewById(R.id.natureABar1_recommend_detail);
        View     member1natureBBar = (View)                    findViewById(R.id.natureBBar1_recommend_detail);
        View     member2natureABar = (View)                    findViewById(R.id.natureABar2_recommend_detail);
        View     member2natureBBar = (View)                    findViewById(R.id.natureBBar2_recommend_detail);
        View     member3natureABar = (View)                    findViewById(R.id.natureABar3_recommend_detail);
        View     member3natureBBar = (View)                    findViewById(R.id.natureBBar3_recommend_detail);
        View     member4natureABar = (View)                    findViewById(R.id.natureABar4_recommend_detail);
        View     member4natureBBar = (View)                    findViewById(R.id.natureBBar4_recommend_detail);

        View     member1tacticsABar = (View)                  findViewById(R.id.tacticsABar1_recommend_detail);
        View     member1tacticsBBar = (View)                  findViewById(R.id.tacticsBBar1_recommend_detail);
        View     member2tacticsABar = (View)                  findViewById(R.id.tacticsABar2_recommend_detail);
        View     member2tacticsBBar = (View)                  findViewById(R.id.tacticsBBar2_recommend_detail);
        View     member3tacticsABar = (View)                  findViewById(R.id.tacticsABar3_recommend_detail);
        View     member3tacticsBBar = (View)                  findViewById(R.id.tacticsBBar3_recommend_detail);
        View     member4tacticsABar = (View)                  findViewById(R.id.tacticsABar4_recommend_detail);
        View     member4tacticsBBar = (View)                  findViewById(R.id.tacticsBBar4_recommend_detail);

        View     member1selfABar = (View)                        findViewById(R.id.selfABar1_recommend_detail);
        View     member1selfBBar = (View)                        findViewById(R.id.selfBBar1_recommend_detail);
        View     member2selfABar = (View)                        findViewById(R.id.selfABar2_recommend_detail);
        View     member2selfBBar = (View)                        findViewById(R.id.selfBBar2_recommend_detail);
        View     member3selfABar = (View)                        findViewById(R.id.selfABar3_recommend_detail);
        View     member3selfBBar = (View)                        findViewById(R.id.selfBBar3_recommend_detail);
        View     member4selfABar = (View)                        findViewById(R.id.selfABar4_recommend_detail);
        View     member4selfBBar = (View)                        findViewById(R.id.selfBBar4_recommend_detail);

        /***********************팀원 정보**********************************************/
        for(int i=0;i<teamMemberId.size();i++) {
            if (teamMemberId.get(i) != null) {
                try {
                    /***************PhP 회원 정보 받아오기*********************/
                    URLConnector conn = new URLConnector(MainActivity.memberUrl);

                    conn.start();

                    try {
                        conn.join();
                    } catch(InterruptedException e) {

                    }
                    memresult = conn.getResult();
                    JSONObject root = new JSONObject(memresult);
                    JSONArray memInfo = root.getJSONArray("memresult");

                    for (int j = 0; j <memInfo.length(); j++) {

                        JSONObject jsonObject = memInfo.getJSONObject(j);
                        // Pulling items from the array

                        String mem_id = jsonObject.getString("member_id");
                        //System.out.println("회원 아이디 : "+mem_id+"  팀 멤버아이디 : "+teamMemberId.get(i));
                        if(mem_id.equals(teamMemberId.get(i))){
                            LinearLayout.LayoutParams barParams;
                            switch (i){
                                case 0:
                                    member1Name.setText(jsonObject.getString("name"));
                                    member1Sex.setText(jsonObject.getString("gender"));
                                    member1Age.setText(jsonObject.getString("age"));

                                    if(teamMemberDistance.get(i).length()>5){
                                        if(teamMemberDistance.get(i).substring(0,3).equals("200"))
                                            member1Distance.setText(teamMemberDistance.get(i).substring(0,3)+"km↑");
                                        else
                                            member1Distance.setText(teamMemberDistance.get(i).substring(0,5)+"km");
                                    }
                                    else
                                        member1Distance.setText(teamMemberDistance.get(i)+"km");
                                    member1Job.setText(jsonObject.getString("job"));
                                    member1Participation.setText(jsonObject.getString("participation_count"));
                                    member1Hoperole.setText(jsonObject.getString("hope_role"));
                                    member1Responsible.setText(teamMemberResponsible.get(i));

                                    int mindA= 100-Integer.parseInt(jsonObject.getString("mind"));
                                    System.out.println(mindA);
                                    member1mindA.setText(Integer.toString(mindA));
                                    member1mindB.setText(jsonObject.getString("mind"));

                                    barParams = (LinearLayout.LayoutParams) member1mindBBar.getLayoutParams();
                                    barParams.weight = 100-mindA;
                                    member1mindBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member1mindABar.getLayoutParams();
                                    barParams.weight = mindA;
                                    member1mindABar.setLayoutParams(barParams);

                                    int energyA= 100-Integer.parseInt(jsonObject.getString("energy"));
                                    member1energyA.setText(Integer.toString(energyA));
                                    member1energyB.setText(jsonObject.getString("energy"));

                                    barParams = (LinearLayout.LayoutParams) member1energyBBar.getLayoutParams();
                                    barParams.weight = 100-energyA;
                                    member1energyBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member1energyABar.getLayoutParams();
                                    barParams.weight = energyA;
                                    member1energyABar.setLayoutParams(barParams);

                                    int natureA= 100-Integer.parseInt(jsonObject.getString("nature"));
                                    member1natureA.setText(Integer.toString(natureA));
                                    member1natureB.setText(jsonObject.getString("nature"));

                                    barParams = (LinearLayout.LayoutParams) member1natureBBar.getLayoutParams();
                                    barParams.weight = 100-natureA;
                                    member1natureBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member1natureABar.getLayoutParams();
                                    barParams.weight = natureA;
                                    member1natureABar.setLayoutParams(barParams);

                                    int tacticsA= 100-Integer.parseInt(jsonObject.getString("tactics"));
                                    member1tacticsA.setText(Integer.toString(tacticsA));
                                    member1tacticsB.setText(jsonObject.getString("tactics"));

                                    barParams = (LinearLayout.LayoutParams) member1tacticsBBar.getLayoutParams();
                                    barParams.weight = 100-tacticsA;
                                    member1tacticsBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member1tacticsABar.getLayoutParams();
                                    barParams.weight = tacticsA;
                                    member1tacticsABar.setLayoutParams(barParams);

                                    int selfA= 100-Integer.parseInt(jsonObject.getString("ego"));
                                    member1selfA.setText(Integer.toString(selfA));
                                    member1selfB.setText(jsonObject.getString("ego"));

                                    barParams = (LinearLayout.LayoutParams) member1selfBBar.getLayoutParams();
                                    barParams.weight = 100-selfA;
                                    member1selfBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member1selfABar.getLayoutParams();
                                    barParams.weight = selfA;
                                    member1selfABar.setLayoutParams(barParams);

                                    break;

                                case 1:
                                    member2Name.setText(jsonObject.getString("name"));
                                    member2Sex.setText(jsonObject.getString("gender"));
                                    member2Age.setText(jsonObject.getString("age"));
                                    if(teamMemberDistance.get(i).length()>5){
                                        if(teamMemberDistance.get(i).substring(0,3).equals("200"))
                                            member2Distance.setText(teamMemberDistance.get(i).substring(0,3)+"km↑");
                                        else
                                            member2Distance.setText(teamMemberDistance.get(i).substring(0,5)+"km");
                                    }
                                    else
                                        member2Distance.setText(teamMemberDistance.get(i)+"km");
                                    member2Job.setText(jsonObject.getString("job"));
                                    member2Participation.setText(jsonObject.getString("participation_count"));
                                    member2Hoperole.setText(jsonObject.getString("hope_role"));
                                    member2Responsible.setText(teamMemberResponsible.get(i));

                                    mindA= 100-Integer.parseInt(jsonObject.getString("mind"));
                                    System.out.println(mindA);
                                    member2mindA.setText(Integer.toString(mindA));
                                    member2mindB.setText(jsonObject.getString("mind"));

                                    barParams = (LinearLayout.LayoutParams) member2mindBBar.getLayoutParams();
                                    barParams.weight = 100-mindA;
                                    member2mindBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member2mindABar.getLayoutParams();
                                    barParams.weight = mindA;
                                    member2mindABar.setLayoutParams(barParams);

                                    energyA= 100-Integer.parseInt(jsonObject.getString("energy"));
                                    member2energyA.setText(Integer.toString(energyA));
                                    member2energyB.setText(jsonObject.getString("energy"));

                                    barParams = (LinearLayout.LayoutParams) member2energyBBar.getLayoutParams();
                                    barParams.weight = 100-energyA;
                                    member2energyBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member2energyABar.getLayoutParams();
                                    barParams.weight = energyA;
                                    member2energyABar.setLayoutParams(barParams);

                                    natureA= 100-Integer.parseInt(jsonObject.getString("nature"));
                                    member2natureA.setText(Integer.toString(natureA));
                                    member2natureB.setText(jsonObject.getString("nature"));

                                    barParams = (LinearLayout.LayoutParams) member2natureBBar.getLayoutParams();
                                    barParams.weight = 100-natureA;
                                    member2natureBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member2natureABar.getLayoutParams();
                                    barParams.weight = natureA;
                                    member2natureABar.setLayoutParams(barParams);

                                    tacticsA= 100-Integer.parseInt(jsonObject.getString("tactics"));
                                    member2tacticsA.setText(Integer.toString(tacticsA));
                                    member2tacticsB.setText(jsonObject.getString("tactics"));

                                    barParams = (LinearLayout.LayoutParams) member2tacticsBBar.getLayoutParams();
                                    barParams.weight = 100-tacticsA;
                                    member2tacticsBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member2tacticsABar.getLayoutParams();
                                    barParams.weight = tacticsA;
                                    member2tacticsABar.setLayoutParams(barParams);

                                    selfA= 100-Integer.parseInt(jsonObject.getString("ego"));
                                    member2selfA.setText(Integer.toString(selfA));
                                    member2selfB.setText(jsonObject.getString("ego"));

                                    barParams = (LinearLayout.LayoutParams) member2selfBBar.getLayoutParams();
                                    barParams.weight = 100-selfA;
                                    member2selfBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member2selfABar.getLayoutParams();
                                    barParams.weight = selfA;
                                    member2selfABar.setLayoutParams(barParams);

                                    break;
                                case 2:
                                    member3Name.setText(jsonObject.getString("name"));
                                    member3Sex.setText(jsonObject.getString("gender"));
                                    member3Age.setText(jsonObject.getString("age"));
                                    if(teamMemberDistance.get(i).length()>5){
                                        if(teamMemberDistance.get(i).substring(0,3).equals("200"))
                                            member3Distance.setText(teamMemberDistance.get(i).substring(0,3)+"km↑");
                                        else
                                            member3Distance.setText(teamMemberDistance.get(i).substring(0,5)+"km");
                                    }
                                    else
                                        member3Distance.setText(teamMemberDistance.get(i)+"km");
                                    member3Job.setText(jsonObject.getString("job"));
                                    member3Participation.setText(jsonObject.getString("participation_count"));
                                    member3Hoperole.setText(jsonObject.getString("hope_role"));
                                    member3Responsible.setText(teamMemberResponsible.get(i));

                                    mindA= 100-Integer.parseInt(jsonObject.getString("mind"));
                                    System.out.println(mindA);
                                    member3mindA.setText(Integer.toString(mindA));
                                    member3mindB.setText(jsonObject.getString("mind"));

                                    barParams = (LinearLayout.LayoutParams) member3mindBBar.getLayoutParams();
                                    barParams.weight = 100-mindA;
                                    member3mindBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member3mindABar.getLayoutParams();
                                    barParams.weight = mindA;
                                    member3mindABar.setLayoutParams(barParams);

                                    energyA= 100-Integer.parseInt(jsonObject.getString("energy"));
                                    member3energyA.setText(Integer.toString(energyA));
                                    member3energyB.setText(jsonObject.getString("energy"));

                                    barParams = (LinearLayout.LayoutParams) member3energyBBar.getLayoutParams();
                                    barParams.weight = 100-energyA;
                                    member3energyBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member3energyABar.getLayoutParams();
                                    barParams.weight = energyA;
                                    member3energyABar.setLayoutParams(barParams);

                                    natureA= 100-Integer.parseInt(jsonObject.getString("nature"));
                                    member3natureA.setText(Integer.toString(natureA));
                                    member3natureB.setText(jsonObject.getString("nature"));

                                    barParams = (LinearLayout.LayoutParams) member3natureBBar.getLayoutParams();
                                    barParams.weight = 100-natureA;
                                    member3natureBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member3natureABar.getLayoutParams();
                                    barParams.weight = natureA;
                                    member3natureABar.setLayoutParams(barParams);

                                    tacticsA= 100-Integer.parseInt(jsonObject.getString("tactics"));
                                    member3tacticsA.setText(Integer.toString(tacticsA));
                                    member3tacticsB.setText(jsonObject.getString("tactics"));

                                    barParams = (LinearLayout.LayoutParams) member3tacticsBBar.getLayoutParams();
                                    barParams.weight = 100-tacticsA;
                                    member3tacticsBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member3tacticsABar.getLayoutParams();
                                    barParams.weight = tacticsA;
                                    member3tacticsABar.setLayoutParams(barParams);

                                    selfA= 100-Integer.parseInt(jsonObject.getString("ego"));
                                    member3selfA.setText(Integer.toString(selfA));
                                    member3selfB.setText(jsonObject.getString("ego"));

                                    barParams = (LinearLayout.LayoutParams) member3selfBBar.getLayoutParams();
                                    barParams.weight = 100-selfA;
                                    member3selfBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member3selfABar.getLayoutParams();
                                    barParams.weight = selfA;
                                    member3selfABar.setLayoutParams(barParams);

                                    break;
                                case 3:
                                    member4Name.setText(jsonObject.getString("name"));
                                    member4Sex.setText(jsonObject.getString("gender"));
                                    member4Age.setText(jsonObject.getString("age"));
                                    if(teamMemberDistance.get(i).length()>5){
                                        if(teamMemberDistance.get(i).substring(0,3).equals("200"))
                                            member4Distance.setText(teamMemberDistance.get(i).substring(0,3)+"km↑");
                                        else
                                            member4Distance.setText(teamMemberDistance.get(i).substring(0,5)+"km");
                                    }
                                    else
                                        member4Distance.setText(teamMemberDistance.get(i)+"km");
                                    member4Job.setText(jsonObject.getString("job"));
                                    member4Participation.setText(jsonObject.getString("participation_count"));
                                    member4Hoperole.setText(jsonObject.getString("hope_role"));
                                    member4Responsible.setText(teamMemberResponsible.get(i));

                                    mindA= 100-Integer.parseInt(jsonObject.getString("mind"));
                                    System.out.println(mindA);
                                    member4mindA.setText(Integer.toString(mindA));
                                    member4mindB.setText(jsonObject.getString("mind"));

                                    barParams = (LinearLayout.LayoutParams) member4mindBBar.getLayoutParams();
                                    barParams.weight = 100-mindA;
                                    member4mindBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member4mindABar.getLayoutParams();
                                    barParams.weight = mindA;
                                    member4mindABar.setLayoutParams(barParams);

                                    energyA= 100-Integer.parseInt(jsonObject.getString("energy"));
                                    member4energyA.setText(Integer.toString(energyA));
                                    member4energyB.setText(jsonObject.getString("energy"));

                                    barParams = (LinearLayout.LayoutParams) member4energyBBar.getLayoutParams();
                                    barParams.weight = 100-energyA;
                                    member4energyBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member4energyABar.getLayoutParams();
                                    barParams.weight = energyA;
                                    member4energyABar.setLayoutParams(barParams);

                                    natureA= 100-Integer.parseInt(jsonObject.getString("nature"));
                                    member4natureA.setText(Integer.toString(natureA));
                                    member4natureB.setText(jsonObject.getString("nature"));

                                    barParams = (LinearLayout.LayoutParams) member4natureBBar.getLayoutParams();
                                    barParams.weight = 100-natureA;
                                    member4natureBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member4natureABar.getLayoutParams();
                                    barParams.weight = natureA;
                                    member4natureABar.setLayoutParams(barParams);

                                    tacticsA= 100-Integer.parseInt(jsonObject.getString("tactics"));
                                    member4tacticsA.setText(Integer.toString(tacticsA));
                                    member4tacticsB.setText(jsonObject.getString("tactics"));

                                    barParams = (LinearLayout.LayoutParams) member4tacticsBBar.getLayoutParams();
                                    barParams.weight = 100-tacticsA;
                                    member4tacticsBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member4tacticsABar.getLayoutParams();
                                    barParams.weight = tacticsA;
                                    member4tacticsABar.setLayoutParams(barParams);

                                    selfA= 100-Integer.parseInt(jsonObject.getString("ego"));
                                    member4selfA.setText(Integer.toString(selfA));
                                    member4selfB.setText(jsonObject.getString("ego"));

                                    barParams = (LinearLayout.LayoutParams) member4selfBBar.getLayoutParams();
                                    barParams.weight = 100-selfA;
                                    member4selfBBar.setLayoutParams(barParams);
                                    barParams = (LinearLayout.LayoutParams) member4selfABar.getLayoutParams();
                                    barParams.weight = selfA;
                                    member4selfABar.setLayoutParams(barParams);

                                    break;
                            }

                        }



                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }





        //팀원 수에 맞게 세부정보를 보여준다.
        //ex) 3명팀이면 팀원 1, 2에 대한 정보만 보이고 팀원 3, 4에 대해서 안나오게
        LinearLayout teamMember2 = (LinearLayout) findViewById(R.id.teamMember2_recommend_detail);
        LinearLayout teamMember3 = (LinearLayout) findViewById(R.id.teamMember3_recommend_detail);
        LinearLayout teamMember4 = (LinearLayout) findViewById(R.id.teamMember4_recommend_detail);
        switch(NOTM) {
            case 2:
                params = (LinearLayout.LayoutParams) teamMember2.getLayoutParams();
                params.height = 0;
                teamMember2.setLayoutParams(params);
            case 3:
                params = (LinearLayout.LayoutParams) teamMember3.getLayoutParams();
                params.height = 0;
                teamMember3.setLayoutParams(params);
            case 4:
                params = (LinearLayout.LayoutParams) teamMember4.getLayoutParams();
                params.height = 0;
                teamMember4.setLayoutParams(params);
        }

        ImageView cancelBtn = (ImageView) findViewById(R.id.cancelBtn_recommend_detail);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamDetail.setBackgroundColor(Color.parseColor("#00000000"));
                teamDetail.removeAllViews();
                teamDetail=null;
            }
        });

        Button sendMsgBtn = (Button) findViewById(R.id.sendMsgBtn_recommend_second);
        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamDetail.setBackgroundColor(Color.parseColor("#00000000"));
                teamDetail.removeAllViews();
                teamDetail=null;
                InsertTeam task = new InsertTeam();
                task.execute(MainActivity.insertTeamUrl,Integer.toString(currentMemberID), Integer.toString(competitionId),teamMemberId.toString());
                showNegativeDialog("메시지를 성공적으로 보냈습니다.");
            }
        });
    }

    // 팀 추천 결과 함수
    public void recommendSuccess(){
        FindRecommendTeamData task = new FindRecommendTeamData();

        task.execute(MainActivity.recommendUrl, Integer.toString(currentMemberID), Integer.toString(numOfPeopleForTeam),
                competitionName, capacityEssential.toString(), capacityPlus.toString(), Integer.toString(checkedBtn));

    }

    // 팀 추천 결과 요청 AsynkTask
    class FindRecommendTeamData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RecommendActivity.this, "Please Wait", "팀원 추천 중입니다.", true, true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            teamResult = result;
            System.out.println(teamResult);

            if(progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }

            if(result.equals("TimeoutException")) {
                RecommendActivity.this.showNegativeDialog("잠시 후에 다시 이용해주세요.");
            } else {
                changeFragment(fourthFragment);
            }

        }

        @Override
        protected String doInBackground(String... params) {

            int mCurrentMemberID = Integer.parseInt(params[1]);           // 현재 로그인한 회원 id
            int mNumOfPeopleForTeam = Integer.parseInt(params[2]);        // 팀원으로 원하는 인원 수
            String mCompetitionName = (String)params[3];                          // 선택한 공모전명
            String mCapacityEssential = (String)params[4];                        // 필수 역량 리스트
            String mCapacityPlus = (String)params[5];                             // 추가 역량 리스트
            int mCheckedBtn = Integer.parseInt(params[6]);

            String[] array_mCapacityEssential = mCapacityEssential.substring(1,mCapacityEssential.length()-1).split(",");
            String[] array_mCapacityPlus = mCapacityPlus.substring(1,mCapacityPlus.length()-1).split(",");

            JSONArray json_mCapacityEssential = new JSONArray();
            JSONArray json_mCapacityPlus = new JSONArray();
            try {
                for (int i = 0; i < array_mCapacityEssential.length; i++) {  // 필수 역량 JSONArray형태로 변경
                    JSONObject object = new JSONObject();
                    object.put(Integer.toString(i), array_mCapacityEssential[i]);
                    json_mCapacityEssential.put(object);
                }
                for (int i = 0; i < array_mCapacityPlus.length; i++) {  // 추가 역량 JSONArray형태로 변경
                    JSONObject object = new JSONObject();
                    object.put(Integer.toString(i), array_mCapacityPlus[i]);
                    json_mCapacityPlus.put(object);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            String serverURL = (String)params[0];



            //PHP로 POST
            String postParameters = "current_member_id=" + mCurrentMemberID + "&num_of_people_for_team=" + mNumOfPeopleForTeam + "&competition_name="
                    + mCompetitionName + "&checked_btn=" + mCheckedBtn + "&essential=" + json_mCapacityEssential + "&additional=" + json_mCapacityPlus;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setConnectTimeout(50000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
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
                Log.d("postError",e.getMessage());
                return new String("TimeoutException");
            }
        }
    }

    class InsertTeam extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);
        }

        @Override
        protected String doInBackground(String... params) {

            int sendmember_id = Integer.parseInt(params[1]);   //요청보내는 회원 아이디
            int competition_id = Integer.parseInt(params[2]); // 요청하는 공모전 아이디

            String tM_id = (String)params[3];  //요청받는 회원들 아이디
            String[] array_teamMembers_id = tM_id.substring(1,tM_id.length() - 1).split(",");
            JSONArray teamMembers_id = new JSONArray();
            try {
                for (int i = 0; i < array_teamMembers_id.length; i++) {  //JSONArray형태로 변경
                    JSONObject object = new JSONObject();
                    object.put(Integer.toString(i), Integer.parseInt(array_teamMembers_id[i].trim()));
                    teamMembers_id.put(object);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            System.out.println(teamMembers_id);
            String serverURL = (String)params[0];
            String postParameters = "sendmember_id=" + sendmember_id + "&competition_id=" + competition_id+ "&teamMembers_id=" + teamMembers_id;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setConnectTimeout(50000);
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
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
