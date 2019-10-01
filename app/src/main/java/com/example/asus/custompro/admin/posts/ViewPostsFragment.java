package com.example.asus.custompro.admin.posts;

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

import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.post.Post;
import com.example.asus.custompro.home.search.post.PostAdapter;
import com.example.asus.custompro.home.search.post.ViewPostActivity;

import java.util.ArrayList;

public class ViewPostsFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    ArrayList<Post> postList = new ArrayList<>();
    PostAdapter adapter;
    UserDatabase db;

    ListView lv;
    TextView txtEmpty;
    SwipeRefreshLayout refreshLayout;

    public ViewPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_posts, container, false);

        getActivity().setTitle("All Posts");

        db = new UserDatabase(getContext());
        postList = db.getAllPosts();

        lv = view.findViewById(R.id.listView);
        txtEmpty = view.findViewById(R.id.textView);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        adapter = new PostAdapter(getContext(), postList);
        lv.setAdapter(adapter);

        if(postList.isEmpty()) {
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
        Intent viewIntent = new Intent(getContext(), ViewPostActivity.class);
        viewIntent.putExtra("userid", postList.get(position).getUserId());
        viewIntent.putExtra("postid", postList.get(position).getPostId());
        startActivity(viewIntent);
    }

    @Override
    public void onRefresh() {
        postList.clear();

        postList = db.getAllPosts();
        adapter = new PostAdapter(getContext(), postList);
        lv.setAdapter(adapter);

        refreshLayout.setRefreshing(false);
    }
}