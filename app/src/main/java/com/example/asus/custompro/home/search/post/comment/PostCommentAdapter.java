package com.example.asus.custompro.home.search.post.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostCommentAdapter extends BaseAdapter {

    Context context;
    ArrayList<PostComment> list = new ArrayList<>();
    LayoutInflater inflater;

    public PostCommentAdapter(Context context, ArrayList<PostComment> list) {
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
        PostCommentHandler handler;

        if(convertView == null) {
            handler = new PostCommentHandler();
            convertView = inflater.inflate(R.layout.adapter_comment_post, null);

            handler.iv = convertView.findViewById(R.id.imageView);
            handler.txtName = convertView.findViewById(R.id.textView);
            handler.txtDate = convertView.findViewById(R.id.textView2);
            handler.txtComment = convertView.findViewById(R.id.textView3);

            convertView.setTag(handler);
        } else {
            handler = (PostCommentHandler) convertView.getTag();
        }

        Picasso.with(context).load(list.get(position).getCommentPic()).transform(new CircleTransform()).into(handler.iv);
        handler.txtName.setText(list.get(position).getCommentName());
        handler.txtDate.setText(list.get(position).getDate());
        handler.txtComment.setText(list.get(position).getComment());

        return convertView;
    }

    static class PostCommentHandler {
        ImageView iv;
        TextView txtName, txtDate, txtComment;
    }
}