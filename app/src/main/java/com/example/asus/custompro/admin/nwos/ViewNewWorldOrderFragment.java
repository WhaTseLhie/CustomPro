package com.example.asus.custompro.admin.nwos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.shop.nwo.NewWorldOrder;
import com.example.asus.custompro.shop.nwo.NewWorldOrderAdapter;

import java.util.ArrayList;

public class ViewNewWorldOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<NewWorldOrder> orderList = new ArrayList<>();
    NewWorldOrderAdapter adapter;
    UserDatabase db;

    ListView lv;
    TextView txtEmpty;
    SwipeRefreshLayout refreshLayout;

    public ViewNewWorldOrderFragment() {
        // Required empty public constructor
    }

        @Override
        public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle
        savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_view_orders, container, false);

        getActivity().setTitle("All Orders");

        db = new UserDatabase(getContext());
        orderList = db.getAllNewWorldOrder();

        lv = view.findViewById(R.id.listView);
        txtEmpty = view.findViewById(R.id.textView);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        adapter = new NewWorldOrderAdapter(getContext(), orderList);
        lv.setAdapter(adapter);

        if (orderList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        refreshLayout.setOnRefreshListener(this);

        return view;
    }

        @Override
        public void onRefresh () {
        orderList.clear();

        orderList = db.getAllNewWorldOrder();
        adapter = new NewWorldOrderAdapter(getContext(), orderList);
        lv.setAdapter(adapter);

        refreshLayout.setRefreshing(false);
    }
}