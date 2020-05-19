package it.polimi.ingsw.Utils.ErrorMessages;

public class GodNotActionableErrorMessage extends ErrorMessage {
    private final String message;

    public GodNotActionableErrorMessage()
    {
        message="The God effect is not actionable";
    }

    public String getMessage()
    {
        return message;
    }
}
