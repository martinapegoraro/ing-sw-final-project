package it.polimi.ingsw.Utils;

/**
 * This class is used to select username of the user
 * and the number of players
 * the same user wants to play with
 */

public class PlayerNumberChoice extends Choice {
    public int playerNumber;
    public String name;

    /**
     * the builder sets the number of players and the
     * username of the player who sent the choice
     * @param name is the name of the player who sends the choice
     * @param pN is the number of players which the player wants to play with
     */

    public PlayerNumberChoice(String name,int pN)
    {
        playerNumber = pN;
        this.name=name;
        this.type = "PlayerNumberChoice";
    }
}
