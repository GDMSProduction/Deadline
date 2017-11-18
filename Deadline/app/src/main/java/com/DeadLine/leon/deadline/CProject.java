package com.DeadLine.leon.deadline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CProject extends CDeadline implements Serializable {

    private static int typeID = 0;

    //TODO: Remove this stuff and any associated code later, except the sort functions which should be commented out
    private List<CTask> m_TaskList = new ArrayList<>();
    private List<CRole> m_Roles = new ArrayList<>();
    private List<CMember> m_Members = new ArrayList<>();

   /* public String           GetName()           {return m_szName;}
    public Date             GetDeadline()       {return m_Deadline;}
    public boolean          isPrivate()         {return m_bPrivate;}
    public List<CTask>      GetTaskList()       {return m_TaskList;}
    public int              GetTaskListSize()   {return m_TaskList.size();}
    public CTask            GetTask(int nTask)  {return m_TaskList.get(nTask);}*/

    public CProject() {

    }

    public CProject(String Name, String Deadline, String Summary, Boolean Complete) {
        super(Name,Deadline,Summary,Complete);
        //setName(Name);
        /*try {
            setDeadline(new SimpleDateFormat("MM/dd/yyyy").parse(Deadline));
        } catch (java.text.ParseException e){}
        */
        //setDeadline(Deadline);
        //mSummary = Summary;
    }

    @Override
    public int getTypeID() {return typeID;}

    // Task List  Get + Set
    public List<CTask> getTaskList() {
        return m_TaskList;
    }
    public void setTaskList(List<CTask> m_TaskList) {
        this.m_TaskList = m_TaskList;
    }

    // Roles  Get + Set
    public List<CRole> getRoles() {
        return m_Roles;
    }
    public void setRoles(List<CRole> m_Roles) {
        this.m_Roles = m_Roles;
    }

    // Members Get + Set
    public List<CMember> getMembers() { return m_Members; }
    public void setMembers(List<CMember> Members) { m_Members = Members; }


// Member Functions
    public boolean addMember(CMember member){
        return m_Members.add(member);
    }

   /* public boolean addMember(CUser member){
        return m_Members.add(new CMember(member));
    }*/



// Task Functions

    public boolean addTask(String szName, String date, String summary, Boolean complete){
        return m_TaskList.add(new CTask(szName, date, summary, complete));
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

    public boolean addRole(String Name, List<String> JobIDPermissions, List<String> TaskIDPermissions, boolean AddMembersPermission, boolean RemoveMemberPermission,
                           boolean EditMemberPermission, boolean JobPermission, boolean TaskPermission, boolean RolePermission, boolean ProjectPermission)
    {
        for(int i = 0; i < m_Roles.size(); i++){
            if(m_Roles.get(i).getName() == Name)
                return false;
        }
        return m_Roles.add(new CRole(Name, JobIDPermissions, TaskIDPermissions, AddMembersPermission, RemoveMemberPermission, EditMemberPermission, JobPermission, TaskPermission, RolePermission, ProjectPermission));
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