package com.example.asus.custompro.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.post.AddPostActivity;
import com.example.asus.custompro.mynwo.NewWorldOrderActivity;
import com.example.asus.custompro.mypost.MyPostActivity;
import com.example.asus.custompro.mynotification.NotificationActivity;
import com.example.asus.custompro.myorder.MyOrderActivity;
import com.example.asus.custompro.mywallet.WalletActivity;
import com.example.asus.custompro.profile.ProfileActivity;
import com.example.asus.custompro.settings.AccountSettingsActivity;
import com.example.asus.custompro.shop.OpenShopActivity;
import com.example.asus.custompro.home.search.shops.ShopFragment;
import com.example.asus.custompro.home.search.post.PostFragment;
import com.example.asus.custompro.myrequest.MyRequestActivity;
import com.example.asus.custompro.shop.nwo.NewWorldOrder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomPro extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {

    UserDatabase db;
    ArrayList<Account> list = new ArrayList<>();
    TextView txtFullname, txtEmail;
    ImageView iv;

    /*Spinner cboFilter;
    String selected;*/

    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_pro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomPro.this, AddPostActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch(Exception e) {
                    Log.d("CUSTOM PRO ERR: ", e.getMessage());
                }
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem tools = menu.findItem(R.id.misc);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.NavView), 0, s.length(), 0);
        tools.setTitle(s);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        db = new UserDatabase(this);
        list = db.getAccountInfo();

        if(!list.isEmpty()) {
            iv = headerView.findViewById(R.id.imageView);
            txtFullname = headerView.findViewById(R.id.textView);
            txtEmail = headerView.findViewById(R.id.textView2);

            Picasso.with(this).load(list.get(0).getProfilePic()).transform(new CircleTransform()).into(iv);
            txtFullname.setText("Name: " +list.get(0).getFirstname() + " " + list.get(0).getLastname());
            txtEmail.setText("Email: " +list.get(0).getEmailAdd());
        }

        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.viewPager = this.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        this.tabLayout = this.findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(viewPager);

        /*cboFilter = findViewById(R.id.spinner);
        cboFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = cboFilter.getItemAtPosition(position).toString();

                if(selected.equals("User Posts")) {
                    PostFragment spFragment = new PostFragment();
                    FragmentManager spManager = getSupportFragmentManager();
                    spManager.beginTransaction().replace(
                            R.id.linearLayout,
                            spFragment,
                            spFragment.getTag()
                    ).commit();
                } else {
                    ShopFragment osFragment = new ShopFragment();
                    FragmentManager osManager = getSupportFragmentManager();
                    osManager.beginTransaction().replace(
                            R.id.linearLayout,
                            osFragment,
                            osFragment.getTag()
                    ).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", this);
            builder.setNegativeButton("No", this);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /*case R.id.nav_request:
                Intent requestIntent = new Intent(this, MyRequestActivity.class);
                startActivity(requestIntent);

                break;*/
            /*case R.id.nav_order:
                Intent orderIntent = new Intent(this, MyOrderActivity.class);
                startActivity(orderIntent);

                break;*/
        switch(item.getItemId()) {
            case R.id.nav_profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                profileIntent.putExtra("type", "customer");
                profileIntent.putExtra("id", list.get(0).getUserId());
                startActivity(profileIntent);

                break;
            case R.id.nav_wallet:
                Intent walletIntent = new Intent(this, WalletActivity.class);
                startActivity(walletIntent);

                break;
            case R.id.nav_post:
                Intent postIntent = new Intent(this, MyPostActivity.class);
                startActivity(postIntent);

                break;
            case R.id.nav_order:
                Intent orderIntent = new Intent(this, NewWorldOrderActivity.class);
                startActivity(orderIntent);

                break;
            case R.id.nav_notification:
                Intent notifyIntent = new Intent(this, NotificationActivity.class);
                startActivity(notifyIntent);

                break;
            case R.id.nav_store:
                Intent openShopIntent = new Intent(this, OpenShopActivity.class);
                try {
                    db.deleteAllSearch();
                } catch(Exception e) {
                    Log.d("CUSTOM PRO ERR: ", e.getMessage());
                }

                db.addSearch(list.get(0).getUserId(), "maker");
                startActivity(openShopIntent);

                break;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, AccountSettingsActivity.class);
                startActivityForResult(settingsIntent, 8989);

                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", this);
                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 8989) {
//            ewfaw
//            Toast.makeText(this, "stat" +list.get(0).getStatus().equals("DEACTIVATED"), Toast.LENGTH_SHORT).show();
            list = db.getAccountInfo();
            if(list.get(0).getStatus().equals("DEACTIVATED")) {
                this.finish();
            }
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch(i) {
            case DialogInterface.BUTTON_POSITIVE:
                db.deleteAllAccount();
                this.finish();

                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new PostFragment(), "Posts");
        adapter.addFragment(new ShopFragment(), "Shops");
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