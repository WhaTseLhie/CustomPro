package com.example.asus.custompro.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.User;
import com.example.asus.custompro.UserDatabase;

import java.util.ArrayList;

public class AccountSettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    TextView txtUsername, txtPassword, txtQuest;
    CheckBox checkBox;
    Button btnChangePassword, btnDeactivate;
    UserDatabase db;
    ArrayList<Account> list = new ArrayList<>();

    String errMsg = "Passwords did not match! Please try again";
    EditText txtOld, txtNew, txtConfirm;
    Button btnSave, btnCancel;

    AlertDialog alertdialog, alertdialog2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        db = new UserDatabase(this);
        list = db.getAccountInfo();

        this.txtUsername = this.findViewById(R.id.textView);
        this.txtPassword = this.findViewById(R.id.textView2);
        this.txtQuest = this.findViewById(R.id.textView3);
        this.checkBox = this.findViewById(R.id.checkBox);
        this.btnChangePassword = this.findViewById(R.id.button);
        this.btnDeactivate = this.findViewById(R.id.button2);

        this.txtUsername.setText(list.get(0).getUsername());
        this.txtPassword.setText(list.get(0).getPassword());

        if(list.get(0).getStatus().equals("DEACTIVATED")) {
            this.btnDeactivate.setText("ACTIVATE ACCOUNT");
            this.txtQuest.setText("Activate your account?");
        } else {
            this.btnDeactivate.setText("DEACTIVATE ACCOUNT");
            this.txtQuest.setText("Deactivate your account?");
        }

        this.checkBox.setOnCheckedChangeListener(this);
        this.btnChangePassword.setOnClickListener(this);
        this.btnDeactivate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.change_password_layout);

                txtOld = dialog.findViewById(R.id.editText);
                txtNew = dialog.findViewById(R.id.editText2);
                txtConfirm = dialog.findViewById(R.id.editText3);
                btnSave = dialog.findViewById(R.id.button);
                btnCancel = dialog.findViewById(R.id.button2);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newPass = txtNew.getText().toString();
                        String confirmPass = txtConfirm.getText().toString();

                        if (TextUtils.equals(txtOld.getText().toString(), list.get(0).getPassword())) {
                            if (!TextUtils.isEmpty(newPass)) {
                                if (TextUtils.equals(newPass, confirmPass)) {
                                    db.editUserAccount(list.get(0).getProfilePic().toString(), list.get(0).getUsername(), confirmPass, list.get(0).getFirstname(), list.get(0).getLastname(), list.get(0).getGender(), list.get(0).getEmailAdd(), list.get(0).getContactnum(), list.get(0).getAddress(), list.get(0).getStatus());
                                    txtPassword.setText(confirmPass);
                                    list.get(0).setPassword(confirmPass);
                                    Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    finish();
                                    startActivity(getIntent());
                                } else {
                                    txtConfirm.requestFocus();
                                    txtConfirm.setError(errMsg);
                                }
                            } else {
                                txtNew.requestFocus();
                                txtNew.setError("This field is empty.");
                            }
                        } else {
                            txtOld.requestFocus();
                            txtOld.setError(errMsg);
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                break;
            case R.id.button2:
                if(list.get(0).getStatus().equals("ACTIVATED")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to deactivate your account?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.editUserAccount(list.get(0).getProfilePic().toString(), list.get(0).getUsername(), list.get(0).getPassword(), list.get(0).getFirstname(), list.get(0).getLastname(), list.get(0).getGender(), list.get(0).getEmailAdd(), list.get(0).getContactnum(), list.get(0).getAddress(), "DEACTIVATED");
                            Toast.makeText(getApplicationContext(), "Account Deactivated", Toast.LENGTH_LONG).show();
                            alertdialog.dismiss();

                            finish();
//                            startActivity(getIntent());

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
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to activate your account?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.editUserAccount(list.get(0).getProfilePic().toString(), list.get(0).getUsername(), list.get(0).getPassword(), list.get(0).getFirstname(), list.get(0).getLastname(), list.get(0).getGender(), list.get(0).getEmailAdd(), list.get(0).getContactnum(), list.get(0).getAddress(), "ACTIVATED");
                            Toast.makeText(getApplicationContext(), "Account Activated", Toast.LENGTH_LONG).show();
                            alertdialog.dismiss();

                            finish();
                            startActivity(getIntent());
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
                }

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked) {
            txtPassword.setTransformationMethod(new PasswordTransformationMethod());
        } else {
            txtPassword.setTransformationMethod(null);
        }
    }
}