package com.zachbartholomew.flickrbrowser;

import java.io.Serializable;

/**
 * Will hold data pertaining to a specific photo
 */

class Photo implements Serializable{

    // need to add this so different versions of Java will be consistent
    // Java will usually auto assign this value, but different versions may assign this value
    // differently
    private static final long serialVersionUID = 1L;

    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mImage;
    private String mTags;

    Photo(String mTitle, String mAuthor, String mAuthorId,
          String mLink, String mTags, String mImage) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mAuthorId = mAuthorId;
        this.mLink = mLink;
        this.mTags = mTags;
        this.mImage = mImage;
    }

    String getTitle() {
        return mTitle;
    }

    String getAuthor() {
        return mAuthor;
    }

    String getAuthorId() {
        return mAuthorId;
    }

    String getLink() {
        return mLink;
    }

    String getImage() {
        return mImage;
    }

    String getTags() {
        return mTags;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mTags='" + mTags + '\'' +
                '}';
    }

}
