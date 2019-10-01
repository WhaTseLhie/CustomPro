package com.example.asus.custompro.home.search.post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.CircleTransform;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.post.bids.PostOfferAdapter;
import com.example.asus.custompro.home.search.shops.Maker;
import com.example.asus.custompro.home.search.post.bids.offer.Offer;
import com.example.asus.custompro.home.search.post.bids.offer.OfferPostActivity;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewPostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView txtName, txtDate, txtGender, txtContact, txtEmail, txtAddress, txtCategory, txtDetails, txtType, txtSize, txtQuantity, txtNone, txtColor;
//    TextView txtStatus;
    ImageView imgUser, imgRequest;//, imgSend;
    //EditText txtComment;

    PostOfferAdapter adapter;
    ListView lv;

    //ArrayList<PostComment> commentList = new ArrayList<>();
    ArrayList<Offer> offerList = new ArrayList<>();
    ArrayList<Account> userList = new ArrayList<>();
    ArrayList<Account> accList = new ArrayList<>();
    ArrayList<Maker> makerList = new ArrayList<>();
    ArrayList<Post> postList = new ArrayList<>();
    UserDatabase db;
    int userId, postId;
    int defaultColor;
    TextView txtDateUse, txtOpt1, txtOpt2;

    AlertDialog alertdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        this.imgUser = this.findViewById(R.id.imageView);
        this.imgRequest = this.findViewById(R.id.imageView2);
        //this.imgSend = this.findViewById(R.id.imageView3);
        this.txtName = this.findViewById(R.id.textView);
        this.txtDate = this.findViewById(R.id.textView2);
        this.txtGender = this.findViewById(R.id.textView3);
        this.txtContact = this.findViewById(R.id.textView4);
        this.txtEmail = this.findViewById(R.id.textView5);
        this.txtAddress = this.findViewById(R.id.textView6);
        this.txtCategory = this.findViewById(R.id.textView7);
        this.txtDetails = this.findViewById(R.id.textView8);
//        this.txtStatus = this.findViewById(R.id.textView9);
        this.txtQuantity = this.findViewById(R.id.textView10);
        this.txtType = this.findViewById(R.id.textView11);
        this.txtSize = this.findViewById(R.id.textView12);
        this.txtNone = this.findViewById(R.id.textView16);
        this.txtColor = this.findViewById(R.id.textView20);
        //this.txtComment = this.findViewById(R.id.editText);
        this.lv = this.findViewById(R.id.listView);


        this.txtDateUse = this.findViewById(R.id.txtDateUse);
        this.txtOpt1 = this.findViewById(R.id.txtOpt1);
        this.txtOpt2 = this.findViewById(R.id.txtOpt2);

        try {
            Bundle b = getIntent().getExtras();
            userId = b.getInt("userid");
            postId = b.getInt("postid");

            db = new UserDatabase(this);
            accList = db.getAccountInfo();
            userList = db.getFindAccount(userId);
            postList = db.getUserPostId(postId);

            if(!accList.isEmpty()) {
                makerList = db.findMaker(accList.get(0).getUserId());
                //txtComment.setVisibility(View.VISIBLE);
                //imgSend.setVisibility(View.VISIBLE);
            }/* else {
                txtComment.setVisibility(View.INVISIBLE);
                imgSend.setVisibility(View.INVISIBLE);
            }*/

            //commentList = db.getAllPostComment(postId);
            //adapter = new PostCommentAdapter(this, commentList);
            offerList = db.getAllOffer(postId);
            adapter = new PostOfferAdapter(this, offerList);
            lv.setAdapter(adapter);
            setListViewHeightBasedOnChildren(lv);

            if(offerList.isEmpty()) {
                txtNone.setVisibility(View.VISIBLE);
            } else {
                txtNone.setVisibility(View.GONE);
            }

            this.setTitle(userList.get(0).getFirstname()+ " " +userList.get(0).getLastname()+ "'s Post");
            Picasso.with(this).load(userList.get(0).getProfilePic()).transform(new CircleTransform()).into(imgUser);
            this.imgRequest.setImageURI(postList.get(0).getPostPic());
            this.txtName.setText("Name: " +userList.get(0).getFirstname() +" "+ userList.get(0).getLastname());
            this.txtDate.setText("Date Posted: " +postList.get(0).getPostDate());
            this.txtGender.setText("Gender: " +userList.get(0).getGender());
            this.txtContact.setText("Contact: " +userList.get(0).getContactnum());
            this.txtEmail.setText("Email: " +userList.get(0).getEmailAdd());
            this.txtAddress.setText("Address: " +userList.get(0).getAddress());
            this.txtCategory.setText("Category: " +postList.get(0).getPostCategory());
            this.txtType.setText("Type: " +postList.get(0).getPostType());
            this.txtSize.setText("Size: " +postList.get(0).getPostSize());
            this.txtQuantity.setText("Quantity: " +postList.get(0).getPostQty());


            this.txtDateUse.setText("Estimated Date of Use: " + postList.get(0).getPostestdate());
            this.txtOpt1.setText("Option 1: " + postList.get(0).getPostoptone());
            this.txtOpt2.setText("Option 2: " + postList.get(0).getPostopttwo());
//            this.txtColor.setText("Color: " +postList.get(0).getPostColor());

            defaultColor = Integer.parseInt(postList.get(0).getPostColor());
            this.txtColor.setBackgroundColor(Integer.parseInt(postList.get(0).getPostColor()));
            this.txtDetails.setText(postList.get(0).getPostDetails());
//            this.txtStatus.setText("Status: " +postList.get(0).);
//            this.txtStatus.setText("Status: " +"Not Yet");
        } catch(Exception e) {
            Log.d("POST ACTIVITY ERR: ", e.getMessage());
        }

        //this.imgSend.setOnClickListener(this);

        try {
            if (accList.get(0).getUserId() == userId && !db.findOfferSet(postId)) {
                lv.setOnItemClickListener(this);
            }
        } catch (Exception e) {
            Log.d("err=", e.getMessage());
        }
    }

    /*@Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageView3:
                if(txtComment.getText().toString().equals("")) {
                    txtComment.requestFocus();
                    txtComment.setError("This field is empty");
                } else {
                    String date = DateFormat.getDateInstance().format(new Date());
                    String comment = txtComment.getText().toString();
                    int id;
                    Uri pic;
                    String name;

                    if(makerList.isEmpty()) {
                        id = accList.get(0).getUserId();
                        pic = accList.get(0).getProfilePic();
                        name = accList.get(0).getFirstname() + " " + accList.get(0).getLastname();
                    } else {
                        id = makerList.get(0).getUserId();
                        pic = makerList.get(0).getShopPic();
                        name = makerList.get(0).getShopName();
                    }

                    long commentId = db.addPostComment(postList.get(0).getPostId(), id, pic.toString(), name, comment, date);
                    if(commentId != -1) {
                        int comId = Integer.parseInt("" +commentId);
                        commentList.add(new PostComment(comId, postList.get(0).getPostId(), id, pic, name, comment, date));
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error Adding", Toast.LENGTH_LONG).show();
                    }

                    txtComment.setText("");

                    setListViewHeightBasedOnChildren(lv);
                }

                break;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!accList.isEmpty() && userId == accList.get(0).getUserId()) {
            getMenuInflater().inflate(R.menu.post_edit_delete, menu);

            try {
//                ArrayList<Post> postsLists = new ArrayList<>();
//                ArrayList<Offer> offersLists = new ArrayList<>();
//                postsLists = db.getUserPostId(postId);
//                offersLists = db.getAllOffer()

                if(db.findOfferSet(postId)) {
                    menu.findItem(R.id.postedit).setVisible(false);
                    menu.findItem(R.id.postdelete).setVisible(false);
                }
            } catch (Exception e) {
                menu.findItem(R.id.postedit).setVisible(false);
                menu.findItem(R.id.postdelete).setVisible(false);
            }
        } else {
            getMenuInflater().inflate(R.menu.offer, menu);

            try {
                if (db.findOfferSet(postId) || userId == accList.get(0).getUserId() || !db.isMaker(accList.get(0).getUserId())) {
//                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    menu.findItem(R.id.offer).setVisible(false);
                } else {
//                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                    menu.findItem(R.id.offer).setVisible(true);
                }
            } catch (Exception e) {
//                Toast.makeText(this, "err1", Toast.LENGTH_SHORT).show();
                menu.findItem(R.id.offer).setVisible(false);
            }


            try {
                if (db.findOfferSet(postId)) {
//                    Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                    menu.findItem(R.id.offer).setVisible(false);
                } else if(db.findOfferSet(postId) && !makerList.get(0).getShopSpecialty().contains(postList.get(0).getPostCategory())) {
                    menu.findItem(R.id.offer).setVisible(false);
//                    Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                } /*else {
                    Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
                    menu.findItem(R.id.offer).setVisible(true);
                }*/
            } catch (Exception e) {
//                Toast.makeText(this, "err2", Toast.LENGTH_SHORT).show();
                menu.findItem(R.id.offer).setVisible(false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!accList.isEmpty() && userId == accList.get(0).getUserId()) {
            switch (item.getItemId()) {
                case R.id.postedit:
//                    Toast.makeText(this, "Edit Post", Toast.LENGTH_SHORT).show();
                    Intent editPostIntent = new Intent(this, EditPostActivity.class);
                    editPostIntent.putExtra("postid", postId);
                    startActivityForResult(editPostIntent, 54);

                    break;
                case R.id.postdelete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to delete this post?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deletePost(postId);
                            Toast.makeText(getApplicationContext(), "Post Deleted", Toast.LENGTH_SHORT).show();

                            alertdialog.dismiss();
                            ViewPostActivity.this.finish();
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

                    break;
            }
        } else {
            int id = item.getItemId();

            if (id == R.id.offer) {
                Intent intent = new Intent(this, OfferPostActivity.class);
                int shopId = makerList.get(0).getUserId();
                intent.putExtra("postid", postId);
                intent.putExtra("shopid", shopId);
                intent.putExtra("userid", userId);
                /*
                String shopPic = makerList.get(0).getShopPic().toString();
                String shopName = makerList.get(0).getShopName();
                intent.putExtra("shoppic", shopPic);
                intent.putExtra("shopname", shopName);*/
                startActivityForResult(intent, 10);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewOfferActivity.class);
        intent.putExtra("postid", offerList.get(position).getPostId());
        intent.putExtra("shopid", offerList.get(position).getShopId());
        intent.putExtra("userid", userId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 10) {
                int shopId = 0;
                String shopPic = "", shopName = "", price= "", date = "", comment = "";
                boolean remove = false;
                Bundle b = data.getExtras();

                try {
                    shopId = makerList.get(0).getUserId();
                    shopPic = makerList.get(0).getShopPic().toString();
                    shopName = makerList.get(0).getShopName();
                    price = b.getString("price");
                    date = b.getString("date");
                    comment = b.getString("comment");

                    remove = b.getBoolean("remove");
                    if (remove) {
                        db.deletePostOffer(postId, makerList.get(0).getUserId());
                        Toast.makeText(this, "You successfully deleted your offer", Toast.LENGTH_LONG).show();
                        this.finish();
                    }

                } catch (Exception e) {
                    Log.d("err=", e.getMessage());
                    /*try {
                        boolean remove = b.getBoolean("remove");
                        if (remove) {
                            db.deletePostOffer(postId, makerList.get(0).getUserId());
                            Toast.makeText(this, "You successfully deleted your offer", Toast.LENGTH_LONG).show();
                            this.finish();
                        }
                    } catch (Exception ex) {
                        Log.d("err2=", e.getMessage());
                    }*/
                }

                ArrayList<Offer> offerList2 = db.getPostOffer(postId, accList.get(0).getUserId());
                if(!remove) {
                    if (offerList2.isEmpty()) {
                        long offerId = db.addPostOffer(0, userId, postId, shopId, shopPic, shopName, price, date, comment, "");
                        if (offerId != -1) {
                            //int bidId = Integer.parseInt("" +offerId);
                            offerList.add(new Offer(0, userId, postId, shopId, shopPic, shopName, price, date, comment, ""));
                            adapter.notifyDataSetChanged();
                            Toast.makeText(this, "Offer Successful", Toast.LENGTH_LONG).show();

                            String notifdate = DateFormat.getDateInstance().format(new Date());
                            String message = "Your post have new offer";
                            db.addNotification(userList.get(0).getUserId(), shopId, postId, notifdate, "Post", message);

                            if (txtNone.getVisibility() == View.VISIBLE) {
                                txtNone.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(this, "Error Adding", Toast.LENGTH_LONG).show();
                        }
                    } else {
                    /*offerList.add(new Offer(userId, postId, shopId, shopPic, shopName, price, date, comment));
                    offerList.clear();

                    if(offerList.isEmpty()) {
                        offerList = db.getAllOffer(postId);
                        adapter.notifyDataSetChanged();
                    }*/
                        db.editPostOffer(0, userId, postId, shopId, comment, date, price, "");
                        Toast.makeText(this, "Offer Updated", Toast.LENGTH_LONG).show();
                    }

                    setListViewHeightBasedOnChildren(lv);
                }
            }
        } else {
            Log.d("result=", "note ok");
        }

        this.finish();
        this.startActivity(getIntent());
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}