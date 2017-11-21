package com.Deadline.leon.deadline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CUser implements Serializable{

    private String m_szName;
    private String m_szUsername;
    private String m_szEmail;
    private String m_szPhone;
    private String m_szBio;
    private boolean m_bTutorial;

    public CUser(CUser tempUser) {
        m_szName = tempUser.m_szName;
        m_szUsername = tempUser.m_szUsername;
        m_szEmail = tempUser.m_szEmail;
        m_szPhone = tempUser.m_szPhone;
        m_szBio = tempUser.m_szBio;
    }

    private List<CProject> m_ProjectList = new ArrayList<>();

    //Remove when possible.
    public CUser(String _name, String _email)
    {
        m_szName = _name;
        m_szEmail = _email;
    }

    public CUser(String _name, String _email,String _username, String _phone, String _bio)
    {
        m_szName = _name;
        m_szEmail = _email;
        m_szUsername = _username;
        m_szPhone = _phone;
        m_szBio = _bio;
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

    // Username Get + Set
    public String getUsername() { return m_szUsername; }
    public void setUsername(String Username) { m_szUsername = Username; }

    // Tutorial Get + Set
    public boolean getTutorial() { return m_bTutorial; }
    public void setTutorial(boolean Tutorial) { m_bTutorial = Tutorial;
    }

    // Gets a project from the list
    public CProject getProject(int nProject) {return m_ProjectList.get(nProject);}

    // Adds a new project to the list
    public void addProject(CProject Project) {m_ProjectList.add(Project);}

    // Get number of Projects stored in list
    public int getProjectListSize() {return m_ProjectList.size();}
}
