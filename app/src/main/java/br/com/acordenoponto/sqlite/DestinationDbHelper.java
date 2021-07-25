package br.com.acordenoponto.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DestinationDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Destination.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Destinations.DestinationEntry.TABLE_NAME + " (" +
                    Destinations.DestinationEntry._ID + " INTEGER PRIMARY KEY," +
                    Destinations.DestinationEntry.COLUMN_NAME_TITLE + " TEXT," +
                    Destinations.DestinationEntry.COLUMN_NAME_LAT + " REAL," +
                    Destinations.DestinationEntry.COLUMN_NAME_LONG + " REAL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Destinations.DestinationEntry.TABLE_NAME;

    public DestinationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
