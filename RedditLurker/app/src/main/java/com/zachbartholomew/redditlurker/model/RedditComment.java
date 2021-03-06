package com.zachbartholomew.redditlurker.model;

import com.zachbartholomew.redditlurker.interfaces.RedditObject;

import java.util.Date;

import lombok.Data;

/**
 * Model for reddit comment section
 */

@Data
public class RedditComment implements RedditObject {

    private String id;
    private String body;
    private String bodyHtml;
    private String author;
    private String subredditId;
    private String linkId;
    private String parentId;
    private String score;
    private String ups;
    private String downs;
    private Date created;
    private Date createdUtc;
}
