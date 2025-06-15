package com.example.fiszingsymulatjon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Główna klasa aplikacji symulującej ekosystem oceaniczny.
 * Zarządza interfejsem graficznym i logiką symulacji, umożliwiając obserwację
 * interakcji między różnymi organizmami morskimi.
 *
 * @author Mateusz Gawronkiewicz/Michał Charlikowski
 * @version 1.0
 */
public class SymulacjaOceanu extends Application {
    /** Główne okno aplikacji */
    private Stage oknoGlowne;
    
    /** Aktualny dzień symulacji */
    private static int aktualnyDzien = 0;
    
    /** Flaga określająca czy symulacja jest aktualnie uruchomiona */
    private boolean czyDziala = false;
    
    /** Obiekt kontrolujący upływ czasu w symulacji */
    private Timeline czasomierz;
    
    /** Etykieta wyświetlająca aktualny dzień symulacji */
    private final Label etykietaDnia = new Label("Dzień: 0");
    
    /** Główna plansza symulacji */
    private Plansza plansza;
    
    /** Obszar rysowania symulacji */
    private Canvas plotno;
    
    /** Rozmiar pojedynczej komórki na planszy w pikselach */
    private static final int ROZMIAR_KOMORKI = 40;
    
    /** Rozmiar siatki symulacji (większy z wymiarów planszy) */
    private int rozmiarSiatki;

    /**
     * Inicjalizuje aplikację i wyświetla okno konfiguracji.
     *
     * @param stage Główne okno aplikacji
     */
    @Override
    public void start(Stage stage) {
        this.oknoGlowne = stage;
        pokazOknoKonfiguracji();
    }

    /**
     * Wyświetla okno konfiguracji symulacji umożliwiające
     * ustawienie parametrów początkowych.
     */
    private void pokazOknoKonfiguracji() {
        Stage oknoKonfiguracji = new Stage();
        oknoKonfiguracji.setTitle("Konfiguracja symulacji");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label szerokoscLabel = new Label("Szerokość planszy:");
        TextField szerokoscField = new TextField("20");

        Label wysokoscLabel = new Label("Wysokość planszy:");
        TextField wysokoscField = new TextField("20");

        Label rybLabel = new Label("Liczba ryb:");
        TextField rybField = new TextField("10");

        Label rekinowLabel = new Label("Liczba rekinów:");
        TextField rekinowField = new TextField("3");

        grid.add(szerokoscLabel, 0, 0);
        grid.add(szerokoscField, 1, 0);
        grid.add(wysokoscLabel, 0, 1);
        grid.add(wysokoscField, 1, 1);
        grid.add(rybLabel, 0, 2);
        grid.add(rybField, 1, 2);
        grid.add(rekinowLabel, 0, 3);
        grid.add(rekinowField, 1, 3);

        Button zatwierdzButton = new Button("Zatwierdź");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(zatwierdzButton);
        grid.add(hbBtn, 1, 4);

        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        grid.add(messageLabel, 1, 5);

        zatwierdzButton.setOnAction(e -> {
            try {
                int szerokosc = Integer.parseInt(szerokoscField.getText());
                int wysokosc = Integer.parseInt(wysokoscField.getText());
                int liczbaRyb = Integer.parseInt(rybField.getText());
                int liczbaRekinow = Integer.parseInt(rekinowField.getText());

                if (szerokosc <= 0 || wysokosc <= 0 || liczbaRyb < 0 || liczbaRekinow < 0) {
                    messageLabel.setText("Wartości muszą być dodatnie!");
                    return;
                }

                if (liczbaRyb + liczbaRekinow > szerokosc * wysokosc) {
                    messageLabel.setText("Za dużo organizmów na planszę!");
                    return;
                }

                oknoKonfiguracji.close();
                rozpocznijSymulacje(szerokosc, wysokosc, liczbaRyb, liczbaRekinow);

            } catch (NumberFormatException ex) {
                messageLabel.setText("Wprowadź poprawne liczby!");
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        oknoKonfiguracji.setScene(scene);
        oknoKonfiguracji.show();
    }

    /**
     * Rozpoczyna symulację z zadanymi parametrami początkowymi.
     *
     * @param szerokosc szerokość planszy
     * @param wysokosc wysokość planszy
     * @param liczbaRyb początkowa liczba ryb
     * @param liczbaRekinow początkowa liczba rekinów
     */
    private void rozpocznijSymulacje(int szerokosc, int wysokosc, int liczbaRyb, int liczbaRekinow) {
        RejestracjaZdarzen.inicjalizujPlik();

        this.rozmiarSiatki = Math.max(szerokosc, wysokosc);
        int canvasSize = rozmiarSiatki * ROZMIAR_KOMORKI;

        plansza = new Plansza(szerokosc, wysokosc);
        plotno = new Canvas(canvasSize, canvasSize);

        inicjalizujObiekty(liczbaRyb, liczbaRekinow);

        HBox controlMenu = stworzMenuKontroli(plotno.getGraphicsContext2D());
        VBox root = new VBox(10);
        root.getChildren().addAll(plotno, controlMenu);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        Scene simulationScene = new Scene(root);
        oknoGlowne.setTitle("Symulacja Oceanu - Dzień " + aktualnyDzien);
        oknoGlowne.setScene(simulationScene);
        oknoGlowne.show();

        czasomierz = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> nastepnyDzien(plotno.getGraphicsContext2D()))
        );
        czasomierz.setCycleCount(Timeline.INDEFINITE);

        rysujSiatke(plotno.getGraphicsContext2D());
        rysujObiekty(plotno.getGraphicsContext2D());
    }

    /**
     * Tworzy panel kontrolny symulacji zawierający przyciski i pola wprowadzania.
     *
     * @param gc kontekst graficzny do rysowania
     * @return panel kontrolny jako obiekt HBox
     */
    private HBox stworzMenuKontroli(GraphicsContext gc) {
        Button przyciskNastepnegoDnia = new Button("Kolejny dzień");
        TextField poleLiczbyDni = new TextField();
        Button przyciskPotwierdz = new Button("Przeskocz");
        Button przyciskStartStop = new Button("Start");

        przyciskNastepnegoDnia.setOnAction(e -> nastepnyDzien(gc));

        przyciskPotwierdz.setOnAction(e -> {
            try {
                int days = Integer.parseInt(poleLiczbyDni.getText());
                if (days > 0) {
                    for (int i = 0; i < days; i++) {
                        nastepnyDzien(gc);
                        if (plansza.czyBrakOrganizmow()) {
                            break;
                        }
                    }
                    poleLiczbyDni.clear();
                } else {
                    System.out.println("Liczba dni musi być większa od 0!");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Wprowadź poprawną liczbę dni!");
            }
        });

        przyciskStartStop.setOnAction(e -> {
            if (czyDziala) {
                czasomierz.stop();
                przyciskStartStop.setText("Start");
            } else {
                czasomierz.play();
                przyciskStartStop.setText("Stop");
            }
            czyDziala = !czyDziala;
        });

        poleLiczbyDni.setPromptText("Liczba dni");
        poleLiczbyDni.setPrefWidth(100);

        HBox menu = new HBox(10);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(etykietaDnia, przyciskNastepnegoDnia, poleLiczbyDni, przyciskPotwierdz, przyciskStartStop);
        return menu;
    }

    /**
     * Przechodzi do następnego dnia symulacji, aktualizując stan wszystkich organizmów
     * i odświeżając widok.
     *
     * @param gc kontekst graficzny do rysowania
     */
    private void nastepnyDzien(GraphicsContext gc) {
        aktualnyDzien++;
        aktualizujWyswietlanieDnia();
        plansza.aktualizuj();

        // Sprawdzanie czy pozostały jakieś organizmy
        if (plansza.czyBrakOrganizmow()) {
            RejestracjaZdarzen.zapiszZdarzenie(aktualnyDzien, "KONIEC SYMULACJI - wszystkie organizmy wymarły");
            System.out.println("Wszystkie organizmy wymarły!");

            if (czyDziala) {
                czasomierz.stop();
                czyDziala = false;
            }

            // Wyświetlenie komunikatu
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Koniec symulacji");
            alert.setHeaderText(null);
            alert.setContentText("Wszystkie organizmy wymarły w dniu " + aktualnyDzien + "!");
            alert.show();
        }

        rysujSiatke(gc);
        rysujObiekty(gc);
    }

    /**
     * Inicjalizuje obiekty na planszy, w tym ryby, rekiny, plankton i schronienia.
     *
     * @param liczbaRyb liczba ryb do utworzenia
     * @param liczbaRekinow liczba rekinów do utworzenia
     */
    private void inicjalizujObiekty(int liczbaRyb, int liczbaRekinow) {
        String[] kolory = {"niebieski", "zolty", "czerwony", "fiolet"};
        plansza.dodajObiekty(liczbaRyb, () -> {
            String losowyKolor = kolory[(int) (Math.random() * kolory.length)];
            return new Ryba(0, 0, losowyKolor, 20, 5440);
        });
        plansza.dodajObiekty(liczbaRekinow, () -> new Rekin(0, 0, 100, 50, false));

        for (int i = 0; i < plansza.getSzerokosc(); i++) {
            for (int j = 0; j < plansza.getWysokosc(); j++) {
                if (Math.random() < 0.15) {
                    plansza.getSiatkaPlanktonow()[i][j] = new Plankton(i, j);
                }
                if (Math.random() < 0.05) {
                    plansza.getSiatkaSchronien()[i][j] = new Jaskinia(i, j, 5, "granit");
                } else if (Math.random() < 0.1) {
                    plansza.getSiatkaSchronien()[i][j] = new Trawa(i, j, 3, 2);
                }
            }
        }
    }

    /**
     * Rysuje siatkę planszy z tłem.
     *
     * @param gc kontekst graficzny do rysowania
     */
    private void rysujSiatke(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());

        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(1);
        for (int i = 0; i <= rozmiarSiatki; i++) {
            gc.strokeLine(0, i * ROZMIAR_KOMORKI, plotno.getWidth(), i * ROZMIAR_KOMORKI);
            gc.strokeLine(i * ROZMIAR_KOMORKI, 0, i * ROZMIAR_KOMORKI, plotno.getHeight());
        }
    }

    /**
     * Rysuje wszystkie obiekty na planszy (schronienia, plankton, organizmy).
     *
     * @param gc kontekst graficzny do rysowania
     */
    private void rysujObiekty(GraphicsContext gc) {
        // Rysowanie schronień
        Schronienie[][] schronienia = plansza.getSiatkaSchronien();
        for (int x = 0; x < plansza.getSzerokosc(); x++) {
            for (int y = 0; y < plansza.getWysokosc(); y++) {
                if (schronienia[x][y] != null) {
                    if (schronienia[x][y] instanceof Jaskinia) {
                        gc.setFill(Color.SADDLEBROWN);
                        gc.fillRect(x * ROZMIAR_KOMORKI, y * ROZMIAR_KOMORKI, ROZMIAR_KOMORKI, ROZMIAR_KOMORKI);
                    } else if (schronienia[x][y] instanceof Trawa) {
                        gc.setFill(Color.GREEN);
                        gc.fillRect(x * ROZMIAR_KOMORKI, y * ROZMIAR_KOMORKI, ROZMIAR_KOMORKI, ROZMIAR_KOMORKI);
                    }
                }
            }
        }

        // Rysowanie planktonu
        Plankton[][] planktony = plansza.getSiatkaPlanktonow();
        for (int x = 0; x < plansza.getSzerokosc(); x++) {
            for (int y = 0; y < plansza.getWysokosc(); y++) {
                if (planktony[x][y] != null) {
                    gc.setFill(Color.LIGHTGREEN);
                    gc.fillOval(x * ROZMIAR_KOMORKI + ROZMIAR_KOMORKI / 3, y * ROZMIAR_KOMORKI + ROZMIAR_KOMORKI / 3,
                            ROZMIAR_KOMORKI / 3, ROZMIAR_KOMORKI / 3);
                }
            }
        }

        // Rysowanie organizmów
        Organizm[][] organizmy = plansza.getSiatka();
        for (int x = 0; x < plansza.getSzerokosc(); x++) {
            for (int y = 0; y < plansza.getWysokosc(); y++) {
                if (organizmy[x][y] != null) {
                    if (organizmy[x][y] instanceof Rekin) {
                        gc.setFill(Color.GRAY);

                        double startX = x * ROZMIAR_KOMORKI;
                        double startY = y * ROZMIAR_KOMORKI;

                        double[] xPoints = {startX + 2, startX + ROZMIAR_KOMORKI - 2, startX + ROZMIAR_KOMORKI / 2};

                        double[] yPoints = {startY + 2, startY + 2, startY + ROZMIAR_KOMORKI};

                        gc.fillPolygon(xPoints, yPoints, 3);

                        // Rysowanie paska głodu dla rekina
                        double poziomGlodu = organizmy[x][y].getGlod() / 100.0;
                        rysujPasekGlodu(gc, x, y, poziomGlodu);
                    } else if (organizmy[x][y] instanceof Ryba) {
                        Ryba ryba = (Ryba) organizmy[x][y];
                        switch (ryba.getKolor()) {
                            case "niebieski" -> gc.setFill(Color.BLUE);
                            case "zolty" -> gc.setFill(Color.GOLDENROD);
                            case "czerwony" -> gc.setFill(Color.RED);
                            case "fiolet" -> gc.setFill(Color.PURPLE);
                        }
                        gc.fillOval(x * ROZMIAR_KOMORKI + 5, y * ROZMIAR_KOMORKI + 5,
                                ROZMIAR_KOMORKI - 10, ROZMIAR_KOMORKI - 10);

                        // Rysowanie paska głodu dla ryby
                        double poziomGlodu = ryba.getGlod() / 20.0; // Ryba ma max 20 głodu
                        rysujPasekGlodu(gc, x, y, poziomGlodu);
                    }
                }
            }
        }
    }

    /**
     * Zwraca aktualny dzień symulacji.
     *
     * @return numer aktualnego dnia
     */
    public static int getAktualnyDzien() {
        return aktualnyDzien;
    }

    /**
     * Rysuje pasek reprezentujący poziom głodu organizmu.
     *
     * @param gc kontekst graficzny do rysowania
     * @param x pozycja X organizmu na planszy
     * @param y pozycja Y organizmu na planszy
     * @param poziomGlodu znormalizowany poziom głodu (0.0 - 1.0)
     */
    private void rysujPasekGlodu(GraphicsContext gc, int x, int y, double poziomGlodu) {
        int szerokoscPaska = (int) (ROZMIAR_KOMORKI * 0.7);
        int wysokoscPaska = 5;
        int margines = (ROZMIAR_KOMORKI - szerokoscPaska) / 2;

        // Pozycja paska
        int pasekX = x * ROZMIAR_KOMORKI + margines;
        int pasekY = y * ROZMIAR_KOMORKI + (int) (ROZMIAR_KOMORKI * 0.35);

        // Rysowanie tła paska głodu
        gc.setFill(Color.INDIANRED);
        gc.fillRect(pasekX, pasekY, szerokoscPaska, wysokoscPaska);

        // Rysowanie aktualnego poziomu głodu
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(pasekX, pasekY, szerokoscPaska * poziomGlodu, wysokoscPaska);
    }

    /**
     * Aktualizuje wyświetlane informacje o aktualnym dniu symulacji.
     */
    private void aktualizujWyswietlanieDnia() {
        etykietaDnia.setText("Dzień: " + aktualnyDzien);
        oknoGlowne.setTitle("Symulacja Oceanu - Dzień " + aktualnyDzien);
    }

    /**
     * Punkt wejścia do aplikacji.
     *
     * @param args argumenty wiersza poleceń (nieużywane)
     */
    public static void main(String[] args) {
        launch();
    }
}