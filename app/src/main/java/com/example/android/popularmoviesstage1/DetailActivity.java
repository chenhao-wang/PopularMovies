package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("singleMovie");
        String title = hashMap.get("title");
        String postPath = hashMap.get("postPath");
        String overview = hashMap.get("overview");
        String voteAverage = hashMap.get("voteAverage") + "/10";
        String releaseDate = hashMap.get("releaseDate");

        TextView titleTextView = (TextView) findViewById(R.id.title_textview);
        ImageView postImageView = (ImageView) findViewById(R.id.post_image_view);
        TextView overviewTextView = (TextView) findViewById(R.id.overview_textview);
        TextView voteAverageTextView = (TextView) findViewById(R.id.vote_average_textview);
        TextView releaseDateTextView = (TextView) findViewById(R.id.release_date_textview);

        titleTextView.setText(title);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w780" + postPath).into(postImageView);
        overviewTextView.setText(overview);
        voteAverageTextView.setText(voteAverage);
        releaseDateTextView.setText(releaseDate);
    }
}
