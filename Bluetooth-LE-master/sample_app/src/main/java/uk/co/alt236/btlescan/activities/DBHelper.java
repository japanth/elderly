package uk.co.alt236.btlescan.activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC on 31/8/59.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "beacon";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "beacon";
    public static final String COL_ITEM_NAME = "item_name";
    public static final String COL_ADDRESS = "address";
    public static final String COL_DISTRANCE = "distrance";
    public static final String COL_MAJOR = "major";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM_NAME + " VARCHAR(255), " + COL_ADDRESS + " VARCHAR(255), " + COL_DISTRANCE + " VARCHAR(255), " + COL_MAJOR + " VARCHAR(255));");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
