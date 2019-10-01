package com.example.asus.custompro.shop.review;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Review> list = new ArrayList<>();
    LayoutInflater inflater;

    public ReviewAdapter(Context context, ArrayList<Review> list) {
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
        ReviewHandler handler;

        if(convertView == null) {
            handler = new ReviewHandler();
            convertView = inflater.inflate(R.layout.adapter_review_item, null);

            handler.iv = convertView.findViewById(R.id.imageView);
            handler.name = convertView.findViewById(R.id.textView);
            handler.date = convertView.findViewById(R.id.textView2);
            handler.rate = convertView.findViewById(R.id.ratingBar);
            handler.comment = convertView.findViewById(R.id.textView3);
            convertView.setTag(handler);
        } else {
            handler = (ReviewHandler) convertView.getTag();
        }

        Picasso.with(convertView.getContext()).load(list.get(position).getUserPic()).transform(new CircleTransform()).into(handler.iv);
        handler.name.setText(list.get(position).getUserName());
        handler.date.setText(list.get(position).getDate());
        handler.rate.setRating(list.get(position).getRating());
        handler.comment.setText(list.get(position).getComment());

        return convertView;
    }

    static class ReviewHandler {
        ImageView iv;
        TextView name, comment, date;
        RatingBar rate;
    }
}