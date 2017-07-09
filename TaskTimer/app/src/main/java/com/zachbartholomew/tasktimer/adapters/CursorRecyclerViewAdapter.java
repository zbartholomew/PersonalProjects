package com.zachbartholomew.tasktimer.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zachbartholomew.tasktimer.R;
import com.zachbartholomew.tasktimer.Task;

import static com.zachbartholomew.tasktimer.data.TasksContract.Columns.TASKS_DESCRIPTION;
import static com.zachbartholomew.tasktimer.data.TasksContract.Columns.TASKS_NAME;
import static com.zachbartholomew.tasktimer.data.TasksContract.Columns.TASKS_SORTORDER;
import static com.zachbartholomew.tasktimer.data.TasksContract.Columns._ID;

/**
 * Provides all the binding to the RecyclerView
 * Utilizes a ViewHolder to cache the views.
 */

public class CursorRecyclerViewAdapter extends
        RecyclerView.Adapter<CursorRecyclerViewAdapter.TaskViewHolder> {

    private static final String TAG = CursorRecyclerViewAdapter.class.getSimpleName();

    private Cursor mCursor;
    private OnTaskClickListener mListener;

    public interface OnTaskClickListener {
        void onEditClick(Task task);

        void onDeleteClick(Task task);
    }

    public CursorRecyclerViewAdapter(Cursor cursor, OnTaskClickListener listener) {
//        Log.d(TAG, "CursorRecyclerViewAdapter: Constructor called");
        mCursor = cursor;
        mListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.task_list_items, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: starts");

        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            Log.d(TAG, "onBindViewHolder: providing instructions");

            holder.name.setText(R.string.instructions_heading);
            holder.description.setText(R.string.instructions);

            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }

            final Task task = new Task(mCursor.getLong(mCursor.getColumnIndex(_ID)),
                    mCursor.getString(mCursor.getColumnIndex(TASKS_NAME)),
                    mCursor.getString(mCursor.getColumnIndex(TASKS_DESCRIPTION)),
                    mCursor.getInt(mCursor.getColumnIndex(TASKS_SORTORDER)));

            holder.name.setText(task.getName());
            holder.description.setText(task.getDescription());
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d(TAG, "onClick: starts");

                    switch (v.getId()) {
                        case R.id.tli_edit:
                            if (mListener != null) {
                                mListener.onEditClick(task);
                            }
                            break;

                        case R.id.tli_delete:
                            if (mListener != null) {
                                mListener.onDeleteClick(task);
                            }
                            break;

                        default:
                            throw new IllegalArgumentException("Found unexpected button id");
                    }

//                    Log.d(TAG, "onClick: button with id " + v.getId() + " clicked");
//                    Log.d(TAG, "onClick: task name is " + task.getName());
                }
            };
            holder.editButton.setOnClickListener(buttonListener);
            holder.deleteButton.setOnClickListener(buttonListener);
        }
    }

    // recycler view uses to see how many items to display
    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: starts");

        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            return 1;
        } else {
            return mCursor.getCount();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.
     * The returned old Cursor is <em>not</em> closed.
     *
     * @param newCursor The new Cursor to be used
     * @return Returns the previously set Cursor, or null if there wasn't one.
     * Cursor, null is also returned
     */
    // boilerplate code - called with the cursor the adapter is using has changed
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;

        if (newCursor != null) {
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            // notify the observers about the lack of a data set
            notifyItemRangeRemoved(0, getItemCount());
        }

        return oldCursor;
    }

    // viewholder to cache our views
    static class TaskViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = TaskViewHolder.class.getSimpleName();

        TextView name = null;
        TextView description = null;
        ImageView editButton = null;
        ImageView deleteButton = null;

        public TaskViewHolder(View itemView) {
            super(itemView);
//            Log.d(TAG, "TaskViewHolder: starts");

            this.name = (TextView) itemView.findViewById(R.id.tli_name);
            this.description = (TextView) itemView.findViewById(R.id.tli_description);
            this.editButton = (ImageView) itemView.findViewById(R.id.tli_edit);
            this.deleteButton = (ImageView) itemView.findViewById(R.id.tli_delete);
        }
    }
}
