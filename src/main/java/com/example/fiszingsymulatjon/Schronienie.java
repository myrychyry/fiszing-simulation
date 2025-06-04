package com.example.fiszingsymulatjon;

public abstract class Schronienie {
    private int x;
    private int y;
    private int pojemnosc;

    public Schronienie(int x, int y, int pojemnosc) {
        this.x = x;
        this.y = y;
        this.pojemnosc = pojemnosc;
    }

    // Gettery i settery
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getPojemnosc() { return pojemnosc; }
    public void setPojemnosc(int pojemnosc) { this.pojemnosc = pojemnosc; }
}