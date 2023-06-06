package com.example.magazinonlineapp.observer;

public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
    void removeAllObservers();
}
