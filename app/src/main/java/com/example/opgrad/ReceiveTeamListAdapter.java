package com.example.opgrad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ReceiveTeamListAdapter extends BaseAdapter {

    private Context context;
    private List<ReceiveTeam> receiveTeamList;

    public ReceiveTeamListAdapter(Context context, List<ReceiveTeam> receiveTeamList) {
        this.context = context;
        this.receiveTeamList = receiveTeamList;
    }

    @Override
    public int getCount() {
        return receiveTeamList.size();
    }

    @Override
    public Object getItem(int position) {
        return receiveTeamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.receiveteam_list2, null);

        TextView teamName = (TextView) v.findViewById(R.id.teamName_receiveTeam_list);
        TextView compTitle = (TextView) v.findViewById(R.id.compTitle_receiveTeam_list);

        TextView teamLeaderName = (TextView) v.findViewById(R.id.teamLeaderName_receiveTeam_list);

        TextView member1 = (TextView) v.findViewById(R.id.member1_receiveTeam_list);
        TextView member2 = (TextView) v.findViewById(R.id.member2_receiveTeam_list);
        TextView member3 = (TextView) v.findViewById(R.id.member3_receiveTeam_list);

        teamName.setText(receiveTeamList.get(position).getTeamName());
        compTitle.setText(lengthCount((receiveTeamList.get(position).getCompTitle()), 120));

        teamLeaderName.setText(receiveTeamList.get(position).getTeamLeaderName());

        member1.setText(receiveTeamList.get(position).getMember1());
        member2.setText(receiveTeamList.get(position).getMember2());
        member3.setText(receiveTeamList.get(position).getMember3());

        return v;
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
