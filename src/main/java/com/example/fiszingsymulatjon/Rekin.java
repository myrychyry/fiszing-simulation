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