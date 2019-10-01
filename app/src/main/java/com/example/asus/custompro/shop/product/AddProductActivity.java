package com.example.asus.custompro.shop.product;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.User;
import com.example.asus.custompro.UserDatabase;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    final static int PICK_IMAGE_REQUEST_CODE = 10;

    ImageView iv;
    EditText txtName, txtDetails;
    Button btnAdd, btnCancel;
    Uri imageUri;
    UserDatabase db;
    ArrayList<Account> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        this.db = new UserDatabase(this);
        this.userList = db.getAccountInfo();

        this.iv = this.findViewById(R.id.imageView);
        this.txtName = this.findViewById(R.id.editText);
        this.txtDetails = this.findViewById(R.id.editText2);
        this.btnAdd = this.findViewById(R.id.button);
        this.btnCancel = this.findViewById(R.id.button2);

        this.iv.setOnClickListener(this);
        this.btnAdd.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageView:
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                this.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);

                break;
            case R.id.button:
                String name = this.txtName.getText().toString();
                String details = this.txtDetails.getText().toString();
                String errMsg = "This field is empty.";

                if(imageUri == null) {
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(name)) {
                    txtName.requestFocus();
                    txtName.setError(errMsg);
                } else if(TextUtils.isEmpty(details)) {
                    txtDetails.requestFocus();
                    txtDetails.setError(errMsg);
                } else {
                    db.addProduct(userList.get(0).getUserId(), imageUri.toString(), name, details);
                    Intent blind = new Intent();
                    blind.putExtra("name", name);
                    this.setResult(Activity.RESULT_OK, blind);
                    this.finish();
                }

                break;
            case R.id.button2:
                this.finish();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            imageUri = data.getData();

            if (imageUri != null) {
                iv.setImageURI(imageUri);
            }
        } catch(Exception e) {
            Log.d("Image on SD Card Err=", e.getMessage());
        }
    }
}