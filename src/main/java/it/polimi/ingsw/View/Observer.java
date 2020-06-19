package it.polimi.ingsw.View;

public interface Observer<T> {

    /**
     * updates the observer
     * @param message
     */

    void update(T message);
}
