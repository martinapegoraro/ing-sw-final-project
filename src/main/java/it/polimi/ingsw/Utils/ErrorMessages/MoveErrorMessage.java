package it.polimi.ingsw.Utils.ErrorMessages;

import it.polimi.ingsw.Model.Exceptions.MoveErrorException;

/**
 *This class represents the MoveErrorMessage
 */

public class MoveErrorMessage extends ErrorMessage{
    private final String message;

    /**
     * contains the message
     */
    public MoveErrorMessage()
    {
        message="move not allowed";
    }

    /**
     * returns the message
     * @return
     */

    public String getMessage()
    {
        return message;
    }

}
