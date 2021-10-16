package com.example.opgrad;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {
    private AlertDialog dialog;

    private LinearLayout.LayoutParams params;

    private FragmentManager fragmentManager;
    private ManageFragmentSend sendFragment;
    private ManageFragmentReceive receiveFragment;
    private FragmentTransaction fragmentTransaction;

    private TextView sendBar;
    private TextView sendTeam;
    private TextView receiveTeam;
    private TextView receiveBar;

    Context context;

    public List<String> teamMemberId=new ArrayList<String>();; //  팀원아이디들
    public List<String> teamMemberDistance=new ArrayList<String>();; //  팀원아이디들
    public List<String> teamMemberResponsible=new ArrayList<String>();; //  팀원아이디들

    LinearLayout teamDetail;

    static public String memresult="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        context =  this;

        fragmentManager = getSupportFragmentManager();

        sendFragment = new ManageFragmentSend();
        receiveFragment = new ManageFragmentReceive();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_manage, sendFragment).commitAllowingStateLoss();


        sendTeam = findViewById(R.id.sendTeam_manage);
        sendTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(sendFragment);
            }
        });

        receiveTeam = findViewById(R.id.receiveTeam_manage);
        receiveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(receiveFragment);
            }
        });
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
    public void onBackPressed() {

        if(teamDetail!=null){
            teamDetail.setBackgroundColor(Color.parseColor("#00000000"));
            teamDetail.removeAllViews();
            teamDetail=null;}
        else
            super.onBackPressed();
    }
    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_manage, fragment).commitAllowingStateLoss();

        sendBar = findViewById(R.id.sendBar_manage);
        receiveBar = findViewById(R.id.receiveBar_manage);

        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        if(fragment.toString().contains("Send")) {
            sendBar.setTextColor(colorPrimaryDark);
            sendTeam.setTextColor(colorPrimaryDark);
            sendTeam.setTextSize(15);

            receiveBar.setTextColor(colorPrimary);
            receiveTeam.setTextColor(colorPrimary);
            receiveTeam.setTextSize(13);

        } else {
            sendBar.setTextColor(colorPrimary);
            sendTeam.setTextColor(colorPrimary);
            sendTeam.setTextSize(13);

            receiveBar.setTextColor(colorPrimaryDark);
            receiveTeam.setTextColor(colorPrimaryDark);
            receiveTeam.setTextSize(15);
        }
    }

    public void showSendTeamDetail(int NOTM, String State1, String State2, String State3, String State4) {
        //teamDetail
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        teamDetail = (LinearLayout) inflater.inflate(R.layout.send_team_detail, null);
        teamDetail.setBackgroundColor(Color.parseColor("#99000000"));
        final LinearLayout.LayoutParams param
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(teamDetail, param);

        //팀원 수에 맞게 세부정보를 보여준다.
        //ex) 3명팀이면 팀원 1, 2에 대한 정보만 보이고 팀원 3, 4에 대해서 안나오게
        LinearLayout teamMember2 = (LinearLayout) findViewById(R.id.teamMember2_send_team_detail);
        LinearLayout teamMember3 = (LinearLayout) findViewById(R.id.teamMember3_send_team_detail);
        LinearLayout teamMember4 = (LinearLayout) findViewById(R.id.teamMember4_send_team_detail);
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

        //상태에 맞게 핸드폰 번호와 이메일을 노출한다.(state가 Y일때만)
        /***********************팀원 정보**********************************************/
        TextView member1Name          = (TextView)          findViewById(R.id.member1Name_send_detail);
        TextView member1Sex           = (TextView)           findViewById(R.id.member1Sex_send_detail);
        TextView member1Age           = (TextView)           findViewById(R.id.member1Age_send_detail);
        TextView member1Distance      = (TextView)      findViewById(R.id.member1Distance_send_detail);
        TextView member1Job           = (TextView)           findViewById(R.id.member1Job_send_detail);
        TextView member1Participation = (TextView) findViewById(R.id.member1Participation_send_detail);
        TextView member1Hoperole      = (TextView)      findViewById(R.id.member1Hoperole_send_detail);
        TextView member1Responsible   = (TextView)   findViewById(R.id.member1Responsible_send_detail);
        TextView member1mindA         = (TextView)         findViewById(R.id.member1mindA_send_detail);
        TextView member1mindB         = (TextView)         findViewById(R.id.member1mindB_send_detail);
        TextView member1energyA       = (TextView)       findViewById(R.id.member1energyA_send_detail);
        TextView member1energyB       = (TextView)       findViewById(R.id.member1energyB_send_detail);
        TextView member1natureA       = (TextView)       findViewById(R.id.member1natureA_send_detail);
        TextView member1natureB       = (TextView)       findViewById(R.id.member1natureB_send_detail);
        TextView member1tacticsA      = (TextView)      findViewById(R.id.member1tacticsA_send_detail);
        TextView member1tacticsB      = (TextView)      findViewById(R.id.member1tacticsB_send_detail);
        TextView member1selfA        = (TextView)          findViewById(R.id.member1selfA_send_detail);
        TextView member1selfB        = (TextView)          findViewById(R.id.member1selfB_send_detail);

        TextView member2Name          = (TextView)          findViewById(R.id.member2Name_send_detail);
        TextView member2Sex           = (TextView)           findViewById(R.id.member2Sex_send_detail);
        TextView member2Age           = (TextView)           findViewById(R.id.member2Age_send_detail);
        TextView member2Distance      = (TextView)      findViewById(R.id.member2Distance_send_detail);
        TextView member2Job           = (TextView)           findViewById(R.id.member2Job_send_detail);
        TextView member2Participation = (TextView) findViewById(R.id.member2Participation_send_detail);
        TextView member2Hoperole      = (TextView)      findViewById(R.id.member2Hoperole_send_detail);
        TextView member2Responsible   = (TextView)   findViewById(R.id.member2Responsible_send_detail);
        TextView member2mindA         = (TextView)         findViewById(R.id.member2mindA_send_detail);
        TextView member2mindB         = (TextView)         findViewById(R.id.member2mindB_send_detail);
        TextView member2energyA       = (TextView)       findViewById(R.id.member2energyA_send_detail);
        TextView member2energyB       = (TextView)       findViewById(R.id.member2energyB_send_detail);
        TextView member2natureA       = (TextView)       findViewById(R.id.member2natureA_send_detail);
        TextView member2natureB       = (TextView)       findViewById(R.id.member2natureB_send_detail);
        TextView member2tacticsA      = (TextView)      findViewById(R.id.member2tacticsA_send_detail);
        TextView member2tacticsB      = (TextView)      findViewById(R.id.member2tacticsB_send_detail);
        TextView member2selfA        = (TextView)          findViewById(R.id.member2selfA_send_detail);
        TextView member2selfB        = (TextView)          findViewById(R.id.member2selfB_send_detail);

        TextView member3Name          = (TextView)          findViewById(R.id.member3Name_send_detail);
        TextView member3Sex           = (TextView)           findViewById(R.id.member3Sex_send_detail);
        TextView member3Age           = (TextView)           findViewById(R.id.member3Age_send_detail);
        TextView member3Distance      = (TextView)      findViewById(R.id.member3Distance_send_detail);
        TextView member3Job           = (TextView)           findViewById(R.id.member3Job_send_detail);
        TextView member3Participation = (TextView) findViewById(R.id.member3Participation_send_detail);
        TextView member3Hoperole      = (TextView)      findViewById(R.id.member3Hoperole_send_detail);
        TextView member3Responsible   = (TextView)   findViewById(R.id.member3Responsible_send_detail);
        TextView member3mindA         = (TextView)         findViewById(R.id.member3mindA_send_detail);
        TextView member3mindB         = (TextView)         findViewById(R.id.member3mindB_send_detail);
        TextView member3energyA       = (TextView)       findViewById(R.id.member3energyA_send_detail);
        TextView member3energyB       = (TextView)       findViewById(R.id.member3energyB_send_detail);
        TextView member3natureA       = (TextView)       findViewById(R.id.member3natureA_send_detail);
        TextView member3natureB       = (TextView)       findViewById(R.id.member3natureB_send_detail);
        TextView member3tacticsA      = (TextView)      findViewById(R.id.member3tacticsA_send_detail);
        TextView member3tacticsB      = (TextView)      findViewById(R.id.member3tacticsB_send_detail);
        TextView member3selfA        = (TextView)          findViewById(R.id.member3selfA_send_detail);
        TextView member3selfB        = (TextView)          findViewById(R.id.member3selfB_send_detail);

        TextView member4Name          = (TextView)          findViewById(R.id.member4Name_send_detail);
        TextView member4Sex           = (TextView)           findViewById(R.id.member4Sex_send_detail);
        TextView member4Age           = (TextView)           findViewById(R.id.member4Age_send_detail);
        TextView member4Distance      = (TextView)      findViewById(R.id.member4Distance_send_detail);
        TextView member4Job           = (TextView)           findViewById(R.id.member4Job_send_detail);
        TextView member4Participation = (TextView) findViewById(R.id.member4Participation_send_detail);
        TextView member4Hoperole      = (TextView)      findViewById(R.id.member4Hoperole_send_detail);
        TextView member4Responsible   = (TextView)   findViewById(R.id.member4Responsible_send_detail);
        TextView member4mindA         = (TextView)         findViewById(R.id.member4mindA_send_detail);
        TextView member4mindB         = (TextView)         findViewById(R.id.member4mindB_send_detail);
        TextView member4energyA       = (TextView)       findViewById(R.id.member4energyA_send_detail);
        TextView member4energyB       = (TextView)       findViewById(R.id.member4energyB_send_detail);
        TextView member4natureA       = (TextView)       findViewById(R.id.member4natureA_send_detail);
        TextView member4natureB       = (TextView)       findViewById(R.id.member4natureB_send_detail);
        TextView member4tacticsA      = (TextView)      findViewById(R.id.member4tacticsA_send_detail);
        TextView member4tacticsB      = (TextView)      findViewById(R.id.member4tacticsB_send_detail);
        TextView member4selfA        = (TextView)          findViewById(R.id.member4selfA_send_detail);
        TextView member4selfB        = (TextView)          findViewById(R.id.member4selfB_send_detail);

        TextView phone1 = (TextView) findViewById(R.id.phone1_send_team_detail);
        TextView phone2 = (TextView) findViewById(R.id.phone2_send_team_detail);
        TextView phone3 = (TextView) findViewById(R.id.phone3_send_team_detail);
        TextView phone4 = (TextView) findViewById(R.id.phone4_send_team_detail);

        TextView email1 = (TextView) findViewById(R.id.email1_send_team_detail);
        TextView email2 = (TextView) findViewById(R.id.email2_send_team_detail);
        TextView email3 = (TextView) findViewById(R.id.email3_send_team_detail);
        TextView email4 = (TextView) findViewById(R.id.email4_send_team_detail);
        View     member1mindABar = (View)                        findViewById(R.id.mindABar1_send_team_detail);
        View     member1mindBBar = (View)                        findViewById(R.id.mindBBar1_send_team_detail);
        View     member2mindABar = (View)                        findViewById(R.id.mindABar2_send_team_detail);
        View     member2mindBBar = (View)                        findViewById(R.id.mindBBar2_send_team_detail);
        View     member3mindABar = (View)                        findViewById(R.id.mindABar3_send_team_detail);
        View     member3mindBBar = (View)                        findViewById(R.id.mindBBar3_send_team_detail);
        View     member4mindABar = (View)                        findViewById(R.id.mindABar4_send_team_detail);
        View     member4mindBBar = (View)                        findViewById(R.id.mindBBar4_send_team_detail);

        View     member1energyABar = (View)                    findViewById(R.id.energyABar1_send_team_detail);
        View     member1energyBBar = (View)                    findViewById(R.id.energyBBar1_send_team_detail);
        View     member2energyABar = (View)                    findViewById(R.id.energyABar2_send_team_detail);
        View     member2energyBBar = (View)                    findViewById(R.id.energyBBar2_send_team_detail);
        View     member3energyABar = (View)                    findViewById(R.id.energyABar3_send_team_detail);
        View     member3energyBBar = (View)                    findViewById(R.id.energyBBar3_send_team_detail);
        View     member4energyABar = (View)                    findViewById(R.id.energyABar4_send_team_detail);
        View     member4energyBBar = (View)                    findViewById(R.id.energyBBar4_send_team_detail);

        View     member1natureABar = (View)                    findViewById(R.id.natureABar1_send_team_detail);
        View     member1natureBBar = (View)                    findViewById(R.id.natureBBar1_send_team_detail);
        View     member2natureABar = (View)                    findViewById(R.id.natureABar2_send_team_detail);
        View     member2natureBBar = (View)                    findViewById(R.id.natureBBar2_send_team_detail);
        View     member3natureABar = (View)                    findViewById(R.id.natureABar3_send_team_detail);
        View     member3natureBBar = (View)                    findViewById(R.id.natureBBar3_send_team_detail);
        View     member4natureABar = (View)                    findViewById(R.id.natureABar4_send_team_detail);
        View     member4natureBBar = (View)                    findViewById(R.id.natureBBar4_send_team_detail);

        View     member1tacticsABar = (View)                  findViewById(R.id.tacticsABar1_send_team_detail);
        View     member1tacticsBBar = (View)                  findViewById(R.id.tacticsBBar1_send_team_detail);
        View     member2tacticsABar = (View)                  findViewById(R.id.tacticsABar2_send_team_detail);
        View     member2tacticsBBar = (View)                  findViewById(R.id.tacticsBBar2_send_team_detail);
        View     member3tacticsABar = (View)                  findViewById(R.id.tacticsABar3_send_team_detail);
        View     member3tacticsBBar = (View)                  findViewById(R.id.tacticsBBar3_send_team_detail);
        View     member4tacticsABar = (View)                  findViewById(R.id.tacticsABar4_send_team_detail);
        View     member4tacticsBBar = (View)                  findViewById(R.id.tacticsBBar4_send_team_detail);

        View     member1selfABar = (View)                        findViewById(R.id.selfABar1_send_team_detail);
        View     member1selfBBar = (View)                        findViewById(R.id.selfBBar1_send_team_detail);
        View     member2selfABar = (View)                        findViewById(R.id.selfABar2_send_team_detail);
        View     member2selfBBar = (View)                        findViewById(R.id.selfBBar2_send_team_detail);
        View     member3selfABar = (View)                        findViewById(R.id.selfABar3_send_team_detail);
        View     member3selfBBar = (View)                        findViewById(R.id.selfBBar3_send_team_detail);
        View     member4selfABar = (View)                        findViewById(R.id.selfABar4_send_team_detail);
        View     member4selfBBar = (View)                        findViewById(R.id.selfBBar4_send_team_detail);

        System.out.println(teamMemberId);
        System.out.println(teamMemberDistance);
        System.out.println(teamMemberResponsible);
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
                                    phone1.setText(jsonObject.getString("contact"));
                                    email1.setText(jsonObject.getString("email"));

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
                                    phone2.setText(jsonObject.getString("contact"));
                                    email2.setText(jsonObject.getString("email"));

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
                                    phone3.setText(jsonObject.getString("contact"));
                                    email3.setText(jsonObject.getString("email"));

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
                                    phone4.setText(jsonObject.getString("contact"));
                                    email4.setText(jsonObject.getString("email"));

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

        if(!State1.equals("수락")) {
            phone1.setText(" ");
            email1.setText(" ");
        }
        if(!State2.equals("수락")) {
            phone2.setText(" ");
            email2.setText(" ");
        }
        if(!State3.equals("수락")) {
            phone3.setText(" ");
            email3.setText(" ");
        }
        if(!State4.equals("수락")) {
            phone4.setText(" ");
            email4.setText(" ");
        }

        //cancel button & click listener
        ImageView cancelBtn = (ImageView) findViewById(R.id.cancelBtn_send_team_detail);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close inflater
                teamDetail.setBackgroundColor(Color.parseColor("#00000000"));
                teamDetail.removeAllViews();
                teamDetail=null;
            }
        });
    }

    public void showReceiveTeamDetail(int NOTM, String teamname) {
        //teamDetail
        final String team = teamname;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        teamDetail = (LinearLayout) inflater.inflate(R.layout.receive_team_detail, null);
        teamDetail.setBackgroundColor(Color.parseColor("#99000000"));
        final LinearLayout.LayoutParams param
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(teamDetail, param);

        /***********************팀원 정보**********************************************/
        TextView member1Name          = (TextView)          findViewById(R.id.member1Name_recieve_detail);
        TextView member1Sex           = (TextView)           findViewById(R.id.member1Sex_recieve_detail);
        TextView member1Age           = (TextView)           findViewById(R.id.member1Age_recieve_detail);
        TextView member1Distance      = (TextView)      findViewById(R.id.member1Distance_recieve_detail);
        TextView member1Job           = (TextView)           findViewById(R.id.member1Job_recieve_detail);
        TextView member1Participation = (TextView) findViewById(R.id.member1Participation_recieve_detail);
        TextView member1Hoperole      = (TextView)      findViewById(R.id.member1Hoperole_recieve_detail);
        TextView member1Responsible   = (TextView)   findViewById(R.id.member1Responsible_recieve_detail);
        TextView member1mindA         = (TextView)         findViewById(R.id.member1mindA_receive_detail);
        TextView member1mindB         = (TextView)         findViewById(R.id.member1mindB_receive_detail);
        TextView member1energyA       = (TextView)       findViewById(R.id.member1energyA_receive_detail);
        TextView member1energyB       = (TextView)       findViewById(R.id.member1energyB_receive_detail);
        TextView member1natureA       = (TextView)       findViewById(R.id.member1natureA_receive_detail);
        TextView member1natureB       = (TextView)       findViewById(R.id.member1natureB_receive_detail);
        TextView member1tacticsA      = (TextView)      findViewById(R.id.member1tacticsA_receive_detail);
        TextView member1tacticsB      = (TextView)      findViewById(R.id.member1tacticsB_receive_detail);
        TextView member1selfA        = (TextView)          findViewById(R.id.member1selfA_receive_detail);
        TextView member1selfB        = (TextView)          findViewById(R.id.member1selfB_receive_detail);

        TextView member2Name          = (TextView)          findViewById(R.id.member2Name_recieve_detail);
        TextView member2Sex           = (TextView)           findViewById(R.id.member2Sex_recieve_detail);
        TextView member2Age           = (TextView)           findViewById(R.id.member2Age_recieve_detail);
        TextView member2Distance      = (TextView)      findViewById(R.id.member2Distance_recieve_detail);
        TextView member2Job           = (TextView)           findViewById(R.id.member2Job_recieve_detail);
        TextView member2Participation = (TextView) findViewById(R.id.member2Participation_recieve_detail);
        TextView member2Hoperole      = (TextView)      findViewById(R.id.member2Hoperole_recieve_detail);
        TextView member2Responsible   = (TextView)   findViewById(R.id.member2Responsible_recieve_detail);
        TextView member2mindA         = (TextView)         findViewById(R.id.member2mindA_receive_detail);
        TextView member2mindB         = (TextView)         findViewById(R.id.member2mindB_receive_detail);
        TextView member2energyA       = (TextView)       findViewById(R.id.member2energyA_receive_detail);
        TextView member2energyB       = (TextView)       findViewById(R.id.member2energyB_receive_detail);
        TextView member2natureA       = (TextView)       findViewById(R.id.member2natureA_receive_detail);
        TextView member2natureB       = (TextView)       findViewById(R.id.member2natureB_receive_detail);
        TextView member2tacticsA      = (TextView)      findViewById(R.id.member2tacticsA_receive_detail);
        TextView member2tacticsB      = (TextView)      findViewById(R.id.member2tacticsB_receive_detail);
        TextView member2selfA        = (TextView)          findViewById(R.id.member2selfA_receive_detail);
        TextView member2selfB        = (TextView)          findViewById(R.id.member2selfB_receive_detail);

        TextView member3Name          = (TextView)          findViewById(R.id.member3Name_recieve_detail);
        TextView member3Sex           = (TextView)           findViewById(R.id.member3Sex_recieve_detail);
        TextView member3Age           = (TextView)           findViewById(R.id.member3Age_recieve_detail);
        TextView member3Distance      = (TextView)      findViewById(R.id.member3Distance_recieve_detail);
        TextView member3Job           = (TextView)           findViewById(R.id.member3Job_recieve_detail);
        TextView member3Participation = (TextView) findViewById(R.id.member3Participation_recieve_detail);
        TextView member3Hoperole      = (TextView)      findViewById(R.id.member3Hoperole_recieve_detail);
        TextView member3Responsible   = (TextView)   findViewById(R.id.member3Responsible_recieve_detail);
        TextView member3mindA         = (TextView)         findViewById(R.id.member3mindA_receive_detail);
        TextView member3mindB         = (TextView)         findViewById(R.id.member3mindB_receive_detail);
        TextView member3energyA       = (TextView)       findViewById(R.id.member3energyA_receive_detail);
        TextView member3energyB       = (TextView)       findViewById(R.id.member3energyB_receive_detail);
        TextView member3natureA       = (TextView)       findViewById(R.id.member3natureA_receive_detail);
        TextView member3natureB       = (TextView)       findViewById(R.id.member3natureB_receive_detail);
        TextView member3tacticsA      = (TextView)      findViewById(R.id.member3tacticsA_receive_detail);
        TextView member3tacticsB      = (TextView)      findViewById(R.id.member3tacticsB_receive_detail);
        TextView member3selfA        = (TextView)          findViewById(R.id.member3selfA_receive_detail);
        TextView member3selfB        = (TextView)          findViewById(R.id.member3selfB_receive_detail);

        TextView member4Name          = (TextView)          findViewById(R.id.member4Name_recieve_detail);
        TextView member4Sex           = (TextView)           findViewById(R.id.member4Sex_recieve_detail);
        TextView member4Age           = (TextView)           findViewById(R.id.member4Age_recieve_detail);
        TextView member4Distance      = (TextView)      findViewById(R.id.member4Distance_recieve_detail);
        TextView member4Job           = (TextView)           findViewById(R.id.member4Job_recieve_detail);
        TextView member4Participation = (TextView) findViewById(R.id.member4Participation_recieve_detail);
        TextView member4Hoperole      = (TextView)      findViewById(R.id.member4Hoperole_recieve_detail);
        TextView member4Responsible   = (TextView)   findViewById(R.id.member4Responsible_recieve_detail);
        TextView member4mindA         = (TextView)         findViewById(R.id.member4mindA_receive_detail);
        TextView member4mindB         = (TextView)         findViewById(R.id.member4mindB_receive_detail);
        TextView member4energyA       = (TextView)       findViewById(R.id.member4energyA_receive_detail);
        TextView member4energyB       = (TextView)       findViewById(R.id.member4energyB_receive_detail);
        TextView member4natureA       = (TextView)       findViewById(R.id.member4natureA_receive_detail);
        TextView member4natureB       = (TextView)       findViewById(R.id.member4natureB_receive_detail);
        TextView member4tacticsA      = (TextView)      findViewById(R.id.member4tacticsA_receive_detail);
        TextView member4tacticsB      = (TextView)      findViewById(R.id.member4tacticsB_receive_detail);
        TextView member4selfA        = (TextView)          findViewById(R.id.member4selfA_receive_detail);
        TextView member4selfB        = (TextView)          findViewById(R.id.member4selfB_receive_detail);

        View     member1mindABar = (View)                        findViewById(R.id.mindABar1_receive_team_detail);
        View     member1mindBBar = (View)                        findViewById(R.id.mindBBar1_receive_team_detail);
        View     member2mindABar = (View)                        findViewById(R.id.mindABar2_receive_team_detail);
        View     member2mindBBar = (View)                        findViewById(R.id.mindBBar2_receive_team_detail);
        View     member3mindABar = (View)                        findViewById(R.id.mindABar3_receive_team_detail);
        View     member3mindBBar = (View)                        findViewById(R.id.mindBBar3_receive_team_detail);
        View     member4mindABar = (View)                        findViewById(R.id.mindABar4_receive_team_detail);
        View     member4mindBBar = (View)                        findViewById(R.id.mindBBar4_receive_team_detail);

        View     member1energyABar = (View)                    findViewById(R.id.energyABar1_receive_team_detail);
        View     member1energyBBar = (View)                    findViewById(R.id.energyBBar1_receive_team_detail);
        View     member2energyABar = (View)                    findViewById(R.id.energyABar2_receive_team_detail);
        View     member2energyBBar = (View)                    findViewById(R.id.energyBBar2_receive_team_detail);
        View     member3energyABar = (View)                    findViewById(R.id.energyABar3_receive_team_detail);
        View     member3energyBBar = (View)                    findViewById(R.id.energyBBar3_receive_team_detail);
        View     member4energyABar = (View)                    findViewById(R.id.energyABar4_receive_team_detail);
        View     member4energyBBar = (View)                    findViewById(R.id.energyBBar4_receive_team_detail);

        View     member1natureABar = (View)                    findViewById(R.id.natureABar1_receive_team_detail);
        View     member1natureBBar = (View)                    findViewById(R.id.natureBBar1_receive_team_detail);
        View     member2natureABar = (View)                    findViewById(R.id.natureABar2_receive_team_detail);
        View     member2natureBBar = (View)                    findViewById(R.id.natureBBar2_receive_team_detail);
        View     member3natureABar = (View)                    findViewById(R.id.natureABar3_receive_team_detail);
        View     member3natureBBar = (View)                    findViewById(R.id.natureBBar3_receive_team_detail);
        View     member4natureABar = (View)                    findViewById(R.id.natureABar4_receive_team_detail);
        View     member4natureBBar = (View)                    findViewById(R.id.natureBBar4_receive_team_detail);

        View     member1tacticsABar = (View)                  findViewById(R.id.tacticsABar1_receive_team_detail);
        View     member1tacticsBBar = (View)                  findViewById(R.id.tacticsBBar1_receive_team_detail);
        View     member2tacticsABar = (View)                  findViewById(R.id.tacticsABar2_receive_team_detail);
        View     member2tacticsBBar = (View)                  findViewById(R.id.tacticsBBar2_receive_team_detail);
        View     member3tacticsABar = (View)                  findViewById(R.id.tacticsABar3_receive_team_detail);
        View     member3tacticsBBar = (View)                  findViewById(R.id.tacticsBBar3_receive_team_detail);
        View     member4tacticsABar = (View)                  findViewById(R.id.tacticsABar4_receive_team_detail);
        View     member4tacticsBBar = (View)                  findViewById(R.id.tacticsBBar4_receive_team_detail);

        View     member1selfABar = (View)                        findViewById(R.id.selfABar1_receive_team_detail);
        View     member1selfBBar = (View)                        findViewById(R.id.selfBBar1_receive_team_detail);
        View     member2selfABar = (View)                        findViewById(R.id.selfABar2_receive_team_detail);
        View     member2selfBBar = (View)                        findViewById(R.id.selfBBar2_receive_team_detail);
        View     member3selfABar = (View)                        findViewById(R.id.selfABar3_receive_team_detail);
        View     member3selfBBar = (View)                        findViewById(R.id.selfBBar3_receive_team_detail);
        View     member4selfABar = (View)                        findViewById(R.id.selfABar4_receive_team_detail);
        View     member4selfBBar = (View)                        findViewById(R.id.selfBBar4_receive_team_detail);
        System.out.println(teamMemberId);



        for(int i=0;i<teamMemberId.size();i++) {
            if (teamMemberId.get(i) != null) {
                System.out.println(teamMemberDistance);
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
                            System.out.println(teamMemberResponsible);
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
        LinearLayout teamMember2 = (LinearLayout) findViewById(R.id.teamMember2_receive_team_detail);
        LinearLayout teamMember3 = (LinearLayout) findViewById(R.id.teamMember3_receive_team_detail);
        LinearLayout teamMember4 = (LinearLayout) findViewById(R.id.teamMember4_receive_team_detail);
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

        //cancel button & click listener
        ImageView cancelBtn = (ImageView) findViewById(R.id.cancelBtn_receive_team_detail);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close inflater
                teamDetail.setBackgroundColor(Color.parseColor("#00000000"));
                teamDetail.removeAllViews();
                teamDetail=null;
            }
        });

        //accept button & click listener
        Button acceptBtn = (Button) findViewById(R.id.acceptBtn_receive_team_detail);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accept confirm
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                dialog = builder.setMessage("해당 요청에 수락하시는 경우\n요청을 보낸 팀장에게\n전화번호, 이메일 정보가 공개됩니다. \n수락하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //state S->Y 변경
                                        HttpPostData(team,"Y");
                                    }
                                }).start();
                                //close inflater
                                teamDetail.setBackgroundColor(Color.parseColor("#00000000"));
                                teamDetail.removeAllViews();
                                teamDetail=null;
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .create();
                dialog.show();
            }
        });

        //refuse button & click listener
        Button refuseBtn = (Button) findViewById(R.id.refuseBtn_receive_team_detail);
        refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //state S->N 변경
                        HttpPostData(team,"N");
                    }
                }).start();
                //close inflater
                teamDetail.setBackgroundColor(Color.parseColor("#00000000"));
                teamDetail.removeAllViews();
                teamDetail=null;
            }
        });
    }

    public void HttpPostData(String teamn, String state) {
        HttpURLConnection http = null;
        String postParameters = "member_id="+MainActivity.userID+"&teamname="+teamn+"&state="+state; // 멤버 아이디 설정
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL url = new URL(MainActivity.updateUrl);       // URL 설정
            http = (HttpURLConnection) url.openConnection();   // 접속

            http.setReadTimeout(50000);
            http.setConnectTimeout(50000);
            http.setRequestMethod("POST"); // POST 형식으로 설정
            http.connect();

            OutputStream outputStream = http.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
    } // HttpPostData
    public void HttpPostTeamInfo(String teamn, String state) {
        HttpURLConnection http = null;
        String postParameters = "member_id="+MainActivity.userID+"&teamname="+teamn+"&state="+state; // 멤버 아이디 설정
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL url = new URL(MainActivity.updateUrl);       // URL 설정
            http = (HttpURLConnection) url.openConnection();   // 접속

            http.setReadTimeout(50000);
            http.setConnectTimeout(50000);
            http.setRequestMethod("POST"); // POST 형식으로 설정
            http.connect();

            OutputStream outputStream = http.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
    } // HttpPostData
}