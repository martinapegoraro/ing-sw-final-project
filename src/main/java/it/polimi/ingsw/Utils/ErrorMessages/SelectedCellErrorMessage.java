package it.polimi.ingsw.Utils.ErrorMessages;

public class SelectedCellErrorMessage extends ErrorMessage {
    private final String message;

    public SelectedCellErrorMessage()
    {
        message="invalid Coordinates";
    }

    public String getMessage()
    {
        return message;
    }
}
