package com.example.asus.custompro.admin.accounts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.User;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.profile.ProfileActivity;
import com.example.asus.custompro.register.RegisterActivity;

import java.util.ArrayList;

public class ViewAccountsFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    ArrayList<Account> accountList = new ArrayList<>();
    AccountAdapter adapter;
    UserDatabase db;

    ListView lv;
    TextView txtEmpty;
    SwipeRefreshLayout refreshLayout;

    public ViewAccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_accounts, container, false);

        getActivity().setTitle("All Accounts");

        db = new UserDatabase(getContext());
        accountList = db.getAllUserAccounts();

        lv = view.findViewById(R.id.listView);
        txtEmpty = view.findViewById(R.id.textView);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        adapter = new AccountAdapter(getContext(), accountList);
        lv.setAdapter(adapter);

        if(accountList.isEmpty()) {
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
        Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
        profileIntent.putExtra("type", "admin");
        profileIntent.putExtra("id", accountList.get(position).getUserId());
        startActivity(profileIntent);
    }

    @Override
    public void onRefresh() {
        accountList.clear();

        accountList = db.getAllUserAccounts();
        adapter = new AccountAdapter(getContext(), accountList);
        lv.setAdapter(adapter);

        refreshLayout.setRefreshing(false);
    }
}