package com.example.fiszingsymulatjon;

public abstract class Organizm {
    private int x;
    private int y;
    private int wiek;
    private int glod;
    private int populacja;
    private String plec;

    public Organizm(int x, int y) {
        this.x = x;
        this.y = y;
        this.wiek = 0;
    }

    public abstract void przemieszczaj();
    public abstract void zyj();

    // Gettery i settery
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWiek() { return wiek; }
    public void setWiek(int wiek) { this.wiek = wiek; }
    public int getGlod() { return glod; }
    public void setGlod(int glod) { this.glod = glod; }
    public int getPopulacja() { return populacja; }
    public void setPopulacja(int populacja) { this.populacja = populacja; }
    public String getPlec() { return plec; }
    public void setPlec(String plec) { this.plec = plec; }
}