package com.example.opgrad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CompListAdapter extends BaseAdapter {

    private Context context;
    private List<Comp> compList;

    public CompListAdapter(Context context, List<Comp> compList) {
        this.context = context;
        this.compList = compList;
    }

    @Override
    public int getCount() {
        return compList.size();
    }

    @Override
    public Object getItem(int position) {
        return compList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.comp_list, null);

        TextView title = (TextView) v.findViewById(R.id.title_comp_list);
        TextView category = (TextView) v.findViewById(R.id.category_comp_list);
        TextView startDate = (TextView) v.findViewById(R.id.startDate_comp_list);
        TextView endDate = (TextView) v.findViewById(R.id.endDate_comp_list);
        TextView dDay = (TextView) v.findViewById(R.id.dDay_comp_list);

        //한글 15글자 + ...
        title.setText(lengthCount(compList.get(position).getTitle(), 90));
        category.setText(lengthCount(compList.get(position).getCategory(), 90));
        startDate.setText(compList.get(position).getStartDate());
        endDate.setText(compList.get(position).getEndDate());
        dDay.setText(compList.get(position).getdDay());

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
