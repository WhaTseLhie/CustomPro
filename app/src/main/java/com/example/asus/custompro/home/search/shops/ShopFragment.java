package com.example.asus.custompro.home.search.shops;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.asus.custompro.home.search.post.PostFragment;
import com.example.asus.custompro.shop.OpenShopActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopFragment extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList<Maker> sourceList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();
    MakerAdapter makerAdapter;

    CheckBox cbShirt, cbBag, cbHat, cbJacket,cbPillow;
    SwipeRefreshLayout refreshLayout;
    TextView txtSelectedCategory;
    String category = "";
    Dialog dialog2;
    ImageView iv;
    ListView lv;

    UserDatabase db;
    EditText txtSearch;

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        db = new UserDatabase(getContext());
        makerList = db.getAllMaker();
        sourceList = db.getAllMaker();

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

        makerAdapter = new MakerAdapter(getContext(), makerList);
        lv.setAdapter(makerAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makerList.clear();
                txtSearch.setText("");
                ///////////////////////////
                category = "No Selected Category";
                txtSelectedCategory.setText("No Selected Category");
                sourceList = db.getAllMaker();

                refreshItems();
                refreshLayout.setRefreshing(false);
            }
        });

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
                        ////////////////
                        makerList = db.getAllMaker(category);
                        makerAdapter = new MakerAdapter(getContext(), makerList);
//                        Toast.makeText(getActivity(), category, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(), category.toLowerCase(), Toast.LENGTH_LONG).show();
                        lv.setAdapter(makerAdapter);
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
                    makerList.clear();

                    for (int i = 0; i < sourceList.size(); i++) {
                        Matcher m1 = pattern.matcher(sourceList.get(i).getShopName().toLowerCase());
                        Matcher m2 = pattern.matcher(sourceList.get(i).getShopSpecialty().toLowerCase());
                        Matcher m3 = pattern.matcher(sourceList.get(i).getShopAddress().toLowerCase());

                        if (m1.find() || m2.find() || m3.find()) {
                            makerList.add(sourceList.get(i));
                        }
                    }

//                    String selCat = txtSelectedCategory.getText().toString().toLowerCase();

                    ////////// TRUE
                /*if(selCat.equals("no selected category")) {
                    for (int i = 0; i < sourceList.size(); i++) {
                        Matcher m1 = pattern.matcher(sourceList.get(i).getShopName().toLowerCase());
                        Matcher m2 = pattern.matcher(sourceList.get(i).getShopSpecialty().toLowerCase());

                        if (m1.find() || m2.find()) {
                            makerList.add(sourceList.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < sourceList.size(); i++) {
                        String sn = sourceList.get(i).getShopName().toLowerCase();
                        String ss = sourceList.get(i).getShopSpecialty().toLowerCase();

                        if(selCat.contains(ss)) {
                            Matcher m1 = pattern.matcher(sn);
                            Matcher m2 = pattern.matcher(ss);

                            if (m1.find() || m2.find()) {
                                makerList.add(sourceList.get(i));
                            }
                        }
                    }
                }*/

                /*if(selCat.equals("no selected category")) {
                    for (int i = 0; i < sourceList.size(); i++) {
                        Matcher m1 = pattern.matcher(sourceList.get(i).getShopName().toLowerCase());
                        Matcher m2 = pattern.matcher(sourceList.get(i).getShopSpecialty().toLowerCase());

                        if (m1.find() || m2.find()) {
                            makerList.add(sourceList.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < sourceList.size(); i++) {
                        String sn = sourceList.get(i).getShopName().toLowerCase();
                        String ss = sourceList.get(i).getShopSpecialty().toLowerCase();

                        if(hasCommon(selCat, ss)) {
                            Matcher m1 = pattern.matcher(sn);
                            Matcher m2 = pattern.matcher(ss);

                            if (m1.find() || m2.find()) {
                                makerList.add(sourceList.get(i));
                            }
                        }
                    }
                }*/

                    makerAdapter.notifyDataSetChanged();
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

    public boolean hasCommon(String selCat, String shopSpecialty) {
        String[] strAList = selCat.split(",");
        List<String> listA = new ArrayList<>(Arrays.asList(strAList));

        String[] strBList = shopSpecialty.split(",");
        List<String> listB = new ArrayList<>(Arrays.asList(strBList));

        for(String str : listA) {
            if(listB.contains(str)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int userid = makerList.get(position).getUserId();

        Intent intent = new Intent(getContext(), OpenShopActivity.class);
        try {
            db.deleteAllSearch();
        } catch(Exception e) {
            Log.d("CUSTOM PRO ERR: ", e.getMessage());
        }

        db.addSearch(userid, "seeker");
        startActivity(intent);
    }

    private void refreshItems() {
        makerList = db.getAllMaker();
        makerAdapter = new MakerAdapter(getContext(), makerList);
        lv.setAdapter(makerAdapter);
    }
}