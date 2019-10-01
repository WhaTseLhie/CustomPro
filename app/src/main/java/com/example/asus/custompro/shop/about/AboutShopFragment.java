package com.example.asus.custompro.shop.about;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.Search;
import com.example.asus.custompro.home.search.shops.Maker;

import java.util.ArrayList;

public class AboutShopFragment extends Fragment {

    TextView txtName, txtSpecialty, txtContact, txtEmail, txtAddress, txtEditShop, txtEditShopContact;
    EditText txtEditEmail, txtEditContact, txtEditName, txtEditSpecialty;
    TextView txtEditAddress, txtSub;
    Button btnAddress, btnSave, btnCancel, btnContactSave, btnContactCancel;
    UserDatabase db;
    ArrayList<Maker> makerList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Search> searchList = new ArrayList<>();

    public AboutShopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_shop, container, false);

        db = new UserDatabase(getContext());
        userList = db.getAccountInfo();
        //makerList = db.findMaker(userList.get(0).getUserId());
        searchList = db.getAllSearch();
        makerList = db.findMaker(searchList.get(0).getUserid());

        txtName = view.findViewById(R.id.textView);
        txtSpecialty = view.findViewById(R.id.textView2);
        txtContact = view.findViewById(R.id.textView3);
        txtEmail = view.findViewById(R.id.textView4);
        txtAddress = view.findViewById(R.id.textView5);
        txtSub = view.findViewById(R.id.textView6);
        txtEditShop = view.findViewById(R.id.txtEditShop);
        txtEditShopContact = view.findViewById(R.id.txtEditShopContact);

        txtName.setText(makerList.get(0).getShopName());
        txtSpecialty.setText(makerList.get(0).getShopSpecialty());
        txtContact.setText(makerList.get(0).getShopContact());
        txtEmail.setText(makerList.get(0).getShopEmail());
        txtAddress.setText(makerList.get(0).getShopAddress());
        txtSub.setText(makerList.get(0).getShopSubscription());

        if(userList.get(0).getUserId() == searchList.get(0).getUserid() || searchList.get(0).getType().equals("maker")) {
            txtEditShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.edit_shop_info_layout);

                    txtEditName = dialog.findViewById(R.id.editText);
                    txtEditSpecialty = dialog.findViewById(R.id.editText2);
                    btnSave = dialog.findViewById(R.id.button);
                    btnCancel = dialog.findViewById(R.id.button2);

                    txtEditName.setText(makerList.get(0).getShopName());
                    txtEditSpecialty.setText(makerList.get(0).getShopSpecialty());

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String editName = txtEditName.getText().toString();
                            String editSpecialty = txtEditSpecialty.getText().toString();
                            String errMsg = "This field is empty.";

                            if (TextUtils.isEmpty(editName)) {
                                txtEditName.requestFocus();
                                txtEditName.setError(errMsg);
                            } else if (TextUtils.isEmpty(editSpecialty)) {
                                txtEditSpecialty.requestFocus();
                                txtEditSpecialty.setError(errMsg);
                            } else {
                                db.editMaker(makerList.get(0).getUserId(), makerList.get(0).getShopPic().toString(), editName, editSpecialty, makerList.get(0).getShopRating(), makerList.get(0).getShopContact(), makerList.get(0).getShopEmail(), makerList.get(0).getShopAddress());
                                Toast.makeText(getActivity(), "Shop Information Updated", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                getActivity().finish();
                                startActivity(getActivity().getIntent());
                            }
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            txtEditShopContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.edit_shop_contact_info_layout);

                    txtEditContact = dialog.findViewById(R.id.editText);
                    txtEditEmail = dialog.findViewById(R.id.editText2);
                    txtEditAddress = dialog.findViewById(R.id.editText3);
                    //btnAddress = dialog.findViewById(R.id.button);
                    btnContactSave = dialog.findViewById(R.id.button2);
                    btnContactCancel = dialog.findViewById(R.id.button3);

                    txtEditEmail.setText(makerList.get(0).getShopEmail());
                    txtEditContact.setText(makerList.get(0).getShopContact());
                    txtEditAddress.setText(makerList.get(0).getShopAddress());

                    /*btnAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txtEditAddress.setText("Address Here");
                            Toast.makeText(getActivity(), "Change Address", Toast.LENGTH_LONG).show();
                        }
                    });*/

                    btnContactSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String editEmail = txtEditEmail.getText().toString();
                            String editContact = txtEditContact.getText().toString();
                            String editAddress = txtEditAddress.getText().toString();
                            String errMsg = "This field is empty.";

                            if (TextUtils.isEmpty(editEmail)) {
                                txtEditEmail.requestFocus();
                                txtEditEmail.setError(errMsg);
                            } else if (TextUtils.isEmpty(editContact)) {
                                txtEditContact.requestFocus();
                                txtEditContact.setError(errMsg);
                            } else if (TextUtils.isEmpty(editAddress)) {
                                txtEditAddress.requestFocus();
                                txtEditAddress.setError(errMsg);
                            } else {
                                makerList.get(0).setShopContact(editContact);
                                makerList.get(0).setShopEmail(editEmail);
                                makerList.get(0).setShopAddress(editAddress);

                                txtContact.setText(editContact);
                                txtEmail.setText(editEmail);
                                txtAddress.setText(editAddress);

                                db.editMaker(makerList.get(0).getUserId(), makerList.get(0).getShopPic().toString(), makerList.get(0).getShopName(), makerList.get(0).getShopSpecialty(), makerList.get(0).getShopRating(), editContact, editEmail, editAddress);
                                Toast.makeText(getActivity(), "Shop Contact Information Updated", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }
                    });

                    btnContactCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
        } else {
            txtEditShopContact.setVisibility(View.INVISIBLE);
            txtEditShop.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}