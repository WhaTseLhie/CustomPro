package com.example.asus.custompro.myorder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.post.Post;
import com.example.asus.custompro.home.search.shops.Maker;
import com.example.asus.custompro.myrequest.Request;
import com.example.asus.custompro.shop.nwo.NewWorldOrder;
import com.example.asus.custompro.shop.nwo.NewWorldOrderAdapter;
import com.example.asus.custompro.shop.nwo.SeekerViewOrderActivity;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<NewWorldOrder> orderList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    NewWorldOrderAdapter adapter;
    TextView txtZero;

    UserDatabase db;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        this.db = new UserDatabase(this);
        this.userList = this.db.getAccountInfo();
        this.orderList = this.db.getAllNewWorldOrderUser(userList.get(0).getUserId());

        this.lv = this.findViewById(R.id.listView);
        this.txtZero = this.findViewById(R.id.textView);
        this.refreshLayout = this.findViewById(R.id.refreshLayout);

        this.adapter = new NewWorldOrderAdapter(this, orderList);
        this.lv.setAdapter(adapter);

        if(!this.orderList.isEmpty()) {
            this.txtZero.setVisibility(View.INVISIBLE);
        } else {
            this.txtZero.setVisibility(View.VISIBLE);
        }

        this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!orderList.isEmpty()) {
                    txtZero.setVisibility(View.INVISIBLE);

                    orderList = db.getAllNewWorldOrderUser(userList.get(0).getUserId());
                    adapter = new NewWorldOrderAdapter(getApplicationContext(), orderList);
                    lv.setAdapter(adapter);
                } else {
                    txtZero.setVisibility(View.VISIBLE);
                }

                refreshLayout.setRefreshing(false);
            }
        });

        if(!orderList.isEmpty()) {
            if (orderList.get(0).getNwoStatus().equals("WORK-IN-PROGRESS")) {
                this.lv.setOnItemClickListener(this);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent seekerIntent = new Intent(this, SeekerViewOrderActivity.class);
        seekerIntent.putExtra("nwoid", orderList.get(position).getNwoId());
        seekerIntent.putExtra("userid", orderList.get(position).getUserId());
        seekerIntent.putExtra("makerid", orderList.get(position).getMakerId());
        startActivity(seekerIntent);


//        dialog = new Dialog(this);
//        dialog.setContentView(R.layout.cancel_order_layout);
//
//        TextView txtOrderId = dialog.findViewById(R.id.textView);
//        TextView txtShopName = dialog.findViewById(R.id.textView2);
//        TextView txtStatus = dialog.findViewById(R.id.textView3);
//        Button btnCancel, btnBack;
//        btnCancel = dialog.findViewById(R.id.button);
//        btnBack = dialog.findViewById(R.id.button2);
//
//        ArrayList<Maker> makerList = db.findMakerId(orderList.get(0).getMakerId());
//        //ArrayList<Request> requestList = db.getRequest(orderList.get(0).getRequestId());
//        //ArrayList<Post> postList = db.getUserPostId(orderList.get(0).getRequestId());
//
//        txtOrderId.setText("" +orderList.get(0).getRequestId());
//        txtShopName.setText(makerList.get(0).getShopName());
//        txtStatus.setText(orderList.get(0).getOrderStatus());
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Cancel Order Confirmation");
//                builder.setMessage("Do you want to cancel this order?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        db.editOrder(orderList.get(0).getRequestId(), orderList.get(0).getMakerId(), orderList.get(0).getOrderMade(), "CANCELED");
//                        finish();
//                        startActivity(getIntent());
//                    }
//                });
//                builder.setNegativeButton("No", null);
//
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//
//                dialog.dismiss();
//            }
//        });
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();











        /*Intent intent = new Intent(this, RequestActivity.class);
        intent.putExtra("userid", this.requestList.get(position).getUserId());
        intent.putExtra("requestid", this.requestList.get(position).getRequestId());
        startActivity(intent);*/
    }
}