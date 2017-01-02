package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmoviesstage1.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Intent intent = getIntent();
        MovieInfo curtMovie = intent.getParcelableExtra("curtMovie");
        final String title = curtMovie.getTitle();
        String postPath = curtMovie.getPostPath();
        String overview = curtMovie.getOverview();
        String voteAverage = curtMovie.getVoteAverage() + "/10";
        String releaseDate = curtMovie.getReleaseDate();
        List<String> trailerList = curtMovie.getVideoList();
        List<String> reviewsList = curtMovie.getReviews();
        final String id = curtMovie.getId();

        mBinding.titleTextview.setText(title);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w780" + postPath).into(mBinding.postImageView);
        mBinding.overviewTextview.setText(overview);
        mBinding.voteAverageTextview.setText(voteAverage);
        mBinding.releaseDateTextview.setText(releaseDate);


        /*set favorite movies section*/
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(id)) {
            mBinding.favoriteImageview.setImageResource(R.drawable.ic_golden_star);
        } else {
            mBinding.favoriteImageview.setImageResource(R.drawable.ic_grey_star);
        }

        mBinding.favoriteImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (sharedPreferences.contains(id)) {
                    mBinding.favoriteImageview.setImageResource(R.drawable.ic_grey_star);
                    editor.remove(id).commit();
                } else {
                    mBinding.favoriteImageview.setImageResource(R.drawable.ic_golden_star);
                    editor.putString(id, title).commit();
                }
            }
        });


        /*set trailer section*/
        TextView trailerTextView = new TextView(this);
        trailerTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        trailerTextView.setText("Trailer");
        mBinding.linearlayoutContainer.addView(trailerTextView);

        for (int i = 0; i < trailerList.size(); i++) {
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText("Trailer " + (i + 1));
            mBinding.linearlayoutContainer.addView(button);
            final String url = "https://www.youtube.com/watch?v=" + trailerList.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });
        }
        /*set reviews section*/
        TextView reviewsTextView = new TextView(this);
        reviewsTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        reviewsTextView.setText("Reviews");
        mBinding.linearlayoutContainer.addView(reviewsTextView);

        for (int i = 0; i < reviewsList.size(); i++) {
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText("Review " + (i + 1));
            if (i % 2 == 0) {
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
            mBinding.linearlayoutContainer.addView(button);
            final String url = reviewsList.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });
        }
    }
}
