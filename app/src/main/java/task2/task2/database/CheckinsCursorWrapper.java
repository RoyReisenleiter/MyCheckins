package task2.task2.database;

import task2.task2.Crime;
import task2.task2.database.CheckinsDbSchema.CrimeTable;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CheckinsCursorWrapper extends CursorWrapper {
    public CheckinsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
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

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setPlace(place);
        crime.setDetails(details);
        crime.setDate(new Date(date));
        crime.setLiked(isLiked != 0);
        crime.setDisliked(isDisliked != 0);
        crime.setSuspect(suspect);
        crime.setLongitude(longitude);
        crime.setLatitude(latitude);

        return crime;

        //return null;
    }
}
