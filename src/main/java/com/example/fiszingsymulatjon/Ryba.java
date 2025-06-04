package com.example.fiszingsymulatjon;

public class Ryba extends Organizm {
    private int silaUcieczki;
    private String kolor;

    public Ryba(int x, int y, String kolor, int glod, int silaUcieczki) {
        super(x, y);
        this.kolor = kolor;
        this.setGlod(glod);
        this.silaUcieczki = silaUcieczki;
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

    @Override
    public void zyj() {
        przemieszczaj();
        setGlod(getGlod() - 1);
    }

    // Gettery i settery
    public int getSilaUcieczki() { return silaUcieczki; }
    public void setSilaUcieczki(int silaUcieczki) { this.silaUcieczki = silaUcieczki; }
    public String getKolor() { return kolor; }
    public void setKolor(String kolor) { this.kolor = kolor; }
}