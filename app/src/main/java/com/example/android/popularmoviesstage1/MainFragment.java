package com.example.android.popularmoviesstage1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment{
    public static final String LOG_TAG = MainFragment.class.getName();
    MovieAdapter mAdapter;
    private TextView mEmptyView;
    private String mMovieUrl;
    private final String BASE_MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    private String mOrderby = "popular";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mainfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_refresh:
                updateMovie();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter.isEmpty()) {
            updateMovie();
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String orderBy = prefs.getString(getString(R.string.settings_orderby_key),
                    getString(R.string.settings_orderby_popular_value));
            if (!mOrderby.equals(orderBy)) {
                updateMovie();
                mOrderby = orderBy;
            }
        }
    }

    private void updateMovie() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.v(LOG_TAG, "update movie successfully");
            FetchMovieTask fetchMovieTask = new FetchMovieTask();
            mMovieUrl = getFinalUrl();
            fetchMovieTask.execute(mMovieUrl);
        } else {
            mEmptyView.setText(R.string.no_internet);
        }
    }

    private String getFinalUrl() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String orderBy = prefs.getString(getString(R.string.settings_orderby_key),
                getString(R.string.settings_orderby_popular_value));

        String MOVIE_URL = BASE_MOVIE_URL + orderBy + "?";
        final String APPID_PARAM = "api_key";
        Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIE_MAP_API_KEY)
                .build();
        return builtUri.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new MovieAdapter(getContext(), new ArrayList<MovieInfo>());
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_movie);
        gridView.setAdapter(mAdapter);

        mEmptyView = (TextView) rootView.findViewById(R.id.empty_view);
        gridView.setEmptyView(mEmptyView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                MovieInfo curtMovie = mAdapter.getItem(position);
                detailIntent.putExtra("curtMovie", curtMovie);

                startActivity(detailIntent);
            }
        });
        return rootView;
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<MovieInfo>> {

        @Override
        protected List<MovieInfo> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            return QueryUtils.fetchMovieData(urls[0]);
        }

        @Override
        protected void onPostExecute(List<MovieInfo> movieInfos) {
            mAdapter.clear();
            if (movieInfos != null && !movieInfos.isEmpty()) {
                mAdapter.addAll(movieInfos);
            }
        }
    }
}
