package com.zachbartholomew.tasktimer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Basic database class.
 *
 * The only class that should use this is {@link TasksContentProvider}.
 */

public class TasksDbHelper extends SQLiteOpenHelper{

    private static final String TAG = TasksDbHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "TaskTimer.db";
    public static final int DATABASE_VERSION = 1;

    // Implement database as Singleton
    private static TasksDbHelper instance = null;

    // Singleton class - we only want one instance of the database
    private TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "TasksDbHelper: constructor");
    }

    /**
     * Get an instance of the database helper object
     * @param context the content providers context.
     * @return a SQLite database helper object
     */
    public static TasksDbHelper getInstance(Context context) {

        if (instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new TasksDbHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");

        // Create a table to hold tasks data
        final String sSQL;    // Use a string variable to facilitate logging
        sSQL = "CREATE TABLE " + TasksContract.TABLE_NAME + " (" +
                TasksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                TasksContract.Columns.TASKS_NAME + " TEXT NOT NULL, " +
                TasksContract.Columns.TASKS_DESCRIPTION + " TEXT, " +
                TasksContract.Columns.TASKS_SORTORDER + " INTEGER ASC" +
                "); ";

        Log.d(TAG, sSQL);

        db.execSQL(sSQL);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG, "onUpgrade: starts");

        switch (oldVersion) {
            case 1:
                // upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion " + newVersion);
        }

        Log.d(TAG, "onUpgrade: ends");
    }
}
