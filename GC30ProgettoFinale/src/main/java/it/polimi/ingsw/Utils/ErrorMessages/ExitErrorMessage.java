package it.polimi.ingsw.Utils.ErrorMessages;

public class ExitErrorMessage extends ErrorMessage {
    private final String message;

    public ExitErrorMessage()
    {
        message="Exit the game is not possible";
    }

    public String getMessage()
    {
        return message;
    }
}
