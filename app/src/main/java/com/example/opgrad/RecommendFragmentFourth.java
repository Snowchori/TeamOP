package com.example.opgrad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragmentFourth extends Fragment {
    private View view;

    private ListView teamList;
    private TeamListAdapter teamAdapter;
    private List<Team> teamArrayList;

    private Button teamDetailBtn;

    private Button previousBtn;
    private Button finishBtn;
    private int list_cnt;
    private int list_cnt2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.recommend_fourth, null);

        teamList = (ListView) view.findViewById(R.id.teamList_recommend_fourth);
        teamArrayList = new ArrayList<Team>();

        String result = RecommendActivity.teamResult;

        String[][] member = new String[4][5];

        try {
            JSONObject proot = new JSONObject(result);
            String exist = proot.getString("is_existed");
            if(exist.equals("1")) {
                JSONArray pja = proot.getJSONArray("team_result");
                list_cnt = pja.length();

                for (int i = 0; i < list_cnt; i++) {
                    for (int a = 0; a < 4; a++){
                        for(int b = 0; b < 3; b++){
                            member[a][b] = " ";
                        }
                    }
                    JSONObject pjo = pja.getJSONObject(i);
                    JSONArray user = pjo.getJSONArray("user_data");
                    list_cnt2 = user.length();
                    for (int j =0; j < list_cnt2; j++){
                        JSONObject jsonObject = user.getJSONObject(j);
                        member[j][0] = jsonObject.getString("user_name");
                        member[j][1] = jsonObject.getString("user_rate");
                        member[j][2] = jsonObject.getString("user_responsible");

                        member[j][3] = jsonObject.getString("user_id");
                        member[j][4] = jsonObject.getString("user_distance");
                    }
                    String score = pjo.getString("avg_team_score");
                    if(score.length()>5) {
                        score = score.substring(0, 5);
                    }
                    teamArrayList.add(new Team(pjo.getString("team_number"), score,
                            member[0][0], member[0][1], lengthCount(member[0][2],48),member[0][3],member[0][4],member[0][2],
                            member[1][0], member[1][1], lengthCount(member[1][2],48),member[1][3],member[1][4],member[1][2],
                            member[2][0], member[2][1], lengthCount(member[2][2],48),member[2][3],member[2][4],member[2][2],
                            member[3][0], member[3][1], lengthCount(member[3][2],48),member[3][3],member[3][4],member[3][2]));
                }
            }else{

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        teamAdapter = new TeamListAdapter(view.getContext(), teamArrayList);

        teamList.setAdapter(teamAdapter);
        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Team team = (Team) adapterView.getItemAtPosition(position);

                String teamMember1 = team.getTeamMember1();
                String teamMember2 = team.getTeamMember2();
                String teamMember3 = team.getTeamMember3();
                String teamMember4 = team.getTeamMember4();

                String preferenceScore1 = team.getPreferenceScore1();
                String preferenceScore2 = team.getPreferenceScore2();
                String preferenceScore3 = team.getPreferenceScore3();
                String preferenceScore4 = team.getPreferenceScore4();
                ((RecommendActivity)getActivity()).teamMemberResponsible.clear();
                switch(((RecommendActivity)getActivity()).NOTM) {
                    case 5:
                        ((RecommendActivity)getActivity()).teamMemberResponsible.add(team.getLongcapability4());
                    case 4:
                        ((RecommendActivity)getActivity()).teamMemberResponsible.add(team.getLongcapability3());
                    case 3:
                        ((RecommendActivity)getActivity()).teamMemberResponsible.add(team.getLongcapability2());
                    case 2:
                        ((RecommendActivity)getActivity()).teamMemberResponsible.add(team.getLongcapability1());
                }

                ((RecommendActivity)getActivity()).teamMemberId.clear();
                switch(((RecommendActivity)getActivity()).NOTM) {
                    case 5:
                        ((RecommendActivity)getActivity()).teamMemberId.add(team.getMemberId4());
                    case 4:
                        ((RecommendActivity)getActivity()).teamMemberId.add(team.getMemberId3());
                    case 3:
                        ((RecommendActivity)getActivity()).teamMemberId.add(team.getMemberId2());
                    case 2:
                        ((RecommendActivity)getActivity()).teamMemberId.add(team.getMemberId1());
                }
                ((RecommendActivity)getActivity()).teamMemberDistance.clear();
                switch(((RecommendActivity)getActivity()).NOTM) {
                    case 5:
                        ((RecommendActivity)getActivity()).teamMemberDistance.add(team.getInterDistance4());
                    case 4:
                        ((RecommendActivity)getActivity()).teamMemberDistance.add(team.getInterDistance3());
                    case 3:
                        ((RecommendActivity)getActivity()).teamMemberDistance.add(team.getInterDistance2());
                    case 2:
                        ((RecommendActivity)getActivity()).teamMemberDistance.add(team.getInterDistance1());
                }

                if(((RecommendActivity)getActivity()).teamDetail==null) {
                    ((RecommendActivity) getActivity()).showTeamDetail();
                }

            }
        });

        previousBtn = (Button) view.findViewById(R.id.previousBtn_recommend_fourth);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecommendActivity)getActivity()).changeFragment(((RecommendActivity)getActivity()).thirdFragment);
            }
        });

        finishBtn = (Button) view.findViewById(R.id.finishBtn_recommend_fourth);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecommendActivity)getActivity()).finish();
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
                text = text.substring(0, index) + "..";
                break;
            }
        }
        return text;
    }
}