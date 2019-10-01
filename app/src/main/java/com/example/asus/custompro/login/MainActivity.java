package com.example.asus.custompro.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.admin.AdminActivity;
import com.example.asus.custompro.home.CustomPro;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.register.RegisterActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    Button btnLogin;
    EditText txtUsername, txtPassword;
    TextView txtSignUp;
    UserDatabase db;
    ArrayList<Account> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = new UserDatabase(this);
        this.db.deleteAllAccount();
        this.txtUsername = this.findViewById(R.id.editText);
        this.txtPassword = this.findViewById(R.id.editText2);
        this.btnLogin = this.findViewById(R.id.button);
        this.txtSignUp = this.findViewById(R.id.textView2);


        //////////////////////////////////////////////////////////////////////////////////////////////////
        /*if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 100);
            }
        }

        String username, pass, fname, lname, gender, email, contact, address;
        username = "user";
        pass = "pass";
        fname = "JJ";
        lname = "Pards";
        gender = "Male";
        email = "email@";
        contact = "09969696969";
        address = "sesame street";
        db.deleteAllAccount();
        db.deleteAllUser();
        db.addUser("content://media/external/images/media/25466", username, pass, fname, lname, gender, email, contact, address, "ACTIVATED");
        Toast.makeText(this, "Account Successfully Saved", Toast.LENGTH_LONG).show();

        String username1, pass1, fname1, lname1, gender1, email1, contact1, address1;
        username1 = "user1";
        pass1 = "pass";
        fname1 = "Jeyms";
        lname1 = "Hardie";
        gender1 = "Male";
        email1 = "email@";
        contact1 = "09981237123";
        address1 = "hiwi dili street";
        db.addUser("content://media/external/images/media/25480", username1, pass1, fname1, lname1, gender1, email1, contact1, address1, "ACTIVATED");
        Toast.makeText(this, "Account Successfully Saved", Toast.LENGTH_LONG).show();

        String username2, pass2, fname2, lname2, gender2, email2, contact2, address2;
        username2 = "user2";
        pass2 = "pass";
        fname2 = "Azul";
        lname2 = "Azul";
        gender2 = "Female";
        email2 = "azul@";
        contact2 = "09981237123";
        address2 = "ako oten kay street";
        db.addUser("content://media/external/images/media/25470", username2, pass2, fname2, lname2, gender2, email2, contact2, address2, "ACTIVATED");
        Toast.makeText(this, "Account Successfully Saved", Toast.LENGTH_LONG).show();
        */
//
//        txtUsername.setText("user");
//        txtPassword.setText("pass");


        this.btnLogin.setOnClickListener(this);
        this.txtSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button:String errMsg = "This field is empty.";
                String username = txtUsername.getText().toString().toLowerCase();
                String password = txtPassword.getText().toString();

                if(TextUtils.isEmpty(username)) {
                    txtUsername.requestFocus();
                    txtUsername.setError(errMsg);
                } else if(TextUtils.isEmpty(password)) {
                    txtPassword.requestFocus();
                    txtPassword.setError(errMsg);
                } else if(TextUtils.equals("admin", username) && TextUtils.equals("admin", password)) {
                    Intent intentAdmin = new Intent(this, AdminActivity.class);
                    startActivityForResult(intentAdmin, 20);
                } else {
                    db.deleteAllAccount();
                    list = db.findAccount(username);

                    if (!list.isEmpty()) {
                        if (!list.get(0).getUsername().equals(username)) {
                            Toast.makeText(this, "Invalid Username! Try Again", Toast.LENGTH_LONG).show();
                            txtUsername.setText("");
                        } else if (!list.get(0).getPassword().equals(password)) {
                            Toast.makeText(this, "Invalid Password! Try Again", Toast.LENGTH_LONG).show();
                            txtPassword.setText("");
                        } else if(list.get(0).getStatus().equals("DEACTIVATED")) {
                            Toast.makeText(this, "Your account has been deactivated. Please contact the administrator.", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            } catch(Exception e) {
                                Log.d("Mainact ERR: ", e.getMessage());
                            }

                            Intent intentCustomPro = new Intent(this, CustomPro.class);
                            startActivityForResult(intentCustomPro, 10);
                        }
                    } else {
                        Toast.makeText(this, "Login Failed! Try Again", Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.textView2:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivity(intentRegister);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", this);
        builder.setNegativeButton("No", this);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch(i) {
            case DialogInterface.BUTTON_POSITIVE:
                System.exit(0);
                db.deleteAllAccount();
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
                }

                break;
        }
    }
}