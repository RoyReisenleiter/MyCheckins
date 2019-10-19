package task2.task2.database;

import task2.task2.Checkins;
import task2.task2.database.CheckinsDbSchema.CrimeTable;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CheckinsCursorWrapper extends CursorWrapper {
    public CheckinsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Checkins getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        String place = getString(getColumnIndex(CrimeTable.Cols.PLACE));
        String details = getString(getColumnIndex(CrimeTable.Cols.DETAILS));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isLiked = getInt(getColumnIndex(CrimeTable.Cols.LIKED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
        int isDisliked = getInt(getColumnIndex(CrimeTable.Cols.DISLIKED));
        Double latitude = getDouble(getColumnIndex(CrimeTable.Cols.LATITUDE));
        Double longitude = getDouble(getColumnIndex(CrimeTable.Cols.LONGITUDE));

        Checkins checkins = new Checkins(UUID.fromString(uuidString));
        checkins.setTitle(title);
        checkins.setPlace(place);
        checkins.setDetails(details);
        checkins.setDate(new Date(date));
        checkins.setLiked(isLiked != 0);
        checkins.setDisliked(isDisliked != 0);
        checkins.setSuspect(suspect);
        checkins.setLongitude(longitude);
        checkins.setLatitude(latitude);

        return checkins;

        //return null;
    }
}
