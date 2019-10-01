package com.example.asus.custompro.shop.nwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewWorldOrderAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewWorldOrder> list = new ArrayList<>();
    LayoutInflater inflater;

    public NewWorldOrderAdapter(Context context, ArrayList<NewWorldOrder> list) {
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
        NewWorldOrderHandler handler;

        if(convertView == null) {
            handler = new NewWorldOrderHandler();
            convertView = inflater.inflate(R.layout.adapter_new_world_order, null);

            handler.userPic = convertView.findViewById(R.id.imageView);
            handler.txtUserName = convertView.findViewById(R.id.textView);
            handler.txtAddress = convertView.findViewById(R.id.textView2);
            handler.txtDate = convertView.findViewById(R.id.textView3);
            handler.txtMade = convertView.findViewById(R.id.textView4);
            handler.txtStatus = convertView.findViewById(R.id.textView5);

            convertView.setTag(handler);
        } else {
            handler = (NewWorldOrderHandler) convertView.getTag();
        }

        UserDatabase db = new UserDatabase(context);
        ArrayList<Account> accList = db.getFindAccount(list.get(position).userId);
        Picasso.with(context).load(accList.get(0).getProfilePic()).transform(new CircleTransform()).into(handler.userPic);
        handler.txtUserName.setText("Name: " +accList.get(0).getFirstname() +" "+ accList.get(0).getLastname());
        handler.txtAddress.setText("Address: " +accList.get(0).getAddress());
        handler.txtDate.setText("Date: " +list.get(position).getNwoEndDate());
        handler.txtMade.setText("Made: " +list.get(position).getNwoMade());
        handler.txtStatus.setText("Status: " +list.get(position).getNwoStatus());

        return convertView;
    }

    static class NewWorldOrderHandler {
        ImageView userPic;
        TextView txtUserName, txtAddress, txtDate, txtMade, txtStatus;
    }
}