package com.project.us.deadline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CMember implements Serializable {
    private List<CRole> m_Roles = new ArrayList<>();

    private String m_szUserID;

    public CMember(String szUserID) { m_szUserID = szUserID; }

    public CMember(String szUserID, List<CRole> roles) {
        m_szUserID = szUserID;
        m_Roles = roles;
    }

    public CMember(CMember member) {
        m_szUserID = member.m_szUserID;
        m_Roles = member.m_Roles;
    }

    //public CMember(CUser member) {  }

    // UserID Get + Set
    public String getUserID() { return m_szUserID; }
    public void setUserID(String szUserID) { m_szUserID = szUserID; }


    public int getRoleSize() { return m_Roles.size(); }

    public CRole getRoleByIndex(int index) { return m_Roles.get(index); }

    // Returns -1 if not found
    public int getRoleIndexByName(String roleName){
        for(int i = 0; i < m_Roles.size(); i++){
            if(m_Roles.get(i).getName() == roleName)
                return i;
        }
        return -1;
    }

    public boolean addRole(CRole role){
        return m_Roles.add(role);
    }

    // Remove by object
    public void removeRole(CRole role){
        m_Roles.remove(role);
    }

    // Remove by index
    public void removeRole(int roleIndex){
        m_Roles.remove(roleIndex);
    }
}
