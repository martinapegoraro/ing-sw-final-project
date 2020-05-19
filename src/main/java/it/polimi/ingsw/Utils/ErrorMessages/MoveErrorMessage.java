package it.polimi.ingsw.Utils.ErrorMessages;

import it.polimi.ingsw.Model.Exceptions.MoveErrorException;

public class MoveErrorMessage extends ErrorMessage{
    private final String message;
    public MoveErrorMessage()
    {
        message="move not allowed";
    }

    public String getMessage()
    {
        return message;
    }

}
