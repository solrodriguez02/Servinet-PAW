package ar.edu.itba.paw.model;

public enum Ratings {
    REGULAR("Regular", 2),
    GOOD("Bueno", 3),
    VERY_GOOD("Muy bueno", 4),
    EXCELLENT("Excelente", 5);

    private final String name;
    private final int minValue;

    Ratings(String name, int minValue) {
        this.name = name;
        this.minValue = minValue;
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
