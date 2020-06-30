package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the InitialWorkerPositionErrorMessage
 */

public class InitialWorkerPositionErrorMessage extends ErrorMessage{
    private final String message;

    /**
     * contains the message
     */

    public InitialWorkerPositionErrorMessage()
    {
        message="The given worker position is not valid";
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
