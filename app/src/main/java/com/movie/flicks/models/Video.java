package com.movie.flicks.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Video data model
 *
 * @author tejalpar
 */
public class Video implements Parcelable {
    public static final String PARCEL_KEY = "VIDEO_DATA";

    public int videoId;
    public int size;
    public String key;
    public String site;

    public Video() {}

    public Video(Parcel source) {
        // read values from parcel
        readFromParcel(source);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(videoId);
        dest.writeInt(size);
        dest.writeString(key);
        dest.writeString(site);
    }

    private void readFromParcel(Parcel source) {
        // read in same order as written
        videoId = source.readInt();
        size = source.readInt();
        key = source.readString();
        site = source.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR
            = new Parcelable.Creator<Video>() {

        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[0];
        }
    };
}
