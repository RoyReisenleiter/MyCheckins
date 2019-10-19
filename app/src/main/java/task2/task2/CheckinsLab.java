package task2.task2;

import task2.task2.database.CheckinsBaseHelper;
import task2.task2.database.CheckinsCursorWrapper;
import task2.task2.database.CheckinsDbSchema.CheckinsTable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckinsLab {
    private static CheckinsLab sCheckinsLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CheckinsLab get(Context context) {
        if (sCheckinsLab == null) {
            sCheckinsLab = new CheckinsLab(context);
        }
        return sCheckinsLab;
    }

    private CheckinsLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CheckinsBaseHelper(mContext)
                .getWritableDatabase();
    }

    public File getPhotoFile(Checkins checkins) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, checkins.getPhotoFilename());
    }

    public void addCheckin(Checkins c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CheckinsTable.NAME, null, values);
    }

    //stuff for delete crime
    public void deleteCheckin(Checkins c) {
        mDatabase.delete(
                CheckinsTable.NAME,
                CheckinsTable.Cols.UUID + "=?",
                new String[] {c.getId().toString()}
        );

    }


    public List<Checkins> getCheckins() {
        List<Checkins> checkins = new ArrayList<>();

        CheckinsCursorWrapper cursor = queryCheckins(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                checkins.add(cursor.getCheckin());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return checkins;
    }

    public Checkins getCheckin(UUID id) {
        CheckinsCursorWrapper cursor = queryCheckins(
                CheckinsTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCheckin();
        } finally {
            cursor.close();
        }


    }

    public void updateCheckin(Checkins checkins){
        String uuidString = checkins.getId().toString();
        ContentValues values = getContentValues(checkins);

        mDatabase.update(CheckinsTable.NAME, values,
                CheckinsTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private CheckinsCursorWrapper queryCheckins(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CheckinsTable.NAME,
                null, //columns - nul
                // l selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null // orderBy
        );

        //return cursor;
        return new CheckinsCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Checkins checkins) {
        ContentValues values = new ContentValues();
        values.put(CheckinsTable.Cols.UUID, checkins.getId().toString());
        values.put(CheckinsTable.Cols.UUID, checkins.getId().toString());
        values.put(CheckinsTable.Cols.TITLE, checkins.getTitle());
        values.put(CheckinsTable.Cols.PLACE, checkins.getPlace());
        values.put(CheckinsTable.Cols.DETAILS, checkins.getDetails());
        values.put(CheckinsTable.Cols.DATE, checkins.getDate().getTime());
        values.put(CheckinsTable.Cols.LIKED, checkins.isLiked() ? 1 : 0);
        values.put(CheckinsTable.Cols.DISLIKED, checkins.isDisliked() ? 1 : 0);
        values.put(CheckinsTable.Cols.SUSPECT, checkins.getSuspect());
        values.put(CheckinsTable.Cols.LONGITUDE, checkins.getLongitude());
        values.put(CheckinsTable.Cols.LATITUDE, checkins.getLatitude());

        return values;

    }



}
