package com.example.asus.custompro.profile;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.User;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.shop.ShopRegisterActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    final static int PICK_ADDRESS_REQUEST_CODE = 10;

    TextView txtFullname, txtFirstName, txtLastName, txtGender, txtContact, txtEmail, txtAddress, txtEditBasic, txtEditContact, txtEditAddress;
    ImageView iv;
    UserDatabase db;
    ArrayList<Account> list = new ArrayList<>();
    Dialog dialog, dialog2;
    EditText txtFname, txtLname, txtPhone, txtEmailAdd;
    RadioGroup rdoGender;
    Button btnSave, btnCancel, btnAddress;
    String address = "", type = "";
    Uri imageUri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new UserDatabase(this);
        //list = db.getAccountInfo();

        this.iv = this.findViewById(R.id.imageView);
        this.txtFullname = this.findViewById(R.id.textView);
        this.txtFirstName = this.findViewById(R.id.textView2);
        this.txtLastName = this.findViewById(R.id.textView3);
        this.txtGender = this.findViewById(R.id.textView4);
        this.txtContact = this.findViewById(R.id.textView5);
        this.txtEmail = this.findViewById(R.id.textView6);
        this.txtAddress = this.findViewById(R.id.textView7);
        this.txtEditBasic = this.findViewById(R.id.txtEditBasic);
        this.txtEditContact = this.findViewById(R.id.txtEditContact);

        try {
            Bundle b = getIntent().getExtras();
            type = b.getString("type");
            int userId = b.getInt("id");
            list = db.getFindAccount(userId);

            Picasso.with(this).load(list.get(0).getProfilePic()).transform(new CircleTransform()).into(iv);
            this.txtFullname.setText(list.get(0).getFirstname() + " " + list.get(0).getLastname());
            this.txtFirstName.setText(list.get(0).getFirstname());
            this.txtLastName.setText(list.get(0).getLastname());
            this.txtGender.setText(list.get(0).getGender());
            this.txtContact.setText(list.get(0).getContactnum());
            this.txtEmail.setText(list.get(0).getEmailAdd());
            this.txtAddress.setText(list.get(0).getAddress());
        } catch(Exception e) {
            Log.d("SET TEXT ERR= ", e.getMessage());
        }

        //if(type.equals("admin")) {
            this.txtEditBasic.setVisibility(View.INVISIBLE);
            this.txtEditContact.setVisibility(View.INVISIBLE);
        //} else {
            this.txtEditBasic.setVisibility(View.VISIBLE);
            this.txtEditContact.setVisibility(View.VISIBLE);
        //}

        this.txtEditBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.edit_basic_info_layout);

                txtLname = dialog.findViewById(R.id.editText);
                txtFname = dialog.findViewById(R.id.editText2);
                rdoGender = dialog.findViewById(R.id.radioGroup);
                btnSave = dialog.findViewById(R.id.button);
                btnCancel = dialog.findViewById(R.id.button2);

                txtLname.setText(list.get(0).getLastname());
                txtFname.setText(list.get(0).getFirstname());
                if(list.get(0).getGender().equals("Male")) {
                    rdoGender.check(R.id.radioButton);
                } else {
                    rdoGender.check(R.id.radioButton2);
                }

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String errMsg = "This field is empty.";

                        if (TextUtils.isEmpty(txtFname.getText().toString())) {
                            txtFname.requestFocus();
                            txtFname.setError(errMsg);
                        } else if (TextUtils.isEmpty(txtLname.getText().toString())) {
                            txtLname.requestFocus();
                            txtLname.setError(errMsg);
                        } else {
                            int selectedSex = rdoGender.getCheckedRadioButtonId();
                            RadioButton selectedButton = dialog.findViewById(selectedSex);
                            String gender = selectedButton.getText().toString();

                            list.get(0).setFirstname(txtFname.getText().toString());
                            list.get(0).setLastname(txtLname.getText().toString());
                            list.get(0).setGender(gender);

                            txtFullname.setText(list.get(0).getFirstname() + " " + list.get(0).getLastname());
                            txtFirstName.setText(list.get(0).getFirstname());
                            txtLastName.setText(list.get(0).getLastname());
                            txtGender.setText(list.get(0).getGender());

                            db.editUserAccount(list.get(0).getProfilePic().toString(), list.get(0).getUsername(), list.get(0).getPassword(), txtFname.getText().toString(), txtLname.getText().toString(), gender, list.get(0).getEmailAdd(), list.get(0).getContactnum(), list.get(0).getAddress(), list.get(0).getStatus());
                            Toast.makeText(ProfileActivity.this, "Basic Info Successfully Edited", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
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

        this.txtEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2 = new Dialog(ProfileActivity.this);
                dialog2.setContentView(R.layout.edit_contact_info_layout);

                txtPhone = dialog2.findViewById(R.id.editText);
                txtEmailAdd = dialog2.findViewById(R.id.editText2);
                txtEditAddress = dialog2.findViewById(R.id.editText3);
                //btnAddress = dialog2.findViewById(R.id.button);
                btnSave = dialog2.findViewById(R.id.button2);
                btnCancel = dialog2.findViewById(R.id.button3);

                txtPhone.setText(list.get(0).getContactnum());
                txtEmailAdd.setText(list.get(0).getEmailAdd());
                txtEditAddress.setText(list.get(0).getAddress());

                /*btnAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        address = txtEditAddress.getText().toString();
                        //txtEditAddress.setText(address);
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        Intent placeIntent;

                        try {
                            placeIntent = builder.build(ProfileActivity.this);
                            startActivityForResult(placeIntent, PICK_ADDRESS_REQUEST_CODE);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                    }
                });*/

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String errMsg = "This field is empty.";

                        if (TextUtils.isEmpty(txtPhone.getText().toString())) {
                            txtPhone.requestFocus();
                            txtPhone.setError(errMsg);
                        } else if (TextUtils.isEmpty(txtEmailAdd.getText().toString())) {
                            txtEmailAdd.requestFocus();
                            txtEmailAdd.setError(errMsg);
                        } else if(TextUtils.isEmpty(txtEditAddress.getText().toString())) {
                            txtEditAddress.requestFocus();
                            txtEditAddress.setError(errMsg);
                        } else {
                            list.get(0).setContactnum(txtPhone.getText().toString());
                            list.get(0).setEmailAdd(txtEmailAdd.getText().toString());
                            list.get(0).setAddress(txtEditAddress.getText().toString());
                            txtContact.setText(list.get(0).getContactnum());
                            txtEmail.setText(list.get(0).getEmailAdd());
                            txtAddress.setText(list.get(0).getAddress());

                            db.editUserAccount(list.get(0).getProfilePic().toString(), list.get(0).getUsername(), list.get(0).getPassword(), list.get(0).getFirstname(), list.get(0).getLastname(), list.get(0).getGender(), txtEmailAdd.getText().toString(), txtPhone.getText().toString(), txtEditAddress.getText().toString(), list.get(0).getStatus());
                            Toast.makeText(ProfileActivity.this, "Contact Info Successfully Edited", Toast.LENGTH_LONG).show();
                            dialog2.dismiss();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });

                dialog2.show();
            }
        });

        iv.setOnClickListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deactivate, menu);

        if(type.equals("admin")) {
            menu.findItem(R.id.deactivate).setVisible(true);
            if (list.get(0).getStatus().equals("ACTIVATED")) {
                menu.findItem(R.id.deactivate).setTitle("DEACTIVATE");
            } else {
                menu.findItem(R.id.deactivate).setTitle("ACTIVATE");
            }
        } else {
            menu.findItem(R.id.deactivate).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.deactivate:
                if (list.get(0).getStatus().equals("ACTIVATED")) {
                    db.editUserAccount(list.get(0).getProfilePic().toString(), list.get(0).getUsername(), list.get(0).getPassword(), list.get(0).getFirstname(), list.get(0).getLastname(), list.get(0).getGender(), list.get(0).getEmailAdd(), list.get(0).getContactnum(), list.get(0).getAddress(), "DEACTIVATED");
                    Toast.makeText(this, "Account Deactivated", Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(this, "Account Already Deactivated", Toast.LENGTH_LONG).show();
                    db.editUserAccount(list.get(0).getProfilePic().toString(), list.get(0).getUsername(), list.get(0).getPassword(), list.get(0).getFirstname(), list.get(0).getLastname(), list.get(0).getGender(), list.get(0).getEmailAdd(), list.get(0).getContactnum(), list.get(0).getAddress(), "ACTIVATED");
                    Toast.makeText(this, "Account Activated", Toast.LENGTH_LONG).show();
                }
                    finish();
                    startActivity(getIntent());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            imageUri = data.getData();

            if (imageUri != null) {
                iv.setImageURI(imageUri);
                db.editUserAccountImage(imageUri.toString(), list.get(0).getUsername());
                Toast.makeText(this, "Profile Picture Updated", Toast.LENGTH_SHORT).show();
//                Log.d("image = ", imageUri.toString());
            }
        } catch(Exception e) {
            Log.d("Image on SD Card Err=", e.getMessage());
        }
            
//        if(requestCode == PICK_ADDRESS_REQUEST_CODE) {
//            if(resultCode == Activity.RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, this);
//                address = place.getAddress().toString();
//                txtAddress.setText(address);
//            } else if(resultCode == Activity.RESULT_CANCELED) {
//                if(address.equals("")) {
//                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//                    alertBuilder.setTitle("Message");
//                    alertBuilder.setMessage("You must select your location.");
//                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                            Intent intent;
//
//                            try {
//                                intent = builder.build(getParent());
//                                startActivityForResult(intent, PICK_ADDRESS_REQUEST_CODE);
//                            } catch (GooglePlayServicesRepairableException e) {
//                                e.printStackTrace();
//                            } catch (GooglePlayServicesNotAvailableException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//
//                    AlertDialog dialog = alertBuilder.create();
//                    dialog.show();
//                }
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{

                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            this.startActivityForResult(intent, 100);
        }
    }
}