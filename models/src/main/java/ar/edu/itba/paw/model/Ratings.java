package ar.edu.itba.paw.model;

public enum Ratings {
    REGULAR("Regular", 2, "rating.regular"),
    GOOD("Bueno", 3, "rating.good"),
    VERY_GOOD("Muy bueno", 4, "rating.verygood"),
    EXCELLENT("Excelente", 5, "rating.excellent");

    private final String name;
    private final int minValue;
    private final String codeMsg;

    Ratings(String name, int minValue, String codeMsg) {
        this.name = name;
        this.minValue = minValue;
        this.codeMsg = codeMsg;
    }

    public String getCodeMsg() {
        return codeMsg;
    }
    public String getName() {
        return name;
    }

    public int getMinValue() {
        return minValue;
    }

    public static int getMinValueByName(String name) {
        for (Ratings rating : Ratings.values()) {
            if (rating.getName().equalsIgnoreCase(name)) {
                return rating.getMinValue();
            }
        }
        return 0;
    }
}
