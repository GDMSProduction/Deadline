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

    public String getName() {
        return m_szName;
    }

    public void setName(String m_szName) {
        this.m_szName = m_szName;
    }



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

    public CRole(String name, List<CTask> taskPermissions) {
        this.m_szName = name;
        this.m_TaskPermissions = taskPermissions;
    }
}
