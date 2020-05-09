package it.polimi.ingsw.Utils.ErrorMessages;

import java.io.Serializable;

public abstract class ErrorMessage implements Serializable
{
    private String message;
    public abstract String getMessage();
}
