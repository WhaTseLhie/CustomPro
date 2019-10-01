package com.example.asus.custompro.shop.review;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.custompro.Account;
import com.example.asus.custompro.R;
import com.example.asus.custompro.UserDatabase;
import com.example.asus.custompro.home.search.Search;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewsFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView lv;
    TextView txtZero;
    ReviewAdapter reviewAdapter;

    ArrayList<Review> reviewList = new ArrayList<>();
    ArrayList<Search> searchList = new ArrayList<>();
    ArrayList<Account> accList = new ArrayList<>();
    UserDatabase db;

    Dialog dialog, dialog2;
    RatingBar ratingBar;
    TextView txtComment;
    int pos;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        db = new UserDatabase(getContext());
        searchList = db.getAllSearch();
        accList = db.getAccountInfo();
        reviewList = db.findReviewMaker(searchList.get(0).getUserid());

        txtZero = view.findViewById(R.id.textView);

        lv = view.findViewById(R.id.listView);
        reviewAdapter = new ReviewAdapter(getContext(), reviewList);
        lv.setAdapter(reviewAdapter);

        if(!reviewList.isEmpty()) {
            txtZero.setVisibility(View.INVISIBLE);
        } else {
            if(!(accList.get(0).getUserId() == searchList.get(0).getUserid()) && searchList.get(0).getType().equals("seeker")) {
                txtZero.setText("This shop has no reviews");
            }
            txtZero.setVisibility(View.VISIBLE);
        }

        lv.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;

        if(accList.get(0).getUserId() == reviewList.get(pos).getUserId()) {
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.layout_review);

            TextView txtTitle = dialog.findViewById(R.id.textView);
            TextView txtEdit = dialog.findViewById(R.id.textView2);
            TextView txtDelete = dialog.findViewById(R.id.textView3);

            txtTitle.setText(reviewList.get(pos).getUserName() + " Review");

            txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    dialog2 = new Dialog(getActivity());
                    dialog2.setContentView(R.layout.review_layout);

                    TextView txtTitle = dialog2.findViewById(R.id.textView);
                    ImageView iv = dialog2.findViewById(R.id.imageView);
                    ratingBar = dialog2.findViewById(R.id.ratingBar);
                    txtComment = dialog2.findViewById(R.id.textView2);
                    Button btnReviewSave = dialog2.findViewById(R.id.button);
                    Button btnReviewCancel = dialog2.findViewById(R.id.button2);

                    txtTitle.setText(reviewList.get(pos).getUserName() + " Review");
                    iv.setImageURI(reviewList.get(pos).getUserPic());
                    ratingBar.setRating(reviewList.get(pos).getRating());
                    txtComment.setText(reviewList.get(pos).getComment());

                    btnReviewSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            float rating = ratingBar.getRating();
                            String comment = txtComment.getText().toString();

                            db.editReview(reviewList.get(pos).getRequestId(), rating, comment);
                            Toast.makeText(getActivity(), "Review Successfully Edited", Toast.LENGTH_LONG).show();

                            dialog2.dismiss();
                            getActivity().finish();
                            startActivity(getActivity().getIntent());
                        }
                    });

                    btnReviewCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                        }
                    });

                    dialog2.show();
                }
            });

            txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteReview(reviewList.get(pos).getRequestId());

                    /*float totalRating = db.findTotalReviewMaker(searchList.get(0).getUserid());
                    db.editMaker(searchList.get(0).getUserid(), (totalRating/reviewList.size()));
                    this.txtShopReviews.setText(reviewList.size() + " Reviews");
                    this.ratingBar.setRating(totalRating/reviewList.size());*/

                    dialog.dismiss();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            });

            dialog.show();
        }
    }
}