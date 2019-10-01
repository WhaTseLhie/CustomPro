package com.example.asus.custompro.home.search.shops;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MakerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Maker> list = new ArrayList<>();
    LayoutInflater inflater;

    public MakerAdapter(Context context, ArrayList<Maker> list) {
        super();
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
        MakerHandler handler;

        if(convertView == null) {
            handler = new MakerHandler();
            convertView = inflater.inflate(R.layout.adapter_maker_list, null);

            handler.iv = convertView.findViewById(R.id.imageView);
            handler.name = convertView.findViewById(R.id.textView);
            handler.contact = convertView.findViewById(R.id.textView2);
            handler.ratingBar = convertView.findViewById(R.id.ratingBar);
            handler.specialty = convertView.findViewById(R.id.textView3);
            handler.address = convertView.findViewById(R.id.textView4);

            convertView.setTag(handler);
        } else {
            handler = (MakerHandler) convertView.getTag();
        }

        ArrayList<Maker> makerList = new ArrayList<>();
        UserDatabase db = new UserDatabase(context);
//        makerList = db.findTotalReviewMaker(list.get(position).getUserId());
        Picasso.with(convertView.getContext()).load(list.get(position).getShopPic()).transform(new CircleTransform()).into(handler.iv);
        handler.name.setText(list.get(position).getShopName());
        handler.contact.setText(list.get(position).getShopContact());
//        handler.ratingBar.setRating(list.get(position).getShopRating());
        handler.ratingBar.setRating(db.findTotalReviewMaker(list.get(position).getUserId()));
        handler.specialty.setText(list.get(position).getShopSpecialty());
        handler.address.setText(list.get(position).getShopAddress());

        return convertView;
    }

    static class MakerHandler {
        ImageView iv;
        RatingBar ratingBar;
        TextView name, contact, specialty, address;
    }
}