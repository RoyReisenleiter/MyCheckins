package task2.task2;
//new version for task 2*

import java.util.Date;
import java.util.UUID;

public class Checkins {

    private UUID mId;
    private String mTitle;
    private String mPlace;
    private String mDetails;
    private Date mDate;
    private boolean mLiked;
    private boolean mDisliked;
    private String mSuspect;
    private Double mLatitude;
    private Double mLongitude;

    public Checkins() {
        this(UUID.randomUUID());

    }

    public Checkins(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPlace() {
        return mPlace;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
    public void setPlace(String place) {
        mPlace = place;
    }
    public void setDetails(String details) {
        mDetails = details;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isLiked() {
        return mLiked;
    }
    public boolean isDisliked() { return mDisliked; }
    public void setLiked(boolean solved) {
        mLiked = solved;
    }
    public void setDisliked(boolean disliked) {
        mDisliked = disliked;
    }

    public Double getLatitude() { return mLatitude; }
    public Double getLongitude() { return mLongitude; }
    public void setLatitude(Double latitude) {mLatitude = latitude;}
    public void setLongitude(Double longitude) {mLongitude = longitude;}


    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getPhotoFilename() {

        return "IMG_" + getId().toString() + ".jpg";
    }

}
