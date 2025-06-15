package com.example.fiszingsymulatjon;

/**
 * Klasa reprezentująca planszę symulacji oceanu.
 * Zawiera informacje o rozmieszczeniu organizmów, schronień i planktonu.
 * Odpowiada za logikę interakcji między elementami symulacji.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class Plansza {
    /** Szerokość planszy */
    private final int szerokosc;
    
    /** Wysokość planszy */
    private final int wysokosc;
    
    /** Tablica przechowująca organizmy na planszy */
    private final Organizm[][] siatka;
    
    /** Tablica przechowująca schronienia na planszy */
    private final Schronienie[][] siatkaSchronien;
    
    /** Tablica przechowująca plankton na planszy */
    private final Plankton[][] siatkaPlanktonow;

    /**
     * Tworzy nową planszę o zadanych wymiarach.
     *
     * @param szerokosc szerokość planszy
     * @param wysokosc wysokość planszy
     */
    public Plansza(int szerokosc, int wysokosc) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.siatka = new Organizm[szerokosc][wysokosc];
        this.siatkaSchronien = new Schronienie[szerokosc][wysokosc];
        this.siatkaPlanktonow = new Plankton[szerokosc][wysokosc];
    }

    /**
     * Aktualizuje stan planszy w każdym kroku symulacji.
     * Obsługuje:
     * - generowanie nowego planktonu
     * - ruch planktonu
     * - ruch organizmów
     * - interakcje między organizmami (polowanie, zjadanie planktonu)
     * - rozmnażanie organizmów
     */
    public void aktualizuj() {
    // Dodawanie nowego planktonu z 1% szansą na pustych polach
    for (int x = 0; x < szerokosc; x++) {
        for (int y = 0; y < wysokosc; y++) {
            if (siatkaPlanktonow[x][y] == null && Math.random() < 0.001) {
                siatkaPlanktonow[x][y] = new Plankton(x, y);
                //RejestracjaZdarzen.zapiszZdarzenie(SymulacjaOceanu.getAktualnyDzien(), "powstał nowy plankton");

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
                            //RejestracjaZdarzen.zapiszZdarzenie(SymulacjaOceanu.getAktualnyDzien(), "ryba zjadła plankton");

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
                                        System.out.printf("Potomstwo powstało :o\n");
                                        RejestracjaZdarzen.zapiszZdarzenie(SymulacjaOceanu.getAktualnyDzien(), "ryba się rozmnożyła");
                                        break;
                                    }
                                }
                            }
                        }
                        
                        // Sprawdzamy czy ryba nie umarła z głodu
                        if (((Ryba) organizm).czyMartwa()) {
                            System.out.printf("Ryba umarła z głodu :(\n");
                            RejestracjaZdarzen.zapiszZdarzenie(SymulacjaOceanu.getAktualnyDzien(), "ryba umarła z głodu");
                            continue; // Pomijamy dodawanie martwej ryby do nowej pozycji
                        }
                    }
                    
                    // Sprawdzamy czy na nowej pozycji jest ryba (jeśli organizm to rekin)
                    if (organizm instanceof Rekin && ((Rekin) organizm).isCzyPoluje()) {
                        if (siatka[nowaX][nowaY] instanceof Ryba) {
                            boolean czyMoznaZjesc = true;
                            Ryba ryba = (Ryba) siatka[nowaX][nowaY];
                            Rekin rekin = (Rekin) organizm;
                            
                            // Obliczamy szansę na zjedzenie ryby
                            double wspolczynnik = (ryba.getSilaUcieczki() + rekin.getIloscZebow() * 2) / 2.0;
                            double szansaZjedzenia = wspolczynnik / 2.0;
                            
                            // Sprawdzamy czy ryba jest w jaskini
                            if (siatkaSchronien[nowaX][nowaY] instanceof Jaskinia) {
                                czyMoznaZjesc = false;
                            } 
                            // Sprawdzamy czy ryba jest w trawie
                            else if (siatkaSchronien[nowaX][nowaY] instanceof Trawa) {

                                szansaZjedzenia *= 0.33; // zmniejszamy szansę o 67% w trawie
                                czyMoznaZjesc = Math.random()*100 < szansaZjedzenia;
                                if(!czyMoznaZjesc){
                                    System.out.printf("Ryba unikneła smierci :)" + "\n");
                                    RejestracjaZdarzen.zapiszZdarzenie(SymulacjaOceanu.getAktualnyDzien(), "ryba uniknęła śmierci");
                                }
                            } else {

                                czyMoznaZjesc = Math.random()*100 < szansaZjedzenia;

                                if(!czyMoznaZjesc){
                                    System.out.printf("Ryba unikneła smierci :)"+"\n" );
                                    RejestracjaZdarzen.zapiszZdarzenie(SymulacjaOceanu.getAktualnyDzien(), "ryba uniknęła śmierci");
                                }
                            }
                            
                            if (czyMoznaZjesc) {
                                System.out.printf("Ryba została zjedzona :(" + "\n");
                                RejestracjaZdarzen.zapiszZdarzenie(SymulacjaOceanu.getAktualnyDzien(), "rekin zjadł rybę");
                                rekin.zjedzRybe();
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
                            System.out.printf("Rekin umarł z głodu :}\n");
                            RejestracjaZdarzen.zapiszZdarzenie(SymulacjaOceanu.getAktualnyDzien(), "rekin umarł z głodu");
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

    /**
     * Dodaje określoną liczbę obiektów danego typu na planszę.
     * Obiekty są umieszczane na losowych, pustych pozycjach.
     *
     * @param <T> typ organizmu rozszerzający klasę Organizm
     * @param liczba liczba obiektów do dodania
     * @param fabryka funkcja dostarczająca nowe instancje organizmu
     */
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

    /**
     * Sprawdza czy na planszy znajdują się jakiekolwiek organizmy.
     *
     * @return true jeśli na planszy nie ma żadnych organizmów, false w przeciwnym razie
     */
    public boolean czyBrakOrganizmow() {
    for (int x = 0; x < szerokosc; x++) {
        for (int y = 0; y < wysokosc; y++) {
            if (siatka[x][y] != null) {
                return false; // Znaleziono przynajmniej jeden organizm
            }
        }
    }
    return true; // Nie znaleziono żadnego organizmu
}
    /**
     * @return szerokość planszy
     */
    public int getSzerokosc() { 
        return szerokosc; 
    }

    /**
     * @return wysokość planszy
     */
    public int getWysokosc() { 
        return wysokosc; 
    }

    /**
     * @return dwuwymiarowa tablica organizmów na planszy
     */
    public Organizm[][] getSiatka() { 
        return siatka; 
    }

    /**
     * @return dwuwymiarowa tablica schronień na planszy
     */
    public Schronienie[][] getSiatkaSchronien() { 
        return siatkaSchronien; 
    }

    /**
     * @return dwuwymiarowa tablica planktonu na planszy
     */
    public Plankton[][] getSiatkaPlanktonow() { 
        return siatkaPlanktonow; 
    }
}