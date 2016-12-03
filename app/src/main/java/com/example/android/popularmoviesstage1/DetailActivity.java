package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        MovieInfo curtMovie = intent.getParcelableExtra("curtMovie");
        String title = curtMovie.getTitle();
        String postPath = curtMovie.getPostPath();
        String overview = curtMovie.getOverview();
        String voteAverage = curtMovie.getVoteAverage() + "/10";
        String releaseDate = curtMovie.getReleaseDate();

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
