package it.polimi.ingsw.Utils.ErrorMessages;

import java.io.Serializable;

/**
 * This abstract class is used to build
 * error messages when the user makes a
 * wrong choice or does something not
 * permitted by the rules
 */

public abstract class ErrorMessage implements Serializable
{
    private String message;
    public abstract String getMessage();
}
