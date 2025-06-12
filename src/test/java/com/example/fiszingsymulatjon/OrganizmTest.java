package com.example.fiszingsymulatjon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrganizmTest {
    
    // Klasa pomocnicza do testowania klasy abstrakcyjnej
    private static class TestowyOrganizm extends Organizm {
        public TestowyOrganizm(int x, int y) {
            super(x, y);
        }

        @Override
        public void przemieszczaj() {
            // Implementacja testowa
            setX(getX() + 1);
        }

        @Override
        public void zyj() {
            // Implementacja testowa
            setWiek(getWiek() + 1);
        }
    }

    @Test
    void testKonstruktora() {
        Organizm organizm = new TestowyOrganizm(5, 10);
        assertEquals(5, organizm.getX());
        assertEquals(10, organizm.getY());
        assertEquals(0, organizm.getWiek());
    }

    @Test
    void testSettery() {
        Organizm organizm = new TestowyOrganizm(0, 0);
        
        organizm.setX(15);
        assertEquals(15, organizm.getX());
        
        organizm.setY(20);
        assertEquals(20, organizm.getY());
        
        organizm.setWiek(5);
        assertEquals(5, organizm.getWiek());
        
        organizm.setGlod(10);
        assertEquals(10, organizm.getGlod());
        
        organizm.setPopulacja(100);
        assertEquals(100, organizm.getPopulacja());
        
        organizm.setPlec("M");
        assertEquals("M", organizm.getPlec());
    }

    @Test
    void testPodstawowychMetod() {
        TestowyOrganizm organizm = new TestowyOrganizm(0, 0);
        
        organizm.przemieszczaj();
        assertEquals(1, organizm.getX(), "Metoda przemieszczaj() powinna zmienić pozycję X");
        
        organizm.zyj();
        assertEquals(1, organizm.getWiek(), "Metoda zyj() powinna zwiększyć wiek");
    }
}