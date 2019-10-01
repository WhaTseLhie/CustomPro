package com.example.asus.custompro.home.search.post;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EditPostActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    final static int PICK_IMAGE_REQUEST_CODE = 10;

    ImageView ivUserPic, ivPostPic, ivMinus, ivAdd;
    TextView txtUserName, txtDatePosted;
    EditText txtPostDetails, txtQty;
    Button btnPost, btnCancel;
    String category = "", type = "", size = "";
//    , color = "";
    Spinner cboCategory, cboType, cboSize;
    TextView txtColor;
    ImageView imgColor;
//            , cboColor;
    int defaultColor;
    boolean isSizeNotSelected = false, isTypeNotSelected = false;

    ArrayList<Post> postList = new ArrayList<>();

    Uri imageUri;
    UserDatabase db;
    ArrayList<Account> userList = new ArrayList<>();

    int typeId, sizeId;

    Spinner cboOpt1, cboOpt2;

    String option1 = "";
    String option2 = "";

    TextView txtDateUse;
    ImageView ivDateUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        db = new UserDatabase(this);
        userList = db.getAccountInfo();

        this.ivUserPic = this.findViewById(R.id.imageView);
        this.ivPostPic = this.findViewById(R.id.imageView2);
        this.ivMinus = this.findViewById(R.id.imageView3);
        this.ivAdd = this.findViewById(R.id.imageView4);
        this.txtUserName = this.findViewById(R.id.textView);
        this.txtDatePosted = this.findViewById(R.id.textView2);
        this.txtPostDetails = this.findViewById(R.id.editText);
        this.txtQty = this.findViewById(R.id.editText2);
        this.cboCategory = this.findViewById(R.id.spinner);
        this.cboType = this.findViewById(R.id.spinner2);
        this.cboSize = this.findViewById(R.id.spinner3);
        this.cboOpt1 = this.findViewById(R.id.spinner4);
        this.cboOpt2 = this.findViewById(R.id.spinner5);
//        this.cboColor = this.findViewById(R.id.spinner4);
        this.txtColor = this.findViewById(R.id.txtColor);
        this.imgColor= this.findViewById(R.id.imgColor);
        this.btnPost = this.findViewById(R.id.button);
        this.btnCancel = this.findViewById(R.id.button2);
        this.txtDateUse = this.findViewById(R.id.txtDateUse);
        this.ivDateUse = this.findViewById(R.id.ivDateUse);

        try {
            Bundle b = getIntent().getExtras();
            int postId = b.getInt("postid");

            postList = db.getUserPostId(postId);

            this.ivPostPic.setImageURI(postList.get(0).getPostPic());
            this.txtDatePosted.setText(postList.get(0).getPostDate());
            this.txtPostDetails.setText(postList.get(0).getPostDetails());
            this.txtQty.setText("" +postList.get(0).getPostQty());

            String cat[] = getResources().getStringArray(R.array.category);
            int catIndex = Arrays.asList(cat).indexOf(postList.get(0).getPostCategory());
            category = cat[catIndex];
            this.cboCategory.setSelection(catIndex);

            typeId = 0;
            sizeId = 0;
            String postType = postList.get(0).getPostType();
//            Toast.makeText(this, "pt= " +postType, Toast.LENGTH_SHORT).show();

            switch(category) {
                case "T-Shirt":
                    typeId = R.array.shirt_type;
                    sizeId = R.array.shirt_size;

                    break;
                case "Bags":
                    typeId = R.array.bag_type;

                    switch (postType) {
                        case "Drawstring Bag":
                            sizeId = R.array.drawstring_bag_size;

                            break;
                        case "School Status Bag":
                            sizeId = R.array.school_status_bag_size;

                            break;
                    }

                    break;
                case "Hats":
                    typeId = R.array.hats_type;

                    switch (postType) {
                        case "Baseball Cap":
                            sizeId = R.array.baseball_cap_size;

                            break;
                        case "Flat Cap":
                            sizeId = R.array.flat_cap_size;

                            break;
                    }

                    break;
                case "Hoodie or Jacket":
                    typeId = R.array.hoodie_type;

                    switch (postType) {
                        case "Sweater Hoodie":
                            sizeId = R.array.sweater_hoodie_size;

                            break;
                        case "Turtleneck Jacket":
                            sizeId = R.array.turtleneck_jacket_size;

                            break;
                    }

                    break;
                case "Pillow":
                    typeId = R.array.pillow_type;

                    switch (postType) {
                        case "Head Pillow":
                            sizeId = R.array.head_pillow_size;

                            break;
                        case "Square Pillow":
                            sizeId = R.array.square_pillow_size;

                            break;
                    }

                    break;
            }

            ///////
            ////////
//            String typ[] = getResources().getStringArray(R.array.shirt_type);
//            int typeIndex = Arrays.asList(typ).indexOf(postList.get(0).getPostType());
//            type = typ[typeIndex];
//            this.cboType.setSelection(typeIndex);


//            String siz[] = getResources().getStringArray(sizeId);
//            int sizIndex = Arrays.asList(siz).indexOf(postList.get(0).getPostSize());
//            size = siz[sizIndex];
//            this.cboSize.setSelection(sizIndex);
            /////////
            ///////

//            String col[] = getResources().getStringArray(R.array.color);
//            int colorIndex = Arrays.asList(col).indexOf(postList.get(0).getPostColor());
//            color = cat[colorIndex];
//            this.cboColor.setSelection(colorIndex);
            defaultColor = Integer.parseInt(postList.get(0).getPostColor());
            this.txtColor.setBackgroundColor(defaultColor);

            imageUri = postList.get(0).getPostPic();
        } catch (Exception e) {
            Log.d("err", e.getMessage());
        }

        String newDate = DateFormat.getDateInstance().format(new Date());
        this.txtDatePosted.setText(newDate);
        Picasso.with(this).load(userList.get(0).getProfilePic()).transform(new CircleTransform()).into(ivUserPic);
        this.txtUserName.setText(userList.get(0).getFirstname() +" "+ userList.get(0).getLastname());

        this.txtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null) {
                    if(s.length() > 1) {
                        if(s.toString().startsWith("0")) {
                            s = s.toString().substring(1, s.toString().length());
                            txtQty.setText(s);
                            txtQty.setSelection(1);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(txtQty.getText().length() == 0) {
                    txtQty.setText("0");
                    txtQty.setSelection(1);
                }
            }
        });

        this.cboCategory.setOnItemSelectedListener(this);
        this.cboType.setOnItemSelectedListener(this);
        this.cboSize.setOnItemSelectedListener(this);
//        this.cboColor.setOnItemSelectedListener(this);
        this.imgColor.setOnClickListener(this);
        this.ivPostPic.setOnClickListener(this);
        this.ivMinus.setOnClickListener(this);
        this.ivAdd.setOnClickListener(this);
        this.btnPost.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);

        this.cboOpt1.setOnItemSelectedListener(this);
        this.cboOpt2.setOnItemSelectedListener(this);
//        this.cboColor.setOnItemSelectedListener(this);
        this.ivDateUse.setOnClickListener(this);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String yy = String.valueOf(year);
            String mm = String.valueOf(month+1);
            String dd = String.valueOf(dayOfMonth);

            txtDateUse.setText(mm +"/"+ dd +"/"+ yy);
        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ivDateUse:
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog datePicker = new DatePickerDialog(this,
                        R.style.AppTheme, datePickerListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePicker.setCancelable(false);
                datePicker.setTitle("Select Date");
                datePicker.show();

                break;
            case R.id.imgColor:
                AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        defaultColor = color;
                        txtColor.setBackgroundColor(defaultColor);
                    }
                });
                colorPicker.show();

                break;
            case R.id.imageView2:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                this.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);

                break;
            case R.id.imageView3:
                int qtyM = Integer.parseInt(txtQty.getText().toString());
                if(qtyM > 0) {
                    qtyM -= 1;
                    txtQty.setText("" +qtyM);
                }

                break;
            case R.id.imageView4:
                int qtyA = Integer.parseInt(txtQty.getText().toString());
                qtyA += 1;
                txtQty.setText("" +qtyA);

                break;
            case R.id.button:
                String details = this.txtPostDetails.getText().toString();
                String date = this.txtDatePosted.getText().toString();
                String dateUse = this.txtDateUse.getText().toString();
                int qty = Integer.parseInt(this.txtQty.getText().toString());

                if(imageUri == null) {
                    Toast.makeText(this, "Please select photo", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(details)) {
                    txtPostDetails.requestFocus();
                    txtPostDetails.setError("This field is empty");
                } else if(TextUtils.equals("Select Category", category)) {
                    Toast.makeText(this, "Please select category", Toast.LENGTH_LONG).show();
                } else if(isTypeNotSelected()) {
                    Toast.makeText(this, "Please select type", Toast.LENGTH_LONG).show();
                } else if(isSizeNotSelected()) {
                    Toast.makeText(this, "Please select size", Toast.LENGTH_LONG).show();
                } else if(qty == 0) {
                    Toast.makeText(this, "Qty must not be zero", Toast.LENGTH_LONG).show();
                } /*else if(TextUtils.equals("Select Color", color) || TextUtils.equals("", color)) {
                    Toast.makeText(this, "Please select a color", Toast.LENGTH_LONG).show();
                }*/ else if(TextUtils.equals(dateUse, "mm/dd/yyyy")) {
                    Toast.makeText(this, "Please select estimated date used", Toast.LENGTH_LONG).show();
                } else {
                    String userName = userList.get(0).getFirstname() +" "+ userList.get(0).getLastname();
                    db.editPost(postList.get(0).getPostId(), userList.get(0).getUserId(), userList.get(0).getProfilePic().toString(), userName, imageUri.toString(), details, category, type, size, qty, defaultColor+"", date, dateUse, option1, option2);
                    Toast.makeText(this, "Post Successfully Updated", Toast.LENGTH_LONG).show();
                    this.finish();
                }

                break;
            case R.id.button2:
                this.finish();

                break;
        }
    }

    public boolean isTypeNotSelected() {
        isTypeNotSelected = false;

        switch(type) {
            case "Select Pillow Type": isTypeNotSelected = true; break;
            case "Select Hoodie or Jacket Type": isTypeNotSelected = true; break;
            case "Select Hat Type": isTypeNotSelected = true; break;
            case "Select Bag Type": isTypeNotSelected = true; break;
            case "Select Shirt Type": isTypeNotSelected = true; break;
            case "No Category Selected": isTypeNotSelected = true; break;
        }

        return isTypeNotSelected;
    }

    public boolean isSizeNotSelected() {
        isSizeNotSelected = false;

        switch(size) {
            case "Select Sweater Hoodie Size": isSizeNotSelected = true; break;
            case "Select Turtleneck Jacket Size": isSizeNotSelected = true; break;
            case "Select Shirt Size": isSizeNotSelected = true; break;
            case "No Type Selected": isSizeNotSelected = true; break;
            case "No Category Selected": isSizeNotSelected = true; break;
        }

        return isSizeNotSelected;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            imageUri = data.getData();

            if(imageUri != null) {
                ivPostPic.setImageURI(imageUri);
            }
        } catch(Exception e) {
            Log.d(
                    "IMAGE ON SD CARD ERR: ", e.getMessage());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.spinner:
                category = cboCategory.getItemAtPosition(position).toString();
                //Toast.makeText(this, "cat = " +category, Toast.LENGTH_LONG).show();

                switch(position) {
                    case 0:
                        ArrayAdapter<CharSequence> nocatAdapter = ArrayAdapter.createFromResource(this, R.array.nocat, android.R.layout.simple_spinner_item);
                        nocatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(nocatAdapter);

                        ArrayAdapter<CharSequence> notypeAdapter = ArrayAdapter.createFromResource(this, R.array.nocat, android.R.layout.simple_spinner_item);
                        notypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboSize.setAdapter(notypeAdapter);

                        break;
                    case 1:
                        ArrayAdapter<CharSequence> shirtTypeAdapter = ArrayAdapter.createFromResource(this, R.array.shirt_type, android.R.layout.simple_spinner_item);
                        shirtTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(shirtTypeAdapter);

                        break;
                    case 20:
                        ArrayAdapter<CharSequence> bagTypeAdapter = ArrayAdapter.createFromResource(this, R.array.bag_type, android.R.layout.simple_spinner_item);
                        bagTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(bagTypeAdapter);

                        break;
                    case 2:
                        ArrayAdapter<CharSequence> hatTypeAdapter = ArrayAdapter.createFromResource(this, R.array.hats_type, android.R.layout.simple_spinner_item);
                        hatTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(hatTypeAdapter);

                        break;
                    case 3:
                        ArrayAdapter<CharSequence> hoodieTypeAdapter = ArrayAdapter.createFromResource(this, R.array.hoodie_type, android.R.layout.simple_spinner_item);
                        hoodieTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(hoodieTypeAdapter);

                        break;
                    case 5:
                        ArrayAdapter<CharSequence> pillowTypeAdapter = ArrayAdapter.createFromResource(this, R.array.pillow_type, android.R.layout.simple_spinner_item);
                        pillowTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(pillowTypeAdapter);

                        break;
                }

                break;
            case R.id.spinner2:
                type = cboType.getItemAtPosition(position).toString();
                //Toast.makeText(this, "type = " +type, Toast.LENGTH_LONG).show();

                switch(category) {
                    case "T-Shirt":
                        if(type.equals("Select Shirt Type")) {
                            ArrayAdapter<CharSequence> noSizeAdapter = ArrayAdapter.createFromResource(this, R.array.notype, android.R.layout.simple_spinner_item);
                            noSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cboSize.setAdapter(noSizeAdapter);
                        } else {
                            ArrayAdapter<CharSequence> shirtSizeAdapter = ArrayAdapter.createFromResource(this, R.array.shirt_size, android.R.layout.simple_spinner_item);
                            shirtSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cboSize.setAdapter(shirtSizeAdapter);

                            /////////////////////////////////////
                            /////////////////////////////////////
                            /////////////////////////////////////
                            /////////////////////////////////////
                            /////////////////////////////////////
                            /////////////////////////////////////
                            ArrayAdapter<CharSequence> shirtOpt1Adapter = ArrayAdapter.createFromResource(this, R.array.shirt_option1, android.R.layout.simple_spinner_item);
                            shirtOpt1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cboOpt1.setAdapter(shirtOpt1Adapter);

                            ArrayAdapter<CharSequence> shirtOpt2Adapter = ArrayAdapter.createFromResource(this, R.array.shirt_option2, android.R.layout.simple_spinner_item);
                            shirtOpt2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cboOpt2.setAdapter(shirtOpt2Adapter);
                        }

                        break;
                    case "Bags":
                        switch(type) {
                            case "Drawstring Bag":
                                ArrayAdapter<CharSequence> dsSizeAdapter = ArrayAdapter.createFromResource(this, R.array.drawstring_bag_size, android.R.layout.simple_spinner_item);
                                dsSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(dsSizeAdapter);

                                break;
                            case "School Status Bag":
                                ArrayAdapter<CharSequence> ssSizeAdapter = ArrayAdapter.createFromResource(this, R.array.school_status_bag_size, android.R.layout.simple_spinner_item);
                                ssSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(ssSizeAdapter);

                                break;
                            case "Select Bag Type":
                                ArrayAdapter<CharSequence> noSizeAdapter = ArrayAdapter.createFromResource(this, R.array.notype, android.R.layout.simple_spinner_item);
                                noSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(noSizeAdapter);

                                break;
                        }

                        break;
                    case "Hats":

                        /////////////////////////////////////
                        /////////////////////////////////////
                        /////////////////////////////////////
                        /////////////////////////////////////
                        /////////////////////////////////////
                        /////////////////////////////////////
                        ArrayAdapter<CharSequence> hatOpt1Adapter = ArrayAdapter.createFromResource(this, R.array.bucket_option1, android.R.layout.simple_spinner_item);
                        hatOpt1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboOpt1.setAdapter(hatOpt1Adapter);

                        ArrayAdapter<CharSequence> hatOpt2Adapter = ArrayAdapter.createFromResource(this, R.array.bucket_option2, android.R.layout.simple_spinner_item);
                        hatOpt2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboOpt2.setAdapter(hatOpt2Adapter);

//                        Toast.makeText(this, "this", Toast.LENGTH_SHORT).show();
                        
                        switch(type) {
                            case "Baseball Cap":
                                ArrayAdapter<CharSequence> bcSizeAdapter = ArrayAdapter.createFromResource(this, R.array.baseball_cap_size, android.R.layout.simple_spinner_item);
                                bcSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(bcSizeAdapter);

                                break;
                            case "Flat Cap":
                                ArrayAdapter<CharSequence> fcSizeAdapter = ArrayAdapter.createFromResource(this, R.array.flat_cap_size, android.R.layout.simple_spinner_item);
                                fcSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(fcSizeAdapter);

                                break;
                            case "Select Hat Type":
                                ArrayAdapter<CharSequence> noSizeAdapter = ArrayAdapter.createFromResource(this, R.array.notype, android.R.layout.simple_spinner_item);
                                noSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(noSizeAdapter);

                                break;
                        }

                        break;
                    case "Hoodie or Jacket":

                        /////////////////////////////////////
                        /////////////////////////////////////
                        /////////////////////////////////////
                        /////////////////////////////////////
                        /////////////////////////////////////
                        /////////////////////////////////////
                        ArrayAdapter<CharSequence> hoodOpt1Adapter = ArrayAdapter.createFromResource(this, R.array.bomber_jacket_option1, android.R.layout.simple_spinner_item);
                        hoodOpt1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboOpt1.setAdapter(hoodOpt1Adapter);

                        ArrayAdapter<CharSequence> hoodOpt2Adapter = ArrayAdapter.createFromResource(this, R.array.bomber_jacket_option2, android.R.layout.simple_spinner_item);
                        hoodOpt2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboOpt2.setAdapter(hoodOpt2Adapter);

                        switch(type) {
                            case "Sweater Hoodie":
                                ArrayAdapter<CharSequence> shSizeAdapter = ArrayAdapter.createFromResource(this, R.array.sweater_hoodie_size, android.R.layout.simple_spinner_item);
                                shSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(shSizeAdapter);

                                break;
                            case "Turtleneck Jacket":
                                ArrayAdapter<CharSequence> tjSizeAdapter = ArrayAdapter.createFromResource(this, R.array.turtleneck_jacket_size, android.R.layout.simple_spinner_item);
                                tjSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(tjSizeAdapter);

                                break;
                            case "Select Hoodie or Jacket Type":
                                ArrayAdapter<CharSequence> noSizeAdapter = ArrayAdapter.createFromResource(this, R.array.notype, android.R.layout.simple_spinner_item);
                                noSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(noSizeAdapter);

                                break;
                        }

                        break;
                    case "Pillow":
                        switch(type) {
                            case "Head Pillow":
                                ArrayAdapter<CharSequence> hpSizeAdapter = ArrayAdapter.createFromResource(this, R.array.head_pillow_size, android.R.layout.simple_spinner_item);
                                hpSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(hpSizeAdapter);

                                break;
                            case "Square Pillow":
                                ArrayAdapter<CharSequence> spSizeAdapter = ArrayAdapter.createFromResource(this, R.array.square_pillow_size, android.R.layout.simple_spinner_item);
                                spSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(spSizeAdapter);

                                break;
                            case "Select Pillow Type":
                                ArrayAdapter<CharSequence> noSizeAdapter = ArrayAdapter.createFromResource(this, R.array.notype, android.R.layout.simple_spinner_item);
                                noSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cboSize.setAdapter(noSizeAdapter);

                                break;
                        }

                        break;
                    /*default:
                        ArrayAdapter<CharSequence> noTypeAdapter = ArrayAdapter.createFromResource(this, R.array.notype, android.R.layout.simple_spinner_item);
                        noTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboSize.setAdapter(noTypeAdapter);*/
                }

                break;
            case R.id.spinner3:
                size = cboSize.getItemAtPosition(position).toString();
                //Toast.makeText(this, "size = " +size, Toast.LENGTH_LONG).show();

                break;
            case R.id.spinner4:
                option1 = cboOpt1.getItemAtPosition(position).toString();
//                Toast.makeText(this, "option1 = " +option1, Toast.LENGTH_LONG).show();

                break;
            case R.id.spinner5:
                option2 = cboOpt2.getItemAtPosition(position).toString();
//                Toast.makeText(this, "option2 = " +option2, Toast.LENGTH_LONG).show();

                break;
//            case R.id.spinner4:
//                color = cboColor.getItemAtPosition(position).toString();
//
//                break;
        }

        /*if(!pre_category.equals(category)) {
            ArrayAdapter<CharSequence> noTypeAdapter = ArrayAdapter.createFromResource(this, R.array.notype, android.R.layout.simple_spinner_item);
            noTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cboSize.setAdapter(noTypeAdapter);
        }

        pre_category = category;*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}