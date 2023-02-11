package edu.java.satudentorder.Domein;
import java.time.LocalDate;

public abstract class Person {
    private String surNAme;
    private String givenNAme;
    private String patronymic;
    private LocalDate dateOfBirth;
    private Address address;

    public Person(String surNAme, String givenNAme, String patronymic, LocalDate dateOfBirth) {
        this.surNAme = surNAme;
        this.givenNAme = givenNAme;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
    }

    public Person() {

    }

    public String getSurNAme() {
        return surNAme;
    }

    public void setSurNAme(String surNAme) {
        this.surNAme = surNAme;
    }

    public String getGivenNAme() {
        return givenNAme;
    }

    public void setGivenNAme(String givenNAme) {
        this.givenNAme = givenNAme;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
