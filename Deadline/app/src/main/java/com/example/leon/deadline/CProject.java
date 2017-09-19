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
    private Date    m_Deadline;
    private boolean m_bPrivate;

    private List<CUser> m_MemberList = new ArrayList<>();
    private List<CTask> m_TaskList = new ArrayList<>();


    public String           GetName()           {return m_szName;}
    public Date             GetDeadline()       {return m_Deadline;}
    public boolean          isPrivate()         {return m_bPrivate;}
    public List<CTask>      GetTaskList()       {return m_TaskList;}
    public int              GetTaskListSize()   {return m_TaskList.size();}
    public CTask            GetTask(int nTask)  {return m_TaskList.get(nTask);}

}