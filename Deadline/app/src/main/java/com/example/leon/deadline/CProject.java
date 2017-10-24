package com.example.leon.deadline;

/**
 * Created by sml91_000 on 9/7/2017.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CProject implements Serializable {
    private String  m_szName;
    private String m_szSummary;
    private String    m_Deadline;
    private boolean m_bPrivate;

    private List<CUser> m_MemberList = new ArrayList<>();
    private List<CTask> m_TaskList = new ArrayList<>();

    public String           GetName()           {return m_szName;}
    public String             GetDeadline()       {return m_Deadline;}
    //public boolean          isPrivate()         {return m_bPrivate;}
    public List<CTask>      GetTaskList()       {return m_TaskList;}
    public int              GetTaskListSize()   {return m_TaskList.size();}
    public CTask            GetTask(int nTask)  {return m_TaskList.get(nTask);}

    public CProject(String _name, String _date, Boolean _private) {

        m_szName = _name;
        m_Deadline = _date;
        m_bPrivate = _private;
    }

    public String getM_szName() {
        return m_szName;
    }

    public void setM_szName(String m_szName) {
        this.m_szName = m_szName;
    }

    public String getM_szSummary() {
        return m_szSummary;
    }

    public void setM_szSummary(String m_szSummary) {
        this.m_szSummary = m_szSummary;
    }

    public String getM_Deadline() {
        return m_Deadline;
    }

    public void setM_Deadline(String m_Deadline) {
        this.m_Deadline = m_Deadline;
    }

    public boolean isM_bPrivate() {
        return m_bPrivate;
    }

    public void setM_bPrivate(boolean m_bPrivate) {
        this.m_bPrivate = m_bPrivate;
    }


    public List<CUser> getM_MemberList() {
        return m_MemberList;
    }

    public void setM_MemberList(List<CUser> m_MemberList) {
        this.m_MemberList = m_MemberList;
    }

    public List<CTask> getM_TaskList() {
        return m_TaskList;
    }

    public void setM_TaskList(List<CTask> m_TaskList) {
        this.m_TaskList = m_TaskList;
    }
}