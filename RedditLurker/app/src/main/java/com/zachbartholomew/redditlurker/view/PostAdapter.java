package com.zachbartholomew.redditlurker.view;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zachbartholomew.redditlurker.R;
import com.zachbartholomew.redditlurker.viewmodel.PostViewModel;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides all binding to the RecyclerView
 * Utilizes a ViewHolder to cache the views
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<PostViewModel> items = new ArrayList<>();

    public PostAdapter() {
        setHasStableIds(true);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item_posts, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    public PostViewModel getItem(int position) {
        return items.get(position);
    }

    public void setItems(List<PostViewModel> items) {
        if (items == null) {
            return;
        }

        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class that caches our views
     */
    static class PostViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView titleTextView;
        private TextView subtitleTextView;
        private TextView subtitle2TextView;
        private ImageView thumbnailImageView;

        PostViewHolder(View view) {
            super(view);

            this.view = view;
            this.titleTextView = (TextView) view.findViewById(R.id.title);
            this.subtitleTextView = (TextView) view.findViewById(R.id.subtitle);
            this.subtitle2TextView = (TextView) view.findViewById(R.id.subtitle2);
            this.thumbnailImageView = (ImageView) view.findViewById(R.id.thumbnail);
        }

        @SuppressLint("DefaultLocale")
        void bind(PostViewModel viewModel) {
            titleTextView.setText(viewModel.getTitle());
            subtitleTextView.setText(String.format("%s - %s - %s (%s)", viewModel.getAuthor(), viewModel.getCreatedOn(), viewModel.getSubreddit(), viewModel.getDomain()));
            subtitle2TextView.setText(String.format("%d points - %d comments", viewModel.getScore(), viewModel.getNumComments()));

            UrlValidator urlValidator = new UrlValidator();
            boolean hasThumbnail = viewModel.getThumbnailUrl() != null && urlValidator.isValid(viewModel.getThumbnailUrl());

            // Show/hide the thumbnail if there is/isn't one
            thumbnailImageView.setVisibility(hasThumbnail ? View.VISIBLE : View.GONE);

            // Load the thumbnail if there is one
            if (hasThumbnail) {
                Picasso.with(view.getContext()).load(viewModel.getThumbnailUrl()).into(thumbnailImageView);
            }
        }
    }
}
