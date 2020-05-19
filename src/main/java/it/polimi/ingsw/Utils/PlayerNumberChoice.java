package it.polimi.ingsw.Utils;

public class PlayerNumberChoice extends Choice {
    public int playerNumber;
    public String name;
    public PlayerNumberChoice(String name,int pN)
    {
        playerNumber = pN;
        this.name=name;
        this.type = "PlayerNumberChoice";
    }
}
