package com.example.fiszingsymulatjon;

public class Plansza {
    private final int szerokosc;
    private final int wysokosc;
    private final Organizm[][] siatka;
    private final Schronienie[][] siatkaSchronien;
    private final Plankton[][] siatkaPlanktonow;

    public Plansza(int szerokosc, int wysokosc) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.siatka = new Organizm[szerokosc][wysokosc];
        this.siatkaSchronien = new Schronienie[szerokosc][wysokosc];
        this.siatkaPlanktonow = new Plankton[szerokosc][wysokosc];
    }

    public void aktualizuj() {
        for (int x = 0; x < szerokosc; x++) {
            for (int y = 0; y < wysokosc; y++) {
                if (siatka[x][y] != null) {
                    Organizm organizm = siatka[x][y];
                    int staraX = organizm.getX();
                    int staraY = organizm.getY();
                    
                    // Usuwamy organizm z obecnej pozycji
                    siatka[x][y] = null;
                    
                    // Wykonujemy ruch
                    organizm.zyj();
                    
                    // Sprawdzamy granice i korygujemy pozycję
                    int nowaX = Math.min(Math.max(organizm.getX(), 0), szerokosc - 1);
                    int nowaY = Math.min(Math.max(organizm.getY(), 0), wysokosc - 1);
                    organizm.setX(nowaX);
                    organizm.setY(nowaY);
                    
                    // Jeśli nowa pozycja jest zajęta, wracamy na starą
                    if (siatka[nowaX][nowaY] != null) {
                        organizm.setX(staraX);
                        organizm.setY(staraY);
                        nowaX = staraX;
                        nowaY = staraY;
                    }
                    
                    // Umieszczamy organizm na nowej pozycji
                    siatka[nowaX][nowaY] = organizm;
                }
                
                if (siatkaPlanktonow[x][y] != null) {
                    siatkaPlanktonow[x][y].prad();
                }
            }
        }
    }

    public <T extends Organizm> void dodajObiekty(int liczba, java.util.function.Supplier<T> fabryka) {
        for (int i = 0; i < liczba; i++) {
            while (true) {
                int x = (int) (Math.random() * szerokosc);
                int y = (int) (Math.random() * wysokosc);
                if (siatka[x][y] == null) {
                    T organizm = fabryka.get();
                    organizm.setX(x);
                    organizm.setY(y);
                    siatka[x][y] = organizm;
                    break;
                }
            }
        }
    }

    // Gettery
    public int getSzerokosc() { return szerokosc; }
    public int getWysokosc() { return wysokosc; }
    public Organizm[][] getSiatka() { return siatka; }
    public Schronienie[][] getSiatkaSchronien() { return siatkaSchronien; }
    public Plankton[][] getSiatkaPlanktonow() { return siatkaPlanktonow; }
}