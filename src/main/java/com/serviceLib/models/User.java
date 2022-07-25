package com.serviceLib.models;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private int cityId;
    private Date birthDate;
    private Date createdAt;

    public User(String username, String password, String firstname, String lastname, String email, String phoneNumber, int cityId, Date birthDate) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cityId = cityId;
        this.birthDate = birthDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getCityId() {
        return cityId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "User:" + "\n" +
                "   id=" + id + "\n" +
                "   username='" + username + '\'' + "\n" +
                "   password='" + password + '\'' + "\n" +
                "   firstname='" + firstname + '\'' + "\n" +
                "   lastname='" + lastname + '\'' + "\n" +
                "   email='" + email + '\'' + "\n" +
                "   phoneNumber='" + phoneNumber + '\'' + "\n" +
                "   cityId='" + cityId + '\'' + "\n" +
                "   birthDate=" + birthDate + "\n" +
                "   createdAt=" + createdAt +  "\n";
    }

}
