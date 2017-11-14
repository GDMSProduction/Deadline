package com.example.leon.deadline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 10/24/2017.
 */

public class CRole implements Serializable {
    private String m_szName = null;
    private List<String> m_TaskIDPermissions = new ArrayList<>();

    private boolean m_bAddMembersPermission;
    private boolean m_bRemoveMemberPermission;
    private boolean m_bEditMemberPermission;
    private boolean m_bTaskPermission;
    private boolean m_bRolePermission;
    private boolean m_bProjectPermission;


    // Name  Get + Set
    public String getName() { return m_szName; }
    public void setName(String Name) {
        m_szName = Name;
    }

    // AddMemberPermission Get + Set
    public boolean getAddMembersPermission() { return m_bAddMembersPermission; }
    public void setAddMembersPermission(boolean AddMembersPermission) { m_bAddMembersPermission = AddMembersPermission; }

    // RemoveMemberPermission  Get + Set
    public boolean getRemoveMemberPermission() { return m_bRemoveMemberPermission; }
    public void setRemoveMemberPermission(boolean RemoveMemberPermission) { m_bRemoveMemberPermission = RemoveMemberPermission; }

    // EditMemberPermission  Get + Set
    public boolean getEditMemberPermission() { return m_bEditMemberPermission; }
    public void setEditMemberPermission(boolean EditMemberPermission) { m_bEditMemberPermission = EditMemberPermission; }

    // TaskPermission  Get + Set
    public boolean getTaskPermission() { return m_bTaskPermission; }
    public void setTaskPermission(boolean TaskPermission) { m_bTaskPermission = TaskPermission; }

    // RolePermission  Get + Set
    public boolean getRolePermission() { return m_bRolePermission; }
    public void setRolePermission(boolean RolePermission) { m_bRolePermission = RolePermission; }

    // ProjectPermission  Get + Set
    public boolean getProjectPermission() { return m_bProjectPermission; }
    public void setProjectPermission(boolean ProjectPermission) { m_bProjectPermission = ProjectPermission;}



    public boolean addTaskPermission(String taskID){
       return m_TaskIDPermissions.add(taskID);
    }

    // Remove by object
    public void removeTaskPermission(String taskID){
        m_TaskIDPermissions.remove(taskID);
    }

    // Remove by index
    public void removeTaskPermission(int  taskIDIndex){
        m_TaskIDPermissions.remove(taskIDIndex);
    }

  /*  public CRole(String name, List<CTask> taskPermissions) {
        this.m_szName = name;
        this.m_TaskPermissions = taskPermissions;
    }*/

    public CRole(String Name, List<String> TaskIDPermissions, boolean AddMembersPermission, boolean RemoveMemberPermission,
                 boolean EditMemberPermission, boolean TaskPermission, boolean RolePermission, boolean ProjectPermission)
    {
        m_szName = Name;
        m_TaskIDPermissions = TaskIDPermissions;
        m_bAddMembersPermission = AddMembersPermission;
        m_bRemoveMemberPermission = RemoveMemberPermission;
        m_bEditMemberPermission = EditMemberPermission;
        m_bTaskPermission = TaskPermission;
        m_bRolePermission = RolePermission;
        m_bProjectPermission = ProjectPermission;
    }
}
