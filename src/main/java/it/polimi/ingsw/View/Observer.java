package it.polimi.ingsw.View;

public interface Observer<T> {

    /**
     * updates the observer
     */

    void update(T message);
}
