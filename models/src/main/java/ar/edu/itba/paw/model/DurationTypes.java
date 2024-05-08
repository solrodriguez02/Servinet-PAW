package ar.edu.itba.paw.model;

public enum DurationTypes{
    FIFTEEN_MINS(15,"duration.fifteenmins"),
    HALF_HOUR(30,"duration.halfhour"),
    FORTYFIVE_MINS(45,"duration.fortyfivemins"),
    HOUR(60,"duration.hour"),
    NINETY_MINS(90,"duration.ninetymins"),
    TWO_HOURS(120,"duration.twohours"),
    THREE_HOURS(180,"duration.threehours");
    private final int value;
    private final String codeMsg;

    DurationTypes(int value,String codeMsg){
        this.value=value;
        this.codeMsg=codeMsg;
    }

    public int getValue() {
        return value;
    }
    public String getCodeMsg(){
        return codeMsg;
    }
}
