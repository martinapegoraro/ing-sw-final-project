package it.polimi.ingsw.Utils.ErrorMessages;

public class SentChoiceError extends ErrorMessage {
    final String errorMessage;

    public SentChoiceError()
    {
        errorMessage="the sent choice is not valid";
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
