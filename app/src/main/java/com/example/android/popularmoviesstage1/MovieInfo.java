package com.example.android.popularmoviesstage1;


public class MovieInfo {
    private String mTitle;
    private String mPostPath;
    private String mOverview;
    private String mVoteAverage;
    private String mReleaseDate;

    public MovieInfo(String mTitle, String mPostPath, String mOverview, String mVoteAverage, String mReleaseDate) {
        this.mTitle = mTitle;
        this.mPostPath = mPostPath;
        this.mOverview = mOverview;
        this.mVoteAverage = mVoteAverage;
        this.mReleaseDate = mReleaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPostPath() {
        return mPostPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
}
