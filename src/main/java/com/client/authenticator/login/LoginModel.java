package com.client.authenticator.login;

import com.client.Mediator;
import com.serviceLib.interfaces.Observable;
import com.serviceLib.interfaces.Observer;
import com.serviceLib.models.User;
import com.client.ClientAdapter;

import java.util.LinkedList;
import java.util.List;

public class LoginModel implements Observable {

    private List<Observer> observers = new LinkedList<>();

    private String password;

    private String username;

    private User user;

    private ClientAdapter clientService = ClientAdapter.getInstance();

    public  Mediator mediator;

    public LoginModel(Mediator mediator) {
        this.mediator = mediator;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyObservers("validatePassword");
    }

    public void setUsername(String username) {
        this.username = username;
        notifyObservers("validateUsername");
    }

    public boolean checkPassword() {
        getUser();
        boolean b = user.getPassword().equals(mediator.getHashCode(password));
        if(!b) {
            notifyObservers("setIncorrectPasswordStyle");
        }
        return b;
    }

    public User getUser() {
        user = clientService.getUser(username);
        if(user != null) {
            notifyObservers("setCorrectUsernameStyle");
        } else {
            notifyObservers("setIncorrectUsernameStyle");
        }
        return user;
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
