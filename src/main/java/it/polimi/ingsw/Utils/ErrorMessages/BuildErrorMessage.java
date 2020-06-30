package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the BuildErrorMessage
 */

public class BuildErrorMessage extends ErrorMessage {
    private final String message;

    /**
     * contains the message
     */

    public BuildErrorMessage()
    {
        message="Build not allowed!";
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
