package com.example.asus.custompro.shop.nwo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.Search;
import com.example.asus.custompro.home.search.shops.Maker;

import java.util.ArrayList;

public class NewWorldOrderFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<Search> searchList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<NewWorldOrder> orderList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();
    NewWorldOrderAdapter adapter;

    TextView txtZero;
    UserDatabase db;

    Button btnSave, btnCancel;
    Spinner cboStatus;
    EditText txtMadeProd;
    Dialog dialog;
    String status = "";

    public NewWorldOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_world_order, container, false);

        db = new UserDatabase(getContext());
        searchList = db.getAllSearch();
        userList = db.getAccountInfo();
        orderList = db.getAllNewWorldOrderMaker(searchList.get(0).getUserid());

        lv = view.findViewById(R.id.listView);
        txtZero = view.findViewById(R.id.textView);

        adapter = new NewWorldOrderAdapter(getContext(), orderList);
        lv.setAdapter(adapter);

        if (!orderList.isEmpty()) {
            txtZero.setVisibility(View.INVISIBLE);
        } else {
            if (!(userList.get(0).getUserId() == searchList.get(0).getUserid()) && searchList.get(0).getType().equals("seeker")) {
                txtZero.setText("This shop has no orders");
            }
            txtZero.setVisibility(View.VISIBLE);
        }

        ////////////////////////////////////////
        ////////////////////////////////////////
        ////////////////////////////////////////
        FloatingActionButton fab = view.findViewById(R.id.fab);
        if(userList.get(0).getUserId() != searchList.get(0).getUserid() && searchList.get(0).getType().equals("seeker")) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getActivity(), "NwoF addrequestact", Toast.LENGTH_SHORT).show();
                    Intent addIntent = new Intent(getContext(), AddNewWorldOrderActivity.class);
//                    addIntent.putExtra("makerid", orderList.get(0).getMakerId());
                    addIntent.putExtra("makerid", searchList.get(0).getUserid());
                    startActivityForResult(addIntent, 1010);
//                    startActivityForResult(addIntent, 10);
                }
            });
        } else {
            fab.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            getActivity().finish();
            startActivity(getActivity().getIntent());
        } catch (Exception e) {
            Log.d("err", e.getMessage());
        }

//        if(resultCode == Activity.RESULT_OK) {
//            if(requestCode == 1010) {
//                try {
//                    getActivity().finish();
//                    startActivity(getActivity().getIntent());
//                } catch (Exception e) {
//                    Log.d("err", e.getMessage());
//                }
//            }
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if ((userList.get(0).getUserId() == searchList.get(0).getUserid())) {
            Intent makerIntent = new Intent(getContext(), MakerViewOrderActivity.class);
            makerIntent.putExtra("nwoid", orderList.get(position).getNwoId());
            makerIntent.putExtra("userid", orderList.get(position).getUserId());
            makerIntent.putExtra("makerid", orderList.get(position).getMakerId());
//            Toast.makeText(getContext(), "nwoid=" +orderList.get(position).getNwoId(), Toast.LENGTH_SHORT).show();
            startActivity(makerIntent);
        }
        else {
//            Toast.makeText(getContext(), "gg", Toast.LENGTH_SHORT).show();
            Intent seekerIntent = new Intent(getContext(), SeekerViewOrderActivity.class);
            seekerIntent.putExtra("nwoid", orderList.get(position).getNwoId());
            seekerIntent.putExtra("userid", orderList.get(position).getUserId());
            seekerIntent.putExtra("makerid", orderList.get(position).getMakerId());
            startActivity(seekerIntent);
        }


//        dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.order_progress_layout);
//
//        cboStatus = dialog.findViewById(R.id.spinner);
//        txtMadeProd = dialog.findViewById(R.id.editText);
//        btnSave = dialog.findViewById(R.id.button);
//        btnCancel = dialog.findViewById(R.id.button2);
//
//        txtMadeProd.setText(orderList.get(0).getOrderMade());
//
//        txtMadeProd.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s != null) {
//                    if(s.length() > 1) {
//                        if(s.toString().startsWith("0")) {
//                            s = s.toString().substring(1, s.toString().length());
//                            txtMadeProd.setText(s);
//                            txtMadeProd.setSelection(1);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(txtMadeProd.getText().length() == 0) {
//                    txtMadeProd.setText("0");
//                    txtMadeProd.setSelection(1);
//                }
//            }
//        });
//
//        switch (orderList.get(0).getOrderStatus().toLowerCase()) {
//            case "processing":
//                cboStatus.setSelection(1);
//                status = "PROCESSING";
//
//                break;
//            case "on-hold":
//                cboStatus.setSelection(2);
//                status = "ON-HOLD";
//
//                break;
//            case "done":
//                cboStatus.setSelection(3);
//                status = "DONE";
//
//                break;
//            case "failed":
//                cboStatus.setSelection(4);
//                status = "FAILED";
//
//                break;
//        }
//
//        cboStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                status = cboStatus.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String made = txtMadeProd.getText().toString();
//                String errMsg = "This field is empty";
//
//                if(TextUtils.equals("", made)) {
//                    txtMadeProd.requestFocus();
//                    txtMadeProd.setError(errMsg);
//                } else if(TextUtils.equals("", status) || TextUtils.equals("Select Status", status)) {
//                    Toast.makeText(getActivity(), "Please select status", Toast.LENGTH_LONG).show();
//                } else {
//                    makerList = db.findMakerId(orderList.get(0).getMakerId());
//                    db.editOrder(orderList.get(0).getRequestId(), orderList.get(0).getMakerId(), made, status);
//                    Toast.makeText(getActivity(), "Order Successfully Updated", Toast.LENGTH_LONG).show();
//
//                    String date = DateFormat.getDateInstance().format(new Date());
//                    String message = "";
//                    switch (status) {
//                        case "DONE":
//                            message = "Your order is done.\nPlease contact this shop number to discuss where to claim the order.\nShop Phone #: " +makerList.get(0).getShopContact();
//
//                            break;
//
//                        default:
//                            message = "Your order have been updated";
//                    }
//
//                    db.addNotification(userList.get(0).getUserId(), orderList.get(0).getMakerId(), orderList.get(0).getRequestId(), date, "Order", message);
//
//                    dialog.dismiss();
//                    getActivity().finish();
//                    startActivity(getActivity().getIntent());
//                }
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
    }
}