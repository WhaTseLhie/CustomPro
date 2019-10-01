package com.example.asus.custompro.admin.shops;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.shops.Maker;
import com.example.asus.custompro.home.search.shops.MakerAdapter;

import java.util.ArrayList;

public class AdminShopFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    ArrayList<Maker> makerList = new ArrayList<>();
    MakerAdapter adapter;
    UserDatabase db;

    ListView lv;
    TextView txtEmpty;
    SwipeRefreshLayout refreshLayout;

    public AdminShopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_shop, container, false);

        getActivity().setTitle("All Shops");

        db = new UserDatabase(getContext());
        makerList = db.getAllMaker();

        lv = view.findViewById(R.id.listView);
        txtEmpty = view.findViewById(R.id.textView);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        adapter = new MakerAdapter(getContext(), makerList);
        lv.setAdapter(adapter);

        if(makerList.isEmpty()) {
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
        /*Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
        profileIntent.putExtra("type", "admin");
        profileIntent.putExtra("id", accountList.get(position).getUserId());
        startActivity(profileIntent);*/

        //Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        makerList.clear();

        makerList = db.getAllMaker();
        adapter = new MakerAdapter(getContext(), makerList);
        lv.setAdapter(adapter);

        refreshLayout.setRefreshing(false);
    }
}