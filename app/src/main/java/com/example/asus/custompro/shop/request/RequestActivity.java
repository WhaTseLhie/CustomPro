package com.example.asus.custompro.shop.request;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.Search;
import com.example.asus.custompro.home.search.shops.Maker;
import com.example.asus.custompro.myrequest.Request;
import com.example.asus.custompro.shop.review.Review;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtName, txtDate, txtGender, txtContact, txtEmail, txtAddress, txtCategory, txtDetails, txtStatus, txtQuantity, txtShopName, txtShopAddress, txtType, txtSize, txtColor;
    Button btnSave, btnCancel, btnReview;
    ImageView imgUser, imgRequest, imgMaker;
    RatingBar shopRatingBar;
    RadioGroup rdoStatus;

    //ArrayList<Product> productList = new ArrayList<>();
    ArrayList<Request> requestList = new ArrayList<>();
    ArrayList<Review> reviewList = new ArrayList<>();
    ArrayList<Search> searchList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Account> accList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();

    UserDatabase db;
    String option;
    String status;

    RatingBar ratingBar;
    EditText txtComment;
    Dialog dialog;

    String message, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        this.imgUser = this.findViewById(R.id.imageView);
        this.imgRequest = this.findViewById(R.id.imageView2);
        this.imgMaker = this.findViewById(R.id.imageView3);
        this.txtName = this.findViewById(R.id.textView);
        this.txtDate = this.findViewById(R.id.textView2);
        this.txtGender = this.findViewById(R.id.textView3);
        this.txtContact = this.findViewById(R.id.textView4);
        this.txtEmail = this.findViewById(R.id.textView5);
        this.txtAddress = this.findViewById(R.id.textView6);
        this.txtCategory = this.findViewById(R.id.textView7);
        this.txtDetails = this.findViewById(R.id.textView8);
        this.txtStatus = this.findViewById(R.id.textView9);
        this.txtQuantity = this.findViewById(R.id.textView10);
        this.txtShopName = this.findViewById(R.id.textView12);
        this.txtShopAddress = this.findViewById(R.id.textView13);
        this.txtType = this.findViewById(R.id.textView20);
        this.txtSize = this.findViewById(R.id.textView21);
        this.txtColor = this.findViewById(R.id.textView31);
        this.shopRatingBar = this.findViewById(R.id.ratingBar);
        this.rdoStatus = this.findViewById(R.id.radioGroup);
        this.btnReview = this.findViewById(R.id.button3);
        this.btnSave = this.findViewById(R.id.button);
        this.btnCancel = this.findViewById(R.id.button2);

        try {
            Bundle b = getIntent().getExtras();
            int userid = b.getInt("userid");
            int requestid = b.getInt("requestid");

            db = new UserDatabase(this);
            accList = db.getAccountInfo();
            userList = db.getFindAccount(userid);
            requestList = db.getRequest(requestid);
            searchList = db.getAllSearch();
            //productList = db.getMakerProduct(requestList.get(0).getMakerId());
            makerList = db.findMaker(requestList.get(0).getMakerId());
            reviewList = db.findReviewRequest(requestList.get(0).getRequestId());
            //reviewList = db.findReviewMaker(requestList.get(0).getMakerId());
            status = requestList.get(0).getRequestStatus();

            Picasso.with(this).load(userList.get(0).getProfilePic()).transform(new CircleTransform()).into(this.imgUser);
            this.imgRequest.setImageURI(requestList.get(0).getRequestPic());
            this.txtName.setText("Name: " +userList.get(0).getFirstname() +" "+ userList.get(0).getLastname());
            this.txtDate.setText("Date Posted: " +requestList.get(0).getRequestedDate());
            this.txtGender.setText("Gender: " +userList.get(0).getGender());
            this.txtContact.setText("Contact: " +userList.get(0).getContactnum());
            this.txtEmail.setText("Email: " +userList.get(0).getEmailAdd());
            this.txtAddress.setText("Address: " +userList.get(0).getAddress());
            this.txtCategory.setText("Category: " +requestList.get(0).getRequestCategory());
            this.txtType.setText("Type: " +requestList.get(0).getRequestType());
            this.txtSize.setText("Size: " +requestList.get(0).getRequestSize());
            this.txtQuantity.setText("Quantity: " +requestList.get(0).getRequestedQty());
            this.txtColor.setText("Size: " +requestList.get(0).getRequestColor());
            this.txtDetails.setText(requestList.get(0).getRequestDetails());
            this.txtStatus.setText("Status: " +status);

            Picasso.with(this).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(this.imgMaker);
            this.shopRatingBar.setRating(makerList.get(0).getShopRating());
            this.txtShopName.setText("Name: " +makerList.get(0).getShopName());
            this.txtShopAddress.setText("Address: " +makerList.get(0).getShopAddress());

            if(makerList.get(0).getUserId() == reviewList.get(0).getMakerId()) {
                btnReview.setText("EDIT REVIEW");
            }
        } catch(Exception e) {
            Log.d("REQUEST ACTIVITY ERR: ", e.getMessage());
        }

        if(!accList.isEmpty() && requestList.get(0).getUserId() == accList.get(0).getUserId()) {
            this.btnSave.setText("CONFIRM");
            this.rdoStatus.setVisibility(View.INVISIBLE);
            this.btnReview.setVisibility(View.VISIBLE);
            this.btnReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog = new Dialog(RequestActivity.this);
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
                        if(requestList.get(0).getRequestId() == reviewList.get(0).getRequestId()) {
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

                            if(!reviewList.isEmpty() && requestList.get(0).getRequestId() == reviewList.get(0).getRequestId()) {
                                db.editReview(requestList.get(0).getRequestId(), rating, comment);
                                Toast.makeText(getApplicationContext(), "Review Successfully Edited", Toast.LENGTH_LONG).show();
                            } else {
                                db.addReview(requestList.get(0).getRequestId(), userList.get(0).getUserId(), requestList.get(0).getMakerId(), userList.get(0).getProfilePic().toString(), userList.get(0).getFirstname() + " " + userList.get(0).getLastname(), date, rating, comment);
                                Toast.makeText(getApplicationContext(), "Review Successfully Added", Toast.LENGTH_LONG).show();
                            }

                            /////////////////////////
                            try {
                                reviewList = db.findReviewRequest(requestList.get(0).getRequestId());
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
                }
            });
        } else if(accList.isEmpty() || requestList.get(0).getUserId() != accList.get(0).getUserId() && accList.get(0).getUserId() != requestList.get(0).getMakerId()) {
            this.btnSave.setText("CONFIRM");
            this.rdoStatus.setVisibility(View.INVISIBLE);
            this.btnReview.setVisibility(View.INVISIBLE);
        } else {
            btnReview.setVisibility(View.INVISIBLE);

            switch(status) {
                case "PENDING":
                    this.btnSave.setText("SAVE");
                    this.rdoStatus.setVisibility(View.VISIBLE);

                    break;
                case "PROGRESS":
                    this.btnSave.setText("DONE");
                    this.rdoStatus.setVisibility(View.INVISIBLE);

                    break;
                case "DONE":
                    this.btnSave.setText("CONFIRM");
                    this.rdoStatus.setVisibility(View.INVISIBLE);

                    break;
                case "DECLINE":
                    this.btnSave.setText("CONFIRM");
                    this.rdoStatus.setVisibility(View.INVISIBLE);

                    break;
            }
        }

        this.btnSave.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                message = "Hello";
                number = userList.get(0).getContactnum();

                if(!accList.isEmpty() && searchList.get(0).getUserid() == accList.get(0).getUserId() || searchList.get(0).getType().equals("maker")) {
                    if (status.equals("PENDING") || status.equals("PROGRESS")) {
                        int selectedOption = rdoStatus.getCheckedRadioButtonId();
                        RadioButton selectedButton = findViewById(selectedOption);

                        if (selectedButton != null) {
                            option = selectedButton.getText().toString();

                            if (option.equals("Decline")) {
                                db.editRequest(requestList.get(0).getRequestId(), "DECLINE");
                                Toast.makeText(getApplicationContext(), "Request Declined", Toast.LENGTH_LONG).show();
                                message = "Your request have been declined";
                            } else if (option.equals("Accept")) {
                                db.editRequest(requestList.get(0).getRequestId(), "PROGRESS");
                                Toast.makeText(getApplicationContext(), "Request Accepted", Toast.LENGTH_LONG).show();
                                message = "Your request have been accepted";
                            }
                        }

                        if (status.equals("PROGRESS")) {
                            db.editRequest(requestList.get(0).getRequestId(), "DONE");
                            Toast.makeText(getApplicationContext(), "Request Done", Toast.LENGTH_LONG).show();
                            message = "Your request is done";
                        }

                        String date = DateFormat.getDateInstance().format(new Date());
                        db.addNotification(userList.get(0).getUserId(), makerList.get(0).getUserId(), requestList.get(0).getRequestId(), date, "Request", message);

                        /*if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{

                                        Manifest.permission.SEND_SMS}, 100);
                            }
                        } else {
                            SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
                        }*/
                    }
                }

                this.finish();

                break;
            case R.id.button2:
                this.finish();

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 100:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    final Dialog dialog2 = new Dialog(this);
                    dialog2.setContentView(R.layout.permission_layout);

                    TextView t1 = (TextView) dialog2.findViewById(R.id.textView6);
                    TextView t2 = (TextView) dialog2.findViewById(R.id.textView7);
                    t1.setText("Request Permissions");
                    t2.setText("Please allow permissions if you want this application to perform the task.");

                    Button dialogButton = (Button) dialog2.findViewById(R.id.button4);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                        }
                    });

                    dialog2.show();
                } else {
                    SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
                }

                break;
        }
    }
}