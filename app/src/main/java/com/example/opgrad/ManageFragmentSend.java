package com.example.opgrad;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import java.util.Timer;
import java.util.TimerTask;


public class ManageFragmentSend extends Fragment {
    private View view;
    private LinearLayout.LayoutParams params;

    private ListView sendList;
    private SendTeamListAdapter sendAdapter;
    private List<SendTeam> sendArrayList;

    private String mem_id;
    private String myResult;
    private int list_cnt;

    private TimerTask timerTask;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.manage_send, null);

        sendList = (ListView) view.findViewById(R.id.teamList_send);
        // 회원번호 설정
        mem_id = Integer.toString(MainActivity.userID);

        sendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                SendTeam sendTeam = (SendTeam) adapterView.getItemAtPosition(position);

                int NOTM = 5;   //Number Of Team Member
                if(sendTeam.getMember4Name().equals(" "))
                    NOTM = 4;
                if(sendTeam.getMember3Name().equals(" "))
                    NOTM = 3;
                if(sendTeam.getMember2Name().equals(" "))
                    NOTM = 2;
                ((ManageActivity)getActivity()).teamMemberId.clear();
                ((ManageActivity)getActivity()).teamMemberDistance.clear();
                ((ManageActivity)getActivity()).teamMemberResponsible.clear();

                switch(NOTM) {
                    case 5:
                        ((ManageActivity)getActivity()).teamMemberId.add(sendTeam.getMember4Id());
                        ((ManageActivity)getActivity()).teamMemberDistance.add(sendTeam.getMember4Distance());
                        ((ManageActivity)getActivity()).teamMemberResponsible.add(sendTeam.getMember4Responsible());
                    case 4:
                        ((ManageActivity)getActivity()).teamMemberId.add(sendTeam.getMember3Id());
                        ((ManageActivity)getActivity()).teamMemberDistance.add(sendTeam.getMember3Distance());
                        ((ManageActivity)getActivity()).teamMemberResponsible.add(sendTeam.getMember3Responsible());

                    case 3:
                        ((ManageActivity)getActivity()).teamMemberId.add(sendTeam.getMember2Id());
                        ((ManageActivity)getActivity()).teamMemberDistance.add(sendTeam.getMember2Distance());
                        ((ManageActivity)getActivity()).teamMemberResponsible.add(sendTeam.getMember2Responsible());

                    case 2:
                        ((ManageActivity)getActivity()).teamMemberId.add(sendTeam.getMember1Id());
                        ((ManageActivity)getActivity()).teamMemberDistance.add(sendTeam.getMember1Distance());
                        ((ManageActivity)getActivity()).teamMemberResponsible.add(sendTeam.getMember1Responsible());
                }

                String State1 = sendTeam.getMember1State();
                String State2 = sendTeam.getMember2State();
                String State3 = sendTeam.getMember3State();
                String State4 = sendTeam.getMember4State();
                if(((ManageActivity)getActivity()).teamDetail==null){
                    ((ManageActivity)getActivity()).showSendTeamDetail(NOTM, State1, State2, State3, State4);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //TimerTask
        timerTask = new TimerTask() {
            @Override
            public void run() {
                // 스레드 생성
                Thread t1 = new Thread(){
                    public void run(){
                        myResult = HttpPostData();   // 서버와 자료 주고받기
                        // 요청 보낸 팀 정보 추가
                        try {
                            JSONObject proot = new JSONObject(myResult);
                            JSONArray pja = proot.getJSONArray("sendteam");
                            list_cnt = pja.length();

                            //ArrayList 재생성
                            sendArrayList = new ArrayList<SendTeam>();

                            for(int i = 0; i < list_cnt; i++){
                                JSONObject pjo = pja.getJSONObject(i);
                                sendArrayList.add(new SendTeam(pjo.getString("recommend_id"),pjo.getString("title"),
                                        pjo.getString("member1_name"),pjo.getString("member1_state"),pjo.getString("member1_id"),pjo.getString("member1_distance"),pjo.getString("member1_responsible"),
                                        pjo.getString("member2_name"),pjo.getString("member2_state"),pjo.getString("member2_id"),pjo.getString("member2_distance"),pjo.getString("member2_responsible"),
                                        pjo.getString("member3_name"),pjo.getString("member3_state"),pjo.getString("member3_id"),pjo.getString("member3_distance"),pjo.getString("member3_responsible"),
                                        pjo.getString("member4_name"),pjo.getString("member4_state"),pjo.getString("member4_id"),pjo.getString("member4_distance"),pjo.getString("member4_responsible")  ));
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                // 스레드 시작
                t1.start();
                // 스레드가 끝날 때까지 대기
                try {
                    t1.join();

                    //handler를 이용해 adapter 설정 & 연결
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            sendAdapter = new SendTeamListAdapter(view.getContext(), sendArrayList);
                            sendList.setAdapter(sendAdapter);
                        }
                    });
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };


        Timer timer = new Timer();
        //timerTask를 0초부터 3초단위로 실행
        timer.schedule(timerTask, 0, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();

        timerTask.cancel();

    }

    public String HttpPostData() {
        HttpURLConnection http = null;
        String postParameters = "member_id=" + mem_id; // 멤버 아이디 설정
        StringBuilder builder = new StringBuilder();;
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL url = new URL(MainActivity.sendteamUrl);       // URL 설정
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
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
        return builder.toString();
    }
}