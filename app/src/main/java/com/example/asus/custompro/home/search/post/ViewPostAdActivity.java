package com.example.asus.custompro.home.search.post;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.shops.Maker;
import com.example.asus.custompro.shop.OpenShopActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPostAdActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtName, txtDate, txtContact, txtEmail, txtAddress, txtDetails;
    ImageView imgUser, imgRequest;
    RatingBar ratingBar;
    Button btnVisit;

    ListView lv;
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Account> accList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();
    ArrayList<Post> postList = new ArrayList<>();
    UserDatabase db;
    int userId, postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post_ad);

        this.imgUser = this.findViewById(R.id.imageView);
        this.imgRequest = this.findViewById(R.id.imageView2);
        this.txtName = this.findViewById(R.id.textView);
        this.txtDate = this.findViewById(R.id.textView2);
        this.txtContact = this.findViewById(R.id.textView4);
        this.txtEmail = this.findViewById(R.id.textView5);
        this.txtAddress = this.findViewById(R.id.textView6);
        this.txtDetails = this.findViewById(R.id.textView8);
        this.ratingBar = this.findViewById(R.id.ratingBar);
        this.btnVisit = this.findViewById(R.id.button);
        this.lv = this.findViewById(R.id.listView);

        try {
            Bundle b = getIntent().getExtras();
            userId = b.getInt("userid");
            postId = b.getInt("postid");

            db = new UserDatabase(this);
            accList = db.getAccountInfo();
            userList = db.getFindAccount(userId);
            postList = db.getUserPostId(postId);

            makerList = db.findMaker(userId);

//            if(!accList.isEmpty()) {
//                makerList = db.findMaker(accList.get(0).getUserId());
//            }

            this.setTitle(makerList.get(0).getShopName()+ "'s Advertisement");
            Picasso.with(this).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(imgUser);
            this.imgRequest.setImageURI(postList.get(0).getPostPic());
            this.txtName.setText("Name: " +makerList.get(0).getShopName());
            this.txtDate.setText("Date Posted: " +postList.get(0).getPostDate());
            this.txtContact.setText("Contact: " +makerList.get(0).getShopContact());
            this.txtEmail.setText("Email: " +makerList.get(0).getShopEmail());
            this.txtAddress.setText("Address: " +makerList.get(0).getShopAddress());
            this.txtDetails.setText(postList.get(0).getPostDetails());
            this.ratingBar.setRating(makerList.get(0).getShopRating());
        } catch(Exception e) {
            Log.d("POST ACTIVITY ERR: ", e.getMessage());
        }

        this.btnVisit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int userid = makerList.get(0).getUserId();

        Intent intent = new Intent(this, OpenShopActivity.class);
        try {
            db.deleteAllSearch();
        } catch(Exception e) {
            Log.d("CUSTOM PRO ERR: ", e.getMessage());
        }

        db.addSearch(userid, "seeker");
        startActivity(intent);
    }
}
