package com.example.opgrad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MenuListAdapter extends BaseAdapter {

    private Context context;
    private List<Menu> menuList;

    public MenuListAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.menu_list, null);
        TextView item = (TextView) v.findViewById(R.id.item_menu_list);

        item.setText(menuList.get(position).getItem());

        v.setTag(menuList.get(position).getItem());
        return v;
    }
}
