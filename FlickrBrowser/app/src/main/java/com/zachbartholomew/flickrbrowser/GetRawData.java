package com.zachbartholomew.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

/**
 * Getting raw data from a url
 */

class GetRawData extends AsyncTask<String, Void, String> {

    private static final String TAG = GetRawData.class.getSimpleName();

    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallback;

    /**
     * Create a OnDownloadComplete interface so we can ensure there will be a callback from our
     * MainActivity after we implement it
     */
    interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete callback) {
        this.mDownloadStatus = mDownloadStatus.IDLE;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was " + response);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            // reads a line from the input stream - can use while or for loop
            // using the while loop initializes the String line outside of loop where as the for
            // loop initializes inside loop and is destroyed when exiting
//            String line;
//            while (null != (line = reader.readLine())) {
            // readLine strips newline character so we need to append it back on
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            mDownloadStatus = DownloadStatus.OK;

            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e);
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data: " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception.  Needs permission?" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage());
                }
            }
        }
        return null;
    }

    void runInSameThread(String s) {
        Log.d(TAG, "runInSameThread: starts");

        // warning calling override methods that also call super
//        onPostExecute(doInBackground(s));
        // if we had the above code uncommented and this code not in, if we were to change
        // the super method of onPostExecute to actually do something, then our code would break
        // the code below would not break in this case
        if (mCallback != null) {
            mCallback.onDownloadComplete(doInBackground(s), mDownloadStatus);
        }

        Log.d(TAG, "runInSameThread: ends");
    }

    @Override
    protected void onPostExecute(String s) {

//        Log.d(TAG, "onPostExecute: Parameter = " + s);

        if (mCallback != null) {
            mCallback.onDownloadComplete(s, mDownloadStatus);
        }

        Log.d(TAG, "onPostExecute: ends");
    }
}
