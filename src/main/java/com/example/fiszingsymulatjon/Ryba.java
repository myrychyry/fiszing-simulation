package com.example.fiszingsymulatjon;

/**
 * Klasa reprezentująca rybę w symulacji oceanu.
 * Ryba jest organizmem, który porusza się w wodzie, żywi się planktonem
 * i może być ofiarą rekinów. Posiada mechanizmy obronne w postaci siły ucieczki,
 * która zależy od wieku, głodu i koloru ryby.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class Ryba extends Organizm {
    /** Siła ucieczki ryby wpływająca na szansę uniknięcia ataku rekina */
    private int silaUcieczki;
    
    /** Kolor ryby wpływający na jej zdolność do ucieczki */
    private String kolor;
    
    /** Szansa na rozmnożenie się ryby (6%) */
    private static final double SZANSA_ROZMNOZENIA = 0.06;
    
    /** Maksymalna możliwa siła ucieczki */
    private static final int MAX_SILA_UCIECZKI = 100;
    
    /** Maksymalny wiek ryby */
    private static final int MAX_WIEK = 100;
    
    /** Waga składowej głodu w obliczaniu siły ucieczki */
    private static final double WAGA_GLODU = 0.5;
    
    /** Waga składowej wieku w obliczaniu siły ucieczki */
    private static final double WAGA_WIEKU = 0.3;
    
    /** Waga składowej koloru w obliczaniu siły ucieczki */
    private static final double WAGA_KOLORU = 0.2;

    /**
     * Tworzy nową rybę o określonych parametrach początkowych.
     *
     * @param x początkowa pozycja X na planszy
     * @param y początkowa pozycja Y na planszy
     * @param kolor kolor ryby wpływający na jej zdolności obronne
     * @param glod początkowy poziom głodu
     * @param silaUcieczki początkowa siła ucieczki
     */
    public Ryba(int x, int y, String kolor, int glod, int silaUcieczki) {
        super(x, y);
        this.kolor = kolor;
        this.setGlod(glod);
        aktualizujSileUcieczki();
    }

    /**
     * Aktualizuje siłę ucieczki ryby na podstawie jej głodu, wieku i koloru.
     * Im większy głód i mniejszy wiek, tym większa siła ucieczki.
     * Kolor również wpływa na końcową wartość siły ucieczki.
     */
    private void aktualizujSileUcieczki() {
        double skladowaGlodu = (getGlod() / 20.0) * WAGA_GLODU * MAX_SILA_UCIECZKI;
        double skladowaWieku = ((MAX_WIEK - getWiek()) / (double) MAX_WIEK) * WAGA_WIEKU * MAX_SILA_UCIECZKI;
        
        double skladowaKoloru = 0;
        switch(getKolor()) {
            case "niebieski" -> skladowaKoloru = 1 * WAGA_KOLORU * MAX_SILA_UCIECZKI;
            case "zolty" -> skladowaKoloru = 0.8 * WAGA_KOLORU * MAX_SILA_UCIECZKI;
            case "czerwony" -> skladowaKoloru = 0.6 * WAGA_KOLORU * MAX_SILA_UCIECZKI;
            case "fiolet" -> skladowaKoloru = 0.4 * WAGA_KOLORU * MAX_SILA_UCIECZKI;
        }

        this.silaUcieczki = (int) Math.min(MAX_SILA_UCIECZKI, 
                                          Math.max(0, skladowaGlodu + skladowaWieku + skladowaKoloru));
    }

    /**
     * Realizuje jeden cykl życiowy ryby.
     * Ryba przemieszcza się i co 5 dni traci jeden punkt głodu.
     */
    @Override
    public void zyj() {
        przemieszczaj();
        if (getWiek() % 5 == 0) {
            setGlod(getGlod() - 1);
        }
        setWiek(getWiek() + 1);
        aktualizujSileUcieczki();
    }

    /**
     * Sprawdza czy ryba może się rozmnożyć.
     * @return true jeśli ryba się rozmnoży, false w przeciwnym razie
     */
    public boolean sprobujRozmnozycSie() {
        return Math.random() < SZANSA_ROZMNOZENIA;
    }

    /**
     * Tworzy nową rybę (potomstwo) o określonej pozycji.
     *
     * @param x pozycja X potomstwa
     * @param y pozycja Y potomstwa
     * @return nowa ryba z domyślnymi parametrami początkowymi
     */
    public Ryba stworzPotomstwo(int x, int y) {
        return new Ryba(x, y, this.kolor, 20, 50);
    }

    /**
     * Przemieszcza rybę w losowym kierunku o jedno pole.
     * Możliwe są ruchy w 8 kierunkach (góra, dół, lewo, prawo i po przekątnych).
     */
    @Override
    public void przemieszczaj() {
        int kierunek = (int)(Math.random() * 8);
        switch (kierunek) {
            case 0 -> setY(getY() - 1); // góra
            case 1 -> { setY(getY() - 1); setX(getX() + 1); } // góra-prawo
            case 2 -> setX(getX() + 1); // prawo
            case 3 -> { setY(getY() + 1); setX(getX() + 1); } // dół-prawo
            case 4 -> setY(getY() + 1); // dół
            case 5 -> { setY(getY() + 1); setX(getX() - 1); } // dół-lewo
            case 6 -> setX(getX() - 1); // lewo
            case 7 -> { setY(getY() - 1); setX(getX() - 1); } // góra-lewo
        }
    }

    /**
     * Zwiększa poziom głodu ryby po zjedzeniu planktonu.
     * Maksymalny poziom głodu wynosi 20.
     */
    public void zjedzPlankton() {
        setGlod(Math.min(20, getGlod() + 5));
    }

    /**
     * Sprawdza czy ryba jest martwa (gdy poziom głodu spadnie do 0).
     *
     * @return true jeśli ryba jest martwa, false w przeciwnym razie
     */
    public boolean czyMartwa() {
        return getGlod() <= 0;
    }

    /**
     * @return aktualna siła ucieczki ryby
     */
    public int getSilaUcieczki() { 
        return silaUcieczki; 
    }

    /**
     * @param silaUcieczki nowa siła ucieczki ryby
     */
    public void setSilaUcieczki(int silaUcieczki) { 
        this.silaUcieczki = silaUcieczki; 
    }

    /**
     * @return aktualny kolor ryby
     */
    public String getKolor() { 
        return kolor; 
    }

    /**
     * @param kolor nowy kolor ryby
     */
    public void setKolor(String kolor) { 
        this.kolor = kolor; 
    }
}