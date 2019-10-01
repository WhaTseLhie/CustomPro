package com.example.asus.custompro.shop.nwo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
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
import com.example.asus.custompro.home.search.Search;
import com.example.asus.custompro.home.search.post.AddPostActivity;
import com.example.asus.custompro.home.search.shops.Maker;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddNewWorldOrderActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    final static int PICK_IMAGE_REQUEST_CODE = 10;

    ImageView ivUserPic, ivRequestPic, ivMinus, ivAdd;
    TextView txtUserName, txtDatePosted;
    EditText txtRequestDetails, txtQty;
    Spinner cboCategory, cboType, cboSize;
//    , cboColor;
    TextView txtColor;
    ImageView imgColor;
    int defaultColor;
    boolean isSizeNotSelected = false, isTypeNotSelected = false;
    Button btnSubmit, btnCancel;
    String category = "Select Category", type = "", size = "";
//    , color = "Select Color";

    Uri imageUri;
    UserDatabase db;
    ArrayList<Maker> makerList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Search> searchList = new ArrayList<>();

    Spinner cboOpt1, cboOpt2;

    String option1 = "";
    String option2 = "";

    TextView txtDateUse;
    ImageView ivDateUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        db = new UserDatabase(this);
        userList = db.getAccountInfo();
        searchList = db.getAllSearch();
        makerList = db.findMaker(searchList.get(0).getUserid());

        this.ivUserPic = this.findViewById(R.id.imageView);
        this.ivRequestPic = this.findViewById(R.id.imageView2);
        this.ivMinus = this.findViewById(R.id.imageView3);
        this.ivAdd = this.findViewById(R.id.imageView4);
        this.txtUserName = this.findViewById(R.id.textView);
        this.txtDatePosted = this.findViewById(R.id.textView2);
        this.txtRequestDetails = this.findViewById(R.id.editText);
        this.txtQty = this.findViewById(R.id.editText2);
        this.cboCategory = this.findViewById(R.id.spinner);
        this.cboType = this.findViewById(R.id.spinner2);
        this.cboSize = this.findViewById(R.id.spinner3);
        this.cboOpt1 = this.findViewById(R.id.spinner4);
        this.cboOpt2 = this.findViewById(R.id.spinner5);
        this.txtDateUse = this.findViewById(R.id.txtDateUse);
        this.ivDateUse = this.findViewById(R.id.ivDateUse);
//        this.cboColor = this.findViewById(R.id.spinner4);
        this.imgColor = this.findViewById(R.id.imgColor);
        this.txtColor = this.findViewById(R.id.txtColor);
        this.btnSubmit = this.findViewById(R.id.button);
        this.btnCancel = this.findViewById(R.id.button2);

        defaultColor = ContextCompat.getColor(AddNewWorldOrderActivity.this, R.color.white);

        String cat = "Select Category," +makerList.get(0).getShopSpecialty();
        String catArray[] = cat.split(",");
//        String catArray[] = {"a", "b"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, catArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.cboCategory.setAdapter(spinnerAdapter);

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
        this.ivRequestPic.setOnClickListener(this);
        this.ivMinus.setOnClickListener(this);
        this.ivAdd.setOnClickListener(this);
        this.btnSubmit.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        this.cboOpt1.setOnItemSelectedListener(this);
        this.cboOpt2.setOnItemSelectedListener(this);
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
                String details = this.txtRequestDetails.getText().toString();
                String date = this.txtDatePosted.getText().toString();
                String dateUse = this.txtDateUse.getText().toString();
                int qty = Integer.parseInt(this.txtQty.getText().toString());

                if(imageUri == null) {
                    Toast.makeText(this, "Please select photo", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(details)) {
                    txtRequestDetails.requestFocus();
                    txtRequestDetails.setError("This field is empty");
                } else if(TextUtils.equals(category, "Select Category")) {
                    Toast.makeText(this, "Please select category", Toast.LENGTH_LONG).show();
                } else if(isTypeNotSelected()) {
                    Toast.makeText(this, "Please select type", Toast.LENGTH_LONG).show();
                } else if(isSizeNotSelected()) {
                    Toast.makeText(this, "Please select size", Toast.LENGTH_LONG).show();
                } else if(qty == 0) {
                    Toast.makeText(this, "Qty must not be zero", Toast.LENGTH_LONG).show();
                } /*else if(TextUtils.equals("Select Color", color)) {
                    Toast.makeText(this, "Please select a color", Toast.LENGTH_LONG).show();
                }*/else if(TextUtils.equals(dateUse, "mm/dd/yyyy")) {
                    Toast.makeText(this, "Please select estimated date used", Toast.LENGTH_LONG).show();
                } else {
                    long res = db.addNewWorldOrder(userList.get(0).getUserId(), makerList.get(0).getUserId(), qty, imageUri.toString(), details, category, type, size, defaultColor+"", date, date, "0", "REQUESTING ORDER", dateUse, option1, option2);
                    if(res != -1) {
                        Toast.makeText(this, "Order Successfully Submitted", Toast.LENGTH_LONG).show();
//                        Intent intent1 = new Intent();
//                        this.setResult(RESULT_OK);
                        this.finish();
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.button2:
                this.finish();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            imageUri = data.getData();

            if(imageUri != null) {
                ivRequestPic.setImageURI(imageUri);
            }
        } catch(Exception e) {
            Log.d("IMAGE ON SD CARD ERR: ", e.getMessage());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*try {
            this.category = this.cboCategory.getItemAtPosition(position).toString();
            Toast.makeText(this, "category: " +category, Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            Log.d("ADD POST ACTIVITY ERR: ", e.getMessage());
        }*/
        switch(parent.getId()) {
            case R.id.spinner:
                category = cboCategory.getItemAtPosition(position).toString();
                //Toast.makeText(this, category, Toast.LENGTH_LONG).show();

                ArrayAdapter<CharSequence> noTypeAdapter = ArrayAdapter.createFromResource(this, R.array.notype, android.R.layout.simple_spinner_item);
                noTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cboSize.setAdapter(noTypeAdapter);

                switch(category.trim()) {
                    case "Select Category":
                        ArrayAdapter<CharSequence> nocatAdapter = ArrayAdapter.createFromResource(this, R.array.nocat, android.R.layout.simple_spinner_item);
                        nocatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(nocatAdapter);

                        ArrayAdapter<CharSequence> notypeAdapter = ArrayAdapter.createFromResource(this, R.array.nocat, android.R.layout.simple_spinner_item);
                        notypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboSize.setAdapter(notypeAdapter);


                        break;
                    case "T-Shirt":
                        ArrayAdapter<CharSequence> shirtTypeAdapter = ArrayAdapter.createFromResource(this, R.array.shirt_type, android.R.layout.simple_spinner_item);
                        shirtTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(shirtTypeAdapter);

                        break;
                    case "Bags":
                        ArrayAdapter<CharSequence> bagTypeAdapter = ArrayAdapter.createFromResource(this, R.array.bag_type, android.R.layout.simple_spinner_item);
                        bagTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(bagTypeAdapter);

                        break;
                    case "Hats":
                        ArrayAdapter<CharSequence> hatTypeAdapter = ArrayAdapter.createFromResource(this, R.array.hats_type, android.R.layout.simple_spinner_item);
                        hatTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(hatTypeAdapter);

                        break;
                    case "Hoodie or Jacket":
                        ArrayAdapter<CharSequence> hoodieTypeAdapter = ArrayAdapter.createFromResource(this, R.array.hoodie_type, android.R.layout.simple_spinner_item);
                        hoodieTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(hoodieTypeAdapter);

                        break;
                    case "Pillow":
                        ArrayAdapter<CharSequence> pillowTypeAdapter = ArrayAdapter.createFromResource(this, R.array.pillow_type, android.R.layout.simple_spinner_item);
                        pillowTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboType.setAdapter(pillowTypeAdapter);

                        break;
                }

                break;
            case R.id.spinner2:
                type = cboType.getItemAtPosition(position).toString();

                switch(category.trim()) {
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

                break;case R.id.spinner4:
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
}