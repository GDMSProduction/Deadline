package com.DeadLine.leon.deadline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CDeadline extends HashMap implements Serializable {
    private String m_szName;
    private String m_Deadline;
    private String m_szSummary;
    private Boolean m_bComplete;

    public String getUniqueID() {
        return m_uniqueID;
    }

    public void setUniqueID(String m_uniqueID) {
        this.m_uniqueID = m_uniqueID;
    }

    private String m_uniqueID;

    private static int typeID = 9;

    //TODO LW3 - (FIXED) HASHMAP STUFF
    public static String ITEM_NAME = "name";

    public static String DEADLINE_DATE = "date";
    @Override
    public String get(Object k) {
        String key = (String) k;
        if(ITEM_NAME.equals(key))
        {
            return m_szName;
        }
        else if(DEADLINE_DATE.equals(key))
        {
            return m_Deadline;
        }
        return null;
    };

    private List<CMember> m_MemberList = new ArrayList<>();

    public CDeadline() {
    }

    public CDeadline(String Name, String Deadline, String Summary, Boolean Complete) {
        m_szName = Name;
        m_Deadline = Deadline;
        m_szSummary = Summary;
        m_bComplete = Complete;
    }

    public CDeadline(CDeadline deadline) {
        m_szName = deadline.m_szName;
        m_Deadline = deadline.m_Deadline;
        m_szSummary = deadline.m_szSummary;
        m_bComplete = deadline.m_bComplete;
    }

    public int getTypeID() {return typeID;}
    public void setTypeID(int id) {typeID = id;}

    // Name Get + Set
    public String getName() {
        return m_szName;
    }
    public void setName(String Name) {
        m_szName = Name;
    }

    // Deadline Get + Set
    public String getDeadline() {
        return m_Deadline;
    }
    public void setDeadline(String Deadline) {
        m_Deadline = Deadline;
    }

    // Summary Get + Set
    public String getSummary() {
        return m_szSummary;
    }
    public void setSummary(String Summary) {
        m_szSummary = Summary;
    }

    // Complete Get + Set
    public Boolean getComplete() {
        return m_bComplete;
    }
    public void setComplete(Boolean m_bComplete) {
        this.m_bComplete = m_bComplete;
    }

    // Member List  Get + Set
    public List<CMember> getMemberList() {
        return m_MemberList;
    }
    public void setMemberList(List<CMember> MemberList) {
        m_MemberList = MemberList;
    }

    // Member Functions
    public boolean addMember(CMember member){
        for(int i = 0; i < m_MemberList.size(); i++){
            if(m_MemberList.get(i).getUserID() == member.getUserID())
                return false;
        }
        return m_MemberList.add(new CMember(member));
    }

    public boolean addMember(String UserID, List<CRole> roles){
        for(int i = 0; i < m_MemberList.size(); i++){
            if(m_MemberList.get(i).getUserID() == UserID)
                return false;
        }
        return m_MemberList.add(new CMember(UserID, roles));
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
    public int getMemberIndex(String userID){
        for(int i = 0; i < m_MemberList.size(); i++){
            if(m_MemberList.get(i).getUserID() == userID)
                return i;
        }
        return -1;
    }


    /*public void sortMembersAlphabeticalForward(){
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
    }*/
}
