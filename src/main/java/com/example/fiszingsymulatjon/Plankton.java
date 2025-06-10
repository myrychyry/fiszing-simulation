package com.example.fiszingsymulatjon;

public class Plankton {
    private int x;
    private int y;

    public Plankton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void prad() {
        if (Math.random() < 0.03) {
            // Losowy kierunek ruchu (góra, dół, lewo, prawo)
            int kierunek = (int)(Math.random() * 4);
            switch (kierunek) {
                case 0 -> y--; // góra
                case 1 -> y++; // dół
                case 2 -> x--; // lewo
                case 3 -> x++; // prawo
            }
        }
    }

    // Gettery i settery
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}