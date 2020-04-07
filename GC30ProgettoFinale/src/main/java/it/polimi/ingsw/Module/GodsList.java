package it.polimi.ingsw.Module;

public enum GodsList {
    APOLLO(1, "Apollo"), ARTEMIS(1,"Artemis"), ATHENA(2, "Athena"), ATLAS(1, "Atlas"), DEMETER(1,"Demeter"), HEPHAESTUS(1,"Hephaestus"), MINOTAUR(1, "Minotaur"), PAN(0, "Pan"), PROMETHEUS(1,"Prometheus");
    private int type;
    private String name;
    GodsList(int type, String name){
        this.type = type;
        this.name = name;
    }
    public int getType() {
        return type;
    }

    public String getName(){
        return name;

    }
}

