package com.example.asus.custompro.home.search.post;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostFragment extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList<Post> sourceList = new ArrayList<>();
    ArrayList<Post> postList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    PostAdapter postAdapter;

    CheckBox cbShirt, cbBag, cbHat, cbJacket,cbPillow;
    TextView txtSelectedCategory;
    EditText txtSearch;
    UserDatabase db;
    ImageView iv;
    ListView lv;

    String category = "";
    Dialog dialog2;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        db = new UserDatabase(getContext());
        postList = db.getAllPosts();
        sourceList = db.getAllPosts();

        txtSelectedCategory = view.findViewById(R.id.textView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        txtSearch = view.findViewById(R.id.editText);
        iv = view.findViewById(R.id.imageView);
        lv = view.findViewById(R.id.listView);

        try {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } catch(Exception e) {
            Log.d("err=", e.getMessage());
        }

        postAdapter = new PostAdapter(getContext(), postList);
        lv.setAdapter(postAdapter);

        if(txtSearch.getText().toString().equals("")) {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    postList.clear();

                    category = "No Selected Category";
                    txtSelectedCategory.setText("No Selected Category");
                    txtSearch.setText("");
                    postList = db.getAllPosts();
                    postAdapter = new PostAdapter(getContext(), postList);
                    lv.setAdapter(postAdapter);
                    sourceList = db.getAllPosts();

                    refreshLayout.setRefreshing(false);
                }
            });
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2 = new Dialog(getActivity());
                dialog2.setContentView(R.layout.layout_specialty);

                cbShirt = dialog2.findViewById(R.id.checkBox);
//                cbBag = dialog2.findViewById(R.id.checkBox2);
                cbHat = dialog2.findViewById(R.id.checkBox3);
                cbJacket = dialog2.findViewById(R.id.checkBox4);
//                cbPillow = dialog2.findViewById(R.id.checkBox5);
                Button btnSave = dialog2.findViewById(R.id.button);
                btnSave.setText("Search");
                Button btnCancel = dialog2.findViewById(R.id.button2);

                String specialty = txtSelectedCategory.getText().toString();
                if(specialty.contains("T-Shirt")) {
                    cbShirt.setChecked(true);
                } /*if(specialty.contains("Bags")) {
                    cbBag.setChecked(true);
                }*/ if(specialty.contains("Hats")) {
                    cbHat.setChecked(true);
                } if(specialty.contains("Hoodie")) {
                    cbJacket.setChecked(true);
                } /*if(specialty.contains("Pillow")) {
                    cbPillow.setChecked(true);
                }*/

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        category = "";

                        if(cbShirt.isChecked()) {
                            category += "T-Shirt";
                        } /*if(cbBag.isChecked()) {
                            if(category.equals("")) {
                                category += "Bags";
                            } else {
                                category += ", Bags";
                            }
                        }*/ if(cbHat.isChecked()) {
                            if(category.equals("")) {
                                category += "Hats";
                            } else {
                                category += ", Hats";
                            }
                        } if(cbJacket.isChecked()) {
                            if(category.equals("")) {
                                category += "Hoodie or Jacket";
                            } else {
                                category += ", Hoodie or Jacket";
                            }
                        } /*if(cbPillow.isChecked()) {
                            if(category.equals("")) {
                                category += "Pillow";
                            } else {
                                category += ", Pillow";
                            }
                        }*/

                        if(category.equals("")) {
                            category = "No Selected Category";
                        }

                        txtSelectedCategory.setText(category);
                        postList = db.getAllPost(category);
                        postAdapter = new PostAdapter(getContext(), postList);
                        lv.setAdapter(postAdapter);
                        dialog2.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });

                dialog2.show();
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String search = s.toString().toLowerCase();
                    Pattern pattern = Pattern.compile(search);
                    postList.clear();

                    for (int i = 0; i < sourceList.size(); i++) {
                        Matcher m = pattern.matcher(sourceList.get(i).getPostDetails().toLowerCase());
                        Matcher m2 = pattern.matcher(sourceList.get(i).getPostCategory().toLowerCase());
                        Matcher m3 = pattern.matcher(sourceList.get(i).getPostType().toLowerCase());

                        if (m.find() || m2.find() || m3.find()) {
                            postList.add(sourceList.get(i));
                        }
                    }
                /*String selCat = txtSelectedCategory.getText().toString().toLowerCase();
                String cat = "T-Shirt, Bags, Hats, Hoodie or Jacket, Pillow";

                if(selCat.equals("No Selected Category") || selCat.equals(cat.toLowerCase())) {
                    for (int i = 0; i < sourceList.size(); i++) {
                        Matcher m = pattern.matcher(sourceList.get(i).getPostCategory().toLowerCase());
                        if (m.find()) {
                            postList.add(sourceList.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < sourceList.size(); i++) {
                        String ss = sourceList.get(i).getPostCategory().toLowerCase();

                        if(selCat.contains(ss)) {
                            Matcher m = pattern.matcher(ss);
                            if (m.find()) {
                                postList.add(sourceList.get(i));
                            }
                        }
                    }
                }*/

                    postAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d("err=", e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String cat = postList.get(position).getPostCategory();
        int quant = postList.get(position).getPostQty();

        if(cat.isEmpty() && quant == 0) {
            Intent viewAdIntent = new Intent(getContext(), ViewPostAdActivity.class);
            viewAdIntent.putExtra("userid", postList.get(position).getUserId());
            viewAdIntent.putExtra("postid", postList.get(position).getPostId());
            startActivity(viewAdIntent);
        } else {
            Intent viewIntent = new Intent(getContext(), ViewPostActivity.class);
            viewIntent.putExtra("userid", postList.get(position).getUserId());
            viewIntent.putExtra("postid", postList.get(position).getPostId());
            startActivity(viewIntent);
        }
    }
}