package com.example.chris.changiappv3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

/**
 * Created by chris on 25/11/2017.
 */

public class LocationDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private final Context context;
    private SQLiteDatabase sqLiteDatabase;


    public LocationDbHelper(Context context) {
        super(context, LocationsContract.LocationEntry.TABLE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + //
                LocationsContract.LocationEntry.TABLE_NAME + "("
                + LocationsContract.LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LocationsContract.LocationEntry.COL_AMOUNT + " TEXT NOT NULL ,"
                + LocationsContract.LocationEntry.COL_LOCATIONNAME + " TEXT NOT NULL );";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//TO DO 2.2 Build the SQLite command string to delete the table
        final String SQL_DROP_TABLE="DROP TABLE IF EXISTS "+LocationsContract.LocationEntry.TABLE_NAME;
//TO DO 2.3 execute this SQLite command
        this.sqLiteDatabase.execSQL(SQL_DROP_TABLE);
//TO DO 2.4 Create the table
        //recreate the recently destroyed table without the deleted value
        onCreate(sqLiteDatabase);
    }


    public Cursor getInformation(SQLiteDatabase db){
       String[] projection={LocationsContract.LocationEntry.COL_AMOUNT,LocationsContract.LocationEntry.COL_LOCATIONNAME};
       Cursor cursor=db.query(LocationsContract.LocationEntry.TABLE_NAME,projection,null,null,null,null,null);
    return cursor;
    }


}
