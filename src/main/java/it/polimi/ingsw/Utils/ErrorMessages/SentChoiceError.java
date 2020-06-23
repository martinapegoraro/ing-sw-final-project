package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the SentChoiceError
 */

public class SentChoiceError extends ErrorMessage {
    final String errorMessage;


    /**
     * contains the message
     */
    public SentChoiceError()
    {
        errorMessage="the sent choice is not valid";
    }

    /**
     * returns the message
     * @return
     */

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
