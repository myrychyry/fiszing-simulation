package com.example.fiszingsymulatjon;

/**
 * Klasa reprezentująca jaskinię w symulacji oceanu.
 * Jaskinia jest rodzajem schronienia, charakteryzującym się określonym rodzajem kamienia,
 * z którego jest zbudowana. Może służyć jako kryjówka dla organizmów morskich.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class Jaskinia extends Schronienie {
    /** Rodzaj kamienia, z którego zbudowana jest jaskinia */
    private String rodzajKamienia;

    /**
     * Tworzy nową jaskinię o określonych parametrach.
     *
     * @param x pozycja X jaskini na planszy
     * @param y pozycja Y jaskini na planszy
     * @param pojemnosc maksymalna liczba organizmów, które mogą się schronić w jaskini
     * @param rodzajKamienia rodzaj kamienia, z którego zbudowana jest jaskinia
     */
    public Jaskinia(int x, int y, int pojemnosc, String rodzajKamienia) {
        super(x, y, pojemnosc);
        this.rodzajKamienia = rodzajKamienia;
    }

    /**
     * Symuluje zawalenie się jaskini.
     * Ta metoda może wpływać na stan jaskini i organizmy w niej przebywające.
     */
    public void zawalenie() {
        // Implementacja
    }

    /**
     * Zwraca rodzaj kamienia, z którego zbudowana jest jaskinia.
     *
     * @return rodzaj kamienia jako String
     */
    public String getRodzajKamienia() { 
        return rodzajKamienia; 
    }

    /**
     * Ustawia nowy rodzaj kamienia dla jaskini.
     *
     * @param rodzajKamienia nowy rodzaj kamienia
     */
    public void setRodzajKamienia(String rodzajKamienia) { 
        this.rodzajKamienia = rodzajKamienia; 
    }
}