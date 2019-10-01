package com.example.asus.custompro.shop.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.asus.custompro.R;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> list = new ArrayList<>();
    LayoutInflater inflater;

    public GridViewAdapter(Context context, ArrayList<Product> list) {
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
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHandler handler;

        if(convertView == null) {
            handler = new ItemHandler();
            convertView = inflater.inflate(R.layout.adapter_gridview, null);

            handler.iv = convertView.findViewById(R.id.imageView);

            convertView.setTag(handler);
        } else {
            handler = (ItemHandler) convertView.getTag();
        }

        handler.iv.setImageURI(list.get(position).getProductPic());

        return convertView;
    }

    static class ItemHandler {
        ImageView iv;
    }
}