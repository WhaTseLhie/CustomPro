package com.example.asus.custompro.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.custompro.R;
import com.example.asus.custompro.admin.accounts.ViewAccountsFragment;
import com.example.asus.custompro.admin.nwos.ViewNewWorldOrderFragment;
import com.example.asus.custompro.admin.orders.ViewOrdersFragment;
import com.example.asus.custompro.admin.posts.ViewPostsFragment;
import com.example.asus.custompro.admin.requests.ViewRequestsFragment;
import com.example.asus.custompro.admin.shops.AdminShopFragment;
import com.example.asus.custompro.register.RegisterActivity;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        ImageView iv = headerView.findViewById(R.id.imageView);
        TextView txtName = headerView.findViewById(R.id.textView);
        TextView txtEmail = headerView.findViewById(R.id.textView2);

        iv.setImageResource(R.drawable.ic_icon);
        txtName.setText("CustomPro Admin");
        txtEmail.setText("Administrator");

        ViewAccountsFragment accountFragment = new ViewAccountsFragment();
        FragmentManager accountManager = getSupportFragmentManager();
        accountManager.beginTransaction().replace(
                R.id.constraintLayout,
                accountFragment,
                accountFragment.getTag()
        ).commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_all_users) {
            ViewAccountsFragment accountFragment = new ViewAccountsFragment();
            FragmentManager accountManager = getSupportFragmentManager();
            accountManager.beginTransaction().replace(
                    R.id.constraintLayout,
                    accountFragment,
                    accountFragment.getTag()
            ).commit();
        } else if (id == R.id.nav_all_shops) {
            AdminShopFragment adminShopFragment = new AdminShopFragment();
            FragmentManager adminShopManager = getSupportFragmentManager();
            adminShopManager.beginTransaction().replace(
                    R.id.constraintLayout,
                    adminShopFragment,
                    adminShopFragment.getTag()
            ).commit();
        } /*else if (id == R.id.nav_all_requests) {
            ViewRequestsFragment requestFragment = new ViewRequestsFragment();
            FragmentManager requestManager = getSupportFragmentManager();
            requestManager.beginTransaction().replace(
                    R.id.constraintLayout,
                    requestFragment,
                    requestFragment.getTag()
            ).commit();
        }*/ else if (id == R.id.nav_all_post) {
            ViewPostsFragment postFragment = new ViewPostsFragment();
            FragmentManager postManager = getSupportFragmentManager();
            postManager.beginTransaction().replace(
                    R.id.constraintLayout,
                    postFragment,
                    postFragment.getTag()
            ).commit();
        } else if (id == R.id.nav_all_order) {
            ViewNewWorldOrderFragment orderFragment = new ViewNewWorldOrderFragment();
            FragmentManager orderManager = getSupportFragmentManager();
            orderManager.beginTransaction().replace(
                    R.id.constraintLayout,
                    orderFragment,
                    orderFragment.getTag()
            ).commit();
        }/*else if (id == R.id.nav_all_order) {
            ViewOrdersFragment orderFragment = new ViewOrdersFragment();
            FragmentManager orderManager = getSupportFragmentManager();
            orderManager.beginTransaction().replace(
                    R.id.constraintLayout,
                    orderFragment,
                    orderFragment.getTag()
            ).commit();
        }*/ else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", this);
            builder.setNegativeButton("No", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch(i) {
            case DialogInterface.BUTTON_POSITIVE:
                this.finish();

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmin, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent regIntent = new Intent(this, RegisterActivity.class);
        startActivity(regIntent);

        return super.onOptionsItemSelected(item);
    }
}