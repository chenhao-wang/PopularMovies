package com.example.android.popularmoviesstage1;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieInfo implements Parcelable {
    private String mTitle;
    private String mPostPath;
    private String mOverview;
    private String mVoteAverage;
    private String mReleaseDate;
    private String mId;
    private List<String> mVideoList;
    private List<String> mReviews;

    public MovieInfo(String title, String postPath, String overview, String voteAverage, String releaseDate, List<String> videoList, List<String> reviews, String id) {
        mTitle = title;
        mPostPath = postPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;

        mVideoList = videoList;
        mReviews = reviews;
        mId = id;
    }

    private MovieInfo(Parcel in) {
        mTitle = in.readString();
        mPostPath = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readString();
        mReleaseDate = in.readString();
        mVideoList = new ArrayList<>();
        in.readStringList(mVideoList);
        mReviews = new ArrayList<>();
        in.readStringList(mReviews);
        mId = in.readString();
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

    public List<String> getReviews() {
        return mReviews;
    }

    public List<String> getVideoList() {
        return mVideoList;
    }

    public String getId() {
        return mId;
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
        dest.writeStringList(mVideoList);
        dest.writeStringList(mReviews);
        dest.writeString(mId);
    }
}
