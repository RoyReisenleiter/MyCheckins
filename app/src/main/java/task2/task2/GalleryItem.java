package task2.task2;

import android.net.Uri;

public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;
    private double mLat;
    private double mLon;
    private String mOwner;

    public void setUrl(String url) {
        mUrl = url;
    }
    public String getOwner() {
        return mOwner;
    }
    public void setOwner(String owner) {
        mOwner = owner;
    }
    public Uri getPhotoPageUri() {
        return Uri.parse("https://www.flickr.com/photos/")
                .buildUpon()
                .appendPath(mOwner)
                .appendPath(mId)
                .build();
    }
    public double getLat() {
        return mLat;
    }
    public void setLat(double lat) {
        mLat = lat;
    }
    public double getLon() {
        return mLon;
    }
    public void setLon(double lon) {
        mLon = lon;
    }


    @Override
    public String toString() {
        return mCaption;
    }

}
