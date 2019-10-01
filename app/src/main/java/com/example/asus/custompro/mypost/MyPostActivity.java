package com.example.asus.custompro.mypost;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.post.Post;
import com.example.asus.custompro.home.search.post.PostAdapter;
import com.example.asus.custompro.home.search.post.ViewPostActivity;
import com.example.asus.custompro.home.search.post.ViewPostAdActivity;

import java.util.ArrayList;

public class MyPostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Post> postList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    PostAdapter adapter;
    TextView txtZero;

    UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        this.db = new UserDatabase(this);
        this.userList = this.db.getAccountInfo();
        this.postList = this.db.getUserPost(userList.get(0).getUserId());

        this.lv = this.findViewById(R.id.listView);
        this.txtZero = this.findViewById(R.id.textView);
        this.refreshLayout = this.findViewById(R.id.refreshLayout);

        this.adapter = new PostAdapter(this, postList);
        this.lv.setAdapter(adapter);

        if (!this.postList.isEmpty()) {
            this.txtZero.setVisibility(View.INVISIBLE);
        } else {
            this.txtZero.setVisibility(View.VISIBLE);
        }

        this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!postList.isEmpty()) {
                    txtZero.setVisibility(View.INVISIBLE);

                    postList = db.getUserPost(userList.get(0).getUserId());
                    adapter = new PostAdapter(getApplicationContext(), postList);
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
//        Intent viewIntent = new Intent(this, ViewPostActivity.class);
//        viewIntent.putExtra("userid", postList.get(position).getUserId());
//        viewIntent.putExtra("postid", postList.get(position).getPostId());
//        startActivity(viewIntent);
        String cat = postList.get(position).getPostCategory();
        int quant = postList.get(position).getPostQty();

        if(cat.isEmpty() && quant == 0) {
            Intent viewAdIntent = new Intent(this, ViewPostAdActivity.class);
            viewAdIntent.putExtra("userid", postList.get(position).getUserId());
            viewAdIntent.putExtra("postid", postList.get(position).getPostId());
            startActivity(viewAdIntent);
        } else {
            Intent viewIntent = new Intent(this, ViewPostActivity.class);
            viewIntent.putExtra("userid", postList.get(position).getUserId());
            viewIntent.putExtra("postid", postList.get(position).getPostId());
            startActivity(viewIntent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}