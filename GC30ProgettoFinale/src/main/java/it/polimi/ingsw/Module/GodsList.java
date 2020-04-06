package it.polimi.ingsw.Module;

public enum GodsList {
    APOLLO(1), ARTEMIS(1), ATHENA(2), ATLAS(1), DEMETER(1), HEPHAESTUS(1), MINOTAUR(1), PAN(0), PROMETHEUS(1);
    private int type;
    GodsList(int type){
        this.type = type;
    }
    public int gettype() {
        return type;
    }

    public static void getEffect(GodsList god){
        switch (god){
            case APOLLO: //effect
                break;
            case ARTEMIS: //effect
                break;
            case ATHENA: //effect
                break;
            case ATLAS: //effect
                break;
            case DEMETER: //effect
                break;
            case HEPHAESTUS: //effect
                break;
            case MINOTAUR: //effect
                break;
            case PAN: //effect
                break;
            case PROMETHEUS: //effect
                break;
            default: //no effect
                break;
        }
    }



}

