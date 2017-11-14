package com.example.leon.deadline;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sean on 10/31/2017.
 */

public class CJob extends CDeadline implements Serializable {

   private eUrgency m_eUrgency;

   private static int typeID = 0;

   public CJob(){};

   public CJob(CJob job){
       super(job.getName(), job.getDeadline(), job.getSummary());
       m_eUrgency = job.m_eUrgency;
   }

    public CJob(String Name, String Deadline, String Summary, eUrgency Urgency) {
        super(Name, Deadline, Summary);
        m_eUrgency = Urgency;
    }

    // Urgency Get + Set
    public eUrgency getUrgency() { return m_eUrgency; }
    public void setUrgency(eUrgency Urgency) { m_eUrgency = Urgency; }

    @Override
    public int getTypeID() {return typeID;}
}
