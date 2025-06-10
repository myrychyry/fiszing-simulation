package com.example.fiszingsymulatjon;

public class Ryba extends Organizm {
    private int silaUcieczki;
    private String kolor;
    private static final double SZANSA_ROZMNOZENIA = 0.049;

    public Ryba(int x, int y, String kolor, int glod, int silaUcieczki) {
        super(x, y);
        this.kolor = kolor;
        this.setGlod(glod);
        this.silaUcieczki = silaUcieczki;
    }
    public boolean sprobujRozmnozycSie() {
        return Math.random() < SZANSA_ROZMNOZENIA;
    }

    public Ryba stworzPotomstwo(int x, int y) {
        return new Ryba(x, y, this.kolor, 20, 50);
    }


    public void szukajPlanktonu() {
        // Implementacja
    }

    public void unikajDrapieznikow() {
        // Implementacja
    }

    public void szukajSchronienia() {
        // Implementacja
    }

    @Override
    public void przemieszczaj() {
        // Losowy ruch o 1 kratkę w dowolnym kierunku (włącznie ze skosem)
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
                setY(getY() + 1);//somzioany
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

    @Override
    public void zyj() {
        przemieszczaj();
        // Zmniejszamy głód co 5 dni
        if (getWiek() % 5 == 0) {
            setGlod(getGlod() - 1);
        }
        // Zwiększamy wiek
        setWiek(getWiek() + 1);
    }
    public void zjedzPlankton() {
        setGlod(Math.min(20, getGlod() + 5)); // Dodajemy 5 punktów głodu, ale nie więcej niż 20
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