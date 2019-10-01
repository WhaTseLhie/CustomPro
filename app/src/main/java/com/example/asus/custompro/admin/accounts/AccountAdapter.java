package com.example.asus.custompro.admin.accounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.User;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.shops.Maker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountAdapter extends BaseAdapter {

    Context context;
    ArrayList<Account> list = new ArrayList<>();
    LayoutInflater inflater;

    public AccountAdapter(Context context, ArrayList<Account> list) {
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
        return list.get(0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccountHandler handler;

        if(convertView == null) {
            handler = new AccountHandler();
            convertView = inflater.inflate(R.layout.adapter_account, null);

            handler.iv = convertView.findViewById(R.id.imageView);
            handler.name = convertView.findViewById(R.id.textView);
            handler.contact = convertView.findViewById(R.id.textView2);
            handler.gender = convertView.findViewById(R.id.textView3);
            handler.address = convertView.findViewById(R.id.textView4);
            handler.type = convertView.findViewById(R.id.textView5);

            convertView.setTag(handler);
        } else {
            handler = (AccountHandler) convertView.getTag();
        }

        UserDatabase db = new UserDatabase(context);
        ArrayList<Maker> makerList = db.findMaker(list.get(position).getUserId());
        String type = makerList.isEmpty()?"Seeker":"Shop";

        Picasso.with(convertView.getContext()).load(list.get(position).getProfilePic()).transform(new CircleTransform()).into(handler.iv);
        handler.name.setText(list.get(position).getFirstname() +" "+list.get(position).getLastname());
        handler.contact.setText(list.get(position).getContactnum());
        handler.gender.setText(list.get(position).getGender());
        handler.address.setText(list.get(position).getAddress());
        handler.type.setText(type);

        return convertView;
    }

    static class AccountHandler {
        ImageView iv;
        TextView name, contact, gender, address, type;
    }
}