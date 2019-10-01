package com.example.asus.custompro.shop.nwo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.asus.custompro.shop.review.Review;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SeekerViewOrderActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtName, txtDate, txtGender, txtContact, txtEmail, txtAddress, txtCategory, txtDetails,
            txtStatus, txtQuantity, txtShopName, txtShopAddress, txtType, txtSize, txtColor,
            txtOfferComment, txtOfferDate, txtOfferCost, txtOrderStatus, txtOrderMade, txtOrderMake;
    Button btnCancelOrder;
    ImageView imgUser, imgRequest, imgMaker;
    RatingBar shopRatingBar;
    Button btnReview;

//    ArrayList<Request> requestList = new ArrayList<>();
//    ArrayList<Review> reviewList = new ArrayList<>();
//    ArrayList<Search> searchList = new ArrayList<>();
//    ArrayList<Account> userList = new ArrayList<>();
//    ArrayList<Account> accList = new ArrayList<>();
//    ArrayList<Maker> makerList = new ArrayList<>();

    //    ArrayList<Post> postList = new ArrayList<>();
    ArrayList<NewWorldOrder> orderList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Account> accList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();
    ArrayList<Offer> offerList = new ArrayList<>();
    ArrayList<Search> searchList = new ArrayList<>();


    TextView txtDateUse, txtOpt1, txtOpt2;


    ArrayList<Review> reviewList = new ArrayList<>();

    UserDatabase db;

    Button btnSave, btnCancel;
    Spinner cboStatus;
    EditText txtMadeProd;
    Dialog dialog;
    RatingBar ratingBar;
    EditText txtComment;
    String status = "";

    AlertDialog alertdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_view_order);

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
        this.btnCancelOrder = this.findViewById(R.id.button);
        this.btnReview = this.findViewById(R.id.button2);

        this.txtDateUse = this.findViewById(R.id.txtDateUse);
        this.txtOpt1 = this.findViewById(R.id.txtOpt1);
        this.txtOpt2 = this.findViewById(R.id.txtOpt2);

        try {
            Bundle b = getIntent().getExtras();
            int nwoid = b.getInt("nwoid");
            int userid = b.getInt("userid");
            int makerid  = b.getInt("makerid");


            db = new UserDatabase(this);
            accList = db.getAccountInfo();
            searchList = db.getAllSearch();
            userList = db.getFindAccount(userid);

            orderList = db.getAllNewWorldOrderNwoId(nwoid);
            offerList = db.getAllOfferNwoId(nwoid);
            makerList = db.getAllMakerUserId(orderList.get(0).getMakerId());

            ///////////////////////////////////
            ///////////////////////////////////
            ///////////////////////////////////
            ///////////////////////////////////
            reviewList = db.findReviewRequest(orderList.get(0).getNwoId());

            status = orderList.get(0).getNwoStatus();

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


//            if(!orderList.get(0).getNwoStatus().equals("PENDING")) {
//                this.txtOfferComment.setText("Comment: " + offerList.get(0).getOfferComment());
//                this.txtOfferDate.setText("Date: " + offerList.get(0).getOfferDate());
//                this.txtOfferCost.setText("Total Cost: \u20B1" + offerList.get(0).getOfferPrice());
//
//
//                this.txtOrderStatus.setText("Status: " + orderList.get(0).getNwoStatus());
//                this.txtOrderMake.setText("Total No. of products to make: " + orderList.get(0).getNwoQty());
//                this.txtOrderMade.setText("Made: " + orderList.get(0).getNwoMade());
//            }

            if(!orderList.get(0).getNwoStatus().equals("REQUESTING ORDER")) {
                this.txtOfferComment.setText("Comment: " + offerList.get(0).getOfferComment());
                this.txtOfferDate.setText("Date: " + offerList.get(0).getOfferDate());
                this.txtOfferCost.setText("Price Per Piece: \u20B1" + offerList.get(0).getOfferPrice());


                this.txtOrderMade.setText("Made: " + orderList.get(0).getNwoMade());
            }

            Picasso.with(this).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(this.imgMaker);
//            this.shopRatingBar.setRating(makerList.get(0).getShopRating());
            this.shopRatingBar.setRating(db.findTotalReviewMaker(makerList.get(0).getUserId()));
            this.txtShopName.setText("Name: " +makerList.get(0).getShopName());
            this.txtShopAddress.setText("Address: " +makerList.get(0).getShopAddress());
        } catch (Exception e) {
            Log.d("err","err");
        }

//        if(status.equals("ACCEPTED, AWAITING PAYMENT") || status.equals("FAILED") || status.equals("FAILED, REFUNDED") || status.equals("COMPLETE")) {
//            this.btnCancelOrder.setVisibility(View.INVISIBLE);
//        } else {
//            this.btnCancelOrder.setVisibility(View.VISIBLE);
//        }

//        WORK-IN-PROGRESS
//        WORK-ON-HOLD
//        Toast.makeText(this, "stat" +status, Toast.LENGTH_SHORT).show();
        if(status.equals("REQUESTING ORDER") || status.equals("ACCEPTED, AWAITING PAYMENT") || status.equals("WORK-IN-PROGRESS") || status.equals("WORK-ON-HOLD") ) {
            this.btnCancelOrder.setVisibility(View.VISIBLE);
        } else {
            this.btnCancelOrder.setVisibility(View.INVISIBLE);
        }

        this.btnCancelOrder.setOnClickListener(this);
        this.btnReview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Cancel Order Confirmation");
                builder.setMessage("Are you sure you want to cancel your order?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.editNewWorldOrderStatus(orderList.get(0).getNwoId(),
                                orderList.get(0).getUserId(),
                                orderList.get(0).getMakerId(),
                                orderList.get(0).getNwoMade(),
                                "FAILED"
                        );

                        Toast.makeText(getApplicationContext(), "Your order has been cancelled", Toast.LENGTH_SHORT).show();
                        alertdialog.dismiss();

                        SeekerViewOrderActivity.this.finish();
                        SeekerViewOrderActivity.this.startActivity(getIntent());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertdialog.dismiss();
                    }
                });

                alertdialog = builder.create();
                alertdialog.show();

                break;
            case R.id.button2:
                dialog = new Dialog(SeekerViewOrderActivity.this);
                dialog.setContentView(R.layout.review_layout);

                TextView txtTitle = dialog.findViewById(R.id.textView);
                ImageView iv = dialog.findViewById(R.id.imageView);
                ratingBar = dialog.findViewById(R.id.ratingBar);
                txtComment = dialog.findViewById(R.id.textView2);
                Button btnReviewSave = dialog.findViewById(R.id.button);
                Button btnReviewCancel = dialog.findViewById(R.id.button2);

                txtTitle.setText("Review " +makerList.get(0).getShopName());
                Picasso.with(getApplicationContext()).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(iv);

                if(!reviewList.isEmpty()) {
                    if(orderList.get(0).getNwoId() == reviewList.get(0).getRequestId()) {
                        ratingBar.setRating(reviewList.get(0).getRating());
                        txtComment.setText(reviewList.get(0).getComment());
                    }
                }

                btnReviewSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float rating = ratingBar.getRating();
                        String comment = txtComment.getText().toString();
                        String date = DateFormat.getDateInstance().format(new Date());

                        if(!reviewList.isEmpty() && orderList.get(0).getNwoId() == reviewList.get(0).getRequestId()) {
                            db.editReview(orderList.get(0).getNwoId(), rating, comment);
                            Toast.makeText(getApplicationContext(), "Review Successfully Edited", Toast.LENGTH_LONG).show();
                        } else {
                            db.addReview(orderList.get(0).getNwoId(), userList.get(0).getUserId(), orderList.get(0).getMakerId(), userList.get(0).getProfilePic().toString(), userList.get(0).getFirstname() + " " + userList.get(0).getLastname(), date, rating, comment);
                            Toast.makeText(getApplicationContext(), "Review Successfully Added", Toast.LENGTH_LONG).show();
                        }

                        /////////////////////////
                        try {
                            reviewList = db.findReviewRequest(orderList.get(0).getNwoId());
                            float totalRating = db.findTotalReviewMaker(makerList.get(0).getUserId());
                            db.editMaker(makerList.get(0).getUserId(), (totalRating/reviewList.size()));
                            ratingBar.setRating(totalRating/reviewList.size());
                        } catch (Exception e) {
                            Log.d("err=", e.getMessage());
                        }

                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });

                btnReviewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
//                Toast.makeText(this, "Cancel order under construction", Toast.LENGTH_SHORT).show();

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
}
