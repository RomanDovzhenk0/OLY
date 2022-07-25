package com.serviceLib.interfaces;

public interface Observable {
    void registerObserver(Observer o);

    void notifyObservers(String message);
}
