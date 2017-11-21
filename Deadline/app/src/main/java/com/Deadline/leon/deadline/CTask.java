package com.Deadline.leon.deadline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CTask extends CDeadline implements Serializable {

    private List<CJob> m_JobList = new ArrayList<>();

   private static int typeID = 1;

    public CTask() {
        //setTypeID(2);
    }
    public CTask(String Name, String Deadline, String Summary, Boolean Complete) {

        super(Name, Deadline, Summary, Complete);
        //setTypeID(2);
    }
    public CTask(CTask task) {
        super(task.getName(), task.getDeadline(), task.getSummary(), task.getComplete());
        //setTypeID(2);
    }

    @Override
    public int getTypeID() {return typeID;}


    // JobList Get + Set
    public List<CJob> getJobList() { return m_JobList; }
    public void setJobList(List<CJob> JobList) { m_JobList = JobList; }


// Job Functions

    public boolean addJob(CJob job){
        return m_JobList.add(new CJob(job.getName(), job.getDeadline(), job.getSummary(), job.getComplete()));
    }

    public boolean addJob(String Name, String Deadline, String Summary, Boolean Complete){
        return m_JobList.add(new CJob(Name, Deadline, Summary, Complete));
    }

    // Remove by Object
    public void removeJob(CJob job){
        m_JobList.remove(job);
    }

    // Remove by Index
    public void removeJob(int jobIndex){
        m_JobList.remove(jobIndex);
    }

    public int numJobs(){
        return m_JobList.size();
    }

    public CJob getJob(int jobIndex){
        return m_JobList.get(jobIndex);
    }

    public void sortJobsAlphabeticalForward(){
        List<CJob> sortJobs = new ArrayList<>();
        int jobIndex;
        while(m_JobList.size() > 0) {
            jobIndex = 0;
            for (int i = 1; i < m_JobList.size(); i++) {
                if(m_JobList.get(jobIndex).getName().compareTo(m_JobList.get(i).getName()) > 0)
                    jobIndex = i;
            }
            sortJobs.add(new CJob(m_JobList.get(jobIndex)));
            m_JobList.remove(jobIndex);
        }
        m_JobList = new ArrayList<>(sortJobs);
    }

    public void sortJobsAlphabeticalBackward(){
        List<CJob> sortJobs = new ArrayList<>();
        int jobIndex;
        while(m_JobList.size() > 0) {
            jobIndex = 0;
            for (int i = 1; i < m_JobList.size(); i++) {
                if(m_JobList.get(jobIndex).getName().compareTo(m_JobList.get(i).getName()) < 0)
                    jobIndex = i;
            }
            sortJobs.add(new CJob(m_JobList.get(jobIndex)));
            m_JobList.remove(jobIndex);
        }
        m_JobList = new ArrayList<>(sortJobs);
    }

    public void sortJobsDateClosest(){
        List<CJob> sortJobs = new ArrayList<>();
        int jobIndex;

        while(m_JobList.size() > 0){
            jobIndex = 0;

            for(int i = 1; i < m_JobList.size(); i++){
                if(m_JobList.get(jobIndex).getDeadline().compareTo(m_JobList.get(i).getDeadline()) > 0)
                    jobIndex = i;
            }
            sortJobs.add(new CJob(m_JobList.get(jobIndex)));
            m_JobList.remove(jobIndex);
        }
        m_JobList = new ArrayList<>(sortJobs);
    }

    public void sortJosDateFarthest(){
        List<CJob> sortJobs = new ArrayList<>();
        int jobndex;

        while(m_JobList.size() > 0){
            jobndex = 0;

            for(int i = 1; i < m_JobList.size(); i++){
                if(m_JobList.get(jobndex).getDeadline().compareTo(m_JobList.get(i).getDeadline()) < 0)
                    jobndex = i;
            }
            sortJobs.add(new CJob(m_JobList.get(jobndex)));
            m_JobList.remove(jobndex);
        }
        m_JobList = new ArrayList<>(sortJobs);
    }

}
