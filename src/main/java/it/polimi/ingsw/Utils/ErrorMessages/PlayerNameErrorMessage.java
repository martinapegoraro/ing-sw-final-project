package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the PlayerNameErrorMessage
 */

public class PlayerNameErrorMessage extends ErrorMessage {
    private String message;

    /**
     * contains the message
     */

    public PlayerNameErrorMessage()
    {
        message="the given name is already used";
    }

    /**
     * returns the message
     * @return the error description
     */

    @Override
    public String getMessage() {
        return message;
    }
}
