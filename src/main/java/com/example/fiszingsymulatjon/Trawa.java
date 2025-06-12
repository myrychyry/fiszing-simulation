package com.example.fiszingsymulatjon;

public class Trawa extends Schronienie {
    private int gestosc;

    public Trawa(int x, int y, int pojemnosc, int gestosc) {
        super(x, y, pojemnosc);
        this.gestosc = gestosc;
    }

    // Gettery i settery
    public int getGestosc() { return gestosc; }
    public void setGestosc(int gestosc) { this.gestosc = gestosc; }
}