package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.NotExistingGodException;

/**
 * This class represents the enumeration of the possible Gods
 */
public enum GodsList {
    APOLLO("Apollo"), ARTEMIS("Artemis"), ATHENA("Athena"),
    ATLAS( "Atlas"), CHRONUS("Chronus"),DEMETER("Demeter"), HEPHAESTUS("Hephaestus"),
    HERA("Hera"), HESTIA("Hestia"),MINOTAUR( "Minotaur"), PAN( "Pan"),
    PERSEPHONE("Persephone"),PROMETHEUS("Prometheus"),ZEUS("Zeus");
    private String name;

    /**
     * given
     * @param name it is filled with a string containing the name of the God
     */
    GodsList(String name){
        this.name = name;
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
            if(godName.equals(god.name))
                return god;
        }
        throw new NotExistingGodException("This God doesn't exist");
    }
}

