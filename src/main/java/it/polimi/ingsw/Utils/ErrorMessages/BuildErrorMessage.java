package it.polimi.ingsw.Utils.ErrorMessages;

public class BuildErrorMessage extends ErrorMessage {
    private final String message;

    public BuildErrorMessage()
    {
        message="Build not allowed!";
    }

    public String getMessage()
    {
        return message;
    }
}
