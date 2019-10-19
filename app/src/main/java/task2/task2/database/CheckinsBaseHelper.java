package task2.task2.database;

import task2.task2.database.CheckinsDbSchema.CheckinsTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CheckinsBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "checkinBase.db";

    public CheckinsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CheckinsTable.NAME  + "(" +
                " _id integer primary key autoincrement, " +
                CheckinsTable.Cols.UUID + ", " +
                CheckinsTable.Cols.TITLE + ", " +
                CheckinsTable.Cols.DATE + ", " +
                CheckinsTable.Cols.LIKED + ", " +
                CheckinsTable.Cols.DISLIKED + ", " +
                CheckinsTable.Cols.SUSPECT + ", " +
                CheckinsTable.Cols.PLACE + "," +
                CheckinsTable.Cols.DETAILS + "," +
                CheckinsTable.Cols.LONGITUDE + "," +
                CheckinsTable.Cols.LATITUDE +
                ")"

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
