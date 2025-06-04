
package com.example.fiszingsymulatjon;

public class Rekin extends Organizm {
    private int iloscZebow;
    private boolean czyPoluje;

    public Rekin(int x, int y, int glod, int iloscZebow, boolean czyPoluje) {
        super(x, y);
        this.setGlod(glod);
        this.iloscZebow = iloscZebow;
        this.czyPoluje = czyPoluje;
    }

    public void poluj() {
        // Implementacja
    }

    @Override
    public void przemieszczaj() {
        setX(getX() + (int)(Math.random() * 5) - 2);
        setY(getY() + (int)(Math.random() * 5) - 2);
    }

    @Override
    public void zyj() {
        przemieszczaj();
        if (czyPoluje) {
            poluj();
        }
        setGlod(getGlod() - 2);
    }

    // Gettery i settery
    public int getIloscZebow() { return iloscZebow; }
    public void setIloscZebow(int iloscZebow) { this.iloscZebow = iloscZebow; }
    public boolean isCzyPoluje() { return czyPoluje; }
    public void setCzyPoluje(boolean czyPoluje) { this.czyPoluje = czyPoluje; }
}