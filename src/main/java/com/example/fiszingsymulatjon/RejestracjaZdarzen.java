package com.example.fiszingsymulatjon;

import java.io.*;
import java.nio.file.*;

public class RejestracjaZdarzen {
    private static String nazwaPliku = "dane_symulacji.txt";

    // Dodajemy metodę do zmiany ścieżki pliku (dla testów)
    public static void ustawPlik(String nazwa) {
        nazwaPliku = nazwa;
    }

    public static void inicjalizujPlik() {
        try {
            Path sciezka = Path.of(nazwaPliku);
            if (!Files.exists(sciezka)) {
                Files.createFile(sciezka);
            }
            Files.writeString(sciezka, "");
        } catch (IOException e) {
            System.err.println("Błąd podczas tworzenia pliku: " + e.getMessage());
        }
    }

    public static void zapiszZdarzenie(int dzien, String zdarzenie) {
        try {
            Path sciezka = Path.of(nazwaPliku);
            if (!Files.exists(sciezka)) {
                Files.createFile(sciezka);
            }
            Files.writeString(
                    sciezka,
                    String.format("Dzień %d: %s%n", dzien, zdarzenie),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania zdarzenia: " + e.getMessage());
        }
    }
}