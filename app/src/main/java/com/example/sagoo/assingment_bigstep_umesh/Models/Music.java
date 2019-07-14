package com.example.sagoo.assingment_bigstep_umesh.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Music implements Parcelable{

    private String artistName;
    private String trackName;
    private Double trackPrice;
    private String artistImgUrl;
    private String previewUrl;

    public Music() {
    }

    private Music(Parcel in) {
        artistName = in.readString();
        trackName = in.readString();
        trackPrice = in.readDouble();
        artistImgUrl = in.readString();
        previewUrl = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(Double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getArtistImgUrl() {
        return artistImgUrl;
    }

    public void setArtistImgUrl(String artistImgUrl) {
        this.artistImgUrl = artistImgUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artistName);
        parcel.writeString(trackName);
        parcel.writeDouble(trackPrice);
        parcel.writeString(artistImgUrl);
        parcel.writeString(previewUrl);
    }
}
