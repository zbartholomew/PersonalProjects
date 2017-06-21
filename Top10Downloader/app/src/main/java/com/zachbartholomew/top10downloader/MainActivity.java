package com.zachbartholomew.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView listApps;
    // add %d for the limit so we can use a menu to change this value depending on what the
    // user wants to see (Top 10 or Top 25)
    private String feedUrl = "http://ax.itunes.apple.com" +
            "/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit = 10;
    private String feedCachedUrl = "INVALIDATED";
    public static final String STATE_URL = "feedUrl";
    public static final String STATE_LIMIT = "feedLimit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listApps = (ListView) findViewById(R.id.xmlListView);

//        Log.d(TAG, "onCreate: starting AsyncTask");

        // check if a bundle exists
        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString(STATE_URL);
            feedLimit = savedInstanceState.getInt(STATE_LIMIT);
        }

        // Get data from internet
        downloadUrl(String.format(feedUrl, feedLimit));

//        Log.d(TAG, "onCreate: done");
    }

    // create/inflate the options menu to appear
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);

        if (feedLimit == 10) {
            menu.findItem(R.id.menu10).setChecked(true);
        } else {
            menu.findItem(R.id.menu25).setChecked(true);
        }

        return true;
    }

    // create logic to execute when option menu items are selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menuFree:
                feedUrl = "http://ax.itunes.apple.com" +
                        "/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;

            case R.id.menuPaid:
                feedUrl = "http://ax.itunes.apple.com" +
                        "/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;

            case R.id.menuSongs:
                feedUrl = "http://ax.itunes.apple.com" +
                        "/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                break;

            case R.id.menu10:
            case R.id.menu25:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    feedLimit = 35 - feedLimit;
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() +
                            " setting feedLimit to " + feedLimit);
                } else {
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() +
                            " feedLimit unchanged");
                }
                break;

            case R.id.menuRefresh:
                feedCachedUrl = "INVALIDATED";
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        downloadUrl(String.format(feedUrl, feedLimit));

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save values so we dont lose info when rotation occurs
        outState.putString(STATE_URL, feedUrl);
        outState.putInt(STATE_LIMIT, feedLimit);

        super.onSaveInstanceState(outState);
    }

    // downloads data from url
    private void downloadUrl(String feedUrl) {
        //only download data if we have not downloaded the data before
        if (!feedUrl.equalsIgnoreCase(feedCachedUrl)) {
            Log.d(TAG, "downloadUrl: Starting AsyncTask");
            DownloadData downloadData = new DownloadData();
            downloadData.execute(feedUrl);
            feedCachedUrl = feedUrl;
            Log.d(TAG, "downloadUrl: done");
        } else {
            Log.d(TAG, "downloadUrl: URL not changed");
        }
    }

    // spawn new thread when trying to download data so UI thread does not get interrupted
    private class DownloadData extends AsyncTask<String, Void, String> {

        private final String TAG = DownloadData.class.getSimpleName();

        // retrieve the rssFeed url from the downloadData.execute(url) above
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: parameter is " + strings[0]);

            String rssFeed = downloadXML(strings[0]);

            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error Downloading");
            }

            return rssFeed;
        }

        // this runs on the main ui thread / no longer on background thread
        @Override
        protected void onPostExecute(String xmlData) {
            super.onPostExecute(xmlData);
//            Log.d(TAG, "onPostExecute: parameter is " + xmlData);

            // parsing the xml data and storing the info in an arraylist
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(xmlData);

            // create an array adapter to communicate with listview layout
            // we will populate the individual listview items with list_item (textview) xml layout
            // those lise_items will be populated from the arraylist stored in
            // parseApplications.getApplications
//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<>(
//                    MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);

            // create a custom adapter to have more control over listview
            FeedAdapter<FeedEntry> feedAdapter =
                    new FeedAdapter<>(MainActivity.this, R.layout.list_record,
                    parseApplications.getApplications());
            listApps.setAdapter(feedAdapter);

        }

        // downloading the xml from url logic
        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();

                Log.d(TAG, "downloadXML: The response code was " + response);

//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
                // chaining the above code together into one line
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charsRead = reader.read(inputBuffer);

                    // Ran out of characters to read so break
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();

            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IOException reading data: " + e.getMessage());
            }

            return null;
        }
    }
}
