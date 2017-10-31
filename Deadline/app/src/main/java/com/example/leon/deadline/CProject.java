package com.example.leon.deadline;

/**
 * Created by sml91_000 on 9/7/2017.
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CProject extends CDeadline implements Serializable {
    //private String  m_szName;
    private String m_szSummary;
    //private Date    m_Deadline;
    private boolean m_bPrivate;

    private List<CMember> m_MemberList = new ArrayList<>();
    private List<CTask> m_TaskList = new ArrayList<>();
    private List<CRole> m_Roles = new ArrayList<>();

   /* public String           GetName()           {return m_szName;}
    public Date             GetDeadline()       {return m_Deadline;}
    public boolean          isPrivate()         {return m_bPrivate;}
    public List<CTask>      GetTaskList()       {return m_TaskList;}
    public int              GetTaskListSize()   {return m_TaskList.size();}
    public CTask            GetTask(int nTask)  {return m_TaskList.get(nTask);}*/

    public CProject() {

    }

    public CProject(String Name, String Deadline, boolean Private) {
        setName(Name);
        try {
            setDeadline(new SimpleDateFormat("MM/dd/yyyy").parse(Deadline));
        } catch (java.text.ParseException e){}

        this.m_bPrivate = m_bPrivate;
    }

    public String getSummary() {
        return m_szSummary;
    }

    public void setSummary(String m_szSummary) {
        this.m_szSummary = m_szSummary;
    }

    public boolean isbPrivate() {
        return m_bPrivate;
    }

    public void setbPrivate(boolean m_bPrivate) {
        this.m_bPrivate = m_bPrivate;
    }

    public List<CMember> getMemberList() {
        return m_MemberList;
    }

    public void setMemberList(List<CMember> m_MemberList) {
        this.m_MemberList = m_MemberList;
    }

    public List<CTask> getTaskList() {
        return m_TaskList;
    }

    public void setTaskList(List<CTask> m_TaskList) {
        this.m_TaskList = m_TaskList;
    }

    public List<CRole> getRoles() {
        return m_Roles;
    }

    public void setRoles(List<CRole> m_Roles) {
        this.m_Roles = m_Roles;
    }


// Member Functions

    public boolean addMember(CMember member){
        for(int i = 0; i < m_MemberList.size(); i++){
            if(m_MemberList.get(i).getEmail() == member.getEmail())
                return false;
        }
        return m_MemberList.add(new CMember(member));
    }

    public boolean addMember(CUser user, List<CRole> roles){
        for(int i = 0; i < m_MemberList.size(); i++){
            if(m_MemberList.get(i).getEmail() == user.getEmail())
                return false;
        }
        return m_MemberList.add(new CMember(user, roles));
    }

    public void removeMember(CMember member){
        m_MemberList.remove(member);
    }

    public void removeMember(int memberIndex){
        m_MemberList.remove(memberIndex);
    }

    public int numMembers(int numMembers){
        return m_MemberList.size();
    }

    public CMember getMember(int memberIndex){
        return m_MemberList.get(memberIndex);
    }

    // Returns the index of the user or -1 if it was not found
    public int getMemberIndex(String email){
        for(int i = 0; i < m_MemberList.size(); i++){
            if(m_MemberList.get(i).getEmail() == email)
                return i;
        }
        return -1;
    }

    public void sortMembersAlphabeticalForward(){
        List<CMember> sortMembers = new ArrayList<>();
        int memberIndex;
        while(m_MemberList.size() > 0) {
            memberIndex = 0;
            for (int i = 1; i < m_MemberList.size(); i++) {
                if(m_MemberList.get(memberIndex).getName().compareTo(m_MemberList.get(i).getName()) > 0)
                    memberIndex = i;
            }
            sortMembers.add(new CMember(m_MemberList.get(memberIndex)));
            m_MemberList.remove(memberIndex);
        }
        m_MemberList = new ArrayList<>(sortMembers);
    }

    public void sortMembersAlphabeticalBackward(){
        List<CMember> sortMembers = new ArrayList<>();
        int memberIndex;
        while(m_MemberList.size() > 0) {
            memberIndex = 0;
            for (int i = 1; i < m_MemberList.size(); i++) {
                if(m_MemberList.get(memberIndex).getName().compareTo(m_MemberList.get(i).getName()) < 0)
                    memberIndex = i;
            }
            sortMembers.add(new CMember(m_MemberList.get(memberIndex)));
            m_MemberList.remove(memberIndex);
        }
        m_MemberList = new ArrayList<>(sortMembers);
    }


    // Task Functions

    public boolean addTask(String szName, Date date, eUrgency urgency){
        return m_TaskList.add(new CTask(szName, date, urgency));
    }

    public boolean addTask(CTask task){
        return m_TaskList.add(new CTask(task));
    }

    public void removeTask(CTask task){
        m_TaskList.remove(task);
    }

    public void removeTask(int taskIndex){
        m_TaskList.remove(taskIndex);
    }

    public int numTasks(){
        return m_TaskList.size();
    }

    public CTask getTask(int taskIndex){
        return m_TaskList.get(taskIndex);
    }

    public void sortTasksAlphabeticalForward(){
        List<CTask> sortTasks = new ArrayList<>();
        int taskIndex;
        while(m_TaskList.size() > 0) {
            taskIndex = 0;
            for (int i = 1; i < m_TaskList.size(); i++) {
                if(m_TaskList.get(taskIndex).getName().compareTo(m_TaskList.get(i).getName()) > 0)
                    taskIndex = i;
            }
            sortTasks.add(new CTask(m_TaskList.get(taskIndex)));
            m_TaskList.remove(taskIndex);
        }
        m_TaskList = new ArrayList<>(sortTasks);
    }

    public void sortTasksAlphabeticalBackward(){
        List<CTask> sortTasks = new ArrayList<>();
        int taskIndex;
        while(m_TaskList.size() > 0) {
            taskIndex = 0;
            for (int i = 1; i < m_TaskList.size(); i++) {
                if(m_TaskList.get(taskIndex).getName().compareTo(m_TaskList.get(i).getName()) < 0)
                    taskIndex = i;
            }
            sortTasks.add(new CTask(m_TaskList.get(taskIndex)));
            m_TaskList.remove(taskIndex);
        }
        m_TaskList = new ArrayList<>(sortTasks);
    }

    public void sortTasksPriority(){
        eUrgency urgency = eUrgency.LOW;
        List<CTask> sortTasks = new ArrayList<>();
        int taskIndex;

        while(m_TaskList.size() > 0) {
            taskIndex = -1;
            for (int i = 0; i < m_TaskList.size(); i++) {
                if (m_TaskList.get(i).getUrgency() == urgency) {
                    taskIndex = i;
                    break;
                }
            }

            if (-1 == taskIndex)
                urgency = eUrgency.values()[urgency.ordinal() + 1];
            else {
                sortTasks.add(new CTask(m_TaskList.get(taskIndex)));
                m_TaskList.remove(taskIndex);
            }
        }
        m_TaskList = new ArrayList<>(sortTasks);
    }

    public void sortTasksDateClosest(){
        List<CTask> sortTasks = new ArrayList<>();
        int taskIndex;

        while(m_TaskList.size() > 0){
            taskIndex = 0;

            for(int i = 1; i < m_TaskList.size(); i++){
                if(m_TaskList.get(taskIndex).getDeadline().compareTo(m_TaskList.get(i).getDeadline()) > 0)
                    taskIndex = i;
            }
            sortTasks.add(new CTask(m_TaskList.get(taskIndex)));
            m_TaskList.remove(taskIndex);
        }
        m_TaskList = new ArrayList<>(sortTasks);
    }

    public void sortTasksDateFarthest(){
        List<CTask> sortTasks = new ArrayList<>();
        int taskIndex;

        while(m_TaskList.size() > 0){
            taskIndex = 0;

            for(int i = 1; i < m_TaskList.size(); i++){
                if(m_TaskList.get(taskIndex).getDeadline().compareTo(m_TaskList.get(i).getDeadline()) < 0)
                    taskIndex = i;
            }
            sortTasks.add(new CTask(m_TaskList.get(taskIndex)));
            m_TaskList.remove(taskIndex);
        }
        m_TaskList = new ArrayList<>(sortTasks);
    }


    // Role Functions

    public boolean addRole(String name, List<CTask> taskPermissions){
        for(int i = 0; i < m_Roles.size(); i++){
            if(m_Roles.get(i).getName() == name)
                return false;
        }
        return m_Roles.add(new CRole(name, taskPermissions));
    }

    public boolean addRole(CRole role){
        for(int i = 0; i < m_Roles.size(); i++){
            if(m_Roles.get(i).getName() == role.getName())
                return false;
        }
        return m_Roles.add(role);
    }

    public void removeRole(CRole role){
        m_Roles.remove(role);
    }

    public void removeRole(int roleIndex){
        m_Roles.remove(roleIndex);
    }

    public int numRoles(){
        return m_Roles.size();
    }

    public CRole getRole(int roleIndex){
        return m_Roles.get(roleIndex);
    }

    // returns the index of the role or -1 if it was not found
    public int getRoleIndex(String roleName){
        for(int i = 0; i < m_Roles.size(); i++){
            if(m_Roles.get(i).getName() == roleName)
                return i;
        }
        return -1;
    }
}