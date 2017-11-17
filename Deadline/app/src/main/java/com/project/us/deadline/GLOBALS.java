package com.project.us.deadline;

/**
 * Created by Leon on 11/15/17.
 */

public class GLOBALS {
    private int colorID = -1;
    public int getColorID() {return colorID;}
    public void setColorID(int _id) {colorID = _id;}

    public CDeadline[] deadlines;
    public CDeadline[] getDeadlines() {return deadlines;}
    public void setDeadlines(CDeadline[] _deadlines) {deadlines = _deadlines;}


    public GLOBALS()
    {

    }
}
