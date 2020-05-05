package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.NotExistingGodException;

public enum GodsList {
    APOLLO("Apollo"), ARTEMIS("Artemis"), ATHENA("Athena"),
    ATLAS( "Atlas"), CHRONUS("Chronus"),DEMETER("Demeter"), HEPHAESTUS("Hephaestus"),
    HERA("Hera"), HESTIA("Hestia"),MINOTAUR( "Minotaur"), PAN( "Pan"),
    PERSEPHONE("Persephone"),PROMETHEUS("Prometheus");
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

