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
        setX(getX() + (int)(Math.random() * 3) - 1);
        setY(getY() + (int)(Math.random() * 3) - 1);
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