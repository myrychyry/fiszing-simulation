package com.example.fiszingsymulatjon;

/**
 * Klasa reprezentująca trawę morską w symulacji oceanu.
 * Trawa morska stanowi rodzaj schronienia dla organizmów wodnych,
 * charakteryzuje się określoną gęstością, która wpływa na skuteczność
 * ukrywania się w niej organizmów.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class Trawa extends Schronienie {
    /** Gęstość trawy morskiej wpływająca na skuteczność ukrywania się w niej */
    private int gestosc;

    /**
     * Tworzy nową trawę morską o określonych parametrach.
     *
     * @param x pozycja X trawy na planszy
     * @param y pozycja Y trawy na planszy
     * @param pojemnosc maksymalna liczba organizmów, które mogą się ukryć w trawie
     * @param gestosc gęstość trawy wpływająca na skuteczność ukrywania się
     */
    public Trawa(int x, int y, int pojemnosc, int gestosc) {
        super(x, y, pojemnosc);
        this.gestosc = gestosc;
    }

    /**
     * Zwraca aktualną gęstość trawy morskiej.
     *
     * @return wartość gęstości trawy
     */
    public int getGestosc() { 
        return gestosc; 
    }

    /**
     * Ustawia nową wartość gęstości trawy morskiej.
     *
     * @param gestosc nowa wartość gęstości
     */
    public void setGestosc(int gestosc) { 
        this.gestosc = gestosc; 
    }
}