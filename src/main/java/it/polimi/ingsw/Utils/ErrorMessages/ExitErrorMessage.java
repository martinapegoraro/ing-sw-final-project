package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the ExitErrorMessage
 */

public class ExitErrorMessage extends ErrorMessage {
    private final String message;

    /**
     * contains the message
     */

    public ExitErrorMessage()
    {
        message="Exit";
    }

    /**
     * returns the message
     * @return the error description
     */

    public String getMessage()
    {
        return message;
    }
}
