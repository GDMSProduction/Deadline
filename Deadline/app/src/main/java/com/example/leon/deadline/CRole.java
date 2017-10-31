package com.example.leon.deadline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 10/24/2017.
 */

public class CRole implements Serializable {
    private String m_szName = null;
    private List<CTask> m_TaskPermissions = new ArrayList<>();

    private boolean m_bAddMembersPermission;
    private boolean m_bRemoveMemberPermission;
    private boolean m_bEditMemberPermission;
    private boolean m_bTaskPermission;
    private boolean m_bRolePermission;
    private boolean m_bProjectPermission;


    // Name  Get + Set
    public String getName() {
        return m_szName;
    }
    public void setName(String m_szName) {
        this.m_szName = m_szName;
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



    public boolean addTaskPermission(CTask task){
       return m_TaskPermissions.add(task);
    }

    // Remove by object
    public void removeTaskPermission(CTask task){
        m_TaskPermissions.remove(task);
    }

    // Remove by index
    public void removeTaskPermission(int  taskIndex){
        m_TaskPermissions.remove(taskIndex);
    }

  /*  public CRole(String name, List<CTask> taskPermissions) {
        this.m_szName = name;
        this.m_TaskPermissions = taskPermissions;
    }*/

    public CRole(String Name, List<CTask> TaskPermissions, boolean AddMembersPermission, boolean RemoveMemberPermission,
                 boolean EditMemberPermission, boolean TaskPermission, boolean RolePermission, boolean ProjectPermission)
    {
        m_szName = Name;
        m_TaskPermissions = TaskPermissions;
        m_bAddMembersPermission = AddMembersPermission;
        m_bRemoveMemberPermission = RemoveMemberPermission;
        m_bEditMemberPermission = EditMemberPermission;
        m_bTaskPermission = TaskPermission;
        m_bRolePermission = RolePermission;
        m_bProjectPermission = ProjectPermission;
    }
}
