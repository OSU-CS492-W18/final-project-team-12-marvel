package example.com.marvelsearch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew on 3/17/2018.
 */

public class MarvelSearchDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "marvelSearch.db";
    private static int DATABASE_VERSION = 1;

    public MarvelSearchDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SAVED_REPOS_TABLE =
                "CREATE TABLE " + MarvelSearchContract.SavedSearches.TABLE_NAME + "(" +
                        MarvelSearchContract.SavedSearches._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MarvelSearchContract.SavedSearches.COLUMN_SEARCH_TERM + " TEXT NOT NULL" +
                        ");";
        db.execSQL(SQL_CREATE_SAVED_REPOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MarvelSearchContract.SavedSearches.TABLE_NAME + ";");
        onCreate(db);
    }
}
