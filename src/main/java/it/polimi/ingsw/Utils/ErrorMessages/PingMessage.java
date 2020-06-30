package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the PingMessage
 */

public class PingMessage extends ErrorMessage{
    private final String message;

    /**
     * contains the message
     */

    public PingMessage()
    {
        message="ping";
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
