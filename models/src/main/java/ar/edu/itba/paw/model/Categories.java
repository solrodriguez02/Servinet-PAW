package ar.edu.itba.paw.model;

public enum Categories {
    LIMPIEZA("Limpieza", "cleaning_services"),
    BELLEZA("Belleza", "diamond"),
    ARREGLOS_CALIFICADOS("Arreglos Calificados", "handyman"),
    MASCOTAS("Mascotas", "pets"),
    EXTERIORES("Exteriores", "local_florist"),
    EVENTOS_Y_CELEBRACIONES ("Eventos y Celebraciones", "celebration"),
    TRANSPORTE ("Transporte", "local_shipping"),
    CONSULTORIA ("Consultoria", "help"),
    PELUQUERIA ("Peluqueria", "face"),
    SALUD ("Salud", "health_and_safety");

    private final String value;
    private final String icon;

    Categories(String value, String icon){
        this.value = value;
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public String getIcon() {
        return icon;
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
