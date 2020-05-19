package it.polimi.ingsw.Utils.ErrorMessages;

public class PingMessage extends ErrorMessage{
    private final String message;

    public PingMessage()
    {
        message="ping";
    }

    public String getMessage()
    {
        return message;
    }
}
