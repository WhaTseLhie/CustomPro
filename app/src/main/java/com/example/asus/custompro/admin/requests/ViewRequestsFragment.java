package com.example.asus.custompro.admin.requests;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.myrequest.Request;
import com.example.asus.custompro.shop.request.RequestActivity;
import com.example.asus.custompro.myrequest.RequestAdapter;

import java.util.ArrayList;

public class ViewRequestsFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    ArrayList<Request> requestList = new ArrayList<>();
    RequestAdapter adapter;
    UserDatabase db;

    ListView lv;
    TextView txtEmpty;
    SwipeRefreshLayout refreshLayout;

    public ViewRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_requests, container, false);

        getActivity().setTitle("All Orders");

        db = new UserDatabase(getContext());
        requestList = db.getAllRequest();

        lv = view.findViewById(R.id.listView);
        txtEmpty = view.findViewById(R.id.textView);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        adapter = new RequestAdapter(getContext(), requestList);
        lv.setAdapter(adapter);

        if(requestList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);

        return view;
   }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), RequestActivity.class);
        intent.putExtra("userid", requestList.get(position).getUserId());
        intent.putExtra("requestid", requestList.get(position).getRequestId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        requestList.clear();

        requestList = db.getAllRequest();
        adapter = new RequestAdapter(getContext(), requestList);
        lv.setAdapter(adapter);

        refreshLayout.setRefreshing(false);
    }
}