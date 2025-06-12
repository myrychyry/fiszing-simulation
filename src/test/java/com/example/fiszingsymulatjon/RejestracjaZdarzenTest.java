package com.example.fiszingsymulatjon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.channels.FileChannel;

class RejestracjaZdarzenTest {
    
    @TempDir
    Path tempDir;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = tempDir.resolve("dane_symulacji.txt");
        // Ustawiamy ścieżkę pliku w klasie RejestracjaZdarzen
        RejestracjaZdarzen.ustawPlik(tempFile.toString());
        RejestracjaZdarzen.inicjalizujPlik();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testInicjalizacjaPlikuIZapis() throws IOException {
        assertTrue(Files.exists(tempFile), "Plik powinien istnieć");
        assertEquals("", Files.readString(tempFile), "Plik powinien być pusty po inicjalizacji");

        String testowaNazwa = "Test";
        RejestracjaZdarzen.zapiszZdarzenie(1, testowaNazwa);
        String zawartosc = Files.readString(tempFile);
        assertTrue(zawartosc.contains("Dzień 1: " + testowaNazwa), 
                "Plik powinien zawierać zapisane zdarzenie");
    }

    @Test
    void testWielokrotnyZapis() throws IOException {
        String test1 = "Test1";
        String test2 = "Test2";
        
        RejestracjaZdarzen.zapiszZdarzenie(1, test1);
        RejestracjaZdarzen.zapiszZdarzenie(2, test2);
        
        String zawartosc = Files.readString(tempFile);
        assertTrue(zawartosc.contains("Dzień 1: " + test1), 
                "Plik powinien zawierać pierwsze zdarzenie");
        assertTrue(zawartosc.contains("Dzień 2: " + test2), 
                "Plik powinien zawierać drugie zdarzenie");
    }

    @Test
    void testObslugaBledu() throws IOException {
        FileChannel channel = FileChannel.open(tempFile, StandardOpenOption.WRITE);
        try (channel) {
            RejestracjaZdarzen.zapiszZdarzenie(1, "Test");
            assertTrue(true, "Metoda powinna obsłużyć błąd bez rzucania wyjątku");
        }
    }
}