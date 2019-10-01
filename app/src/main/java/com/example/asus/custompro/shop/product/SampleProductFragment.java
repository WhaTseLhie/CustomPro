package com.example.asus.custompro.shop.product;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.User;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.Search;
import com.example.asus.custompro.shop.OpenShopActivity;

import java.util.ArrayList;

public class SampleProductFragment extends Fragment {

    final static int ADD_PRODUCT_REQUEST_CODE = 10;
    final static int PICK_IMAGE_REQUEST_CODE = 20;

    GridViewAdapter gridAdapter;
    GridView gv;
    UserDatabase db;
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Search> searchList = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
    AdapterView.AdapterContextMenuInfo info;

    EditText txtName, txtDetails;
    ImageView iv;
    Uri imageUri;

    public SampleProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample_product, container, false);

        db = new UserDatabase(getContext());
        userList = db.getAccountInfo();
        //productList = db.getMakerProduct(userList.get(0).getUserId());
        searchList = db.getAllSearch();
        productList = db.getMakerProduct(searchList.get(0).getUserid());

        gv = view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getContext(), productList);
        gv.setAdapter(gridAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        if(userList.get(0).getUserId() == searchList.get(0).getUserid() || searchList.get(0).getType().equals("maker")) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addIntent = new Intent(getContext(), AddProductActivity.class);
                    startActivityForResult(addIntent, ADD_PRODUCT_REQUEST_CODE);
                }
            });
            registerForContextMenu(gv);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ViewProductActivity.class);
                intent.putExtra("image", productList.get(position).getProductPic());
                intent.putExtra("name", productList.get(position).getProductName());
                intent.putExtra("details", productList.get(position).getProductDetails());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.edit:
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.edit_product_layout);

                iv = dialog.findViewById(R.id.imageView);
                txtName = dialog.findViewById(R.id.editText);
                txtDetails = dialog.findViewById(R.id.editText2);
                Button btnUpdate = dialog.findViewById(R.id.button);
                Button btnCancel = dialog.findViewById(R.id.button2);

                imageUri = productList.get(info.position).getProductPic();
                iv.setImageURI(productList.get(info.position).getProductPic());
                txtName.setText(productList.get(info.position).getProductName());
                txtDetails.setText(productList.get(info.position).getProductDetails());

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = txtName.getText().toString();
                        String details = txtDetails.getText().toString();

//                        imageUri = productList.get(0).

                        if(imageUri == null) {
                            Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_LONG).show();
                        } else if(TextUtils.isEmpty(name)) {
                            txtName.requestFocus();
                            txtName.setError("This field is empty");
                        } else if(TextUtils.isEmpty(details)) {
                            txtDetails.requestFocus();
                            txtDetails.setError("This field is empty");
                        } else {
                            productList.get(info.position).setProductPic(imageUri);
                            productList.get(info.position).setProductName(name);
                            productList.get(info.position).setProductDetails(details);
                            gridAdapter.notifyDataSetChanged();

                            db.editProduct(productList.get(info.position).getUserId(), productList.get(info.position).getProductId(), imageUri.toString(), name, details);
                            Toast.makeText(getActivity(), "Product Successfully Updated", Toast.LENGTH_LONG).show();

                            dialog.dismiss();
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
            case R.id.delete:
                db.deleteProduct(productList.get(info.position).getProductId());
                productList.remove(info.position);
                gridAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Product Successfully Deleted", Toast.LENGTH_LONG).show();

                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);

        info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        String name = productList.get(info.position).getProductName();
        menu.setHeaderTitle(name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == ADD_PRODUCT_REQUEST_CODE) {
                try {
                    Bundle b = data.getExtras();
                    String name = b.getString("name");

                    Toast.makeText(getContext(), name+ " Successfully Added", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                } catch(Exception e) {
                    Log.d("ADD PRODUCT ERR: ", e.getMessage());
                }
            } else {
                try {
                    imageUri = data.getData();

                    if (imageUri != null) {
                        iv.setImageURI(imageUri);
                    }
                } catch(Exception e) {
                    Log.d("IMAGE ON SD CARD ERR=", e.getMessage());
                }
            }
        }
    }
}