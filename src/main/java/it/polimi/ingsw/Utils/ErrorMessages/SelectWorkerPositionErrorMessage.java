package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the SelectedWorkerErrorMessage
 */

public class SelectWorkerPositionErrorMessage extends ErrorMessage{
    private final String message;

    /**
     * contains the message
     */

    public SelectWorkerPositionErrorMessage()
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
