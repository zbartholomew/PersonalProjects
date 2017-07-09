package com.zachbartholomew.tasktimer;

import java.io.Serializable;

/**
 * Fields will store each column of the database and getters
 */

public class Task implements Serializable {

    public static final long serialVersionUID = 20170704L;

    private long m_ID;
    private final String mName;
    private final String mDescription;
    private final int mSortOrder;

    public Task(long id, String name, String description, int sortOrder) {
        this.m_ID = id;
        mName = name;
        mDescription = description;
        mSortOrder = sortOrder;
    }

    public long getId() {
        return m_ID;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getSortOrder() {
        return mSortOrder;
    }

    public void setId(long id) {
        this.m_ID = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "m_ID=" + m_ID +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSortOrder=" + mSortOrder +
                '}';
    }
}
