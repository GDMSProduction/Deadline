package com.example.leon.deadline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sml91_000 on 9/19/2017.
 */

public class CMember extends CUser implements Serializable {
    private List<CRole> m_Roles = new ArrayList<>();

    public CMember(String name, String email )
    {
        super(name, email);
    }

    public CMember(CUser tempUser, List<CRole> roles) {
        super(tempUser);
        this.m_Roles = roles;
    }

    public CMember(CMember member) {
        super(member.getName(), member.getEmail());
        this.m_Roles = member.m_Roles;
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
