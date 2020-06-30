package it.polimi.ingsw.View;

import it.polimi.ingsw.View.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private List<Observer<T>> observers = new ArrayList<>();

    /**
     * add an observer
     */

    public void addObservers(Observer<T> observer){
        observers.add(observer);
    }

    /**
     * notifies a change so that the observers can update
     */

    public void notify(T message){
        for(Observer<T> observer: observers){
            observer.update(message);
        }
    }

}
