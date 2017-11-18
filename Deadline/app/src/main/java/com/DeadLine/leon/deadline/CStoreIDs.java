package com.DeadLine.leon.deadline;

import android.app.Application;

/**
 * Created by Sean on 11/15/2017.
 */

public class CStoreIDs extends Application {
    private String m_szProjectID;
    private String m_szTaskID;
    private String m_szJobID;

    // ProjectID Get + Set
    public String getProjectID() { return m_szProjectID; }
    public void setProjectID(String szProjectID) { m_szProjectID = szProjectID; }

    // Task Get + Set
    public String getTaskID() { return m_szTaskID; }
    public void setTaskID(String szTaskID) { m_szTaskID = szTaskID;}

    // Job  Get + Set
    public String getJobID() { return m_szJobID; }
    public void setJobID(String szJobID) { m_szJobID = szJobID; }
}
