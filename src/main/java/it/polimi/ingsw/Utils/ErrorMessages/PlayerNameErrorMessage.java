package it.polimi.ingsw.Utils.ErrorMessages;

public class PlayerNameErrorMessage extends ErrorMessage {
    private String message;

    public PlayerNameErrorMessage()
    {
        message="the given name is already used";
    }
    @Override
    public String getMessage() {
        return message;
    }
}
