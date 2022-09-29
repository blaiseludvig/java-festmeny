package hu.petrik.festmeny;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static ArrayList<Festmeny> festmenyek = new ArrayList<>();

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        festmenyek.add(new Festmeny("amongus", "béla", "barokk"));
        festmenyek.add(new Festmeny("annsusu", "pista", "gót"));

        System.out.print("Adja meg, hogy hány festmény szeretne megadni: ");

        int festmeny_be = Integer.parseInt(in.nextLine());

        for (int i = 0; i < festmeny_be; i++) {
            System.out.print("A festmény címe: ");
            String cim = in.nextLine();

            System.out.print("A festő: ");
            String festo = in.nextLine();

            System.out.print("A festmény stílusa: ");
            String stilus = in.nextLine();

            festmenyek.add(new Festmeny(cim, festo, stilus));

        }

        parseFestmenyek();
        randomLicit();
        felhasznaloLicit();
        legdragabbFestmeny();
        tobb_mint_10_licit();
        nemKeltEl();
        atrendez();
        fajlbaIr();

    }

    static void parseFestmenyek() {
        try (BufferedReader br = new BufferedReader(new FileReader("festmenyek.csv"))) {
            for (; ; ) {
                String data = br.readLine();
                if (data == null || data.equals("")) {
                    break;
                }

                String[] data_arr = data.split(";");
                festmenyek.add(new Festmeny(data_arr[0], data_arr[1], data_arr[2]));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    static void randomLicit() {
        for (int i = 0; i < 20; i++) {
            int r = ThreadLocalRandom.current().nextInt(0, festmenyek.size());
            festmenyek.get(r).licit();
        }
    }

    static void felhasznaloLicit() {
        for (; ; ) {
            for (int i = 0; i < festmenyek.size(); i++) {
                System.out.printf("%d:%n%s%n", i + 1, festmenyek.get(i));
            }

            System.out.println("0:  kilépés");
            System.out.printf("Adja meg a festmény sorszámát (1 - %d): ", festmenyek.size());

            int index;
            try {
                index = Integer.parseInt(in.nextLine());
            } catch (Exception e) {
                System.out.println("Adjon meg egy egész számot!");
                continue;
            }

            if (index == 0) {
                break;
            }

            if (index < 1 || index > festmenyek.size()) {
                System.out.println("Nem létező sorszám!");
                continue;
            }

            if (festmenyek.get(index - 1).getElekelt()) {
                System.out.println("A festmény már elkelt!");
                continue;
            }

            double perc_utolso_licit_ota = ChronoUnit.MINUTES.between(festmenyek.get(index - 1).getLegutolsoLicitIdeje(), LocalDateTime.now());
            if (perc_utolso_licit_ota > 2) {
                festmenyek.get(index - 1).setElkelt(true);
                System.out.println("A festmény már elkelt!");
            }

            System.out.print("Adja meg a licit mértékét (10%): ");

            int mertek;
            try {
                String input = in.nextLine();
                if (input.equals("")) {
                    mertek = 10;
                } else {
                    mertek = Integer.parseInt(input);
                }

            } catch (Exception e) {
                System.out.println("Adjon meg egy egész számot!");
                continue;
            }

            festmenyek.get(index - 1).licit(mertek);
            System.out.println("Sikeresen licitált a festményre!");

        }

        for (Festmeny f : festmenyek) {
            if (f.getLicitekSzama() > 0) {
                f.setElkelt(true);
            }
        }

    }

    static void legdragabbFestmeny() {
        Festmeny legdragabb = festmenyek.get(0);

        for (int i = 1; i < festmenyek.size(); i++) {
            if (festmenyek.get(i).getLegmagasabbLicit() > legdragabb.getLegmagasabbLicit()) {
                legdragabb = festmenyek.get(i);
            }
        }

        System.out.print("A legdrágább festmény: ");
        System.out.println(legdragabb);


    }

    static void tobb_mint_10_licit() {
        for (Festmeny f : festmenyek) {
            if (f.getLicitekSzama() > 10) {
                System.out.println("A festmény amire 10-nél többször licitáltak: ");
                System.out.println(f);
                break;
            }
        }
    }

    static void nemKeltEl() {
        int n = 0;

        for (Festmeny f : festmenyek) {
            if (!f.getElekelt()) {
                n += 1;
            }
        }

        System.out.printf("%d festmény nem kelt el.%n", n);

    }

    static void atrendez() {
        festmenyek.sort(Comparator.comparing(Festmeny::getLegmagasabbLicit).reversed());
    }

    static void fajlbaIr() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("festmenyek_rendezett.csv"))) {
            for (Festmeny f : festmenyek) {
                bw.write(String.format("%s;%n", f));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
