package com.example.fiszingsymulatjon;

public class Rekin extends Organizm {
    private int iloscZebow;
    private boolean czyPoluje;
    private int licznikDni;
    private static final int MAX_GLOD = 100;
    private static final int PROG_POLOWANIA = 81;
    private static final double SZANSA_UTRATY_ZEBA = 0.4; // 40% szans

    public Rekin(int x, int y, int glod, int iloscZebow, boolean czyPoluje) {
        super(x, y);
        this.setGlod(Math.min(glod, MAX_GLOD));
        this.iloscZebow = iloscZebow;
        this.czyPoluje = glod < PROG_POLOWANIA;
        this.licznikDni = 0;
    }

    public void zjedzRybe() {
        setGlod(getGlod()+20);
        if (Math.random() < SZANSA_UTRATY_ZEBA && iloscZebow > 0) {
            iloscZebow--;
        }
    }

    public boolean czyMartwy() {
        return getGlod() <= 0;
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
        if (czyMartwy()) return;
        przemieszczaj();
        licznikDni++;
        
        // Co 5 dni zmniejszamy głód o 2
        if (licznikDni >= 5) {
            setGlod(Math.max(0, getGlod() - 2));
            licznikDni = 0;
            aktualizujStatusPolowania();
        }
    }

    @Override
    public void setGlod(int glod) {
        super.setGlod(Math.min(Math.max(0, glod), MAX_GLOD));
        aktualizujStatusPolowania();
    }

    private void aktualizujStatusPolowania() {
        setCzyPoluje(getGlod() < PROG_POLOWANIA);
    }


    // Gettery i settery
    public int getIloscZebow() { return iloscZebow; }
    public void setIloscZebow(int iloscZebow) { this.iloscZebow = iloscZebow; }
    public boolean isCzyPoluje() { return czyPoluje; }
    public void setCzyPoluje(boolean czyPoluje) { this.czyPoluje = czyPoluje; }
}