package com.zachbartholomew.tasktimer.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zachbartholomew.tasktimer.BuildConfig;
import com.zachbartholomew.tasktimer.R;
import com.zachbartholomew.tasktimer.Task;
import com.zachbartholomew.tasktimer.TaskDialog;
import com.zachbartholomew.tasktimer.adapters.CursorRecyclerViewAdapter;
import com.zachbartholomew.tasktimer.data.TasksContract;
import com.zachbartholomew.tasktimer.fragments.AddEditActivityFragment;

import static com.zachbartholomew.tasktimer.TaskDialog.DIALOG_ID;

// import android.support.v1.app.AlertDialog;   allows are dialog to use new styling

public class MainActivity extends AppCompatActivity
        implements CursorRecyclerViewAdapter.OnTaskClickListener,
        AddEditActivityFragment.OnSaveClicked,
        TaskDialog.DialogEvents {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Whether the activity is in two pane mode
    private boolean mTwoPane = false;

    public static final int DIALOG_ID_DELETE = 1;
    private static final int DIALOG_ID_CANCEL_EDIT = 2;

    // module scope because we need to dismiss it in onStop
    // when orientation changes - this will avoid memory leaks.
    private AlertDialog mAlertDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.task_detail_container) != null) {
            // The detail container view will be present only in large screen layouts
            // (res/values-land and res/values-sw600dp
            mTwoPane = true;
        }
    }

    @Override
    public void onSaveClicked() {
        Log.d(TAG, "onSaveClicked: starts");

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.task_detail_container);

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Logic when menu options are selected
        switch (id) {
            case R.id.menumain_addTask:
                taskEditRequest(null);
                break;

            case R.id.menumain_showAbout:
                showAboutDialog();
                break;

            case R.id.menumain_generate:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void showAboutDialog() {
        // Using custom dialog so we need to inflate the view then populate
        @SuppressLint("InflateParams") View messageView = getLayoutInflater().inflate(R.layout.about, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setView(messageView);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }
            }
        });

        // create dialog and store reference in mAlertDialog
        mAlertDialog = builder.create();
        mAlertDialog.setCanceledOnTouchOutside(true);

        TextView textView = (TextView) messageView.findViewById(R.id.about_version);
        textView.setText("v" + BuildConfig.VERSION_NAME);

        // Set the about_web_url xml as a clickable link for pre api21 version phones
        // in api21 and above the aboutUrl textview will not be found so the listener will never be
        // set
        TextView aboutUrl = (TextView) messageView.findViewById(R.id.about_url);
        if (aboutUrl != null) {
            aboutUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String s = ((TextView) v).getText().toString();
                    intent.setData(Uri.parse(s));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(MainActivity.this,
                                "No browser application found",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        mAlertDialog.show();
    }

    @Override
    public void onEditClick(Task task) {
        taskEditRequest(task);
    }

    @Override
    public void onDeleteClick(Task task) {
        Log.d(TAG, "onDeleteClick: starts");

        // creating dialog and setting a dialog to popup before deleting
        TaskDialog dialog = new TaskDialog();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ID, DIALOG_ID_DELETE);
        args.putString(TaskDialog.DIALOG_MESSAGE,
                getString(R.string.deldiag_message, task.getId(), task.getName()));
        args.putInt(TaskDialog.DIALOG_POSITIVE_RID, R.string.deldiag_positive_caption);

        // need to add the task id so we can use in onPositiveDialogResult
        args.putLong("TaskId", task.getId());

        dialog.setArguments(args);
//        dialog.show(getFragmentManager(), null);  can't use because we need to support pre api23
        dialog.show(getSupportFragmentManager(), null);
    }

    private void taskEditRequest(Task task) {
        Log.d(TAG, "taskEditRequest: starts");

        if (mTwoPane) {
            Log.d(TAG, "taskEditRequest: in two pane mode");

            AddEditActivityFragment fragment = new AddEditActivityFragment();

            // passing the edit text fields as a bundle so we maintain the values entered
            Bundle arguments = new Bundle();
            arguments.putSerializable(Task.class.getSimpleName(), task);
            fragment.setArguments(arguments);

            // Add FragmentManager to open a FragmentTransaction following this resource
            // https://developer.android.com/guide/components/fragments.html
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.task_detail_container, fragment)
                    .commit();

        } else {
            Log.d(TAG, "taskEditRequest: in single pane mode");
            // in single pane mode, start the detail activity for the selected item id
            Intent detailIntent = new Intent(this, AddEditActivity.class);

            if (task != null) { // editing a task
                detailIntent.putExtra(Task.class.getSimpleName(), task);
                startActivity(detailIntent);
            } else { // adding a task
                startActivity(detailIntent);
            }
        }
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: called");

        // check which dialog id is being shown
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                // get task id from bundle
                Long taskId = args.getLong("TaskId");
                if (BuildConfig.DEBUG && taskId == 0) throw new AssertionError("Task ID is zero");
                // delete task
                getContentResolver().delete(TasksContract.buildTaskUri(taskId), null, null);
                break;
            case DIALOG_ID_CANCEL_EDIT:
                // no action required
                break;
        }
    }

    @Override
    public void onNegativeDialogResults(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResults: called");

        // check which dialog id is being shown
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                // no action required
                break;
            case DIALOG_ID_CANCEL_EDIT:
                finish();
                break;
        }
    }

    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled: called");
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: called");

        // get reference to fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditActivityFragment fragment = (AddEditActivityFragment) fragmentManager.findFragmentById(R.id.task_detail_container);

        // check to see if fragment has data that is not saved and whether it is safe to close
        if ((fragment == null) || fragment.canClose()) {
            super.onBackPressed();
        } else {
            // show dialog to get confirmation to quit editing
            TaskDialog dialog = new TaskDialog();
            Bundle args = new Bundle();
            args.putInt(DIALOG_ID, DIALOG_ID_CANCEL_EDIT);
            args.putString(TaskDialog.DIALOG_MESSAGE, getString(R.string.cancelEditDiag_message));
            args.putInt(TaskDialog.DIALOG_POSITIVE_RID, R.string.cancelEditDiag_positive_caption);
            args.putInt(TaskDialog.DIALOG_NEGATIVE_RID, R.string.cancelEditDiag_negative_caption);

            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // this prevent memory leak if dialog box is showing and screen rotation occurs
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }
}
