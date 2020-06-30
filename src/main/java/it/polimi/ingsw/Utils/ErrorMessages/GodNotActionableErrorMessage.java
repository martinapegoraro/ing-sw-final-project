package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the GodNotActionableErrorMessage
 */

public class GodNotActionableErrorMessage extends ErrorMessage {
    private final String message;

    /**
     * contains the message
     */

    public GodNotActionableErrorMessage()
    {
        message="The God effect is not actionable";
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
