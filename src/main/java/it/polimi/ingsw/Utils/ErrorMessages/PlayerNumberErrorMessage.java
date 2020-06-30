package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the PlayerNumberErrorMessage
 */

public class PlayerNumberErrorMessage extends ErrorMessage {
    private final String message;

    /**
     * contains the message
     */

    public PlayerNumberErrorMessage()
    {
        message="The given number of player is wrong";
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
