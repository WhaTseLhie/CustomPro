package com.example.asus.custompro.home.search.post;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.shops.Maker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    Context context;
    ArrayList<Post> list = new ArrayList<>();
    LayoutInflater inflater;

    public PostAdapter(Context context, ArrayList<Post> list) {
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
        PostHandler handler;

        if(convertView == null) {
            handler = new PostHandler();
            convertView = inflater.inflate(R.layout.adapter_post_list, null);

            handler.imgUserPic = convertView.findViewById(R.id.imageView);
            handler.imgPostPic = convertView.findViewById(R.id.imageView2);
            handler.txtUserName = convertView.findViewById(R.id.textView);
            handler.txtDatePosted = convertView.findViewById(R.id.textView2);
            handler.txtDetails = convertView.findViewById(R.id.textView3);
            handler.txtCategory = convertView.findViewById(R.id.textView4);
            handler.txtType= convertView.findViewById(R.id.textView5);

            convertView.setTag(handler);
        } else {
            handler = (PostHandler) convertView.getTag();
        }

        ArrayList<Maker> makerList = new ArrayList<>();
        UserDatabase db = new UserDatabase(context);
        makerList = db.findMakerId(list.get(0).getUserId());

        if(list.get(position).getPostCategory().isEmpty() && list.get(position).getPostType().isEmpty()) {
            Picasso.with(context).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(handler.imgUserPic);
            handler.txtCategory.setText("Category: Advertisement");
            handler.txtType.setText("");
            handler.txtType.setBackgroundColor(Color.WHITE);
            handler.imgPostPic.setImageURI(list.get(position).getPostPic());
            handler.txtUserName.setText(makerList.get(0).getShopName());
            handler.txtDetails.setText(list.get(position).getPostDetails());
        } else {
            Picasso.with(context).load(list.get(position).getUserPic()).transform(new CircleTransform()).into(handler.imgUserPic);
            handler.imgPostPic.setImageURI(list.get(position).getPostPic());
            handler.txtUserName.setText(list.get(position).getUserName());
            handler.txtDetails.setText(list.get(position).getPostDetails());
            handler.txtDatePosted.setText(list.get(position).getPostDate());
            handler.txtCategory.setText("Category: " + list.get(position).getPostCategory());
            handler.txtType.setText("Type: " + list.get(position).getPostType());
        }

        return convertView;
    }

    static class PostHandler {
        ImageView imgUserPic, imgPostPic;
        TextView txtUserName, txtDatePosted, txtCategory, txtType, txtDetails;
    }
}