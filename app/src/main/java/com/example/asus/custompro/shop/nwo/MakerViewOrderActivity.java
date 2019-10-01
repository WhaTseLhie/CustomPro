package com.example.asus.custompro.shop.nwo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.Search;
import com.example.asus.custompro.home.search.post.bids.offer.Offer;
import com.example.asus.custompro.home.search.shops.Maker;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MakerViewOrderActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtName, txtDate, txtGender, txtContact, txtEmail, txtAddress, txtCategory, txtDetails,
            txtStatus, txtQuantity, txtShopName, txtShopAddress, txtType, txtSize, txtColor,
            txtOfferComment, txtOfferDate, txtOfferCost, txtOrderStatus, txtOrderMade, txtOrderMake;
    Button btnUpdate, btnAccept, btnDecline;
    ImageView imgUser, imgRequest, imgMaker;
    RatingBar shopRatingBar;
//    RadioGroup rdoStatus;

    ArrayList<NewWorldOrder> orderList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Account> accList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();
    ArrayList<Offer> offerList = new ArrayList<>();
    ArrayList<Search> searchList = new ArrayList<>();

    UserDatabase db;

    Button btnSave, btnCancel;
    Spinner cboStatus;
    EditText txtMadeProd;
    Dialog dialog;
    String status = "";
    String option, message, number, stat;

    AlertDialog alerdialog;


    ImageView dialogiv;
    TextView dialogtxtDate;
    Button dialogbtnOffer, dialogbtnCancel;
    EditText dialogtxtComment, dialogtxtPriceOffer;
    TextView txtDateUse, txtOpt1, txtOpt2;

//    UserDatabase db;
//    ArrayList<Offer> offerList = new ArrayList<>();

    int userId, postId, shopId;
    int nwoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_view_order);

        this.setTitle("Order Information");

        this.imgUser = this.findViewById(R.id.imageView);
        this.imgMaker = this.findViewById(R.id.imageView2);
        this.imgRequest = this.findViewById(R.id.imageView3);
        this.txtName = this.findViewById(R.id.textView);
        this.txtDate = this.findViewById(R.id.textView2);
        this.txtGender = this.findViewById(R.id.textView3);
        this.txtContact = this.findViewById(R.id.textView4);
        this.txtEmail = this.findViewById(R.id.textView5);
        this.txtAddress = this.findViewById(R.id.textView6);
        this.txtShopName = this.findViewById(R.id.textView7);
        this.txtShopAddress = this.findViewById(R.id.textView8);
        this.txtStatus = this.findViewById(R.id.textView9);
        this.txtCategory = this.findViewById(R.id.textView10);
        this.txtType = this.findViewById(R.id.textView11);
        this.txtSize = this.findViewById(R.id.textView12);
        this.txtQuantity = this.findViewById(R.id.textView13);
        this.txtColor = this.findViewById(R.id.textView14);
        this.txtDetails = this.findViewById(R.id.textView15);
        this.txtOfferComment = this.findViewById(R.id.textView16);
        this.txtOfferDate = this.findViewById(R.id.textView17);
        this.txtOfferCost = this.findViewById(R.id.textView18);
        this.txtOrderStatus = this.findViewById(R.id.textView19);
        this.txtOrderMake = this.findViewById(R.id.textView20);
        this.txtOrderMade = this.findViewById(R.id.textView21);
        this.shopRatingBar = this.findViewById(R.id.ratingBar);
        this.btnUpdate = this.findViewById(R.id.button);
        this.btnAccept = this.findViewById(R.id.button2);
        this.btnDecline = this.findViewById(R.id.button3);

        this.txtDateUse = this.findViewById(R.id.txtDateUse);
        this.txtOpt1 = this.findViewById(R.id.txtOpt1);
        this.txtOpt2 = this.findViewById(R.id.txtOpt2);

        try {
            Bundle b = getIntent().getExtras();
            nwoid = b.getInt("nwoid");
            int userid = b.getInt("userid");
            int makerid  = b.getInt("makerid");
            ///////////////////////////////////
            ///////////////////////////////////
            ///////////////////////////////////
            ///////////////////////////////////
//            int postId  = b.getInt("postid");


            db = new UserDatabase(this);
            accList = db.getAccountInfo();
            searchList = db.getAllSearch();
            userList = db.getFindAccount(userid);

            orderList = db.getAllNewWorldOrderNwoId(nwoid);
            offerList = db.getAllOfferNwoId(nwoid);
            makerList = db.getAllMakerUserId(orderList.get(0).getMakerId());

            Picasso.with(this).load(userList.get(0).getProfilePic()).transform(new CircleTransform()).into(imgUser);
            this.imgRequest.setImageURI(orderList.get(0).getNwoPic());
            this.txtName.setText("Name: " +userList.get(0).getFirstname() +" "+ userList.get(0).getLastname());
            this.txtDate.setText("Date Posted: " +orderList.get(0).getNwoDate());
            this.txtGender.setText("Gender: " +userList.get(0).getGender());
            this.txtContact.setText("Contact: " +userList.get(0).getContactnum());
            this.txtEmail.setText("Email: " +userList.get(0).getEmailAdd());
            this.txtAddress.setText("Address: " +userList.get(0).getAddress());
            this.txtCategory.setText("Category: " +orderList.get(0).getNwoCategory());
            this.txtType.setText("Type: " +orderList.get(0).getNwoType());
            this.txtSize.setText("Size: " +orderList.get(0).getNwoSize());
            this.txtQuantity.setText("Quantity: " +orderList.get(0).getNwoQty());
//            this.txtColor.setText("Color: " +orderList.get(0).getNwoColor());
            this.txtColor.setBackgroundColor(Integer.parseInt(orderList.get(0).getNwoColor()));
            this.txtDetails.setText(orderList.get(0).getNwoDetails());
            this.txtStatus.setText("Status: " +orderList.get(0).getNwoStatus());
            this.txtOrderStatus.setText("Status: " + orderList.get(0).getNwoStatus());
            this.txtOrderMake.setText("Total No. of products to make: " + orderList.get(0).getNwoQty());


            this.txtDateUse.setText("Estimated Date of Use: " + orderList.get(0).getPostestdate());
            this.txtOpt1.setText("Option 1: " + orderList.get(0).getPostoptone());
            this.txtOpt2.setText("Option 2: " + orderList.get(0).getPostopttwo());


            Picasso.with(this).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(this.imgMaker);
            this.shopRatingBar.setRating(db.findTotalReviewMaker(makerList.get(0).getUserId()));
            this.txtShopName.setText("Name: " +makerList.get(0).getShopName());
            this.txtShopAddress.setText("Address: " +makerList.get(0).getShopAddress());

            if(!orderList.get(0).getNwoStatus().equals("REQUESTING ORDER")) {
                if(!offerList.isEmpty()) {
                    this.txtOfferComment.setText("Comment: " + offerList.get(0).getOfferComment());
                    this.txtOfferDate.setText("Date: " + offerList.get(0).getOfferDate());
                    this.txtOfferCost.setText("Price Per Piece: \u20B1" + offerList.get(0).getOfferPrice());
                }


                this.txtOrderMade.setText("Made: " + orderList.get(0).getNwoMade());
            }

            status = orderList.get(0).getNwoStatus();
//            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
            if(status.equals("REQUESTING ORDER")) {
//                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                this.btnAccept.setVisibility(View.VISIBLE);
                this.btnDecline.setVisibility(View.VISIBLE);
            } else {
//                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                this.btnAccept.setVisibility(View.INVISIBLE);
                this.btnDecline.setVisibility(View.INVISIBLE);
            }

            if(status.equals("REQUESTING ORDER") || status.equals("FAILED") || status.equals("FAILED, REFUNDED") || status.equals("COMPLETE")) {
                this.btnUpdate.setVisibility(View.INVISIBLE);
//                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                this.btnUpdate.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Log.d("err","err");
        }

        this.btnUpdate.setOnClickListener(this);
        this.btnAccept.setOnClickListener(this);
        this.btnDecline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        message = "Hello";
        number = userList.get(0).getContactnum();
        status = orderList.get(0).getNwoStatus();

        switch (v.getId()) {
            case R.id.button2:
                if(status.equals("REQUESTING ORDER")) {
                    stat = "ACCEPT";
                    dialogConfirmation();
                    message = "Your request have been accepted";
                }

                break;
            case R.id.button3:
                if(status.equals("REQUESTING ORDER")) {
                    stat = "DECLINE";
                    dialogConfirmation();
                    message = "Your request have been declined";
                }

                break;
            case R.id.button:
            //                Toast.makeText(this, "order update", Toast.LENGTH_SHORT).show();
                    dialog = new Dialog(MakerViewOrderActivity.this);
                    dialog.setContentView(R.layout.order_progress_layout);

                    cboStatus = dialog.findViewById(R.id.spinner);
                    txtMadeProd = dialog.findViewById(R.id.editText);
                    btnSave = dialog.findViewById(R.id.button);
                    btnCancel = dialog.findViewById(R.id.button2);

                    txtMadeProd.setText(orderList.get(0).getNwoMade());

                    txtMadeProd.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if(s != null) {
                                if(s.length() > 1) {
                                    if(s.toString().startsWith("0")) {
                                        s = s.toString().substring(1, s.toString().length());
                                        txtMadeProd.setText(s);
                                        txtMadeProd.setSelection(1);
                                    }
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(txtMadeProd.getText().length() == 0) {
                                txtMadeProd.setText("0");
                                txtMadeProd.setSelection(1);
                            }

                            if(Integer.parseInt(txtMadeProd.getText().toString()) > orderList.get(0).getNwoQty()) {
                                txtMadeProd.setText("" +orderList.get(0).getNwoQty());
                            }
                        }
                    });

                    switch (orderList.get(0).getNwoStatus().toLowerCase()) {
                        case "work-in-progress":
                            cboStatus.setSelection(1);
                            status = "WORK-IN-PROGRESS";

                            break;
                        case "work-in-hold":
                            cboStatus.setSelection(2);
                            status = "WORK-IN-HOLD";

                            break;
                        case "failed, partially refunded":
                            cboStatus.setSelection(3);
                            status = "FAILED, PARTIALLY REFUNDED";

                            break;
                        case "failed, refunded":
                            cboStatus.setSelection(4);
                            status = "FAILED, REFUNDED";

                            break;
                        case "failed":
                            cboStatus.setSelection(5);
                            status = "FAILED";

                            break;
                        case "complete, awaiting pickup":
                            cboStatus.setSelection(6);
                            status = "COMPLETE, AWAITING PICKUP";

                            break;
                        case "complete":
                            cboStatus.setSelection(7);
                            status = "COMPLETE";

                            break;
        }

        cboStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = cboStatus.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String made = txtMadeProd.getText().toString();
                String errMsg = "This field is empty";

                if(TextUtils.equals("", made)) {
                    txtMadeProd.requestFocus();
                    txtMadeProd.setError(errMsg);
                } else if(TextUtils.equals("", status) || TextUtils.equals("Select Status", status)) {
                    Toast.makeText(MakerViewOrderActivity.this, "Please select status", Toast.LENGTH_LONG).show();
                } else {
//                    makerList = db.findMakerId(orderList.get(0).getMakerId());
                    db.editNewWorldOrderStatus(orderList.get(0).getNwoId(), orderList.get(0).getUserId(), orderList.get(0).getMakerId(), made, status);
                    Toast.makeText(MakerViewOrderActivity.this, "Order Successfully Updated", Toast.LENGTH_LONG).show();

                    String date = DateFormat.getDateInstance().format(new Date());
                    String message = "";
                    switch (status) {
                        case "DONE":
                            message = "Your order is done.\nPlease contact this shop number to discuss where to claim the order.\nShop Phone #: " +makerList.get(0).getShopContact();

                            break;

                        default:
                            message = "Your order have been updated";
                    }

                    db.addNotification(userList.get(0).getUserId(), orderList.get(0).getMakerId(), orderList.get(0).getNwoQty(), date, "Order", message);

                    dialog.dismiss();
                    MakerViewOrderActivity.this.finish();
                    startActivity(MakerViewOrderActivity.this.getIntent());
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                dialog.dismiss();
            }
        });

        dialog.show();

                break;
        }
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch(Exception e) {
            Log.d("CUSTOM PRO ERR: ", e.getMessage());
        }
    }

    public void dialogConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to " +stat+ " this order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(stat.equals("DECLINE")) {
//                    Toast.makeText(getApplicationContext(), "Request Declined", Toast.LENGTH_LONG).show();
                    btnAccept.setVisibility(View.INVISIBLE);
                    btnDecline.setVisibility(View.INVISIBLE);

                    alerdialog.dismiss();

                    String date = DateFormat.getDateInstance().format(new Date());
                    db.addNotification(userList.get(0).getUserId(), makerList.get(0).getUserId(), nwoid, date, "Request", message);

                    db.editNewWorldOrderStatus(orderList.get(0).getNwoId(),
                            orderList.get(0).getUserId(),
                            orderList.get(0).getMakerId(),
                            orderList.get(0).getNwoMade(),
                            "FAILED"
                    );

//                    MakerViewOrderActivity.this.finish();
//                    MakerViewOrderActivity.this.startActivity(MakerViewOrderActivity.this.getIntent());
                } else if(stat.equals("ACCEPT")) {
                    alerdialog.dismiss();

//                    Toast.makeText(getApplicationContext(), "Request Accepted", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(MakerViewOrderActivity.this, NewWorldOfferRequestActivity.class);
//                    intent2.putExtra("nwoid", nwoid);
                    startActivityForResult(intent2, 100);
                }

                ///****************************************
                ///****************************************
                ///****************************************
                ///****************************************
//                String date = DateFormat.getDateInstance().format(new Date());
//                db.addNotification(userList.get(0).getUserId(), makerList.get(0).getUserId(), orderList.get(0).getNwoId(), date, "Request", message);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alerdialog.dismiss();
            }
        });
        builder.setCancelable(false);

        alerdialog = builder.create();
        alerdialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 100) {
                Bundle b = data.getExtras();
                String price = b.getString("price");
                String date = b.getString("date");
                String comment = b.getString("comment");

                db.editNewWorldOrderOffer(nwoid, date, "ACCEPTED, AWAITING PAYMENT");

                db.addPostOffer(nwoid,
                        orderList.get(0).getUserId(),
                        6969,
                        orderList.get(0).getMakerId(),
                        makerList.get(0).getShopPic().toString(),
                        makerList.get(0).getShopName(),
                        price,
                        date,
                        comment,
                        "");
                String datenow = DateFormat.getDateInstance().format(new Date());
                db.addNotification(userList.get(0).getUserId(), makerList.get(0).getUserId(), nwoid, datenow, "Request", message);

                this.finish();
                this.startActivity(this.getIntent());

//                db.addNewWorldOrder(orderList.get(0).getUserId(),
//                        orderList.get(0).getMakerId(),
//                        orderList.get(0).getNwoQty(),
//                        orderList.get(0).getNwoPic().toString(),
//                        orderList.get(0).getNwoDetails(),
//                        orderList.get(0).getNwoCategory(),
//                        orderList.get(0).getNwoType(),
//                        orderList.get(0).getNwoSize(),
//                        orderList.get(0).getNwoColor(),
//                        orderList.get(0).getNwoDate(),
//                        date,
//                        orderList.get(0).getNwoMade(),
//                        orderList.get(0).getNwoStatus());
//                Toast.makeText(this, price+ ", " +date+ "" +comment, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
















