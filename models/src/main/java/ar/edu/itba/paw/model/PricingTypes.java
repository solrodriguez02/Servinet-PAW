package ar.edu.itba.paw.model;

public enum PricingTypes {
    PER_HOUR ("Por hora"),
    PER_TOTAL ("Total"),
    BUDGET ("Producto"),
    TBD ("A determinar");
    private final String value;

   PricingTypes(String value) {
       this.value = value;
   }
    public String getValue() {
        return value;
    }

    //TODO: implementar Interfaz que agrupe enums? -> definir findByValue genérico
    public static PricingTypes findByValue(String value) {
        for (PricingTypes pricingType: values()) {
            if (pricingType.getValue().equalsIgnoreCase(value)) {
                return pricingType;
            }
        }
        return null;
    }


}
