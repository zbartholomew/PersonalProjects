package com.zachbartholomew.tasktimer.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Provider for TaskTimer app.  This is the only class that knows about
 * the database {@link TasksDbHelper}
 */

public class TasksContentProvider extends ContentProvider {

    private static final String TAG = TasksContentProvider.class.getSimpleName();

    private TasksDbHelper mTasksDbHelper;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    // The authority, which is how your code knows which Content Provider to access
    static final String CONTENT_AUTHORITY = "com.zachbartholomew.tasktimer.data.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Define final integer constants for the directory of tasks and a single item.
    private static final int TASKS = 100;
    private static final int TASKS_ID = 101;

    /**
     * Initialize a new matcher object without any matches,
     * then use .addURI(String authority, String path, int match) to add matches
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // eg. content://com.zachbartholomew.tasktimer.data.provider/Tasks
        uriMatcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS);
        // eg. content://com.zachbartholomew.tasktimer.data.provider/Tasks/#
        uriMatcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME + "/#", TASKS_ID);

//        uriMatcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME, TIMINGS);
//        uriMatcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME + "/#", TIMINGS_ID);
//
//        uriMatcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME, TASK_DURATION);
//        uriMatcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME + "/#", TASK_DURATION_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mTasksDbHelper = TasksDbHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Log.d(TAG, "query: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case TASKS:
                queryBuilder.setTables(TasksContract.TABLE_NAME);
                break;

            case TASKS_ID:
                queryBuilder.setTables(TasksContract.TABLE_NAME);
                long taskId = TasksContract.getTaskId(uri);
                queryBuilder.appendWhere(TasksContract.Columns._ID + " = " + taskId);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = mTasksDbHelper.getReadableDatabase();

        // add this code for more predictable results when query data
        // w/o this code if we clicked back from the android device (after saving a new task),
        // the new task would not show up in our task list (recycler view).  We need to notify the
        // observers - like we did in insert/update/delete below, so we setup the notification here
        // to any listeners that are registered and Cursor loaders are automatically registered
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case TASKS:
                return TasksContract.CONTENT_TYPE;

            case TASKS_ID:
                return TasksContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: called with uri: " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "insert: match is " + match);

        final SQLiteDatabase db;

        Uri returnUri;
        long recordId;

        switch (match) {
            case TASKS:
                db = mTasksDbHelper.getWritableDatabase();
                recordId = db.insert(TasksContract.TABLE_NAME, null, values);

                if (recordId >= 0) {
                    returnUri = TasksContract.buildTaskUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;

            default:
                throw new IllegalArgumentException("unknown Uri: " + uri);
        }

        // added this to notify observers that data has been inserted
        if (recordId >= 0) {
            //something has been inserted
            Log.d(TAG, "insert: Setting notifyChanged with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "insert: nothing inserted");
        }
        Log.d(TAG, "insert: exiting and returning " + returnUri);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "update: match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case TASKS:
                db = mTasksDbHelper.getWritableDatabase();
                count = db.delete(TasksContract.TABLE_NAME, selection, selectionArgs);
                break;

            case TASKS_ID:
                db = mTasksDbHelper.getWritableDatabase();
                long taskId = TasksContract.getTaskId(uri);
                selectionCriteria = TasksContract.Columns._ID + "=" + taskId;

                // append additional selection criteria
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }

                count = db.delete(TasksContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("unknown Uri: " + uri);
        }

        // added this to notify observers that data has been deleted
        if (count > 0) {
            //something was deleted
            Log.d(TAG, "delete: Setting notifyChange with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "delete: nothing deleted");
        }
        Log.d(TAG, "update: exiting and returning " + count);

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        Log.d(TAG, "update: called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "update: match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case TASKS:
                db = mTasksDbHelper.getWritableDatabase();
                count = db.update(TasksContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case TASKS_ID:
                db = mTasksDbHelper.getWritableDatabase();
                long taskId = TasksContract.getTaskId(uri);
                selectionCriteria = TasksContract.Columns._ID + "=" + taskId;

                // append additional selection criteria
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }

                count = db.update(TasksContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("unknown Uri: " + uri);
        }

        // added this to notify observers that data has been updated
        if (count > 0) {
            //something was updated
            Log.d(TAG, "delete: Setting notifyChange with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "delete: nothing updated");
        }
        Log.d(TAG, "update: exiting and returning " + count);

        return count;
    }
}