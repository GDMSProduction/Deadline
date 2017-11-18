package com.DeadLine.leon.deadline;

import java.io.Serializable;

public class CJob extends CDeadline implements Serializable {

   private static int typeID = 1;

   public CJob(){}
   public CJob(CJob job){
       super(job.getName(), job.getDeadline(), job.getSummary(), job.getComplete());
   }
   public CJob(String Name, String Deadline, String Summary, Boolean Complete) {
        super(Name, Deadline, Summary, Complete);
    }

    @Override
    public int getTypeID() {return typeID;}
}
