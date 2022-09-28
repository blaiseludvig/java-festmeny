package hu.petrik.festmeny;

import java.time.LocalDateTime;

public class Festmeny {
    private String cim, festo, stilus;

    private int licitekSzama = 0;
    private int legmagasabbLicit = 0;

    private LocalDateTime legutolsoLicitIdeje = null;

    private boolean elkelt = false;

    Festmeny(String cim, String festo, String stilus) {
        this.cim = cim;
        this.festo = festo;
        this.stilus = stilus;
    }


    public String getFesto() {
        return festo;
    }

    public String getStilus() {
        return stilus;
    }

    public int getLicitekSzama() {
        return licitekSzama;
    }

    public int getLegmagasabbLicit() {
        return legmagasabbLicit;
    }

    public LocalDateTime getLegutolsoLicitIdeje() {
        return legutolsoLicitIdeje;
    }

    public boolean getElekelt() {
        return elkelt;
    }

    public void setElkelt(boolean value) {
        elkelt = value;
    }

    public void licit() {

    }

    public void licit(int licit) {

    }

}
