package edu.java.satudentorder.Domein;
import java.time.LocalDate;
public class Child extends Person {
    private String certificateNumber;
    private LocalDate issueDate;
    private RegisterOffice issueDepartment;

    public Child(String surNAme, String givenNAme, String patronymic, LocalDate dateOfBirth) {
        super(surNAme, givenNAme, patronymic, dateOfBirth);
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public RegisterOffice getIssueDepartment() {
        return issueDepartment;
    }

    public void setIssueDepartment(RegisterOffice issueDepartment) {
        this.issueDepartment = issueDepartment;
    }
}
