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
    // Dodawanie nowego planktonu z 1% szansą na pustych polach
    for (int x = 0; x < szerokosc; x++) {
        for (int y = 0; y < wysokosc; y++) {
            if (siatkaPlanktonow[x][y] == null && Math.random() < 0.001) {
                siatkaPlanktonow[x][y] = new Plankton(x, y);
            }
        }
    }

    // Tworzymy tymczasową tablicę na nowe pozycje planktonu
    Plankton[][] nowePozyejePlanktonu = new Plankton[szerokosc][wysokosc];
    
    // Najpierw aktualizujemy pozycje planktonu
    for (int x = 0; x < szerokosc; x++) {
        for (int y = 0; y < wysokosc; y++) {
            if (siatkaPlanktonow[x][y] != null) {
                Plankton plankton = siatkaPlanktonow[x][y];
                int staraX = plankton.getX();
                int staraY = plankton.getY();
                
                // Wykonujemy ruch planktonu
                plankton.prad();
                
                // Sprawdzamy granice i korygujemy pozycję
                int nowaX = Math.min(Math.max(plankton.getX(), 0), szerokosc - 1);
                int nowaY = Math.min(Math.max(plankton.getY(), 0), wysokosc - 1);
                plankton.setX(nowaX);
                plankton.setY(nowaY);
                
                // Jeśli nowa pozycja jest już zajęta przez inny plankton, zostajemy na starej pozycji
                if (nowePozyejePlanktonu[nowaX][nowaY] != null) {
                    plankton.setX(staraX);
                    plankton.setY(staraY);
                    nowePozyejePlanktonu[staraX][staraY] = plankton;
                } else {
                    nowePozyejePlanktonu[nowaX][nowaY] = plankton;
                }
            }
        }
    }
        
        // Aktualizujemy główną siatkę planktonu
        for (int x = 0; x < szerokosc; x++) {
            for (int y = 0; y < wysokosc; y++) {
                siatkaPlanktonow[x][y] = nowePozyejePlanktonu[x][y];
            }
        }
        
        // Reszta kodu aktualizacji (dla organizmów)
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
                    
                    // Sprawdzamy czy ryba może zjeść plankton
                    if (organizm instanceof Ryba) {
                        if (siatkaPlanktonow[nowaX][nowaY] != null) {
                            // Ryba zjada plankton
                            Ryba ryba = (Ryba) organizm;
                            ryba.zjedzPlankton(); // Używamy nowej metody
                            siatkaPlanktonow[nowaX][nowaY] = null; // Usuwamy plankton

                            // Sprawdzamy czy ryba się rozmnoży
                            if (ryba.sprobujRozmnozycSie()) {
                                // Szukamy wolnego miejsca wokół ryby na potomstwo
                                int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
                                int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
                                
                                for (int i = 0; i < 8; i++) {
                                    int noweX = nowaX + dx[i];
                                    int noweY = nowaY + dy[i];
                                    
                                    // Sprawdzamy czy pozycja jest w granicach planszy
                                    if (noweX >= 0 && noweX < szerokosc && 
                                        noweY >= 0 && noweY < wysokosc && 
                                        siatka[noweX][noweY] == null) {
                                        // Tworzymy nową rybę na wolnym miejscu
                                        Ryba potomstwo = ryba.stworzPotomstwo(noweX, noweY);
                                        siatka[noweX][noweY] = potomstwo;
                                        break;
                                    }
                                }
                            }
                        }
                        
                        // Sprawdzamy czy ryba nie umarła z głodu
                        if (((Ryba) organizm).czyMartwa()) {
                            continue; // Pomijamy dodawanie martwej ryby do nowej pozycji
                        }
                    }
                    
                    // Sprawdzamy czy na nowej pozycji jest ryba (jeśli organizm to rekin)
                    if (organizm instanceof Rekin && ((Rekin) organizm).isCzyPoluje()) {
                        if (siatka[nowaX][nowaY] instanceof Ryba) {
                            boolean czyMoznaZjesc = true;
                            
                            // Sprawdzamy czy ryba jest w jaskini
                            if (siatkaSchronien[nowaX][nowaY] instanceof Jaskinia) {
                                czyMoznaZjesc = false;
                            } 
                            // Sprawdzamy czy ryba jest w trawie
                            else if (siatkaSchronien[nowaX][nowaY] instanceof Trawa) {
                                czyMoznaZjesc = Math.random() < 0.33;
                            }
                            
                            if (czyMoznaZjesc) {
                                ((Rekin) organizm).zjedzRybe();
                                siatka[nowaX][nowaY] = null;
                                siatka[nowaX][nowaY] = organizm;
                                continue;
                            } else {
                                organizm.setX(staraX);
                                organizm.setY(staraY);
                                nowaX = staraX;
                                nowaY = staraY;
                            }
                        }
                    }
                    // Sprawdzamy czy rekin nie umarł z głodu
                    if (organizm instanceof Rekin) {
                        if (((Rekin) organizm).czyMartwy()) {
                            continue; // Pomijamy dodawanie martwego rekina do nowej pozycji
                        }
                    }
                    
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