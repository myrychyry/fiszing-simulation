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
        // Losowy ruch o 1 kratkę: góra, dół, lewo lub prawo
        int kierunek = (int)(Math.random() * 4);
        switch (kierunek) {
            case 0 -> setY(getY() - 1); // góra
            case 1 -> setY(getY() + 1); // dół
            case 2 -> setX(getX() - 1); // lewo
            case 3 -> setX(getX() + 1); // prawo
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