package com.revilleza.userauth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
public class Doctor extends User{
    private String prc_license_no;

    public Doctor(String prc_license_no, String specialization) {
        this.prc_license_no = prc_license_no;
        this.specialization = specialization;
    }

    public Doctor(String firstname, String lastname, String middlename, String email, String password, UserRole role, String prc_license_no, String specialization) {
        super(firstname, lastname, middlename, email, password, role);
        this.prc_license_no = prc_license_no;
        this.specialization = specialization;
    }

    public String getPrc_license_no() {
        return prc_license_no;
    }

    public void setPrc_license_no(String prc_license_no) {
        this.prc_license_no = prc_license_no;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    private String specialization;
}
