package com.example.asus.custompro.admin.orders;

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
import com.example.asus.custompro.myorder.Order;
import com.example.asus.custompro.myorder.OrderAdapter;

import java.util.ArrayList;

public class ViewOrdersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<Order> orderList = new ArrayList<>();
    OrderAdapter adapter;
    UserDatabase db;

    ListView lv;
    TextView txtEmpty;
    SwipeRefreshLayout refreshLayout;

    public ViewOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_orders, container, false);

        getActivity().setTitle("All Orders");

        db = new UserDatabase(getContext());
        orderList = db.getAllOrders();

        lv = view.findViewById(R.id.listView);
        txtEmpty = view.findViewById(R.id.textView);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        adapter = new OrderAdapter(getContext(), orderList);
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
    public void onRefresh() {
        orderList.clear();

        orderList = db.getAllOrders();
        adapter = new OrderAdapter(getContext(), orderList);
        lv.setAdapter(adapter);

        refreshLayout.setRefreshing(false);
    }
}