package com.example.leon.deadline;

/**
 * Created by sml91_000 on 9/7/2017.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CTask extends CDeadline implements Serializable {
    /*public enum eUrgency{
        LOW,
        NORMAL,
        HIGH,
    }*/

    //private String m_szName;
    //private Date m_Deadline;
    private eUrgency m_eUrgency;
    private List<CJob> m_JobList = new ArrayList<>();

    public CTask() {
    }

    public CTask(String Name, Date Deadline, String Summary, eUrgency Urgency) {
        super(Name, Deadline, Summary);
        this.m_eUrgency = Urgency;
    }
    public CTask(CTask task) {
        super(task.getName(), task.getDeadline(), task.getSummary());
        this.m_eUrgency = task.m_eUrgency;
    }

    // Urgency  Get + Set
    public eUrgency getUrgency() {
        return m_eUrgency;
    }
    public void setUrgency(eUrgency m_eUrgency) {
        this.m_eUrgency = m_eUrgency;
    }

    // JobList Get + Set
    public List<CJob> getJobList() { return m_JobList; }
    public void setJobList(List<CJob> JobList) { m_JobList = JobList; }


// Job Functions

    public boolean addJob(CJob job){
        return m_JobList.add(new CJob(job.getName(), job.getDeadline(), job.getSummary(), job.getUrgency()));
    }

    public boolean addJob(String Name, Date Deadline, String Summary, eUrgency Urgency){
        return m_JobList.add(new CJob(Name, Deadline, Summary, Urgency));
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

    public void sortJobsPriority(){
        eUrgency urgency = eUrgency.LOW;
        List<CJob> sortJobs = new ArrayList<>();
        int jobIndex;

        while(m_JobList.size() > 0) {
            jobIndex = -1;
            for (int i = 0; i < m_JobList.size(); i++) {
                if (m_JobList.get(i).getUrgency() == urgency) {
                    jobIndex = i;
                    break;
                }
            }

            if (-1 == jobIndex)
                urgency = eUrgency.values()[urgency.ordinal() + 1];
            else {
                sortJobs.add(new CJob(m_JobList.get(jobIndex)));
                m_JobList.remove(jobIndex);
            }
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
