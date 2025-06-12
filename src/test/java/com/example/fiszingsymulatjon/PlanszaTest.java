package com.example.fiszingsymulatjon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class PlanszaTest {
    private Plansza plansza;
    private static final int TESTOWA_SZEROKOSC = 10;
    private static final int TESTOWA_WYSOKOSC = 10;

    @BeforeEach
    void setUp() {
        plansza = new Plansza(TESTOWA_SZEROKOSC, TESTOWA_WYSOKOSC);
    }

    @Test
    void testKonstruktora() {
        assertEquals(TESTOWA_SZEROKOSC, plansza.getSzerokosc());
        assertEquals(TESTOWA_WYSOKOSC, plansza.getWysokosc());
        assertNotNull(plansza.getSiatka());
        assertNotNull(plansza.getSiatkaSchronien());
        assertNotNull(plansza.getSiatkaPlanktonow());
    }

    @Test
    void testDodajObiekty() {
        int liczbaRyb = 5;
        plansza.dodajObiekty(liczbaRyb, () -> new Ryba(0, 0, "niebieski", 20, 50));
        
        int liczbaZnalezionychRyb = 0;
        for (int i = 0; i < TESTOWA_SZEROKOSC; i++) {
            for (int j = 0; j < TESTOWA_WYSOKOSC; j++) {
                if (plansza.getSiatka()[i][j] instanceof Ryba) {
                    liczbaZnalezionychRyb++;
                }
            }
        }
        
        assertEquals(liczbaRyb, liczbaZnalezionychRyb);
    }

    @Test
    void testCzyBrakOrganizmow() {
        assertTrue(plansza.czyBrakOrganizmow(), "Nowa plansza powinna być pusta");
        
        plansza.dodajObiekty(1, () -> new Ryba(0, 0, "niebieski", 20, 50));
        assertFalse(plansza.czyBrakOrganizmow(), "Plansza z jednym organizmem nie powinna być pusta");
    }

    @Test
    void testAktualizuj() {
        // Dodajemy rybę z małym głodem
        Ryba ryba = new Ryba(5, 5, "niebieski", 1, 50);
        plansza.getSiatka()[5][5] = ryba;
        
        // Po aktualizacji ryba powinna umrzeć z głodu
        plansza.aktualizuj();
        assertTrue(ryba.czyMartwa(), "Ryba z głodem 1 powinna umrzeć po aktualizacji");
    }
}