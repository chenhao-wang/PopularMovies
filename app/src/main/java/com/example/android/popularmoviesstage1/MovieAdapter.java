package com.example.android.popularmoviesstage1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private final Context mContext;
    private final MovieAdapterOnClickHandler mClickHandler;
    private List<MovieInfo> mMovieList;


    public interface MovieAdapterOnClickHandler {
        void onClick(MovieInfo movieInfo);
    }

    public void updateData(List<MovieInfo> list) {
        mMovieList = list;
        notifyDataSetChanged();
    }

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler, List<MovieInfo> movieList) {
        mContext = context;
        mClickHandler = clickHandler;
        mMovieList = movieList;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false);
        view.setFocusable(true);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        MovieInfo curtMovie = mMovieList.get(position);
        String postPath = curtMovie.getPostPath();
        String postUrl = "https://image.tmdb.org/t/p/w780" + postPath;
        Picasso.with(mContext).load(postUrl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (mMovieList == null) {
            return 0;
        }
        return mMovieList.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(mMovieList.get(position));
        }
    }
}