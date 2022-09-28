package hu.petrik.festmeny;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        if (getElekelt()) {
            System.out.println("Hiba! A festmény már elkelt.");
            return;
        }

        if (licitekSzama == 0) {
            legmagasabbLicit = 100;
        } else {
            licit(10);
            return;
        }

        licitekSzama += 1;
        legutolsoLicitIdeje = LocalDateTime.now();

        int n = 10;

        while (true) {

            if (legmagasabbLicit / n < 100) {
                legmagasabbLicit = (int) Math.floor(legmagasabbLicit / n) * n;
                return;
            }

            n = n * 10;
        }

    }

    public void licit(int mertek) {
        if (getElekelt()) {
            System.out.println("Hiba! A festmény már elkelt.");
            return;
        }

        if (mertek < 10 || mertek > 100) {
            System.out.println("Hiba! Csak 10% és 100% közötti értékkel lehet licitálni.");
            return;
        }

        licitekSzama += 1;
        legmagasabbLicit = (int) Math.floor(legmagasabbLicit * (1 + (mertek / 100.0)));
        legutolsoLicitIdeje = LocalDateTime.now();

        int n = 10;

        while (true) {

            if (legmagasabbLicit / n < 100) {
                legmagasabbLicit = (int) Math.floor(legmagasabbLicit / n) * n;
                return;
            }

            n = n * 10;
        }

    }

    @Override
    public String toString() {
        String legutolso_licit = "";
        if (legutolsoLicitIdeje != null) {
            legutolso_licit = legutolsoLicitIdeje.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        return String.format("%s: %s (%s)%n", festo, cim, stilus)
                + String.format("%s%n", elkelt ? "elkelt" : "nem kelt el")
                + (licitekSzama > 0
                ? String.format("%d$ - %s (összesen: %d)", legmagasabbLicit, legutolso_licit, licitekSzama)
                : String.format("(nincs licit)")
        );
    }
}
