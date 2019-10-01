package com.example.asus.custompro.shop.nwo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.post.bids.offer.Offer;
import com.example.asus.custompro.home.search.post.bids.offer.OfferPostActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class NewWorldOfferRequestActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    TextView txtDate;
    Button btnOffer, btnCancel;
    EditText txtComment, txtPriceOffer;

    UserDatabase db;
    ArrayList<Offer> offerList = new ArrayList<>();

    int userId, postId, shopId, nwoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_post);

        this.db = new UserDatabase(this);

        this.iv = this.findViewById(R.id.imageView);
        this.txtComment = this.findViewById(R.id.editText);
        this.txtDate = this.findViewById(R.id.textView);
        this.txtPriceOffer = this.findViewById(R.id.editText2);
        this.btnOffer = this.findViewById(R.id.button);
        this.btnCancel = this.findViewById(R.id.button2);

        try {
//            Bundle b = getIntent().getExtras();
//            nwoid = b.getInt("nwoid");
//            userId = b.getInt("userid");
//            postId = b.getInt("postid");
//            shopId = b.getInt("shopid");

//            Toast.makeText(this, userId+"opa", Toast.LENGTH_LONG).show();

//            offerList = db.getPostOffer(postId, shopId);
            if(!offerList.isEmpty()) {
                this.txtComment.setText(offerList.get(0).getOfferComment());
                this.txtDate.setText(offerList.get(0).getOfferDate());
                this.txtPriceOffer.setText(offerList.get(0).getOfferPrice());
                this.btnOffer.setText("Update Offer");

                if(offerList.get(0).getOfferStatus().equals("SELECTED")) {
                    this.btnOffer.setVisibility(View.INVISIBLE);
                    this.btnCancel.setVisibility(View.INVISIBLE);
                } else {
                    this.btnOffer.setVisibility(View.VISIBLE);
                    this.btnCancel.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            Log.d("err=", e.getMessage());
        }

        this.iv.setOnClickListener(this);
        this.btnOffer.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String yy = String.valueOf(year);
            String mm = String.valueOf(month+1);
            String dd = String.valueOf(dayOfMonth);

            txtDate.setText(mm +"/"+ dd +"/"+ yy);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.offer_delete, menu);

        MenuItem menuItem = menu.findItem(R.id.deleteOffer);

        if(!offerList.isEmpty()) {
            if (offerList.get(0).getOfferStatus().equals("SELECTED")) {
                menuItem.setVisible(false);
            } else {
                menuItem.setVisible(true);
            }
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to clear this offer?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtDate.setText("mm/dd/yyyy");
                txtComment.setText("");
                txtPriceOffer.setText("");
//                Intent deleteIntent = new Intent();
//                deleteIntent.putExtra("remove", true);
//                this.setResult(RESULT_OK, deleteIntent);
//                OfferPostActivity.this.finish();
                Toast.makeText(NewWorldOfferRequestActivity.this, "ACCEPTED, AWAITING PAYMENT", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageView:
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog datePicker = new DatePickerDialog(this,
                        R.style.AppTheme, datePickerListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePicker.setCancelable(false);
                datePicker.setTitle("Select Date");
                datePicker.show();

                break;
            case R.id.button:
                String comment = txtComment.getText().toString();
                String date = txtDate.getText().toString();
                String price = txtPriceOffer.getText().toString();
                String errMsg = "This field is empty";

                if(TextUtils.isEmpty(comment)) {
                    txtComment.requestFocus();
                    txtComment.setError(errMsg);
                } else if(TextUtils.isEmpty(price)) {
                    txtPriceOffer.requestFocus();
                    txtPriceOffer.setError(errMsg);
                } else if(TextUtils.equals(date, "mm/dd/yyyy")) {
                    Toast.makeText(this, "Please click the calendar button to select date", Toast.LENGTH_LONG).show();
                } else {
                    Intent b = new Intent();
                    b.putExtra("price", price);
                    b.putExtra("date", date);
                    b.putExtra("comment", comment);
                    this.setResult(Activity.RESULT_OK, b);
                    this.finish();
                }

                break;
            case R.id.button2:
                this.finish();
        }
    }
}