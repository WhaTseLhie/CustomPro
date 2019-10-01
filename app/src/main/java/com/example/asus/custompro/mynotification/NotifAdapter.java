package com.example.asus.custompro.mynotification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.shops.Maker;

import java.util.ArrayList;

public class NotifAdapter extends BaseAdapter {

    Context context;
    ArrayList<Notif> list = new ArrayList<>();
    LayoutInflater inflater;

    public NotifAdapter(Context context, ArrayList<Notif> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotifHandler handler;

        if (convertView == null) {
            handler = new NotifHandler();
            convertView = inflater.inflate(R.layout.adapter_notification, null);

            handler.name = convertView.findViewById(R.id.textView);
            handler.date = convertView.findViewById(R.id.textView2);
            handler.type = convertView.findViewById(R.id.textView3);
            handler.message = convertView.findViewById(R.id.textView4);
            convertView.setTag(handler);
        } else {
            handler = (NotifHandler) convertView.getTag();
        }

        UserDatabase db = new UserDatabase(context);
        ArrayList<Maker> makerList = db.findMaker(list.get(position).getShopid());
        handler.name.setText("Shop Name: " +makerList.get(0).getShopName());
        handler.date.setText("Date: " +list.get(position).getDate());
        handler.type.setText("Type: " +list.get(position).getType());
        handler.message.setText(list.get(position).getMessage());

        return convertView;
    }

    static class NotifHandler {
        TextView name, date, type, message;
    }
}