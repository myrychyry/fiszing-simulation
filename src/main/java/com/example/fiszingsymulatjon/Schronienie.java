package com.example.fiszingsymulatjon;

/**
 * Abstrakcyjna klasa bazowa reprezentująca schronienie w symulacji oceanu.
 * Schronienia zapewniają bezpieczne miejsce dla organizmów morskich,
 * gdzie mogą się ukryć przed drapieżnikami.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public abstract class Schronienie {
    /** Pozycja X schronienia na planszy */
    private int x;
    
    /** Pozycja Y schronienia na planszy */
    private int y;
    
    /** Maksymalna liczba organizmów, które mogą się ukryć w schronieniu */
    private int pojemnosc;

    /**
     * Tworzy nowe schronienie o określonych parametrach.
     *
     * @param x pozycja X schronienia na planszy
     * @param y pozycja Y schronienia na planszy
     * @param pojemnosc maksymalna liczba organizmów, które mogą się ukryć w schronieniu
     */
    public Schronienie(int x, int y, int pojemnosc) {
        this.x = x;
        this.y = y;
        this.pojemnosc = pojemnosc;
    }

    /**
     * @return aktualna pozycja X schronienia na planszy
     */
    public int getX() { return x; }

    /**
     * Ustawia nową pozycję X schronienia na planszy.
     * @param x nowa pozycja X
     */
    public void setX(int x) { this.x = x; }

    /**
     * @return aktualna pozycja Y schronienia na planszy
     */
    public int getY() { return y; }

    /**
     * Ustawia nową pozycję Y schronienia na planszy.
     * @param y nowa pozycja Y
     */
    public void setY(int y) { this.y = y; }

    /**
     * @return maksymalna liczba organizmów, które mogą się ukryć w schronieniu
     */
    public int getPojemnosc() { return pojemnosc; }

    /**
     * Ustawia nową maksymalną pojemność schronienia.
     * @param pojemnosc nowa maksymalna liczba organizmów
     */
    public void setPojemnosc(int pojemnosc) { this.pojemnosc = pojemnosc; }
}