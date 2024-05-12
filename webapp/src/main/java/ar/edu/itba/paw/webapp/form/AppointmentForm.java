package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Neighbourhoods;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class AppointmentForm {
    //acá no toma not empty
    //armo custom?
    private Neighbourhoods neighbourhood;

    @NotEmpty
    @Size(max = 255)
    private String location;

    //no funciona @Future
    //armar conversor para el formato de fecha
    //formato que se acepta 'YYYY-MM-DDTHH:MM'
    @NotNull
    private LocalDateTime date;

    public Neighbourhoods getNeighbourhood() {
        return neighbourhood;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setNeighbourhood(Neighbourhoods neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //ya probamos pasandole date pero no se toma
    public void setDate(String date) {
        this.date = LocalDateTime.parse(date);
    }

}