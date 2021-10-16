package com.example.opgrad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class TeamListAdapter extends BaseAdapter {

    private Context context;
    private List<Team> teamList;

    private LinearLayout.LayoutParams params;

    public TeamListAdapter(Context context, List<Team> teamList) {
        this.context = context;
        this.teamList = teamList;
    }

    @Override
    public int getCount() {
        return teamList.size();
    }

    @Override
    public Object getItem(int position) {
        return teamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.team_list, null);

        TextView rank = (TextView) v.findViewById(R.id.rank_team_list);

        TextView score = (TextView) v.findViewById(R.id.score_team_list);

        LinearLayout profile1 = (LinearLayout) v.findViewById(R.id.profile1_team_list);
        TextView teamMember1 = (TextView) v.findViewById(R.id.teamMember1_team_list);
        TextView preferenceScore1 = (TextView) v.findViewById(R.id.preferenceScore1_team_list);
        TextView capability1 = (TextView) v.findViewById(R.id.capability1_team_list);

        LinearLayout profile2 = (LinearLayout) v.findViewById(R.id.profile2_team_list);
        TextView teamMember2 = (TextView) v.findViewById(R.id.teamMember2_team_list);
        TextView preferenceScore2 = (TextView) v.findViewById(R.id.preferenceScore2_team_list);
        TextView capability2 = (TextView) v.findViewById(R.id.capability2_team_list);

        LinearLayout profile3 = (LinearLayout) v.findViewById(R.id.profile3_team_list);
        TextView teamMember3 = (TextView) v.findViewById(R.id.teamMember3_team_list);
        TextView preferenceScore3 = (TextView) v.findViewById(R.id.preferenceScore3_team_list);
        TextView capability3 = (TextView) v.findViewById(R.id.capability3_team_list);

        LinearLayout profile4 = (LinearLayout) v.findViewById(R.id.profile4_team_list);
        TextView teamMember4 = (TextView) v.findViewById(R.id.teamMember4_team_list);
        TextView preferenceScore4 = (TextView) v.findViewById(R.id.preferenceScore4_team_list);
        TextView capability4 = (TextView) v.findViewById(R.id.capability4_team_list);

        int NOTM = RecommendActivity.NOTM;

        if(NOTM < 5) {
            params = (LinearLayout.LayoutParams) profile4.getLayoutParams();
            params.height = 0;
            profile4.setLayoutParams(params);
        }
        if(NOTM < 4) {
            params = (LinearLayout.LayoutParams) profile3.getLayoutParams();
            params.height = 0;
            profile3.setLayoutParams(params);
        }
        if(NOTM < 3) {
            params = (LinearLayout.LayoutParams) profile2.getLayoutParams();
            params.height = 0;
            profile2.setLayoutParams(params);
        }

        rank.setText(teamList.get(position).getRank());
        score.setText(teamList.get(position).getScore());

        teamMember1.setText(teamList.get(position).getTeamMember1());
        preferenceScore1.setText(teamList.get(position).getPreferenceScore1());
        capability1.setText(teamList.get(position).getCapability1());

        teamMember2.setText(teamList.get(position).getTeamMember2());
        preferenceScore2.setText(teamList.get(position).getPreferenceScore2());
        capability2.setText(teamList.get(position).getCapability2());

        teamMember3.setText(teamList.get(position).getTeamMember3());
        preferenceScore3.setText(teamList.get(position).getPreferenceScore3());
        capability3.setText(teamList.get(position).getCapability3());

        teamMember4.setText(teamList.get(position).getTeamMember4());
        preferenceScore4.setText(teamList.get(position).getPreferenceScore4());
        capability4.setText(teamList.get(position).getCapability4());

        return v;
    }
}
