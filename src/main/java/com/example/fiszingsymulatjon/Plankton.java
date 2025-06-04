
package com.example.fiszingsymulatjon;

public class Plankton {
    private int x;
    private int y;

    public Plankton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void prad() {
        x += (int)(Math.random() * 3) - 1;
        y += (int)(Math.random() * 3) - 1;
    }

    // Gettery i settery
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}