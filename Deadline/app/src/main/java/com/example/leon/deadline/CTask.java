package com.example.leon.deadline;

/**
 * Created by sml91_000 on 9/7/2017.
 */

import java.io.Serializable;
import java.util.Date;



public class CTask extends CDeadline implements Serializable {
    /*public enum eUrgency{
        LOW,
        NORMAL,
        HIGH,
    }*/

    //private String m_szName;
    //private Date m_Deadline;
    private eUrgency m_eUrgency;

    public CTask(String Name, Date Deadline, eUrgency Urgency) {
        super(Name, Deadline);
        this.m_eUrgency = Urgency;
    }
    public CTask(CTask task) {
        super(task.getName(), task.getDeadline());
        this.m_eUrgency = task.m_eUrgency;
    }

    // Urgency  Get + Set
    public eUrgency getUrgency() {
        return m_eUrgency;
    }
    public void setUrgency(eUrgency m_eUrgency) {
        this.m_eUrgency = m_eUrgency;
    }
}
