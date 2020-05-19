package it.polimi.ingsw.Utils.ErrorMessages;

public class ExitErrorMessage extends ErrorMessage {
    private final String message;

    public ExitErrorMessage()
    {
        message="One player left the game";
    }

    public String getMessage()
    {
        return message;
    }
}
