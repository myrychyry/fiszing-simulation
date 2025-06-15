package com.example.fiszingsymulatjon;

/**
 * Abstrakcyjna klasa bazowa reprezentująca organizm w symulacji oceanu.
 * Definiuje podstawowe właściwości i zachowania wspólne dla wszystkich organizmów,
 * takie jak położenie, wiek, poziom głodu, liczebność populacji i płeć.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public abstract class Organizm {
    /** Pozycja X organizmu na planszy */
    private int x;
    
    /** Pozycja Y organizmu na planszy */
    private int y;
    
    /** Wiek organizmu w dniach symulacji */
    private int wiek;
    
    /** Poziom głodu organizmu */
    private int glod;
    
    /** Aktualna liczebność populacji danego gatunku */
    private int populacja;
    
    /** Płeć organizmu */
    private String plec;

    /**
     * Tworzy nowy organizm o określonej pozycji początkowej.
     * Inicjalizuje wiek na 0.
     *
     * @param x początkowa pozycja X na planszy
     * @param y początkowa pozycja Y na planszy
     */
    public Organizm(int x, int y) {
        this.x = x;
        this.y = y;
        this.wiek = 0;
    }

    /**
     * Abstrakcyjna metoda definiująca sposób przemieszczania się organizmu.
     * Każda konkretna implementacja musi określić własny sposób ruchu.
     */
    public abstract void przemieszczaj();

    /**
     * Abstrakcyjna metoda definiująca cykl życiowy organizmu.
     * Każda konkretna implementacja musi określić własne zachowanie życiowe.
     */
    public abstract void zyj();

    /**
     * @return aktualna pozycja X organizmu na planszy
     */
    public int getX() { return x; }

    /**
     * Ustawia nową pozycję X organizmu na planszy.
     * @param x nowa pozycja X
     */
    public void setX(int x) { this.x = x; }

    /**
     * @return aktualna pozycja Y organizmu na planszy
     */
    public int getY() { return y; }

    /**
     * Ustawia nową pozycję Y organizmu na planszy.
     * @param y nowa pozycja Y
     */
    public void setY(int y) { this.y = y; }

    /**
     * @return aktualny wiek organizmu w dniach symulacji
     */
    public int getWiek() { return wiek; }

    /**
     * Ustawia wiek organizmu.
     * @param wiek nowy wiek w dniach symulacji
     */
    public void setWiek(int wiek) { this.wiek = wiek; }

    /**
     * @return aktualny poziom głodu organizmu
     */
    public int getGlod() { return glod; }

    /**
     * Ustawia poziom głodu organizmu.
     * @param glod nowy poziom głodu
     */
    public void setGlod(int glod) { this.glod = glod; }

    /**
     * @return aktualna liczebność populacji gatunku
     */
    public int getPopulacja() { return populacja; }

    /**
     * Ustawia liczebność populacji gatunku.
     * @param populacja nowa liczebność populacji
     */
    public void setPopulacja(int populacja) { this.populacja = populacja; }

    /**
     * @return płeć organizmu
     */
    public String getPlec() { return plec; }

    /**
     * Ustawia płeć organizmu.
     * @param plec nowa płeć organizmu
     */
    public void setPlec(String plec) { this.plec = plec; }
}