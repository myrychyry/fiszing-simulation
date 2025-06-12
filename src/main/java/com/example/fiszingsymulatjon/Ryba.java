package com.example.fiszingsymulatjon;

public class Ryba extends Organizm {
    private int silaUcieczki;
    private String kolor;
    private static final double SZANSA_ROZMNOZENIA = 0.06;
    private static final int MAX_SILA_UCIECZKI = 100;
    private static final int MAX_WIEK = 100;
    private static final double WAGA_GLODU = 0.5;
    private static final double WAGA_WIEKU = 0.3;
    private static final double WAGA_KOLORU = 0.2;
    public Ryba(int x, int y, String kolor, int glod, int silaUcieczki) {
        super(x, y);
        this.kolor = kolor;
        this.setGlod(glod);
        aktualizujSileUcieczki();
    }

    private void aktualizujSileUcieczki() {
        // Składowa głodu (im większy głód, tym większa siła ucieczki)
        double skladowaGlodu = (getGlod() / 20.0) * WAGA_GLODU * MAX_SILA_UCIECZKI;
        
        // Składowa wieku (im mniejszy wiek, tym większa siła ucieczki)
        double skladowaWieku = ((MAX_WIEK - getWiek()) / (double) MAX_WIEK) * WAGA_WIEKU * MAX_SILA_UCIECZKI;
        
        // Składowa koloru
        double skladowaKoloru = 0;
        switch(getKolor()) {
            case "niebieski" -> skladowaKoloru = 1 * WAGA_KOLORU * MAX_SILA_UCIECZKI;
            case "zolty" -> skladowaKoloru = 0.8 * WAGA_KOLORU * MAX_SILA_UCIECZKI;
            case "czerwony" -> skladowaKoloru = 0.6 * WAGA_KOLORU * MAX_SILA_UCIECZKI;
            case "fiolet" -> skladowaKoloru = 0.4 * WAGA_KOLORU * MAX_SILA_UCIECZKI;
        }

        // Całkowita siła ucieczki
        this.silaUcieczki = (int) Math.min(MAX_SILA_UCIECZKI, Math.max(0, skladowaGlodu + skladowaWieku + skladowaKoloru));
        //System.out.printf("siema ucieczki: " + this.silaUcieczki + "\n");
    }

    @Override
    public void zyj() {
        przemieszczaj();
        if (getWiek() % 5 == 0) {
            setGlod(getGlod() - 1);
        }
        setWiek(getWiek() + 1);
        //System.out.printf("wiek sie zwiekrzyl: " + getWiek() + "\n");
        aktualizujSileUcieczki();
    }

    public boolean sprobujRozmnozycSie() {
        return Math.random() < SZANSA_ROZMNOZENIA;
    }

    public Ryba stworzPotomstwo(int x, int y) {
        return new Ryba(x, y, this.kolor, 20, 50);
    }

    @Override
    public void przemieszczaj() {
        // Losowy ruch o 1 kratkę w dowolnym kierunku
        int kierunek = (int)(Math.random() * 8);
        switch (kierunek) {
            case 0 -> { // góra
                setY(getY() - 1);
            }
            case 1 -> { // góra-prawo
                setY(getY() - 1);
                setX(getX() + 1);
            }
            case 2 -> { // prawo
                setX(getX() + 1);
            }
            case 3 -> { // dół-prawo
                setY(getY() + 1);
                setX(getX() + 1);
            }
            case 4 -> { // dół
                setY(getY() + 1);
            }
            case 5 -> { // dół-lewo
                setY(getY() + 1);
                setX(getX() - 1);
            }
            case 6 -> { // lewo
                setX(getX() - 1);
            }
            case 7 -> { // góra-lewo
                setY(getY() - 1);
                setX(getX() - 1);
            }
        }
    }

    public void zjedzPlankton() {
        setGlod(Math.min(20, getGlod() + 5));
    }

    public boolean czyMartwa() {
        return getGlod() <= 0;
    }

    // Gettery i settery
    public int getSilaUcieczki() { return silaUcieczki; }
    public void setSilaUcieczki(int silaUcieczki) { this.silaUcieczki = silaUcieczki; }
    public String getKolor() { return kolor; }
    public void setKolor(String kolor) { this.kolor = kolor; }
}