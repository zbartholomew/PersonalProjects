package com.zachbartholomew.tasktimer.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.zachbartholomew.tasktimer.R;
import com.zachbartholomew.tasktimer.TaskDialog;
import com.zachbartholomew.tasktimer.fragments.AddEditActivityFragment;

import static com.zachbartholomew.tasktimer.TaskDialog.DIALOG_ID;

public class AddEditActivity extends AppCompatActivity
        implements AddEditActivityFragment.OnSaveClicked,
        TaskDialog.DialogEvents{

    private static final String TAG = AddEditActivity.class.getSimpleName();

    private static final int DIALOG_ID_CANCEL_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddEditActivityFragment fragment = new AddEditActivityFragment();

        Bundle arguments = getIntent().getExtras();
        fragment.setArguments(arguments);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // content_add_edit layout
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: home button pressed");
                AddEditActivityFragment fragment = (AddEditActivityFragment)
                        getSupportFragmentManager().findFragmentById(R.id.fragment);

                if (fragment.canClose()) {
                    return super.onOptionsItemSelected(item);
                } else {
                    showConfirmationDialog();
                    return true;    // indicate we are handling this
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSaveClicked() {
        finish();
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: called");
    }

    @Override
    public void onNegativeDialogResults(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResults: called");
        finish();
    }

    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled: called");
    }

    private void showConfirmationDialog() {
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

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: called");

        // get reference to fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditActivityFragment fragment = (AddEditActivityFragment) fragmentManager.findFragmentById(R.id.fragment);

        // check to see if fragment has data that is not saved and whether it is safe to close
        if (fragment.canClose()) {
            super.onBackPressed();
        } else {
            showConfirmationDialog();
        }
    }
}
