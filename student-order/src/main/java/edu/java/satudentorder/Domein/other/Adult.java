package edu.java.satudentorder.Domein.other;
import edu.java.satudentorder.Domein.PassportOffice;
import edu.java.satudentorder.Domein.Person;
import edu.java.satudentorder.Domein.University;

import java.time.LocalDate;
public class Adult extends Person
{
    private String passportSeria;
    private String passportNumber;
    private LocalDate issueDate;
    private PassportOffice issueDepartment;
    private University university;
    private String studentid;

    public Adult() {
    }

    public Adult(String surNAme, String givenNAme, String patronymic, LocalDate dateOfBirth) {
        super(surNAme, givenNAme, patronymic, dateOfBirth);
    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public void setPassportSeria(String passportSeria) {
        this.passportSeria = passportSeria;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public PassportOffice getIssueDepartment() {return issueDepartment;}

    public void setIssueDepartment(PassportOffice issueDepartment) {this.issueDepartment = issueDepartment;}

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }


}
