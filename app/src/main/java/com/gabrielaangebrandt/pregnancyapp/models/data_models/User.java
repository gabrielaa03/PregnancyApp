package com.gabrielaangebrandt.pregnancyapp.models.data_models;

import android.net.Uri;

import java.util.Date;
import java.util.List;

public class User {
    private String name;
    private String username;
    private String password;
    private String secureAnswer;
    private String email;
    private String address;
    private String dateOfBirth;
    private String bloodType;
    private String coombsTest;
    private String labProtocolNumber;
    private String childBirth;
    private Date dueDate;
    private MedicalRecord record;
    private List<Uri> photos;
    private List<String> anamnesis;

    public User(String name, String username, String password, String secureAnswer, String email, String address, String dateOfBirth, String bloodType, String coombsTest, String childBirth, String labProtocolNumber, Date dueDate, MedicalRecord record, List<Uri> photos, List<String> anamnesis) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.secureAnswer = secureAnswer;
        this.email = email;
        this.address = address;
        this.bloodType = bloodType;
        this.coombsTest = coombsTest;
        this.labProtocolNumber = labProtocolNumber;
        this.childBirth = childBirth;
        this.dateOfBirth = dateOfBirth;
        this.dueDate = dueDate;
        this.record = record;
        this.photos = photos;
        this.anamnesis = anamnesis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecureAnswer() {
        return secureAnswer;
    }

    public void setSecureAnswer(String secureAnswer) {
        this.secureAnswer = secureAnswer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public MedicalRecord getRecord() {
        return record;
    }

    public void setRecord(MedicalRecord record) {
        this.record = record;
    }

    public List<Uri> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Uri> photos) {
        this.photos = photos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getCoombsTest() {
        return coombsTest;
    }

    public void setCoombsTest(String coombsTest) {
        this.coombsTest = coombsTest;
    }

    public String getLabProtocolNumber() {
        return labProtocolNumber;
    }

    public void setLabProtocolNumber(String labProtocolNumber) {
        this.labProtocolNumber = labProtocolNumber;
    }

    public List<String> getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(List<String> anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getChildBirth() {
        return childBirth;
    }

    public void setChildBirth(String childBirth) {
        this.childBirth = childBirth;
    }
}
