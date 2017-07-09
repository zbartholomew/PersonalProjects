package com.zachbartholomew.tasktimer.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zachbartholomew.tasktimer.R;
import com.zachbartholomew.tasktimer.adapters.CursorRecyclerViewAdapter;

import java.security.InvalidParameterException;

import static com.zachbartholomew.tasktimer.data.TasksContract.CONTENT_URI;
import static com.zachbartholomew.tasktimer.data.TasksContract.Columns.TASKS_DESCRIPTION;
import static com.zachbartholomew.tasktimer.data.TasksContract.Columns.TASKS_NAME;
import static com.zachbartholomew.tasktimer.data.TasksContract.Columns.TASKS_SORTORDER;
import static com.zachbartholomew.tasktimer.data.TasksContract.Columns._ID;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    public static final int LOADER_ID = 0;

    // add adapter reference
    private CursorRecyclerViewAdapter mAdapter;

    public MainActivityFragment() {
        Log.d(TAG, "MainActivityFragment: constructor called");
    }

    // get the loader manager when activity is created
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // no data yet for adapter - not until loader calls onLoadFinished
        mAdapter = new CursorRecyclerViewAdapter(null,
                (CursorRecyclerViewAdapter.OnTaskClickListener) getActivity());
        recyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreateView: returning");
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: starts with id " + id);

        String[] projection = {_ID, TASKS_NAME, TASKS_DESCRIPTION, TASKS_SORTORDER};
        // <order by> Tasks.SortOrder, Tasks.Name COLLATE NOCASE - we want to ignore case
        // sensitive sorting
        String sortOrder = TASKS_SORTORDER + "," + TASKS_NAME + " COLLATE NOCASE";

        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder);

            default:
                throw new InvalidParameterException(TAG +
                        ".onCreateLoader called with invalid loader id " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: Entering onLoadFinished");

        mAdapter.swapCursor(data);
        int count = mAdapter.getItemCount();

        // used for testing
//        if (data != null) {
//            while (data.moveToNext()) {
//                for (int i = 0; i < data.getColumnCount(); i++) {
//                    Log.d(TAG, "onLoadFinished: " + data.getColumnName(i) + ":" + data.getString(i));
//                }
//                Log.d(TAG, "onLoadFinished: ========================");
//            }
//            count = data.getCount();
//        }
        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mAdapter.swapCursor(null);
    }
}
