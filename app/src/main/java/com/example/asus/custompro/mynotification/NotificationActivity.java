package com.example.asus.custompro.mynotification;

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

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<Notif> notifList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    NotifAdapter adapter;
    TextView txtZero;

    UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        this.db = new UserDatabase(this);
        this.userList = this.db.getAccountInfo();
        this.notifList = this.db.getAllNotificationUser(userList.get(0).getUserId());

        this.lv = this.findViewById(R.id.listView);
        this.txtZero = this.findViewById(R.id.textView);
        this.refreshLayout = this.findViewById(R.id.refreshLayout);

        this.adapter = new NotifAdapter(this, notifList);
        this.lv.setAdapter(adapter);

        if(!this.notifList.isEmpty()) {
            this.txtZero.setVisibility(View.INVISIBLE);
        } else {
            this.txtZero.setVisibility(View.VISIBLE);
        }

        this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!notifList.isEmpty()) {
                    txtZero.setVisibility(View.INVISIBLE);

                    notifList = db.getAllNotificationUser(userList.get(0).getUserId());
                    adapter = new NotifAdapter(getApplicationContext(), notifList);
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
        /*Intent intent = new Intent(this, RequestActivity.class);
        intent.putExtra("userid", this.requestList.get(position).getUserId());
        intent.putExtra("requestid", this.requestList.get(position).getRequestId());
        startActivity(intent);*/
    }
}