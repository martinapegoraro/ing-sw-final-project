package it.polimi.ingsw.Utils.ErrorMessages;

public class InitialWorkerPositionErrorMessage extends ErrorMessage{
    private final String message;

    public InitialWorkerPositionErrorMessage()
    {
        message="The given worker position is not valid";
    }

    public String getMessage()
    {
        return message;
    }
}
