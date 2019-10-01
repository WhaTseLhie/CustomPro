package com.example.asus.custompro.home.search.post;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.post.bids.offer.Offer;
import com.example.asus.custompro.home.search.shops.Maker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewOfferActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    Button btnSetMaker, btnBack;
    TextView txtShopName, txtShopAddress, txtComment, txtDate, txtPrice;

    UserDatabase db;
    ArrayList<Offer> offerList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();
    ArrayList<Account> accList = new ArrayList<>();
    ArrayList<Post> postList = new ArrayList<>();
    int postId, shopId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer);

        this.db = new UserDatabase(this);
        this.iv = this.findViewById(R.id.imageView);
        this.txtShopName = this.findViewById(R.id.textView);
        this.txtShopAddress = this.findViewById(R.id.textView2);
        this.txtComment = this.findViewById(R.id.textView3);
        this.txtDate = this.findViewById(R.id.textView4);
        this.txtPrice = this.findViewById(R.id.textView5);
        this.btnSetMaker = this.findViewById(R.id.button);
        this.btnBack = this.findViewById(R.id.button2);

        try {
            Bundle b = getIntent().getExtras();
            postId = b.getInt("postid");
            shopId = b.getInt("shopid");
            userId = b.getInt("userid");

            offerList = db.getPostOffer(postId, shopId);
            accList = db.getAccountInfo();
            makerList = db.findMaker(shopId);
            postList = db.getUserPostId(postId);

            Picasso.with(this).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(this.iv);
            this.txtShopName.setText("Shop Name: " +makerList.get(0).getShopName());
            this.txtShopAddress.setText("Shop Address: " +makerList.get(0).getShopAddress());
            this.txtComment.setText(offerList.get(0).getOfferComment());
            this.txtDate.setText(offerList.get(0).getOfferDate());
        } catch (Exception e) {
            Log.d("err: ", e.getMessage());
        }

        try {
            this.txtPrice.setText("\u20B1" +Double.parseDouble(offerList.get(0).getOfferPrice()));
        } catch (Exception e) {
//            this.txtPrice.setText("\u20B1" +offerList.get(0).getOfferPrice());
            Log.d("err=", e.getMessage());
        }

        if(db.isSet(postId)) {
            this.btnSetMaker.setVisibility(View.GONE);
            this.btnBack.setVisibility(View.GONE);
        } else {
            this.btnSetMaker.setVisibility(View.VISIBLE);
            this.btnBack.setVisibility(View.VISIBLE);
        }

        if(!offerList.isEmpty()) {
            if (offerList.get(0).getOfferStatus().equals("SELECTED")) {
                this.btnSetMaker.setVisibility(View.GONE);
                this.btnBack.setVisibility(View.GONE);
            }
        }

        this.btnSetMaker.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                String orderEndDate = DateFormat.getDateInstance().format(new Date());
//                db.addOrder(accList.get(0).getUserId(), postId, shopId, orderDate, "0", "ON-HOLD");
                long res = db.addNewWorldOrder(
                        accList.get(0).getUserId(),
                        makerList.get(0).getUserId(),
                        postList.get(0).getPostQty(),
                        postList.get(0).getPostPic().toString(),
                        postList.get(0).getPostDetails(),
                        postList.get(0).getPostCategory(),
                        postList.get(0).getPostType(),
                        postList.get(0).getPostSize(),
                        postList.get(0).getPostColor(),
                        postList.get(0).getPostDate(),
                        orderEndDate,
                        "0",
                        "ACCEPTED, AWAITING PAYMENT",
                        postList.get(0).getPostestdate(),
                        postList.get(0).getPostoptone(),
                        postList.get(0).getPostopttwo());

                if(res != -1) {
                    Toast.makeText(this, "You successfully selected a maker", Toast.LENGTH_LONG).show();

                    String date = DateFormat.getDateInstance().format(new Date());
                    String message = "Your have been select to be the maker";
                    String restr = res+"";
//                    Toast.makeText(this, "restr=" +restr, Toast.LENGTH_SHORT).show();
                    db.editPostOffer(Integer.parseInt(restr), userId, postId, shopId, offerList.get(0).getOfferComment(), offerList.get(0).getOfferDate(), offerList.get(0).getOfferPrice(), "SELECTED");
                    db.addNotification(makerList.get(0).getUserId(), shopId, postId, date, "Order", message);
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            case R.id.button2:
                //db.deleteAllOrder();
                this.finish();

                break;
        }
    }
}