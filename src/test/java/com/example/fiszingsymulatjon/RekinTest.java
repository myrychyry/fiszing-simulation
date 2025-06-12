package com.example.fiszingsymulatjon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class RekinTest {
    private Rekin rekin;
    private static final int TESTOWE_X = 5;
    private static final int TESTOWE_Y = 5;
    private static final int TESTOWY_GLOD = 50;
    private static final int TESTOWA_ILOSC_ZEBOW = 10;
    private static final boolean TESTOWE_CZY_POLUJE = false;

    @BeforeEach
    void setUp() {
        rekin = new Rekin(TESTOWE_X, TESTOWE_Y, TESTOWY_GLOD, TESTOWA_ILOSC_ZEBOW, TESTOWE_CZY_POLUJE);
    }

    @Test
    void testKonstruktora() {
        assertEquals(TESTOWE_X, rekin.getX(), "Pozycja X powinna być równa wartości początkowej");
        assertEquals(TESTOWE_Y, rekin.getY(), "Pozycja Y powinna być równa wartości początkowej");
        assertEquals(TESTOWY_GLOD, rekin.getGlod(), "Głód powinien być równy wartości początkowej");
        assertEquals(TESTOWA_ILOSC_ZEBOW, rekin.getIloscZebow(), "Ilość zębów powinna być równa wartości początkowej");
    }

    @Test
    void testZjedzRybe() {
        int poczatkowyGlod = rekin.getGlod();
        int poczatkowaIloscZebow = rekin.getIloscZebow();
        
        rekin.zjedzRybe();
        
        assertEquals(poczatkowyGlod + 20, rekin.getGlod(), 
                "Głód powinien wzrosnąć o 20 po zjedzeniu ryby");
        assertTrue(rekin.getIloscZebow() <= poczatkowaIloscZebow, 
                "Ilość zębów powinna pozostać taka sama lub się zmniejszyć");
    }

    @Test
    void testCzyMartwy() {
        rekin.setGlod(0);
        assertTrue(rekin.czyMartwy(), "Rekin z głodem 0 powinien być martwy");
        
        rekin.setGlod(1);
        assertFalse(rekin.czyMartwy(), "Rekin z głodem większym niż 0 powinien być żywy");
    }

    @Test
    void testPrzemieszczaj() {
        int poczatkoweX = rekin.getX();
        int poczatkoweY = rekin.getY();
        
        rekin.przemieszczaj();
        
        assertTrue(Math.abs(rekin.getX() - poczatkoweX) <= 1 && 
                  Math.abs(rekin.getY() - poczatkoweY) <= 1,
                "Rekin powinien przemieścić się maksymalnie o 1 pole w każdym kierunku");
    }

    @Test
    void testZyj() {
        rekin.setGlod(50);
        int poczatkowyGlod = rekin.getGlod();
        
        // Symulujemy 5 dni życia
        for(int i = 0; i < 5; i++) {
            rekin.zyj();
        }
        
        assertEquals(poczatkowyGlod - 2, rekin.getGlod(), 
                "Głód powinien zmniejszyć się o 2 po 5 dniach");
    }

    @Test
    void testZyjGdyMartwy() {
        rekin.setGlod(0);
        int poczatkoweX = rekin.getX();
        int poczatkoweY = rekin.getY();
        
        rekin.zyj();
        
        assertEquals(poczatkoweX, rekin.getX(), 
                "Martwy rekin nie powinien się przemieszczać");
        assertEquals(poczatkoweY, rekin.getY(), 
                "Martwy rekin nie powinien się przemieszczać");
    }

    @Test
    void testSetGlod() {
        // Test maksymalnej wartości głodu (100)
        rekin.setGlod(150);
        assertEquals(100, rekin.getGlod(), 
                "Głód nie powinien przekraczać 100");
        
        // Test minimalnej wartości głodu (0)
        rekin.setGlod(-10);
        assertEquals(0, rekin.getGlod(), 
                "Głód nie powinien być mniejszy niż 0");
        
        // Test normalnej wartości
        rekin.setGlod(50);
        assertEquals(50, rekin.getGlod(), 
                "Głód powinien być ustawiony na podaną wartość");
    }

    @Test
    void testAktualizacjaStatusuPolowania() {
        // Test gdy głód jest poniżej progu polowania (81)
        rekin.setGlod(70);
        assertTrue(rekin.isCzyPoluje(), 
                "Rekin powinien polować gdy głód jest poniżej 81");
        
        // Test gdy głód jest powyżej progu polowania
        rekin.setGlod(90);
        assertFalse(rekin.isCzyPoluje(), 
                "Rekin nie powinien polować gdy głód jest powyżej 81");
    }

    @Test
    void testUtrataZebow() {
        int liczbaProb = 1000;
        int liczbaUtraconych = 0;
        
        for(int i = 0; i < liczbaProb; i++) {
            Rekin testRekin = new Rekin(0, 0, 50, 10, false);
            int poczatkowaIloscZebow = testRekin.getIloscZebow();
            testRekin.zjedzRybe();
            if(testRekin.getIloscZebow() < poczatkowaIloscZebow) {
                liczbaUtraconych++;
            }
        }
        
        // Sprawdzamy czy procent utraty zębów jest zbliżony do 40% (z pewnym marginesem błędu)
        double procentUtraconych = (double) liczbaUtraconych / liczbaProb;
        assertTrue(procentUtraconych > 0.35 && procentUtraconych < 0.45,
                "Procent utraconych zębów powinien być zbliżony do 40%");
    }
}