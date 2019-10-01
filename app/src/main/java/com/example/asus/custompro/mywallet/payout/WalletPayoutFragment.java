package com.example.asus.custompro.mywallet.payout;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.User;
import com.example.asus.custompro.UserDatabase;

import java.util.ArrayList;

public class WalletPayoutFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ListView lv;
    WalletPayoutAdapter adapter;
    ArrayList<WalletPayout> payoutList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();

    UserDatabase db;
    TextView txtEmpty;

    SwipeRefreshLayout refreshLayout;

    public WalletPayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_payout, container, false);

        db = new UserDatabase(getContext());
        userList = db.getAccountInfo();
        payoutList = db.getAllWalletPayoutUser(userList.get(0).getUserId());

        lv = view.findViewById(R.id.listView);
        txtEmpty = view.findViewById(R.id.textView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        adapter = new WalletPayoutAdapter(getContext(), payoutList);
        lv.setAdapter(adapter);

        if (payoutList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        refreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh() {
        payoutList.clear();

        payoutList = db.getAllWalletPayoutUser(userList.get(0).getUserId());
        adapter = new WalletPayoutAdapter(getContext(), payoutList);
        lv.setAdapter(adapter);

        if (payoutList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        refreshLayout.setRefreshing(false);
    }
}