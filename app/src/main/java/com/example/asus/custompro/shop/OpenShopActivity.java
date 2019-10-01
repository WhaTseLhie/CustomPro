package com.example.asus.custompro.shop;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.asus.custompro.shop.about.AboutShopFragment;
import com.example.asus.custompro.home.search.shops.Maker;
import com.example.asus.custompro.shop.nwo.NewWorldOrderFragment;
import com.example.asus.custompro.shop.order.OrderFragment;
import com.example.asus.custompro.shop.product.SampleProductFragment;
import com.example.asus.custompro.shop.request.RequestFragment;
import com.example.asus.custompro.shop.review.Review;
import com.example.asus.custompro.shop.review.ReviewsFragment;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OpenShopActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;

    ImageView iv;
    TextView txtShopName, txtShopAddress, txtSpecialty, txtShopReviews;
    RatingBar ratingBar;

    UserDatabase db;
    ArrayList<Account> accList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();
    ArrayList<Search> searchList = new ArrayList<>();
    ArrayList<Review> reviewList = new ArrayList<>();

    Intent sendSMS, call;

    int amount = 0;
    Dialog dialog3;
    Spinner cboSub;
    boolean isExpired;
    String subscription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop);

        this.db = new UserDatabase(this);

        this.searchList = db.getAllSearch();
        this.makerList = db.findMaker(searchList.get(0).getUserid());
        this.reviewList = db.findReviewMaker(searchList.get(0).getUserid());
        this.accList = db.getAccountInfo();

        if(searchList.get(0).getUserid() == accList.get(0).getUserId()) {
            this.setTitle("My Shop");
        } else {
            this.setTitle("View Shop");
        }

        this.iv = this.findViewById(R.id.imageView);
        this.txtShopName = this.findViewById(R.id.textView);
        this.txtShopAddress = this.findViewById(R.id.textView2);
        this.txtSpecialty = this.findViewById(R.id.textView4);
        this.ratingBar = this.findViewById(R.id.ratingBar);
        this.txtShopReviews = this.findViewById(R.id.textView3);

        if(!makerList.isEmpty()) {
            String strCurrentDate = DateFormat.getDateInstance().format(new Date());
            String strExpiryDate = makerList.get(0).getShopSubscription();
            DateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
            DateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            Date currentDate = null;
            Date expiryDate = null;
            isExpired = false;

            try {
                currentDate = sdf2.parse(strCurrentDate);
                expiryDate = sdf.parse(strExpiryDate);
                isExpired = currentDate.after(expiryDate);
            } catch (Exception e) {
                e.printStackTrace();
//                Toast.makeText(this, "err: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if(makerList.isEmpty() && searchList.get(0).getType().equals("maker")) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Message");
            alertBuilder.setMessage("You need to register to view your shop");
            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(OpenShopActivity.this, ShopRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        } else if(accList.get(0).getUserId() == searchList.get(0).getUserid() && isExpired) {
            dialog3 = new Dialog(this);
            dialog3.setContentView(R.layout.subscription_layout);
            dialog3.setCancelable(false);

            cboSub = dialog3.findViewById(R.id.spinner);
            TextView txtExpired = dialog3.findViewById(R.id.textView);
            Button btnSave = dialog3.findViewById(R.id.button);
            Button btnCancel = dialog3.findViewById(R.id.button2);
            subscription = cboSub.getItemAtPosition(0).toString();

            txtExpired.setText("Renew Subscription");
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, this.getResources().getStringArray(R.array.exSubscription));
            cboSub.setAdapter(spinnerAdapter);

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

                        switch(subscription) {
                            case "\u20B1 99/30 Days": amount = 60;
                                break;
                            case "\u20B1 199/2 Months": amount = 90;
                                break;
                            case "\u20B1 499/6 Months": amount = 210;
                                break;
                            case "\u20B1 999/1 Year": amount = 365;
                                break;
                        }

                        c.add(Calendar.DAY_OF_MONTH, amount);
                        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                        subscription = sdf2.format(c.getTime());

                        db.editMakerSub(makerList.get(0).getUserId(), subscription);
                        Toast.makeText(OpenShopActivity.this, "You added " +amount+ " days subscription", Toast.LENGTH_LONG).show();
                        dialog3.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(OpenShopActivity.this);
                        builder.setTitle("CustomPro Message");
                        builder.setMessage("Expiration Date: " +subscription);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(getIntent());
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            dialog3.show();
        } else {
            Picasso.with(this).load(makerList.get(0).getShopPic()).transform(new CircleTransform()).into(iv);
            this.txtShopName.setText(makerList.get(0).getShopName());
            this.txtSpecialty.setText(makerList.get(0).getShopSpecialty());
            this.txtShopAddress.setText(makerList.get(0).getShopAddress());

            if(reviewList.isEmpty()) {
                this.txtShopReviews.setText("0 Reviews");
            } else {
                float totalRating = db.findTotalReviewMaker(makerList.get(0).getUserId());
                db.editMaker(makerList.get(0).getUserId(), (totalRating/reviewList.size()));
                this.txtShopReviews.setText(reviewList.size() + " Reviews");
                this.ratingBar.setRating(totalRating/reviewList.size());
            }
        }

        this.txtShopReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.viewPager = this.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        this.tabLayout = this.findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(viewPager);
    }

    public void sendMessage() {
        sendSMS = new Intent(Intent.ACTION_VIEW);
        sendSMS.putExtra("address", makerList.get(0).getShopContact());
        sendSMS.putExtra("sms_body","");
        sendSMS.setType("vnd.android-dir/mms-sms");

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.SEND_SMS}, 20);
            }
        } else {
            this.startActivity(sendSMS);
        }
    }

    public void callPhone() {
        String phone = makerList.get(0).getShopContact();
        Uri uriPhone = Uri.parse("tel:" +phone);
        call = new Intent(Intent.ACTION_CALL, uriPhone);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{

                        Manifest.permission.CALL_PHONE}, 10);
            }
        } else {
            this.startActivity(call);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 10:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                    this.startActivity(call);
                }

                break;
            case 20:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    final Dialog dialog1 = new Dialog(this);
                    dialog1.setContentView(R.layout.permission_layout);

                    TextView t1 = (TextView) dialog1.findViewById(R.id.textView6);
                    TextView t2 = (TextView) dialog1.findViewById(R.id.textView7);
                    t1.setText("Request Permissions");
                    t2.setText("Please allow permissions if you want this application to perform the task.");

                    Button dialogButton = (Button) dialog1.findViewById(R.id.button4);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });

                    dialog1.show();
                } else {
                    this.startActivity(sendSMS);
                }

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(searchList.get(0).getUserid() == accList.get(0).getUserId()) {
            getMenuInflater().inflate(R.menu.advertisement_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_open_shop, menu);
        }
        
//        getMenuInflater().inflate(R.menu.menu_open_shop, menu);
        
//        if(searchList.get(0).getUserid() == accList.get(0).getUserId()) {            
//            menu.findItem(R.id.call).setVisible(false);
//            menu.findItem(R.id.text).setVisible(false);
//        } else {
////            menu.findItem(R.id.text).setVisible(true);
////            getMenuInflater().inflate(R.menu.advertisement_menu, menu);
////
////            menu.findItem(R.id.advertisement).setVisible(false);
//        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        
        if(id == R.id.call) {
            callPhone();
        } else if(id == R.id.text) {
            sendMessage();
        } else if(id == R.id.advertisement) {
            Intent adIntent = new Intent(this, AddAdvertisementActivity.class);
            startActivity(adIntent);
//            Toast.makeText(this, "Advertisement", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new SampleProductFragment(), "Products");
//        adapter.addFragment(new RequestFragment(), "Orders");
        adapter.addFragment(new NewWorldOrderFragment(), "Orders");
//        adapter.addFragment(new RequestFragment(), "Request");
//        adapter.addFragment(new OrderFragment(), "Orders");
        adapter.addFragment(new ReviewsFragment(), "Reviews");
        adapter.addFragment(new AboutShopFragment(), "About");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}