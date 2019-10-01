package com.example.asus.custompro.shop.product;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.squareup.picasso.Picasso;

public class ViewProductActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    TextView txtName, txtDetails;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        this.iv = this.findViewById(R.id.imageView);
        this.txtName = this.findViewById(R.id.textView);
        this.txtDetails = this.findViewById(R.id.textView2);
        this.btnConfirm = this.findViewById(R.id.button);

        try {
            Bundle b = getIntent().getExtras();
            Uri image = b.getParcelable("image");
            String name = b.getString("name");
            String details = b.getString("details");

            this.iv.setImageURI(image);
            this.txtName.setText(name);
            this.txtDetails.setText(details);
        } catch(Exception e) {
            Log.d("SET TEXT ERR;", e.getMessage());
        }

        this.btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}