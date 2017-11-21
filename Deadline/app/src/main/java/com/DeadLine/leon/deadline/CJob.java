package com.DeadLine.leon.deadline;

import java.io.Serializable;

public class CJob extends CDeadline implements Serializable {

   //private static int typeID = 1;

   public CJob(){
       setTypeID(1);
   }
   public CJob(CJob job){
       super(job.getName(), job.getDeadline(), job.getSummary(), job.getComplete());
       setTypeID(1);
   }
   public CJob(String Name, String Deadline, String Summary, Boolean Complete) {
        super(Name, Deadline, Summary, Complete);
       setTypeID(1);
    }

    /*@Override
    public int getTypeID() {return typeID;}*/
}
