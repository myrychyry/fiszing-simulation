package com.example.fiszingsymulatjon;

import java.io.*;
import java.nio.file.*;

/**
 * Klasa odpowiedzialna za rejestrację zdarzeń zachodzących podczas symulacji.
 * Zapisuje wszystkie istotne wydarzenia do pliku tekstowego.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class RejestracjaZdarzen {
    /** Nazwa pliku do zapisywania zdarzeń */
    private static String nazwaPliku = "dane_symulacji.txt";

    /**
     * Zmienia nazwę pliku do zapisywania zdarzeń.
     * Używane głównie do celów testowych.
     *
     * @param nazwa Nowa nazwa pliku
     */
    public static void ustawPlik(String nazwa) {
        nazwaPliku = nazwa;
    }

    /**
     * Inicjalizuje plik do zapisywania zdarzeń.
     * Jeśli plik nie istnieje, zostanie utworzony.
     * Jeśli istnieje, zostanie wyczyszczony.
     *
     * @throws IOException w przypadku problemów z dostępem do pliku
     */
    public static void inicjalizujPlik() {
        // implementacja...
    }

    /**
     * Zapisuje pojedyncze zdarzenie do pliku.
     *
     * @param dzien Dzień symulacji, w którym wystąpiło zdarzenie
     * @param zdarzenie Opis zdarzenia do zapisania
     * @throws IOException w przypadku problemów z zapisem do pliku
     */
    public static void zapiszZdarzenie(int dzien, String zdarzenie) {
        // implementacja...
    }
}