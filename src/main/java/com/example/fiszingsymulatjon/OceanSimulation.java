package com.example.fiszingsymulatjon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class OceanSimulation extends Application {
    private static final int GRID_SIZE = 30; // Rozmiar siatki 30x30
    private static final int CELL_SIZE = 20; // Rozmiar pojedynczego pola: 20x20 pikseli
    private static final int CANVAS_SIZE = GRID_SIZE * CELL_SIZE; // Rozmiar planszy: 600x600

    private boolean isRunning = false; // Flaga: czy symulacja działa
    private Timeline timeline;        // Przesuwanie automatyczne co 1 sekunda
    private int currentDay = 0;       // Liczba dni w symulacji

    @Override
    public void start(Stage stage) {
        // Tworzymy Canvas jako "planszę"
        Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Tworzymy menu sterowania
        HBox controlMenu = createControlMenu(gc);

        // Layout główny
        VBox root = new VBox();
        root.getChildren().addAll(canvas, controlMenu);

        // Inicjalizacja sceny
        Scene scene = new Scene(root, CANVAS_SIZE, CANVAS_SIZE + 100); // +100 dla miejsca na menu
        stage.setTitle("Symulacja Oceanu - Sterowanie");
        stage.setScene(scene);
        stage.show();

        // Tworzymy animację (automatyczne przesuwanie symulacji co 1 sekundę)
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> nextDay(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE); // Powtarzaj bez końca

        // Rysujemy początkowy stan siatki
        renderGrid(gc);
        renderObjects(gc);
    }

    // Tworzymy menu sterowania
    private HBox createControlMenu(GraphicsContext gc) {
        // Przyciski i elementy sterowania
        Button nextDayButton = new Button("Kolejny dzień");
        TextField daysInput = new TextField();
        daysInput.setPromptText("Liczba dni");
        Button confirmButton = new Button("Zatwierdź");
        Button startStopButton = new Button("Start");

        // Akcja: kolejny dzień
        nextDayButton.setOnAction(e -> nextDay(gc));

        // Akcja: przesunięcie symulacji o określoną liczbę dni
        confirmButton.setOnAction(e -> {
            try {
                int days = Integer.parseInt(daysInput.getText());
                for (int i = 0; i < days; i++) {
                    nextDay(gc);
                }
                daysInput.clear(); // Czyścimy pole tekstowe
            } catch (NumberFormatException ex) {
                System.out.println("Nieprawidłowa liczba dni.");
            }
        });

        // Akcja: start/stop automatycznego odświeżania
        startStopButton.setOnAction(e -> {
            if (isRunning) {
                timeline.stop();
                startStopButton.setText("Start");
            } else {
                timeline.play();
                startStopButton.setText("Stop");
            }
            isRunning = !isRunning;
        });

        // Układ elementów w menu
        HBox menu = new HBox(10, nextDayButton, daysInput, confirmButton, startStopButton);
        menu.setStyle("-fx-padding: 10; -fx-background-color: #d3d3d3; -fx-alignment: center;");
        return menu;
    }

    // Przesunięcie symulacji o jeden dzień
    private void nextDay(GraphicsContext gc) {
        currentDay++;
        System.out.println("Dzień: " + currentDay);
        renderGrid(gc);
        renderObjects(gc);
    }

    // Rysowanie siatki
    private void renderGrid(GraphicsContext gc) {
        // Tło planszy
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        // Siatka
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(1);
        for (int i = 0; i <= GRID_SIZE; i++) {
            gc.strokeLine(0, i * CELL_SIZE, CANVAS_SIZE, i * CELL_SIZE); // Linie poziome
            gc.strokeLine(i * CELL_SIZE, 0, i * CELL_SIZE, CANVAS_SIZE); // Linie pionowe
        }
    }

    // Rysowanie obiektów
    private void renderObjects(GraphicsContext gc) {
        // Przykładowy obiekt (np. ryba) przesuwa się o jeden krok na dzień
        gc.setFill(Color.RED);
        gc.fillOval((currentDay % GRID_SIZE) * CELL_SIZE + 5, 5 * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
    }

    public static void main(String[] args) {
        launch(args);
    }
}