package com.example.leon.deadline;

/**
 * Created by sml91_000 on 9/7/2017.
 *
 * User is the class for someones account
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CUser implements Serializable{

    private String m_szName;
    private String m_szEmail;
    private String m_szPhone;
    private String m_szBio;

    private List<CProject> m_ProjectList = new ArrayList<>();

    public CUser(String _name, String _email)
    {
        m_szName = _name;
        m_szEmail = _email;
    }

    // Name Get + Set
    public String getName() {return m_szName;}
    public void setName(String szName) {m_szName = szName;}

    // Email Get + Set
    public String getEmail() {return m_szEmail;}
    public void setEmail(String szEmail) {m_szEmail = szEmail;}

    // Phone Get + Set
    public String getPhone() {return m_szPhone;}
    public void setPhone(String szPhone) {m_szPhone = szPhone;}

    // Bio Get + Set
    public String getBio() {return m_szBio;}
    public void setBio(String szBio) {m_szBio = szBio;}

    // ProjectList Get + Set
    public List<CProject> getProjectList() {return m_ProjectList;}
    public void setProjectList(List<CProject> ProjectList) {m_ProjectList = ProjectList;}

    // Gets a project from the list
    public CProject getProject(int nProject) {return m_ProjectList.get(nProject);}

    // Adds a new project to the list
    public void addProject(CProject Project) {m_ProjectList.add(Project);}

    // Get number of Projects stored in list
    public int getProjectListSize() {return m_ProjectList.size();}
}
