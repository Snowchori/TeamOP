package com.example.opgrad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class SendTeamListAdapter extends BaseAdapter {

    private Context context;
    private List<SendTeam> sendTeamList;

    public SendTeamListAdapter(Context context, List<SendTeam> sendTeamList) {
        this.context = context;
        this.sendTeamList = sendTeamList;
    }

    @Override
    public int getCount() {
        return sendTeamList.size();
    }

    @Override
    public Object getItem(int position) {
        return sendTeamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.sendteam_list, null);

        TextView teamName = (TextView) v.findViewById(R.id.teamName_sendTeam_list);
        TextView compTitle = (TextView) v.findViewById(R.id.compTitle_sendTeam_list);

        TextView member1Name = (TextView) v.findViewById(R.id.member1Name_sendTeam_list);
        TextView member1State = (TextView) v.findViewById(R.id.member1State_sendTeam_list);

        TextView member2Name = (TextView) v.findViewById(R.id.member2Name_sendTeam_list);
        TextView member2State = (TextView) v.findViewById(R.id.member2State_sendTeam_list);

        TextView member3Name = (TextView) v.findViewById(R.id.member3Name_sendTeam_list);
        TextView member3State = (TextView) v.findViewById(R.id.member3State_sendTeam_list);

        TextView member4Name = (TextView) v.findViewById(R.id.member4Name_sendTeam_list);
        TextView member4State = (TextView) v.findViewById(R.id.member4State_sendTeam_list);


        teamName.setText(sendTeamList.get(position).getTeamName());
        compTitle.setText(lengthCount((sendTeamList.get(position).getCompTitle()), 120));

        member1Name.setText(sendTeamList.get(position).getMember1Name());
        member1State.setText(sendTeamList.get(position).getMember1State());

        member2Name.setText(sendTeamList.get(position).getMember2Name());
        member2State.setText(sendTeamList.get(position).getMember2State());

        member3Name.setText(sendTeamList.get(position).getMember3Name());
        member3State.setText(sendTeamList.get(position).getMember3State());

        member4Name.setText(sendTeamList.get(position).getMember4Name());
        member4State.setText(sendTeamList.get(position).getMember4State());


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
