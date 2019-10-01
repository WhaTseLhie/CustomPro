package com.example.asus.custompro.shop.request;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.Search;
import com.example.asus.custompro.myrequest.Request;
import com.example.asus.custompro.myrequest.RequestAdapter;

import java.util.ArrayList;

public class RequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<Search> searchList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Request> requestList = new ArrayList<>();
    RequestAdapter adapter;

    TextView txtZero;
    UserDatabase db;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        db = new UserDatabase(getContext());
        searchList = db.getAllSearch();
        userList = db.getAccountInfo();
        requestList = db.getRequestMaker(searchList.get(0).getUserid());

        lv = view.findViewById(R.id.listView);
        txtZero = view.findViewById(R.id.textView);

        adapter = new RequestAdapter(getContext(), requestList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        if(!requestList.isEmpty()) {
            txtZero.setVisibility(View.INVISIBLE);
        } else {
            if(!(userList.get(0).getUserId() == searchList.get(0).getUserid()) && searchList.get(0).getType().equals("seeker")) {
                txtZero.setText("This shop has no orders");
            }
            txtZero.setVisibility(View.VISIBLE);
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);
        if(userList.get(0).getUserId() != searchList.get(0).getUserid() && searchList.get(0).getType().equals("seeker")) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addIntent = new Intent(getContext(), AddRequestActivity.class);
                    startActivityForResult(addIntent, 10);
                }
            });
        } else {
            fab.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), RequestActivity.class);
        intent.putExtra("userid", requestList.get(position).getUserId());
        intent.putExtra("requestid", requestList.get(position).getRequestId());
        startActivityForResult(intent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            getActivity().finish();
            startActivity(getActivity().getIntent());
        } catch (Exception e) {
            Log.d("err:", e.getMessage());
        }
    }
}