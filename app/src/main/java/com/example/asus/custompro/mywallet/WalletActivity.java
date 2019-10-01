package com.example.asus.custompro.mywallet;

import android.app.Dialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.mywallet.cashin.WalletCashInFragment;
import com.example.asus.custompro.mywallet.payout.WalletPayoutFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WalletActivity extends AppCompatActivity {

    TextView txtBal;
    Button btnTopup;
    UserDatabase userDatabase;
    ArrayList<Account> userList = new ArrayList<>();
    TextView txtAmount;

    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        userDatabase = new UserDatabase(this);
        userList = userDatabase.getAccountInfo();
        btnTopup = findViewById(R.id.button);
        txtBal = findViewById(R.id.textView);
        String tottolbal = userDatabase.dbGetWalletBalanceUser(userList.get(0).getUserId());
        txtBal.setText(tottolbal);

        btnTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(WalletActivity.this);
                dialog.setContentView(R.layout.dialog_top_up);



                txtAmount = dialog.findViewById(R.id.editText);
                txtAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
                Button btnSubmit = dialog.findViewById(R.id.button);
                Button btnCancel = dialog.findViewById(R.id.button1);

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!txtAmount.getText().toString().trim().equals("")) {
                            int totalBal = Integer.parseInt(txtAmount.getText().toString());

                            if (!txtBal.getText().toString().equals("0.0")) {
                                try {
                                    totalBal += (Integer.parseInt(txtBal.getText().toString()));
                                    txtBal.setText(""+totalBal);
//                                    Toast.makeText(getApplicationContext(), "Total Bal: " +totalBal, Toast.LENGTH_LONG).show();
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "Message: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

//                            hm.put("laundSeeker_totalbal", Integer.toString(totalBal));
//                            userDatabase.deleteAllUser();
//                            userDatabase.addUser(userList.get(0).getLaundSeeker_cnum(),
//                                    userList.get(0).getLaundSeeker_email(),
//                                    userList.get(0).getLaundSeeker_fbid(),
//                                    userList.get(0).getLaundSeeker_fn(),
//                                    userList.get(0).getLaundSeeker_gender(),
//                                    userList.get(0).getLaundSeeker_ln(),
//                                    userList.get(0).getLaundSeeker_link(),
//                                    userList.get(0).getLaundSeeker_pic(),
//                                    userList.get(0).getLaundSeeker_status(),
//                                    Integer.toString(totalBal));

                            String cashin_date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(new Date());
                            String cashin_time = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());

//                            userDatabase.deleteAllUser();
//                            userDatabase.addUser(userList.get(0).getLaundSeeker_cnum(), userList.get(0).getLaundSeeker_email(), userList.get(0).getLaundSeeker_fbid(), userList.get(0).getLaundSeeker_fn(), userList.get(0).getLaundSeeker_gender(), userList.get(0).getLaundSeeker_ln(), userList.get(0).getLaundSeeker_link(), userList.get(0).getLaundSeeker_pic(), userList.get(0).getLaundSeeker_status(), Integer.toString(totalBal));
//                            userDatabase.addWalletCashIn(txtAmount.getText().toString(), cashin_date, key, cashin_time);

                            if(userDatabase.findWalletUser(userList.get(0).getUserId())) {
                                userDatabase.editWalletUser(userList.get(0).getUserId(), totalBal + "");
                            } else {
                                userDatabase.addWalletUser(userList.get(0).getUserId(), totalBal + "");
                            }
                            userDatabase.addWalletCashIn(userList.get(0).getUserId(), txtAmount.getText().toString(), cashin_date, cashin_time);
                            Toast.makeText(getApplicationContext(), "Top-up Successful", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please put an amount", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new WalletCashInFragment(), "Cash In");
        adapter.addFragment(new WalletPayoutFragment(), "Payout");
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
