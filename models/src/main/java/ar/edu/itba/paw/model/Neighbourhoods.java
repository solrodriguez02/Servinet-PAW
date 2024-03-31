package ar.edu.itba.paw.model;

public enum Neighbourhoods {
    ALMAGRO("Almagro"),
    BALVANERA("Balvanera"),
    BARRACAS("Barracas"),
    BELGRANO("Belgrano"),
    BOEDO("Boedo"),
    CABALLITO("Caballito"),
    CHACARITA("Chacarita"),
    COGHLAN("Coghlan"),
    COLEGIALES("Colegiales"),
    CONSTITUCION("Constitucion"),
    FLORES("Flores"),
    FLORESTA("Floresta"),
    LA_BOCA("La Boca"),
    LA_PATERNAL("La Paternal"),
    LINIERS("Liniers"),
    MATADEROS("Mataderos"),
    MONTE_CASTRO("Monte Castro"),
    MONTSERRAT("Montserrat"),
    NUEVA_POMPEYA("Nueva Pompeya"),
    PALERMO("Palermo"),;
    private final String value;
    Neighbourhoods(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //TODO: implementar Interfaz que agrupe enums? -> definir findByValue genérico
    public static Neighbourhoods findByValue(String value) {
        for (Neighbourhoods neighbourhood: values()) {
            if (neighbourhood.getValue().equalsIgnoreCase(value)) {
                return neighbourhood;
            }
        }
        return null;
    }
}
