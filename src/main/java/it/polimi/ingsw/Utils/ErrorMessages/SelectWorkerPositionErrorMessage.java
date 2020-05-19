package it.polimi.ingsw.Utils.ErrorMessages;

public class SelectWorkerPositionErrorMessage extends ErrorMessage{
    private final String message;

    public SelectWorkerPositionErrorMessage()
    {
        message="The given worker position is not valid";
    }

    public String getMessage()
    {
        return message;
    }
}
