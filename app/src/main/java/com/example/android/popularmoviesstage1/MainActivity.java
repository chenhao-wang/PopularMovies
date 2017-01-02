package com.example.android.popularmoviesstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.popularmoviesstage1.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<List<MovieInfo>> {
    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String PREFS_NAME = "FAVORITES";
    ActivityMainBinding mBinding;
    MovieAdapter mAdapter;
    private String prevOrderBy = "popular";
    private String curtOrderBy;
    private static final int MOVIELIST_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.recyclerViewMovieList.setLayoutManager(layoutManager);
        mBinding.recyclerViewMovieList.setHasFixedSize(true);

        mAdapter = new MovieAdapter(this, this, new ArrayList<MovieInfo>());
        mBinding.recyclerViewMovieList.setAdapter(mAdapter);

        mBinding.emptyView.setText(R.string.no_internet);

        getLoaderManager().initLoader(MOVIELIST_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_refresh:
                updateMovie();
                return true;
            case R.id.action_favorites:
                Intent favoriteIntent = new Intent(this, FavoritesActivity.class);
                startActivity(favoriteIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        Log.v(LOG_TAG, "onStart");
        super.onStart();
        if (mAdapter.equals(null) || mAdapter.getItemCount() == 0) {
            updateMovie();
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            curtOrderBy = prefs.getString(getString(R.string.settings_orderby_key),
                    getString(R.string.settings_orderby_popular_value));
            if (!prevOrderBy.equals(curtOrderBy)) {
                updateMovie();
                prevOrderBy = curtOrderBy;
            }
        }
    }

    private void updateMovie() {
        if (networkConnected()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            curtOrderBy = prefs.getString(getString(R.string.settings_orderby_key),
                    getString(R.string.settings_orderby_popular_value));
            getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, this);
        }
    }

    private boolean networkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            mBinding.recyclerViewMovieList.setVisibility(View.VISIBLE);
            mBinding.emptyView.setVisibility(View.GONE);
            return true;
        }
        mBinding.recyclerViewMovieList.setVisibility(View.GONE);
        mBinding.emptyView.setVisibility(View.VISIBLE);
        return false;
    }


    @Override
    public Loader<List<MovieInfo>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(this, curtOrderBy);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieInfo>> loader, List<MovieInfo> data) {
        mAdapter.updateData(null);
        if (data != null && !data.isEmpty()) {
            mAdapter.updateData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieInfo>> loader) {
        mAdapter.updateData(null);
    }

    @Override
    public void onClick(MovieInfo movieInfo) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("curtMovie", movieInfo);

        startActivity(detailIntent);
    }
}
