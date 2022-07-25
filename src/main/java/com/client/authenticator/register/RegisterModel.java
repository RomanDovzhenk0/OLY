package com.client.authenticator.register;

import com.client.ClientAdapter;
import com.client.Mediator;
import com.serviceLib.interfaces.Observable;
import com.serviceLib.interfaces.Observer;
import com.serviceLib.models.User;
import java.util.ArrayList;
import java.util.List;

public class RegisterModel implements Observable {

    List<Observer> observers = new ArrayList<>();

    private String confirmPassword, password, email, firstname, lastname, phoneNumber, username;

    private User user;

    private Mediator mediator;

    private ClientAdapter clientService = ClientAdapter.getInstance();

    public RegisterModel(Mediator mediator) {
        this.mediator = mediator;
    }

    public boolean setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        if(confirmPassword.equals(password)) {
            notifyObservers("setCorrectConfirmPasswordStyle");
        } else {
            notifyObservers("setIncorrectConfirmPasswordStyle");
        }
        return confirmPassword.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyObservers("validatePassword");
    }

    public void setEmail(String email) {
        this.email = email;
        notifyObservers("validateEmail");
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        notifyObservers("validateFirstname");
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        notifyObservers("validateLastname");
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyObservers("validatePhone");
    }

    public void setUsername(String username) {
        this.username = username;
        notifyObservers("validateUsername");
    }

    public User getUser() {
        user = clientService.getUser(username);
        if(user != null) {
            notifyObservers("setUserAlreadyExistStyle");
        }
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String message) {
        observers.forEach(observer -> observer.notification(message));
    }
}
