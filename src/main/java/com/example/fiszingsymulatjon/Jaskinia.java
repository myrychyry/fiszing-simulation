
package com.example.fiszingsymulatjon;

public class Jaskinia extends Schronienie {
    private String rodzajKamienia;

    public Jaskinia(int x, int y, int pojemnosc, String rodzajKamienia) {
        super(x, y, pojemnosc);
        this.rodzajKamienia = rodzajKamienia;
    }

    public void zawalenie() {
        // Implementacja
    }

    // Gettery i settery
    public String getRodzajKamienia() { return rodzajKamienia; }
    public void setRodzajKamienia(String rodzajKamienia) { this.rodzajKamienia = rodzajKamienia; }
}