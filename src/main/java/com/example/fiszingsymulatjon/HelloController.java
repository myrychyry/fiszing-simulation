package com.example.fiszingsymulatjon;

/**
 * Kontroler inicjalizujący główną aplikację symulacji oceanu.
 * Służy jako punkt startowy do uruchomienia symulacji przy wykorzystaniu
 * interfejsu JavaFX.
 *
 * @see SymulacjaOceanu
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class HelloController {
    
    /**
     * Inicjalizuje i uruchamia główną aplikację symulacji.
     * Metoda wywoływana automatycznie przez framework JavaFX
     * podczas ładowania aplikacji.
     */
    public void initialize() {
        SymulacjaOceanu.main(new String[]{});
    }
}