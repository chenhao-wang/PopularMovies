package com.example.android.popularmoviesstage1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmoviesstage1.MainFragment.LOG_TAG;

public class QueryUtils {
    /*to avoid initializing this constructor*/
    private QueryUtils() {
    }

    public static List<MovieInfo> fetchMovieData(String movieURL) {
        URL url = createURL(movieURL);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream");
        }

        return extractMovieData(jsonResponse);
    }

    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Movie Json results");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputstream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputstream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<MovieInfo> extractMovieData(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<MovieInfo> movieList = new ArrayList<>();

        try {
            JSONObject movies = new JSONObject(jsonResponse);
            JSONArray results = movies.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject curtMovie = results.getJSONObject(i);
                String title = curtMovie.getString("title");
                String postPath = curtMovie.getString("poster_path");
                String overview = curtMovie.getString("overview");
                String voteAverage = curtMovie.getString("vote_average");
                String releaseDate = curtMovie.getString("release_date");

                movieList.add(new MovieInfo(title, postPath, overview, voteAverage, releaseDate));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing movie json results");
        }

        return movieList;
    }
}
