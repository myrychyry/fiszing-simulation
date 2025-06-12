package com.example.fiszingsymulatjon;

import java.io.*;
import java.nio.file.*;

public class RejestracjaZdarzen {
    private static final String NAZWA_PLIKU = "dane_symulacji.txt";

    public static void inicjalizujPlik() {
        try {
            // Tworzymy nowy plik (lub nadpisujemy istniejący)
            Files.writeString(Path.of(NAZWA_PLIKU), "");
        } catch (IOException e) {
            System.err.println("Błąd podczas tworzenia pliku: " + e.getMessage());
        }
    }

    public static void zapiszZdarzenie(int dzien, String zdarzenie) {
        try {
            Files.writeString(
                    Path.of(NAZWA_PLIKU),
                    String.format("Dzień %d: %s%n", dzien, zdarzenie),
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania zdarzenia: " + e.getMessage());
        }
    }
}
