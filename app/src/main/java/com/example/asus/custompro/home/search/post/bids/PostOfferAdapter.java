package com.example.asus.custompro.home.search.post.bids;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.post.bids.offer.Offer;
import com.example.asus.custompro.home.search.shops.Maker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostOfferAdapter extends BaseAdapter {

    Context context;
    ArrayList<Offer> list = new ArrayList<>();
    LayoutInflater inflater;

    public PostOfferAdapter(Context context, ArrayList<Offer> list) {
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
        OfferHandler handler;

        if(convertView == null) {
            handler = new OfferHandler();
            convertView = inflater.inflate(R.layout.adapter_post_offer, null);

            handler.iv = convertView.findViewById(R.id.imageView);
            handler.name = convertView.findViewById(R.id.textView);
            handler.ratingBar = convertView.findViewById(R.id.ratingBar);
            handler.date = convertView.findViewById(R.id.textView2);
            handler.price = convertView.findViewById(R.id.textView3);
            handler.comment = convertView.findViewById(R.id.textView4);
            handler.ll = convertView.findViewById(R.id.linearLayout);

            convertView.setTag(handler);
        } else {
            handler = (OfferHandler) convertView.getTag();
        }

        UserDatabase db = new UserDatabase(context);
        ArrayList<Maker> makerList = db.findMaker(list.get(position).getShopId());
        Picasso.with(convertView.getContext()).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(handler.iv);
        handler.name.setText("Name: " +makerList.get(0).getShopName());
        handler.date.setText("Date Finish: " +list.get(position).getOfferDate());
        handler.ratingBar.setRating(makerList.get(0).getShopRating());
        handler.price.setText("Price: \u20B1" +list.get(position).getOfferPrice());
        handler.comment.setText(list.get(position).getOfferComment());

        if(list.get(position).getOfferStatus().equals("SELECTED")) {
            handler.ll.setBackgroundColor(Color.YELLOW);
        }

        return convertView;
    }

    static class OfferHandler {
        ImageView iv;
        RatingBar ratingBar;
        TextView name, date, comment, price;
        LinearLayout ll;
    }
}