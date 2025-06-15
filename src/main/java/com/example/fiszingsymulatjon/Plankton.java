package com.example.fiszingsymulatjon;

/**
 * Klasa reprezentująca plankton w symulacji oceanu.
 * Plankton stanowi podstawę łańcucha pokarmowego i może być zjadany przez ryby.
 * Porusza się w sposób pasywny, zgodnie z prądami wodnymi.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class Plankton {
    /** Pozycja X planktonu na planszy */
    private int x;
    
    /** Pozycja Y planktonu na planszy */
    private int y;

    /**
     * Tworzy nowy plankton o określonej pozycji początkowej.
     *
     * @param x początkowa pozycja X na planszy
     * @param y początkowa pozycja Y na planszy
     */
    public Plankton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Symuluje ruch planktonu pod wpływem prądu morskiego.
     * Plankton ma 3% szansy na przemieszczenie się w losowym kierunku
     * (góra, dół, lewo lub prawo) w każdym kroku symulacji.
     */
    public void prad() {
        if (Math.random() < 0.03) {
            // Losowy kierunek ruchu
            int kierunek = (int)(Math.random() * 4);
            switch (kierunek) {
                case 0 -> y--; // góra
                case 1 -> y++; // dół
                case 2 -> x--; // lewo
                case 3 -> x++; // prawo
            }
        }
    }

    /**
     * Zwraca aktualną pozycję X planktonu.
     *
     * @return pozycja X na planszy
     */
    public int getX() { return x; }

    /**
     * Ustawia nową pozycję X planktonu.
     *
     * @param x nowa pozycja X na planszy
     */
    public void setX(int x) { this.x = x; }

    /**
     * Zwraca aktualną pozycję Y planktonu.
     *
     * @return pozycja Y na planszy
     */
    public int getY() { return y; }

    /**
     * Ustawia nową pozycję Y planktonu.
     *
     * @param y nowa pozycja Y na planszy
     */
    public void setY(int y) { this.y = y; }
}