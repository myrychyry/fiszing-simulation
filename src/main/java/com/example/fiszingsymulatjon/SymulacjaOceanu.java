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

public class SymulacjaOceanu extends Application {
    private Stage oknoGlowne;
    private int aktualnyDzien = 0;
    private boolean czyDziala = false;
    private Timeline czasomierz;
    private final Label etykietaDnia = new Label("Dzień: 0");
    private Plansza plansza;
    private Canvas plotno;
    private static final int ROZMIAR_KOMORKI = 60;
    private int rozmiarSiatki;         //

    @Override
    public void start(Stage stage) {
        this.oknoGlowne = stage;
        pokazOknoKonfiguracji();
    }

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

    private void rozpocznijSymulacje(int szerokosc, int wysokosc, int liczbaRyb, int liczbaRekinow) {
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

    private void nastepnyDzien(GraphicsContext gc) {
        aktualnyDzien++;
        aktualizujWyswietlanieDnia();
        plansza.aktualizuj();
        rysujSiatke(gc);
        rysujObiekty(gc);
    }

    private void inicjalizujObiekty(int liczbaRyb, int liczbaRekinow) {
        plansza.dodajObiekty(liczbaRyb, () -> new Ryba(0, 0, "niebieski", 20, 50));
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
                        gc.fillOval(x * ROZMIAR_KOMORKI, y * ROZMIAR_KOMORKI, ROZMIAR_KOMORKI, ROZMIAR_KOMORKI);
                        
                        // Rysowanie paska głodu dla rekina
                        double poziomGlodu = organizmy[x][y].getGlod() / 100.0; // Rekin ma max 100 głodu
                        rysujPasekGlodu(gc, x, y, poziomGlodu);
                        
                    } else if (organizmy[x][y] instanceof Ryba) {
                        Ryba ryba = (Ryba) organizmy[x][y];
                        switch (ryba.getKolor()) {
                            case "niebieski" -> gc.setFill(Color.BLUE);
                            case "żółty" -> gc.setFill(Color.YELLOW);
                            case "czerwony" -> gc.setFill(Color.RED);
                            default -> gc.setFill(Color.PURPLE);
                        }
                        gc.fillOval(x * ROZMIAR_KOMORKI + 2, y * ROZMIAR_KOMORKI + 2,
                                ROZMIAR_KOMORKI - 4, ROZMIAR_KOMORKI - 4);
                        
                        // Rysowanie paska głodu dla ryby
                        double poziomGlodu = ryba.getGlod() / 20.0; // Ryba ma max 20 głodu
                        rysujPasekGlodu(gc, x, y, poziomGlodu);
                    }
                }
            }
        }
    }

    private void rysujPasekGlodu(GraphicsContext gc, int x, int y, double poziomGlodu) {
        int szerokoscPaska = (int)(ROZMIAR_KOMORKI * 0.7); // 80% szerokości komórki
        int wysokoscPaska = 5; // 4 piksele wysokości
        int margines = (ROZMIAR_KOMORKI - szerokoscPaska) / 2;
        
        // Pozycja paska (na górze organizmu)
        int pasekX = x * ROZMIAR_KOMORKI + margines;
        int pasekY = y * ROZMIAR_KOMORKI + (int)(ROZMIAR_KOMORKI * 0.35);
        
        // Rysowanie tła paska (szary)
        gc.setFill(Color.INDIANRED);
        gc.fillRect(pasekX, pasekY, szerokoscPaska, wysokoscPaska);
        
        // Rysowanie aktualnego poziomu głodu (czarny)
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(pasekX, pasekY, szerokoscPaska * poziomGlodu, wysokoscPaska);
    }

    private void aktualizujWyswietlanieDnia() {
        etykietaDnia.setText("Dzień: " + aktualnyDzien);
        oknoGlowne.setTitle("Symulacja Oceanu - Dzień " + aktualnyDzien);
    }

    public static void main(String[] args) {
        launch();
    }
}