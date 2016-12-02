package com.example.android.popularmoviesstage1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<MovieInfo> {

    public MovieAdapter(Context context, List<MovieInfo> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        ViewHolder viewHolder;
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.imageView = (ImageView) gridItemView.findViewById(R.id.image_movie);
            gridItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) gridItemView.getTag();
        }

        MovieInfo curtMovie = getItem(position);
        String postPath = curtMovie.getPostPath();

        String postUrl = "https://image.tmdb.org/t/p/w780" + postPath;
        Picasso.with(getContext()).load(postUrl).into(viewHolder.imageView);

        return gridItemView;
    }

    public static class ViewHolder{
        ImageView imageView;
    }
}
