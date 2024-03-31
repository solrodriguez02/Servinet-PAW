package ar.edu.itba.paw.model;

public enum Categories {
    LIMPIEZA("Limpieza"),
    BELLEZA("Belleza"),
    ARREGLOS_CALIFICADOS("Arreglos Calificados"),
    MASCOTAS("Mascotas"),
    EXTERIORES("Exteriores"),
    EVENTOS_Y_CELEBRACIONES ("Eventos y Celebraciones"),
    TRANSPORTE ("Transporte"),
    CONSULTORIA ("Consultoría"),
    SALUD ("Salud");

    private final String value;
    Categories(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //TODO: implementar Interfaz que agrupe enums? -> definir findByValue genérico
    public static Categories findByValue(String value) {
        for (Categories category: values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        return null;
    }

}
