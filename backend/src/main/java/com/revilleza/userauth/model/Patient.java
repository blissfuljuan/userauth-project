package com.revilleza.userauth.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Patient extends User{

    private LocalDate date_of_birth;

    public Patient() {
    }

    public Patient(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Patient(String firstname, String lastname, String email, String password, UserRole role, LocalDate date_of_birth) {
        super(firstname, lastname, email, password, role);
        this.date_of_birth = date_of_birth;
    }

    public Patient(String firstname, String lastname, String middlename, String email, String password, UserRole role, LocalDate date_of_birth) {
        super(firstname, lastname, middlename, email, password, role);
        this.date_of_birth = date_of_birth;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
}
