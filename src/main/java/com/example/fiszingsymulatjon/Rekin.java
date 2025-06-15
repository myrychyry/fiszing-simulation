package com.example.fiszingsymulatjon;

/**
 * Klasa reprezentująca rekina w symulacji oceanu.
 * Rekin jest drapieżnikiem, który poluje na ryby gdy jego poziom głodu przekracza określony próg.
 * Podczas polowania może stracić zęby, co wpływa na jego skuteczność.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class Rekin extends Organizm {
    /** Liczba zębów rekina wpływająca na skuteczność polowania */
    private int iloscZebow;
    
    /** Określa czy rekin jest obecnie w trybie polowania */
    private boolean czyPoluje;
    
    /** Licznik dni służący do kontroli poziomu głodu */
    private int licznikDni;
    
    /** Maksymalny poziom głodu rekina */
    private static final int MAX_GLOD = 100;
    
    /** Próg głodu, po którego przekroczeniu rekin zaczyna polować */
    private static final int PROG_POLOWANIA = 81;
    
    /** Prawdopodobieństwo utraty zęba podczas polowania (40%) */
    private static final double SZANSA_UTRATY_ZEBA = 0.4;

    /**
     * Tworzy nowego rekina o zadanych parametrach początkowych.
     *
     * @param x Początkowa pozycja X na planszy
     * @param y Początkowa pozycja Y na planszy
     * @param glod Początkowy poziom głodu
     * @param iloscZebow Początkowa liczba zębów
     * @param czyPoluje Początkowy stan polowania
     */
    public Rekin(int x, int y, int glod, int iloscZebow, boolean czyPoluje) {
        super(x, y);
        this.setGlod(Math.min(glod, MAX_GLOD));
        this.iloscZebow = iloscZebow;
        this.czyPoluje = glod < PROG_POLOWANIA;
        this.licznikDni = 0;
    }

    /**
     * Wykonuje akcję zjedzenia ryby przez rekina.
     * Zwiększa poziom głodu o 20 jednostek i może spowodować utratę zęba z szansą 40%.
     */
    public void zjedzRybe() {
        setGlod(getGlod()+20);
        if (Math.random() < SZANSA_UTRATY_ZEBA && iloscZebow > 0) {
            iloscZebow--;
        }
    }

    /**
     * Sprawdza czy rekin jest martwy (gdy poziom głodu spadnie do 0).
     *
     * @return true jeśli rekin jest martwy, false w przeciwnym razie
     */
    public boolean czyMartwy() {
        return getGlod() <= 0;
    }

    /**
     * Przemieszcza rekina w losowym kierunku o jedno pole.
     * Możliwe są ruchy w 8 kierunkach (góra, dół, lewo, prawo i po przekątnych).
     */
    @Override
    public void przemieszczaj() {
        // Losowy ruch o 1 kratkę w dowolnym kierunku
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

    /**
     * Realizuje cykl życiowy rekina w jednym dniu symulacji.
     * Co 5 dni zmniejsza poziom głodu o 2 jednostki.
     */
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

    /**
     * Ustawia poziom głodu rekina z zachowaniem limitu maksymalnego.
     *
     * @param glod Nowy poziom głodu
     */
    @Override
    public void setGlod(int glod) {
        super.setGlod(Math.min(Math.max(0, glod), MAX_GLOD));
        aktualizujStatusPolowania();
    }

    /**
     * Aktualizuje stan polowania rekina w zależności od poziomu głodu.
     */
    private void aktualizujStatusPolowania() {
        setCzyPoluje(getGlod() < PROG_POLOWANIA);
        }

    // Gettery i settery z dokumentacją
    /**
     * @return Aktualna liczba zębów rekina
     */
    public int getIloscZebow() { return iloscZebow; }

    /**
     * @param iloscZebow Nowa liczba zębów rekina
     */
    public void setIloscZebow(int iloscZebow) { this.iloscZebow = iloscZebow; }

    /**
     * @return true jeśli rekin jest w trybie polowania, false w przeciwnym razie
     */
    public boolean isCzyPoluje() { return czyPoluje; }

    /**
     * @param czyPoluje Nowy stan trybu polowania
     */
    public void setCzyPoluje(boolean czyPoluje) { this.czyPoluje = czyPoluje; }
}