package com.example.fiszingsymulatjon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SchronienieTest {
    
    @Test
    void testJaskinia() {
        Jaskinia jaskinia = new Jaskinia(5, 5, 10, "granit");
        
        assertEquals(5, jaskinia.getX());
        assertEquals(5, jaskinia.getY());
        assertEquals(10, jaskinia.getPojemnosc());
    }

    @Test
    void testTrawa() {
        Trawa trawa = new Trawa(3, 3, 5, 2);
        
        assertEquals(3, trawa.getX());
        assertEquals(3, trawa.getY());
        assertEquals(5, trawa.getPojemnosc());
        assertEquals(2, trawa.getGestosc());
    }
}