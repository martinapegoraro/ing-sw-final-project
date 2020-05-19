package it.polimi.ingsw.View;

public interface Observer<T> {

    void update(T message);
}
