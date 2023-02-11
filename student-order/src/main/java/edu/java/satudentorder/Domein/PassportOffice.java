package edu.java.satudentorder.Domein;

public class PassportOffice {
    private long officeID;
    private String officeAreaID;
    private String officeName;

    public PassportOffice() {
    }

    public PassportOffice(long officeID, String officeAreaID, String officeName) {
        this.officeID = officeID;
        this.officeAreaID = officeAreaID;
        this.officeName = officeName;
    }

    public long getOfficeID() {
        return officeID;
    }

    public void setOfficeID(long officeID) {
        this.officeID = officeID;
    }

    public String getOfficeAreaID() {
        return officeAreaID;
    }

    public void setOfficeAreaID(String officeAreaID) {
        this.officeAreaID = officeAreaID;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}
