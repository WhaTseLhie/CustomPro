package com.example.asus.custompro.shop;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.User;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.mywallet.WalletActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShopRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    final static int PICK_ADDRESS_REQUEST_CODE = 10;
    final static int PICK_IMAGE_REQUEST_CODE = 20;

    ImageView iv, imgDialog;
    TextView txtSpecialty;
    EditText txtShopName, txtContactNum, txtEmailAdd, txtAddress;
    Button btnRegister, btnCancelReg;
    Spinner cboContact, cboEmail;
    RadioGroup rdoChoice;
    Dialog dialog;
    Uri imageUri;
    String address = "";
    String shopname, shopspecialty, shopcontact, shopemail, shopaddress;
    boolean noError = false;

    UserDatabase db;
    ArrayList<Account> userList = new ArrayList<>();

    Dialog dialog2, dialog3, dialog5;
    CheckBox cbShirt, cbBag, cbHat, cbJacket,cbPillow;

    Spinner cboSub;
    String subscription = "";
    String strtotalbal;
    int totalbal, rembal;
    int newtotalbal, payable, amount;
    TextView txtAmount;
    AlertDialog alertDialog;
//    , txtBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_register);

        this.db = new UserDatabase(this);
        this.userList = this.db.getAccountInfo();

        this.iv = this.findViewById(R.id.imageView);
        this.imgDialog = this.findViewById(R.id.imageView2);
        this.txtShopName = this.findViewById(R.id.editText);
        this.txtSpecialty = this.findViewById(R.id.textView2);
        this.txtContactNum = this.findViewById(R.id.editText3);
        this.txtEmailAdd = this.findViewById(R.id.editText4);
        this.txtAddress = this.findViewById(R.id.editText5);
        this.cboContact = this.findViewById(R.id.spinner);
        this.cboEmail = this.findViewById(R.id.spinner2);
        this.btnRegister = this.findViewById(R.id.button2);
        this.btnCancelReg = this.findViewById(R.id.button3);

//        this.txtShopName.setText("UC");
//        this.txtAddress.setText("UC");

        this.imgDialog.setOnClickListener(this);
        this.iv.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);
        this.btnCancelReg.setOnClickListener(this);

        this.cboContact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_option_contact = cboContact.getItemAtPosition(position).toString();

                if(TextUtils.equals("Another Contact Number", selected_option_contact)) {
                    txtContactNum.setText("");
                    shopcontact = "";
                } else {
                    txtContactNum.setText(userList.get(0).getContactnum());
                    shopcontact = userList.get(0).getContactnum();
                    txtContactNum.setError(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.cboEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_option_email = cboEmail.getItemAtPosition(position).toString();

                if(TextUtils.equals("Another Email Address", selected_option_email)) {
                    txtEmailAdd.setText("");
                    shopemail = "";
                } else {
                    txtEmailAdd.setText(userList.get(0).getEmailAdd());
                    shopemail = userList.get(0).getEmailAdd();
                    txtEmailAdd.setError(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*this.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent placeIntent;

                try {
                    placeIntent = builder.build(ShopRegisterActivity.this);
                    startActivityForResult(placeIntent, PICK_ADDRESS_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.imageView:
                final Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                this.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);

                break;
            case R.id.button2:
                shopname = txtShopName.getText().toString();
                shopspecialty = txtSpecialty.getText().toString();
                shopcontact = txtContactNum.getText().toString();
                shopemail = txtEmailAdd.getText().toString();
                //shopaddress = "Shop Address Here";
                shopaddress = txtAddress.getText().toString();
                checkFields();

                if(noError) {
                    try {
//                        String tldr =
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n" +
//                                "TANG NA MO KA! ACCEPT NA GAGO\n";
                        dialog = new Dialog(this);
                        dialog.setContentView(R.layout.terms_agreement_layout);

                        TextView txtTLDR = dialog.findViewById(R.id.textView);
                        Button btnConfirm = dialog.findViewById(R.id.button);
                        Button btnCancel = dialog.findViewById(R.id.button2);
                        rdoChoice = dialog.findViewById(R.id.radioGroup);
                        RadioButton rb = dialog.findViewById(R.id.radioButton2);
                        rb.setVisibility(View.INVISIBLE);

//                        txtTLDR.setText(tldr);

                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String option;
                                int selectedOption = rdoChoice.getCheckedRadioButtonId();
                                RadioButton selectedButton = dialog.findViewById(selectedOption);
                                if(selectedButton != null) {
                                    option = selectedButton.getText().toString();
                                } else {
                                    option = "Decline";
                                }

                                if(TextUtils.equals("Accept", option)) {
                                    dialog.dismiss();
                                    dialog3 = new Dialog(ShopRegisterActivity.this);
                                    dialog3.setContentView(R.layout.subscription_layout);

                                    cboSub = dialog3.findViewById(R.id.spinner);
                                    Button btnSave = dialog3.findViewById(R.id.button);
                                    Button btnCancel = dialog3.findViewById(R.id.button2);
                                    subscription = cboSub.getItemAtPosition(0).toString();

                                    cboSub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            subscription = cboSub.getItemAtPosition(position).toString();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    btnSave.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(subscription.equals("")) {
                                                Toast.makeText(getApplicationContext(), "Please Select Subscription", Toast.LENGTH_LONG).show();
                                            } else {
                                                String date = DateFormat.getDateInstance().format(new Date());
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                                Calendar c = Calendar.getInstance();

                                                try {
                                                    c.setTime(sdf.parse(date));
                                                } catch(Exception e) {
                                                    e.printStackTrace();
                                                }

                                                amount = 0;
                                                payable = 0;
                                                switch(subscription) {
                                                    case "30 Days Free Trial": amount = 30; payable = 0;
                                                        break;
                                                    case "\u20B1 99/30 Days + 30 Days Free Trial": amount = 60; payable = 99;
                                                        break;
                                                    case "\u20B1 199/2 Months + 30 Days Free Trial": amount = 90; payable = 199;
                                                        break;
                                                    case "\u20B1 499/6 Months + 30 Days Free Trial": amount = 210; payable = 499;
                                                        break;
                                                    case "\u20B1 999/1 Year + 30 Days Free Trial": amount = 365; payable = 999;
                                                        break;
                                                }

                                                c.add(Calendar.DAY_OF_MONTH, amount);
                                                SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                                                subscription = sdf2.format(c.getTime());














                                                dialog3.dismiss();
                                                strtotalbal = db.dbGetWalletBalanceUser(userList.get(0).getUserId());
                                                totalbal = Integer.parseInt(strtotalbal);
                                                if(totalbal < payable) {
                                                    Toast.makeText(ShopRegisterActivity.this, "Your total balance of \u20B1" +strtotalbal+ " is less than the subscription amount of \u20B1" +payable, Toast.LENGTH_LONG).show();
                                                    Toast.makeText(ShopRegisterActivity.this, "You need at least \u20B1" +(payable-totalbal)+ " to register your shop", Toast.LENGTH_LONG).show();
                                                    dialog5 = new Dialog(ShopRegisterActivity.this);
                                                    dialog5.setContentView(R.layout.dialog_top_up);



                                                    txtAmount = dialog5.findViewById(R.id.editText);
                                                    txtAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                    Button btnSubmit = dialog5.findViewById(R.id.button);
                                                    Button btnCancel = dialog5.findViewById(R.id.button1);

                                                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if(!txtAmount.getText().toString().trim().equals("")) {
//                                                                int totalBal = Integer.parseInt(strtotalbal);

//                                                                if (!txtBal.getText().toString().equals("0.0")) {
//                                                                    try {
//                                                                        totalBal += (Integer.parseInt(strtotalbal));
//                                                                        txtBal.setText(""+totalBal);
//                                                                    } catch (NumberFormatException e) {
//                                                                        Toast.makeText(getApplicationContext(), "Message: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                                                                    }
//                                                                }

                                                                String cashin_date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(new Date());
                                                                String cashin_time = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());

//                                                                Toast.makeText(ShopRegisterActivity.this, "ntb=" +newtotalbal, Toast.LENGTH_SHORT).show();
//                                                                db.editWalletUser(userList.get(0).getUserId(), newtotalbal+"");
//                                                                db.addWalletCashIn(userList.get(0).getUserId(), txtAmount.getText().toString(), cashin_date, cashin_time);
//                                                                Toast.makeText(getApplicationContext(), "Top-up Successful", Toast.LENGTH_LONG).show();

                                                                newtotalbal = Integer.parseInt(txtAmount.getText().toString()) + totalbal;
                                                                if(db.findWalletUser(userList.get(0).getUserId())) {
                                                                    db.editWalletUser(userList.get(0).getUserId(), newtotalbal + "");
                                                                } else {
                                                                    db.addWalletUser(userList.get(0).getUserId(), newtotalbal + "");
                                                                }
                                                                Toast.makeText(getApplicationContext(), "Top-up Successful! Total Balance \u20B1" +newtotalbal, Toast.LENGTH_LONG).show();
                                                                db.addWalletCashIn(userList.get(0).getUserId(), txtAmount.getText().toString(), cashin_date, cashin_time);

                                                                dialog5.dismiss();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Please put an amount", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });

                                                    btnCancel.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            dialog5.dismiss();
                                                        }
                                                    });

                                                    dialog5.show();
//                                                    Intent intent1 = new Intent(getApplicationContext(), WalletActivity.class);
//                                                    startActivity(intent1);
                                                } else {
                                                    newtotalbal = totalbal;
                                                    deductInShopRegistration();
                                                }




















                                            }
                                        }
                                    });

                                    btnCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog3.dismiss();
                                        }
                                    });

                                    dialog3.show();
                                } else {
                                    Toast.makeText(ShopRegisterActivity.this, "Please Accept Terms And Agreement", Toast.LENGTH_LONG).show();
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
                    } catch (Exception e) {
                        Log.d("ERROR DIALOG", e.getMessage());
                        Toast.makeText(this, String.format("ERROR DIALOG %s", e.getMessage()), Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.button3:
                finish();

                break;

            case R.id.imageView2:
                selectSpecialty();

                break;
        }
    }

    public void deductInShopRegistration() {
        rembal = newtotalbal - payable;
        String alertDmessage;
        if(payable==0) {
            alertDmessage = "Expiration Date: " + subscription;
        } else {
            alertDmessage = "Expiration Date: " + subscription
                    + "\nWe will deducted \u20B1" + payable + " to your total balance \u20B1" + newtotalbal + " if you confirm"
                    + "\nYour remaining balance will be \u20B1" + rembal + "\nAre you sure you want to open a shop and subscribe?";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ShopRegisterActivity.this);
        builder.setTitle("Confirm Subscription");
        builder.setMessage(alertDmessage);
        builder.setCancelable(false);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //////
                //////
                //////
                if(payable>0) {
                    String payoutdate = DateFormat.getDateInstance().format(new Date());
                    db.addWalletPayout(userList.get(0).getUserId(), userList.get(0).getUserId(), "9999", payoutdate, payable + "");
                }

                db.editWalletUser(userList.get(0).getUserId(), rembal + "");
                db.addMaker(userList.get(0).getUserId(), imageUri.toString(), shopname, shopspecialty, "0.0", shopcontact, shopemail, shopaddress, subscription);
                Toast.makeText(ShopRegisterActivity.this, shopname+ " has been successfully created", Toast.LENGTH_LONG).show();
                dialog3.dismiss();
                alertDialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void checkFields() {
        String errMsg = "This field is empty.";

        if(imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(shopname)) {
            txtShopName.requestFocus();
            txtShopName.setError(errMsg);
        } else if(TextUtils.equals("Specialty", shopspecialty)) {
            Toast.makeText(this, "Please select specialty", Toast.LENGTH_SHORT).show();
            selectSpecialty();
        } else if(TextUtils.isEmpty(shopcontact)) {
            txtContactNum.requestFocus();
            txtContactNum.setError(errMsg);
        } else if(TextUtils.isEmpty(shopemail)) {
            txtEmailAdd.requestFocus();
            txtEmailAdd.setError(errMsg);
        } else if(TextUtils.isEmpty(shopaddress)) {
            txtAddress.requestFocus();
            txtAddress.setError(errMsg);
        } else {
            noError = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            imageUri = data.getData();

            if (imageUri != null) {
                iv.setImageURI(imageUri);
            }
        } catch(Exception e) {
            Log.d("Image on SD Card Err=", e.getMessage());
        }
        /*if(requestCode == PICK_IMAGE_REQUEST_CODE) {
            try {
                imageUri = data.getData();

                if (imageUri != null) {
                    iv.setImageURI(imageUri);
                }
            } catch(Exception e) {
                Log.d("Image on SD Card Err=", e.getMessage());
            }
        } else if(requestCode == PICK_ADDRESS_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                address = place.getAddress().toString();
                txtAddress.setText(address);
            } else if(resultCode == Activity.RESULT_CANCELED) {
                if(address.equals("")) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setTitle("Message");
                    alertBuilder.setMessage("You must select your location.");
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                            Intent intent;

                            try {
                                intent = builder.build(getParent());
                                startActivityForResult(intent, PICK_ADDRESS_REQUEST_CODE);
                            } catch (GooglePlayServicesRepairableException e) {
                                e.printStackTrace();
                            } catch (GooglePlayServicesNotAvailableException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    AlertDialog dialog = alertBuilder.create();
                    dialog.show();
                }
            }
        }*/
    }

    private void selectSpecialty() {
        dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.layout_specialty);

        cbShirt = dialog2.findViewById(R.id.checkBox);
//        cbBag = dialog2.findViewById(R.id.checkBox2);
        cbHat = dialog2.findViewById(R.id.checkBox3);
        cbJacket = dialog2.findViewById(R.id.checkBox4);
//        cbPillow = dialog2.findViewById(R.id.checkBox5);
        Button btnSave = dialog2.findViewById(R.id.button);
        Button btnCancel = dialog2.findViewById(R.id.button2);

        String specialty = txtSpecialty.getText().toString();
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
                String category = "";

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

                txtSpecialty.setText(category);
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
}