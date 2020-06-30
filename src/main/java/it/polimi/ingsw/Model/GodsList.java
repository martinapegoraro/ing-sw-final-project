package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.NotExistingGodException;

/**
 * This class represents the enumeration of the possible Gods, with a short description of the god effect
 */
public enum GodsList {
    APOLLO("Apollo", "<html>Your Worker may<br>" +
            "move into an opponent Worker’s<br>" +
            "space by forcing their Worker to<br>" +
            "the space yours just vacated.</html>"),
    ARTEMIS("Artemis", "<html>Your Worker may<br>" +
            "move one additional time, but not<br>" +
            "back to its initial space.</html>"),
    ATHENA("Athena", "<html>If one of your<br>" +
            "Workers moved up on your last<br>" +
            "turn, opponent Workers cannot<br>" +
            "move up this turn</html>"),
    ATLAS( "Atlas", "<html>Your Worker may<br>" +
            "build a dome at any level.</html>"),
    CHRONUS("Chronus", "<html>You also win<br>" +
            "when there are at least five<br>" +
            "Complete Towers on the board</html>"),
    DEMETER("Demeter", "<html>Your Worker may<br>" +
            "build one additional time, but not<br>" +
            "on the same space</html>"),
    HEPHAESTUS("Hephaestus", "<html>Your Worker may<br>" +
            "build one additional block (not<br>" +
            "dome) on top of your first block</html>"),
    HERA("Hera", "<html>An opponent<br>" +
            "cannot win by moving into a<br>" +
            "perimeter space</html>"),
    HESTIA("Hestia", "<html>Your Worker may<br>" +
            "build one additional time, but this<br>" +
            "cannot be on a perimeter space</html>"),
    MINOTAUR( "Minotaur", "<html>Your Worker may<br>" +
            "move into an opponent Worker’s<br>" +
            "space, if their Worker can be<br>" +
            "forced one space straight backwards to an<br>" +
            "unoccupied space at any level.</html>"),
    PAN( "Pan", "<html>You also win if<br>" +
            "your Worker moves down two or<br>" +
            "more levels.</html>"),
    PERSEPHONE("Persephone", "<html>If possible, at<br>" +
            "least one Worker must move up<br>" +
            "this turn</html>"),
    PROMETHEUS("Prometheus", "<html>If your Worker does<br>" +
            "not move up, it may build both<br>" +
            "before and after moving.</html>"),
    ZEUS("Zeus", "<html>Your Worker may<br>" +
            "build a block under itself.</html>");
    private String name, desc;

    /**
     * given
     * @param name contains the name of the God
     * @param desc contains a description of the God effect
     */
    GodsList(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    /**
     * @return the name of the God
     */
    public String getName(){
        return name;
    }

    /**
     * given
     * @param godName , which is a string containing a God's name
     * @return the right element of the enumeration
     * @throws NotExistingGodException if the given string (godName) does not correspond to any of the existing Gods
     */
    public static GodsList getGod(String godName) throws NotExistingGodException
    {
        for (GodsList god:GodsList.values()) {
            if(godName.equals(god.name) || godName.equals(god.name.toUpperCase()))
                return god;
        }
        throw new NotExistingGodException("This God doesn't exist");
    }

    /**
     * @return a description of the gods effect*/
    public String getDesc()
    {
        return desc;
    }
}

