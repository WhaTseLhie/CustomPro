package com.example.asus.custompro.register;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    final static int PICK_ADDRESS_REQUEST_CODE = 10;
    final static int PICK_IMAGE_REQUEST_CODE = 20;

    Button btnRegister, btnCancel, btnAddress;
    //TextView txtAddress;
    ImageView iv;
    EditText txtFname, txtLname, txtUsername, txtEmail, txtAddress, txtContact, txtPass, txtPass2;
    RadioGroup rdoGender;
    String fname, lname, username, email, contact, pass, pass2;
    UserDatabase db;
    Uri imageUri;
    String address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new UserDatabase(this);

        this.btnRegister = this.findViewById(R.id.button);
        this.btnCancel = this.findViewById(R.id.button2);
        this.rdoGender = this.findViewById(R.id.radioGroup);
        this.iv = this.findViewById(R.id.imageView);
        this.txtFname = this.findViewById(R.id.editText);
        this.txtLname = this.findViewById(R.id.editText2);
        this.txtEmail = this.findViewById(R.id.editText3);
        this.txtContact = this.findViewById(R.id.editText4);
        this.txtUsername = this.findViewById(R.id.editText5);
        this.txtPass = this.findViewById(R.id.editText6);
        this.txtPass2 = this.findViewById(R.id.editText7);
        this.btnAddress = this.findViewById(R.id.button3);
        this.txtAddress = this.findViewById(R.id.editText8);

//        this.txtFname.setText("Bruce and Scarred");
//        this.txtLname.setText("Bruce and Scarred");
//        this.txtEmail.setText("Bruce and Scarred");
//        this.txtContact.setText("012312");
//        this.txtUsername.setText("user");
//        this.txtPass.setText("pass");
//        this.txtPass2.setText("pass");
//        this.txtAddress.setText("Bruce and Scarred");

        this.iv.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);

        /*this.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent placeIntent;

                try {
                    placeIntent = builder.build(RegisterActivity.this);
                    startActivityForResult(placeIntent, PICK_ADDRESS_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            imageUri = data.getData();

            if (imageUri != null) {
                iv.setImageURI(imageUri);
                Log.d("image = ", imageUri.toString());
            }
        } catch(Exception e) {
            Log.d("Image on SD Card Err=", e.getMessage());
        }
        /*if(requestCode == PICK_IMAGE_REQUEST_CODE) {
            try {
                imageUri = data.getData();

                if (imageUri != null) {
                    iv.setImageURI(imageUri);
                }
            } catch(Exception e) {
                Log.d("Image on SD Card Err=", e.getMessage());
            }
        } else if(requestCode == PICK_ADDRESS_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                address = place.getAddress().toString();
                txtAddress.setText(address);
            } else if(resultCode == Activity.RESULT_CANCELED) {
                if(address.equals("")) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setTitle("Message");
                    alertBuilder.setMessage("You must select your location.");
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                            Intent intent;

                            try {
                                intent = builder.build(getParent());
                                startActivityForResult(intent, PICK_ADDRESS_REQUEST_CODE);
                            } catch (GooglePlayServicesRepairableException e) {
                                e.printStackTrace();
                            } catch (GooglePlayServicesNotAvailableException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    AlertDialog dialog = alertBuilder.create();
                    dialog.show();
                }
            }
        }*/
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.imageView:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{

                                Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    this.startActivityForResult(intent,PICK_IMAGE_REQUEST_CODE);
                }

                break;
            case R.id.button:
                fname = txtFname.getText().toString();
                lname = txtLname.getText().toString();
                email = txtEmail.getText().toString();
                contact = txtContact.getText().toString();
                address = txtAddress.getText().toString();
                username = txtUsername.getText().toString().toLowerCase();
                pass = txtPass.getText().toString();
                pass2 = txtPass2.getText().toString();
                checkFields(fname, lname, email, contact, address, username, pass, pass2);
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
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    this.startActivityForResult(intent,100);
                }

                break;
        }
    }

    private void checkFields(String fname, String lname, String email, String contact, String address, String username, String pass, String pass2){
        String errMsg = "This field is empty.";

        if(TextUtils.isEmpty(fname)) {
            txtFname.requestFocus();
            txtFname.setError(errMsg);
        } else if(TextUtils.isEmpty(lname)) {
            txtLname.requestFocus();
            txtLname.setError(errMsg);
        } else if(TextUtils.isEmpty(email)) {
            txtEmail.requestFocus();
            txtEmail.setError(errMsg);
        } else if(TextUtils.isEmpty(contact)) {
            txtContact.requestFocus();
            txtContact.setError(errMsg);
        } else if(TextUtils.isEmpty(address)) {
            txtAddress.requestFocus();
            txtContact.setError(errMsg);
        } else if(TextUtils.isEmpty(username)) {
            txtUsername.requestFocus();
            txtUsername.setError(errMsg);
        } else if(TextUtils.isEmpty(pass)) {
            txtPass.requestFocus();
            txtPass.setError(errMsg);
        } else if(TextUtils.isEmpty(pass2)) {
            txtPass2.requestFocus();
            txtPass2.setError(errMsg);
        } else if(!TextUtils.equals(pass, pass2)) {
            errMsg = "Passwords did not match! Please try again";
            txtPass2.requestFocus();
            txtPass2.setError(errMsg);
        } else if(imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<Account> accList = db.findAccount(username);

            if(accList.isEmpty()) {
                int selectedSex = this.rdoGender.getCheckedRadioButtonId();
                RadioButton selectedButton = this.findViewById(selectedSex);
                String gender = selectedButton.getText().toString();

                db.addUser(imageUri.toString(), username, pass, fname, lname, gender, email, contact, address, "ACTIVATED");
                Toast.makeText(this, "Account Successfully Saved", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(this, "Username is taken! Please select another", Toast.LENGTH_LONG).show();
            }
        }
    }
}