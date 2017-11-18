package com.DeadLine.leon.deadline;

import java.util.Comparator;

/**
 * Created by Sean on 11/16/2017.
 */

public class CDeadlineComparator implements Comparator<CDeadline> {
    @Override
    public int compare(CDeadline o1, CDeadline o2) {
        return o1.getDeadline().compareTo(o2.getDeadline());
    }
}
