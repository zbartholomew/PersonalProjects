package com.zachbartholomew.redditlurker.viewmodel;

import com.zachbartholomew.redditlurker.model.RedditLink;

import org.ocpsoft.prettytime.PrettyTime;

import lombok.Data;

/**
 * Exposing only the necessary fields that the view needs to display the post.
 * It also transforms the date into a string for display.
 * In this version of the app, it contains no operations - only properties.
 * Operations could include upvoting/downvoting, hiding, saving, and so on -
 * keeping all of the logic nicely separated from the view.
 */

@Data
public class PostViewModel {

    private String id;
    private String title;
    private String author;
    private String thumbnailUrl;
    private String createdOn;
    private String subreddit;
    private String domain;
    private int numComments;
    private int score;

    public PostViewModel(RedditLink redditLink) {
        this.id = redditLink.getId();
        this.title = redditLink.getTitle();
        this.author = redditLink.getAuthor();
        this.thumbnailUrl = redditLink.getThumbnail();
        this.subreddit = redditLink.getSubreddit();
        this.domain = redditLink.getDomain();
        this.numComments = redditLink.getNumComments();
        this.score = redditLink.getScore();

        // PrettyTime is an OpenSource time formatting library.
        // Completely customizable, it creates human readable, relative timestamps like those
        // seen on Twitter and Facebook.
        PrettyTime pt = new PrettyTime();
        this.createdOn = pt.format(redditLink.getCreated());
    }
}
