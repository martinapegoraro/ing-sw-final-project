package it.polimi.ingsw.Module;

import it.polimi.ingsw.Module.Exceptions.NotExistingGodException;

public enum GodsList {
    APOLLO("Apollo"), ARTEMIS("Artemis"), ATHENA("Athena"),
    ATLAS( "Atlas"),DEMETER("Demeter"), HEPHAESTUS("Hephaestus"),
    MINOTAUR( "Minotaur"), PAN( "Pan"),PROMETHEUS("Prometheus");
    private String name;
    GodsList(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public GodsList getGod(String godName) throws NotExistingGodException
    {
        for (GodsList god:GodsList.values()) {
            if(godName.equals(god.name))
                return god;
        }
        throw new NotExistingGodException("This God doesn't exist");
    }
}

