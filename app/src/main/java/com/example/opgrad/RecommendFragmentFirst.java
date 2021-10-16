package com.example.opgrad;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

public class RecommendFragmentFirst extends Fragment {
    private View view;
    private LinearLayout.LayoutParams params;

    private ImageView removeImage;
    private ImageView addImage;

    private TextView numberOfTeamMember;

    private ListView favoriteList;
    private TextView favoriteListIsEmpty;

    private CompListAdapter compAdapter;
    private List<Comp> compArrayList;

    private TextView favoriteCorrcetText;
    // 초리초리 시작
    private String mem_id;
    private String myResult;
    private int list_cnt;
    // 초리초리 끝

    CustomCircleProgressDialog customCircleProgressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.recommend_first, null);

        // 회원번호 설정
        mem_id = Integer.toString(MainActivity.userID);

        removeImage = (ImageView) view.findViewById(R.id.removeImage_recommend_first);
        addImage = (ImageView) view.findViewById(R.id.addImage_recommend_first);

        removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfTeamMember = (TextView) view.findViewById(R.id.numberOfTeamMember_recommend_first);

                int num = Integer.parseInt(numberOfTeamMember.getText().toString()) - 1;
                if(num == 1) {
                    ((RecommendActivity)getActivity()).showNegativeDialog("팀은 최소 2명입니다.");
                } else {
                    numberOfTeamMember.setText(Integer.toString(num));
                }
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfTeamMember = (TextView) view.findViewById(R.id.numberOfTeamMember_recommend_first);

                int num = Integer.parseInt(numberOfTeamMember.getText().toString()) + 1;
                if(num == 5) {
                    ((RecommendActivity)getActivity()).showNegativeDialog("팀은 최대 4명입니다.");
                } else {
                    numberOfTeamMember.setText(Integer.toString(num));
                }
            }
        });

        favoriteCorrcetText = (TextView) view.findViewById(R.id.favoriteCorrectText_recommend_first);
        favoriteCorrcetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((RecommendActivity)getActivity()), MoreActivity.class);
                intent.putExtra("key", " More");
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        numberOfTeamMember = (TextView) view.findViewById(R.id.numberOfTeamMember_recommend_first);
        String num = Integer.toString(((RecommendActivity)getActivity()).NOTM);
        numberOfTeamMember.setText(num);

        favoriteList = (ListView) view.findViewById(R.id.favoriteList_recommend_first);
        compArrayList = new ArrayList<Comp>();
        compAdapter = new CompListAdapter(getContext(), compArrayList);

        favoriteList.setAdapter(compAdapter);
        favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Comp comp = (Comp) adapterView.getItemAtPosition(position);
                ((RecommendActivity)getActivity()).NOCP = comp.getTitle();
                ((RecommendActivity)getActivity()).numOfPeopleForTeam = Integer.parseInt(numberOfTeamMember.getText().toString());
                ((RecommendActivity)getActivity()).competitionName = comp.getTitle();
                ((RecommendActivity)getActivity()).competitionId =  comp.getcomp_id();
                ((RecommendActivity)getActivity()).changeFragment(((RecommendActivity)getActivity()).secondFragment);
            }
        });

        updateData();

    }

    @Override
    public void onPause() {
        super.onPause();

        numberOfTeamMember = (TextView) view.findViewById(R.id.numberOfTeamMember_recommend_first);
        int num = Integer.parseInt(numberOfTeamMember.getText().toString());
        ((RecommendActivity)getActivity()).NOTM = num;
    }

    public void updateData() {

        dataupdate task = new dataupdate();
        task.execute();
    }

    class dataupdate extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCircleProgressDialog =new CustomCircleProgressDialog((RecommendActivity)getActivity());
            customCircleProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            URLConnector conn = new URLConnector(MainActivity.curtcompUrl);
            conn.start();
            try {
                conn.join();
            } catch(InterruptedException e) {

            }
            MainActivity.result = conn.getResult();
            return  null;
        }

        @Override
        protected void onPostExecute(Void num) {   //UI작업은 onPostExecute에서 함.
            super.onPostExecute(num);
            // 초리초리 시작
            Thread t1 = new Thread(){
                public void run(){
                    myResult = HttpPostData();   // 서버와 자료 주고받기
                    // 요청 받은팀 정보 추가
                    try {
                        JSONObject proot = new JSONObject(myResult);
                        JSONArray pja = proot.getJSONArray("favorite");
                        list_cnt = pja.length();
                        for (int i = 0; i < list_cnt; i++) {
                            JSONObject jsonObject = pja.getJSONObject(i);
                            String item1 = jsonObject.getString("title");
                            String item2 = jsonObject.getString("period_start");
                            String item3 = jsonObject.getString("period_end");
                            String item4 = jsonObject.getString("field");
                            int item5 = jsonObject.getInt("competition_id");
                            String dday = MainActivity.calDDay(item3);
                            compArrayList.add(new Comp(item1, item4, item2, item3, dday, item5));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            // 초리초리 끝

            favoriteListIsEmpty = (TextView) view.findViewById(R.id.favoriteListIsEmpty_recommend_first);

            //즐겨찾기 리스트가 비어있다면
            if(compArrayList.isEmpty()) {
                params = (LinearLayout.LayoutParams) favoriteListIsEmpty.getLayoutParams();
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
                favoriteListIsEmpty.setLayoutParams(params);
                params = (LinearLayout.LayoutParams) favoriteList.getLayoutParams();
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                favoriteList.setLayoutParams(params);
            } else {
                params = (LinearLayout.LayoutParams) favoriteList.getLayoutParams();
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
                favoriteList.setLayoutParams(params);
                params = (LinearLayout.LayoutParams) favoriteListIsEmpty.getLayoutParams();
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                favoriteListIsEmpty.setLayoutParams(params);
            }
            customCircleProgressDialog.dismiss();
        }

    }
    // 초리초리 시작
    public String HttpPostData() {
        HttpURLConnection http = null;
        String postParameters = "member_id=" + mem_id; // 멤버 아이디 설정
        StringBuilder builder = new StringBuilder();;
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL url = new URL(MainActivity.favoriteUrl);       // URL 설정
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
    // 초리초리 끝
}