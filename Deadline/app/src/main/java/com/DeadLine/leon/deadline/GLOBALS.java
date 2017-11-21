package com.DeadLine.leon.deadline;

/**
 * Created by Leon on 11/15/17.
 */

public class GLOBALS {
    private int colorID = -1;
    public int getColorID() {return colorID;}
    public void setColorID(int _id) {colorID = _id;}

    public CDeadline[] deadlines;
    public int[] IDArray;

    public CDeadline[] getDeadlines() {return deadlines;}
    public void setDeadlines(CDeadline[] _deadlines) {deadlines = _deadlines;}

    public int[] getIDArray() { return IDArray;}
    public void setIDArray(int[] _IDArray) {IDArray = _IDArray; }


    public GLOBALS()
    {

    }
}
