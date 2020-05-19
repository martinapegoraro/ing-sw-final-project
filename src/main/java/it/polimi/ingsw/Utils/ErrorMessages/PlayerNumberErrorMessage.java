package it.polimi.ingsw.Utils.ErrorMessages;

public class PlayerNumberErrorMessage extends ErrorMessage {
    private final String message;

    public PlayerNumberErrorMessage()
    {
        message="The given number of player is wrong";
    }

    public String getMessage()
    {
        return message;
    }
}
