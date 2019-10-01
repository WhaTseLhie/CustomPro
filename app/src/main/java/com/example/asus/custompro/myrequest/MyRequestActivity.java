package com.example.asus.custompro.myrequest;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.shop.request.RequestActivity;

import java.util.ArrayList;

public class MyRequestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<Request> requestList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    RequestAdapter adapter;
    TextView txtZero;

    UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);

        this.db = new UserDatabase(this);
        this.userList = this.db.getAccountInfo();
        this.requestList = this.db.getRequestUser(userList.get(0).getUserId());

        this.lv = this.findViewById(R.id.listView);
        this.txtZero = this.findViewById(R.id.textView);
        this.refreshLayout = this.findViewById(R.id.refreshLayout);

        this.adapter = new RequestAdapter(this, requestList);
        this.lv.setAdapter(adapter);

        if(!this.requestList.isEmpty()) {
            this.txtZero.setVisibility(View.INVISIBLE);
        } else {
            this.txtZero.setVisibility(View.VISIBLE);
        }

        this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!requestList.isEmpty()) {
                    txtZero.setVisibility(View.INVISIBLE);

                    requestList = db.getRequestUser(userList.get(0).getUserId());
                    adapter = new RequestAdapter(getApplicationContext(), requestList);
                    lv.setAdapter(adapter);
                } else {
                    txtZero.setVisibility(View.VISIBLE);
                }

                refreshLayout.setRefreshing(false);
            }
        });

        this.lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, RequestActivity.class);
        intent.putExtra("userid", this.requestList.get(position).getUserId());
        intent.putExtra("requestid", this.requestList.get(position).getRequestId());
        startActivity(intent);
    }
}