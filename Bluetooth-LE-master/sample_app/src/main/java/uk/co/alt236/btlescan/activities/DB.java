package uk.co.alt236.btlescan.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by PC on 31/8/59.
 */
public class DB {

    public static void insert(Context context, String address,String item_name,String distracne,String major ){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "INSERT INTO " + DBHelper.TABLE_NAME + "(" + DBHelper.COL_ITEM_NAME + ", " + DBHelper.COL_ADDRESS + ", " + DBHelper.COL_DISTRANCE  +","+DBHelper.COL_MAJOR +  ") VALUES ('" + item_name + "', '" + address + "', '" + distracne + "','"+major + "')";
        db.execSQL(sql);

        db.close();
        dbHelper.close();

    }

    public static String[] selectbeacon(Context context,String adrress ){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE "+ DBHelper.COL_ADDRESS + "= ? ", new String[]{adrress});

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAME));
            res[1] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DISTRANCE));
            cursor.moveToNext();
        }
        return res;
    }

    public static String[] selectListname(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT "+DBHelper.COL_ITEM_NAME +" FROM " + DBHelper.TABLE_NAME,null );

        cursor.moveToFirst();

        String[] name = new String[1];
        while (!cursor.isAfterLast()){
            name[0]= cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAME));

        }
        return  name;

    }

}
