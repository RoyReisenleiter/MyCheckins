package task2.task2;
//new version for task 2*
// original package name: android.bignerdranch.criminalIntent
import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private String mPlace;
    private String mDetails;
    private Date mDate;
    private boolean mSolved;
    private boolean mDisliked;
    private String mSuspect;
    private Double mLatitude;
    private Double mLongitude;

    public Crime() {
        this(UUID.randomUUID());
        //mId = UUID.randomUUID();
        //mDate = new Date();
    }

    public Crime(UUID id) {
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

    public boolean isSolved() {
        return mSolved;
    }
    public boolean isDisliked() { return mDisliked; }
    public void setSolved(boolean solved) {
        mSolved = solved;
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
