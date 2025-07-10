package com.grupo2.clinicasalud.model.api;

import java.time.LocalDateTime;
import java.util.Date;

public class Evento {

    private String title;

    private LocalDateTime start;

    private LocalDateTime end;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
