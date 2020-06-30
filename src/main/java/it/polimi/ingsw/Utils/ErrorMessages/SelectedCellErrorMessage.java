package it.polimi.ingsw.Utils.ErrorMessages;

/**
 * This class represents the SelectedCellErrorMessage
 */

public class SelectedCellErrorMessage extends ErrorMessage {
    private final String message;

    /**
     * contains the message
     */

    public SelectedCellErrorMessage()
    {
        message="invalid Coordinates";
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
