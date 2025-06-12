package com.example.fiszingsymulatjon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class RybaTest {
    private Ryba ryba;
    private static final int TESTOWE_X = 5;
    private static final int TESTOWE_Y = 5;
    private static final String TESTOWY_KOLOR = "niebieski";
    private static final int TESTOWY_GLOD = 15;
    private static final int TESTOWA_SILA = 50;

    @BeforeEach
    void setUp() {
        ryba = new Ryba(TESTOWE_X, TESTOWE_Y, TESTOWY_KOLOR, TESTOWY_GLOD, TESTOWA_SILA);
    }

    @Test
    void testKonstruktora() {
        assertEquals(TESTOWE_X, ryba.getX(), "Pozycja X powinna być równa wartości początkowej");
        assertEquals(TESTOWE_Y, ryba.getY(), "Pozycja Y powinna być równa wartości początkowej");
        assertEquals(TESTOWY_KOLOR, ryba.getKolor(), "Kolor powinien być równy wartości początkowej");
        assertEquals(TESTOWY_GLOD, ryba.getGlod(), "Głód powinien być równy wartości początkowej");
    }

    @Test
    void testZjedzPlankton() {
        int poczatkowyGlod = ryba.getGlod();
        ryba.zjedzPlankton();
        assertEquals(Math.min(20, poczatkowyGlod + 5), ryba.getGlod(), 
                "Głód powinien wzrosnąć o 5, ale nie przekroczyć 20");
    }

    @Test
    void testCzyMartwa() {
        ryba.setGlod(0);
        assertTrue(ryba.czyMartwa(), "Ryba z głodem 0 powinna być martwa");
        
        ryba.setGlod(1);
        assertFalse(ryba.czyMartwa(), "Ryba z głodem większym niż 0 powinna być żywa");
    }

    @Test
    void testPrzemieszczaj() {
        int poczatkoweX = ryba.getX();
        int poczatkoweY = ryba.getY();
        
        ryba.przemieszczaj();
        
        // Sprawdzamy czy ryba przemieściła się maksymalnie o 1 pole w każdym kierunku
        assertTrue(Math.abs(ryba.getX() - poczatkoweX) <= 1 && 
                  Math.abs(ryba.getY() - poczatkoweY) <= 1,
                "Ryba powinna przemieścić się maksymalnie o 1 pole w każdym kierunku");
    }

    @Test
    void testZyj() {
        int poczatkowyWiek = ryba.getWiek();
        int poczatkowyGlod = ryba.getGlod();
        
        // Symulujemy 5 dni życia
        for(int i = 0; i < 5; i++) {
            ryba.zyj();
        }
        
        assertEquals(poczatkowyWiek + 5, ryba.getWiek(), 
                "Wiek powinien zwiększyć się o 5");
        assertEquals(poczatkowyGlod - 1, ryba.getGlod(), 
                "Głód powinien zmniejszyć się o 1 po 5 dniach");
    }

    @Test
    void testStworzPotomstwo() {
        Ryba potomstwo = ryba.stworzPotomstwo(10, 10);
        
        assertEquals(10, potomstwo.getX(), "Pozycja X potomstwa powinna być zgodna z parametrem");
        assertEquals(10, potomstwo.getY(), "Pozycja Y potomstwa powinna być zgodna z parametrem");
        assertEquals(ryba.getKolor(), potomstwo.getKolor(), "Kolor potomstwa powinien być taki sam jak rodzica");
        assertEquals(20, potomstwo.getGlod(), "Początkowy głód potomstwa powinien wynosić 20");
    }

    @Test
    void testSprobujRozmnozycSie() {
        boolean czyRozmnozylaSie = false;
        // Próbujemy 1000 razy, aby zwiększyć szansę na rozmnożenie
        for(int i = 0; i < 1000; i++) {
            if(ryba.sprobujRozmnozycSie()) {
                czyRozmnozylaSie = true;
                break;
            }
        }
        assertTrue(czyRozmnozylaSie, "Ryba powinna mieć szansę na rozmnożenie się w ciągu 1000 prób");
    }

    @Test
    void testSilaUcieczki() {
        // Testujemy czy siła ucieczki jest zawsze w prawidłowym zakresie
        assertTrue(ryba.getSilaUcieczki() >= 0 && ryba.getSilaUcieczki() <= 100,
                "Siła ucieczki powinna być w zakresie 0-100");
        
        // Testujemy wpływ wieku na siłę ucieczki
        int silaPrzed = ryba.getSilaUcieczki();
        ryba.setWiek(50);
        ryba.zyj(); // Wywołujemy zyj() aby zaktualizować siłę ucieczki
        assertTrue(ryba.getSilaUcieczki() < silaPrzed,
                "Siła ucieczki powinna zmaleć wraz z wiekiem");
    }
}