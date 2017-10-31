package com.example.leon.deadline;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sean on 10/26/2017.
 */

public class CDeadline implements Serializable {
    private String m_szName;
    private Date m_Deadline;

    public CDeadline() {
    }

    public CDeadline(String Name, Date Deadline) {
        this.m_szName = Name;
        this.m_Deadline = Deadline;
    }

    // Name Get + Set
    public String getName() {
        return m_szName;
    }
    public void setName(String Name) {
        this.m_szName = Name;
    }

    // Deadline Get + Set
    public Date getDeadline() {
        return m_Deadline;
    }
    public void setDeadline(Date Deadline) {
        this.m_Deadline = Deadline;
    }
}
