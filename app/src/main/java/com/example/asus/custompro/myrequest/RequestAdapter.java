package com.example.asus.custompro.myrequest;

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

public class RequestAdapter extends BaseAdapter {

    Context context;
    ArrayList<Request> list = new ArrayList<>();
    LayoutInflater inflater;

    public RequestAdapter(Context context, ArrayList<Request> list) {
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
        RequestHandler handler;

        if(convertView == null) {
            handler = new RequestHandler();
            convertView = inflater.inflate(R.layout.adapter_request, null);

            handler.imgUserPic = convertView.findViewById(R.id.imageView);
            handler.imgRequestPic = convertView.findViewById(R.id.imageView2);
            handler.txtUserName = convertView.findViewById(R.id.textView);
            handler.txtDateRequested = convertView.findViewById(R.id.textView2);
            handler.txtQty = convertView.findViewById(R.id.textView3);
            //handler.txtDetails = convertView.findViewById(R.id.textView3);
            //handler.txtCategory = convertView.findViewById(R.id.textView4);
            handler.imgStatus = convertView.findViewById(R.id.imageView3);

            convertView.setTag(handler);
        } else {
            handler = (RequestHandler) convertView.getTag();
        }

        Picasso.with(context).load(list.get(position).getUserPic()).transform(new CircleTransform()).into(handler.imgUserPic);
        handler.imgRequestPic.setImageURI(list.get(position).getRequestPic());
        handler.txtUserName.setText(list.get(position).getUserName());
        handler.txtQty.setText("" +list.get(position).getRequestedQty());
        //handler.txtDetails.setText(list.get(position).getRequestDetails());
        //handler.txtCategory.setText("Category: " +list.get(position).getRequestCategory());
        handler.txtDateRequested.setText(list.get(position).getRequestedDate());

        if(list.get(position).getRequestStatus().equals("PENDING")) {
            handler.imgStatus.setImageResource(R.drawable.ic_pending);
        } else if(list.get(position).getRequestStatus().equals("PROGRESS")) {
            handler.imgStatus.setImageResource(R.drawable.ic_progress);
        } else if(list.get(position).getRequestStatus().equals("DECLINE")) {
            handler.imgStatus.setImageResource(R.drawable.ic_cancel);
        } else if(list.get(position).getRequestStatus().equals("DONE")) {
            handler.imgStatus.setImageResource(R.drawable.ic_check_circle);
        }

        return convertView;
    }

    static class RequestHandler {
        ImageView imgUserPic, imgRequestPic, imgStatus;
        TextView txtUserName, txtDateRequested, txtQty;//, txtCategory, txtDetails;
    }
}