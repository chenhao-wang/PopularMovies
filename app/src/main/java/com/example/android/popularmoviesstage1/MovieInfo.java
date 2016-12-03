package com.example.android.popularmoviesstage1;


import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable {
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

    private MovieInfo(Parcel in) {
        mTitle = in.readString();
        mPostPath = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readString();
        mReleaseDate = in.readString();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPostPath);
        dest.writeString(mOverview);
        dest.writeString(mVoteAverage);
        dest.writeString(mReleaseDate);
    }
}
