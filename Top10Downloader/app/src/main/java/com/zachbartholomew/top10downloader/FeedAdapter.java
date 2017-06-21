package com.zachbartholomew.top10downloader;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Custom adapter we need to bind to listview layout
 * This will allow us to better organize data into the listview
 */

class FeedAdapter<T extends FeedEntry> extends ArrayAdapter {

    private static final String TAG = FeedAdapter.class.getSimpleName();

    private final int layoutResource;
    private final LayoutInflater layoutInflator;
    private List<T> applications;

    public FeedAdapter(@NonNull Context context, @LayoutRes int resource, List<T> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflator = LayoutInflater.from(context);
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        // reuse views if available - this is more efficient then creating a view for every
        // feed entry
        if (convertView == null) {
            Log.d(TAG, "getView: called with null convertView");

            convertView = layoutInflator.inflate(layoutResource, parent, false);
            // use viewholder since findbyviewid is expensive to use and this allows us to only
            // search once instead of for each view
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            Log.d(TAG, "getView: provided a convertView");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // instead utilize the viewholder for efficiency see below (line 65-67)
        // viewHolder.tvName.setText(currentApp.getName());

//        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//        TextView tvArtist = (TextView) convertView.findViewById(R.id.tvArtist);
//        TextView tvSummary = (TextView) convertView.findViewById(R.id.tvSummary);


        T currentApp = applications.get(position);

        viewHolder.tvName.setText(currentApp.getName());
        viewHolder.tvArtist.setText(currentApp.getArtist());
        viewHolder.tvSummary.setText(currentApp.getSummary());

        return convertView;
    }

    private class ViewHolder {

        final TextView tvName;
        final TextView tvArtist;
        final TextView tvSummary;

        ViewHolder(View v) {
            this.tvName = (TextView) v.findViewById(R.id.tvName);
            this.tvArtist = (TextView) v.findViewById(R.id.tvArtist);
            this.tvSummary = (TextView) v.findViewById(R.id.tvSummary);
        }
    }
}
