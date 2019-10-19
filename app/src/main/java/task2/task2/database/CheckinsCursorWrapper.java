package task2.task2.database;

import task2.task2.Checkins;
import task2.task2.database.CheckinsDbSchema.CheckinsTable;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CheckinsCursorWrapper extends CursorWrapper {
    public CheckinsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Checkins getCheckin() {
        String uuidString = getString(getColumnIndex(CheckinsTable.Cols.UUID));
        String title = getString(getColumnIndex(CheckinsTable.Cols.TITLE));
        String place = getString(getColumnIndex(CheckinsTable.Cols.PLACE));
        String details = getString(getColumnIndex(CheckinsTable.Cols.DETAILS));
        long date = getLong(getColumnIndex(CheckinsTable.Cols.DATE));
        int isLiked = getInt(getColumnIndex(CheckinsTable.Cols.LIKED));
        String suspect = getString(getColumnIndex(CheckinsTable.Cols.SUSPECT));
        int isDisliked = getInt(getColumnIndex(CheckinsTable.Cols.DISLIKED));
        Double latitude = getDouble(getColumnIndex(CheckinsTable.Cols.LATITUDE));
        Double longitude = getDouble(getColumnIndex(CheckinsTable.Cols.LONGITUDE));

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
