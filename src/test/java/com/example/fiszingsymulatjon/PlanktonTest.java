package com.example.fiszingsymulatjon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class PlanktonTest {
    private Plankton plankton;
    private static final int POCZATKOWE_X = 5;
    private static final int POCZATKOWE_Y = 5;

    @BeforeEach
    void setUp() {
        plankton = new Plankton(POCZATKOWE_X, POCZATKOWE_Y);
    }

    @Test
    void testKonstruktora() {
        assertEquals(POCZATKOWE_X, plankton.getX(), "Pozycja X powinna być równa wartości początkowej");
        assertEquals(POCZATKOWE_Y, plankton.getY(), "Pozycja Y powinna być równa wartości początkowej");
    }

    @Test
    void testSettery() {
        int noweX = 10;
        int noweY = 15;
        
        plankton.setX(noweX);
        plankton.setY(noweY);
        
        assertEquals(noweX, plankton.getX(), "Nowa wartość X powinna zostać ustawiona");
        assertEquals(noweY, plankton.getY(), "Nowa wartość Y powinna zostać ustawiona");
    }

    @Test
    void testPrad() {
        // Wywołujemy metodę prad() wiele razy, aby zwiększyć szansę na ruch
        boolean czyNastapilRuch = false;
        int poczatkoweX = plankton.getX();
        int poczatkoweY = plankton.getY();
        
        for(int i = 0; i < 1000; i++) {
            plankton.prad();
            if(plankton.getX() != poczatkoweX || plankton.getY() != poczatkoweY) {
                czyNastapilRuch = true;
                break;
            }
        }
        
        assertTrue(czyNastapilRuch, "Plankton powinien się poruszyć przynajmniej raz na 1000 prób");
    }
}