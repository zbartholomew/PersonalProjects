package com.zachbartholomew.redditlurker.model;

import com.zachbartholomew.redditlurker.interfaces.RedditObject;

import java.util.List;

/**
 * The top posts response is firstly made up of a 'listing' parent element,
 * that contains a list of children (which are links), as well as before and after
 * tokens for pagination.
 */

//@Data // provides getters/settings and all necessary boilerplate code
public class RedditListing implements RedditObject {

    private List<RedditObject> children;
    private String before;
    private String after;

    public List<RedditObject> getChildren() {
        return children;
    }

    public String getBefore() {
        return before;
    }


    public String getAfter() {
        return after;
    }

}
